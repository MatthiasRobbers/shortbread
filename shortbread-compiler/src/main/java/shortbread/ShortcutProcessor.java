package shortbread;

import androidx.annotation.Nullable;

import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;
import com.sun.tools.javac.tree.JCTree;

import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static net.ltgt.gradle.incap.IncrementalAnnotationProcessorType.AGGREGATING;

@AutoService(Processor.class)
@IncrementalAnnotationProcessor(AGGREGATING)
@SupportedAnnotationTypes({"shortbread.Shortcut"})
public class ShortcutProcessor extends AbstractProcessor {

    private boolean processed;
    private Trees trees;
    private final RScanner rScanner = new RScanner();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        try {
            trees = Trees.instance(processingEnv);
        } catch (IllegalArgumentException ignored) {
            try {
                // Get original ProcessingEnvironment from Gradle-wrapped one or KAPT-wrapped one.
                for (Field field : processingEnv.getClass().getDeclaredFields()) {
                    if (field.getName().equals("delegate") || field.getName().equals("processingEnv")) {
                        field.setAccessible(true);
                        ProcessingEnvironment javacEnv = (ProcessingEnvironment) field.get(processingEnv);
                        trees = Trees.instance(javacEnv);
                        break;
                    }
                }
            } catch (Throwable ignored2) {
            }
        }
    }

    @Override
    public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
        if (processed) {
            return true;
        } else {
            processed = true;
        }

        List<ShortcutAnnotatedElement<? extends Element>> annotatedElements = new ArrayList<>();
        ShortcutValidator validator = new ShortcutValidator(processingEnv.getMessager());

        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Shortcut.class)) {
            if (!validator.validate(element)) {
                return true;
            }

            if (element.getKind() == ElementKind.CLASS) {
                final TypeElement typeElement = (TypeElement) element;
                annotatedElements.add(new ShortcutAnnotatedClass(typeElement, getShortcutWithIds(element)));
            } else if (element.getKind() == ElementKind.METHOD) {
                final ExecutableElement executableElement = (ExecutableElement) element;
                annotatedElements.add(new ShortcutAnnotatedMethod(executableElement, getShortcutWithIds(element)));
            }
        }

        new CodeGenerator(processingEnv.getFiler(), annotatedElements).generate();
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private ShortcutData getShortcutWithIds(Element element) {
        Shortcut shortcut = element.getAnnotation(Shortcut.class);
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();
        JCTree tree = (JCTree) trees.getTree(element, getMirror(element));
        if (tree != null) { // tree can be null if the references are compiled types and not source
            rScanner.reset();
            tree.accept(rScanner);
            resourceIds = rScanner.resourceIds;
        }

        return new ShortcutData(shortcut, resourceIds);
    }

    private static @Nullable
    AnnotationMirror getMirror(Element element) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(Shortcut.class.getCanonicalName())) {
                return annotationMirror;
            }
        }
        return null;
    }
}
