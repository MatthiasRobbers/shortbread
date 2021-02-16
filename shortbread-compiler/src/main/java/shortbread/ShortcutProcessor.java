package shortbread;

import androidx.annotation.Nullable;

import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;

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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import static java.util.Objects.requireNonNull;
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

        for (final Element element : roundEnvironment.getElementsAnnotatedWith(Shortcut.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                final TypeElement typeElement = (TypeElement) element;
                if (!isSubtypeOfActivity(typeElement.asType())) {
                    error(element, "Only activities can be annotated with @%s", Shortcut.class.getSimpleName());
                    return true;
                }

                annotatedElements.add(new ShortcutAnnotatedClass(typeElement, getShortcutData(element)));
            } else if (element.getKind() == ElementKind.METHOD) {
                final ExecutableElement executableElement = (ExecutableElement) element;

                final Element enclosingElement = executableElement.getEnclosingElement();
                if (!isSubtypeOfActivity(enclosingElement.asType())) {
                    error(element, "Methods annotated with @%s must be part of activities", Shortcut.class.getSimpleName());
                    return true;
                }

                if (!executableElement.getModifiers().contains(Modifier.PUBLIC)) {
                    error(element, "Methods annotated with @%s must be public", Shortcut.class.getSimpleName());
                    return true;
                }

                if (executableElement.getParameters().size() > 0) {
                    error(element, "Methods annotated with @%s can't have parameters", Shortcut.class.getSimpleName());
                    return true;
                }

                annotatedElements.add(new ShortcutAnnotatedMethod(executableElement, getShortcutData(element)));
            } else {
                error(element, "Only classes and methods can be annotated with @", Shortcut.class.getSimpleName());
                return true;
            }
        }

        new CodeGenerator(processingEnv.getFiler(), annotatedElements).generate();
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private boolean isSubtypeOfActivity(TypeMirror typeMirror) {
        if ("android.app.Activity".equals(typeMirror.toString())) {
            return true;
        }

        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }

        DeclaredType declaredType = (DeclaredType) typeMirror;
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }

        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        return isSubtypeOfActivity(superType);
    }

    private ShortcutData getShortcutData(Element element) {
        Shortcut shortcut = element.getAnnotation(Shortcut.class);
        final Map<Integer, Id> resourceIds = new LinkedHashMap<>(elementToIds(element));
        return new ShortcutData(shortcut, resourceIds);
    }

    private Map<Integer, Id> elementToIds(Element element) {
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();
        JCTree tree = (JCTree) trees.getTree(element, getMirror(element));
        if (tree != null) { // tree can be null if the references are compiled types and not source
            rScanner.reset();
            tree.accept(rScanner);
            resourceIds = rScanner.resourceIds;
        }

        return resourceIds;
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

    private static class RScanner extends TreeScanner {
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();

        @Override
        public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
            Symbol symbol = jcFieldAccess.sym;
            Id id = parseId(symbol);
            if (id != null) {
                resourceIds.put(id.value, id);
            }
        }

        @Nullable
        private Id parseId(Symbol symbol) {
            Id id = null;
            if (symbol.getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
                try {
                    int value = (Integer) requireNonNull(((Symbol.VarSymbol) symbol).getConstantValue());
                    id = new Id(value, symbol);
                } catch (Exception ignored) {
                }
            }
            return id;
        }

        void reset() {
            resourceIds.clear();
        }
    }
}
