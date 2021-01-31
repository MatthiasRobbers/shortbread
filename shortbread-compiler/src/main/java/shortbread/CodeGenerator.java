package shortbread;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.NoType;

class CodeGenerator {

    private static final String EXTRA_METHOD = "shortbread_method";
    private final ClassName suppressLint = ClassName.get("android.annotation", "SuppressLint");
    private final ClassName context = ClassName.get("android.content", "Context");
    private final ClassName shortcutInfo = ClassName.get("android.content.pm", "ShortcutInfo");
    private final ClassName intent = ClassName.get("android.content", "Intent");
    private final ClassName icon = ClassName.get("android.graphics.drawable", "Icon");
    private final ClassName activity = ClassName.get("android.app", "Activity");
    private final ClassName componentName = ClassName.get("android.content", "ComponentName");
    private final ClassName list = ClassName.get("java.util", "List");
    private final TypeName listOfShortcutInfo = ParameterizedTypeName.get(list, shortcutInfo);
    private final TypeName listOfListOfShortcutInfo = ParameterizedTypeName.get(list, listOfShortcutInfo);
    private final ClassName taskStackBuilder = ClassName.get("android.app", "TaskStackBuilder");
    private final ClassName shortcutUtils = ClassName.get("shortbread", "ShortcutUtils");

    private final Filer filer;
    private final List<ShortcutAnnotatedElement<? extends Element>> annotatedElements;

    CodeGenerator(final Filer filer, final List<ShortcutAnnotatedElement<? extends Element>> annotatedElements) {
        this.filer = filer;
        this.annotatedElements = annotatedElements;
    }

    void generate() {
        TypeSpec shortbread = TypeSpec.classBuilder("ShortbreadGenerated")
                .addAnnotation(AnnotationSpec.builder(suppressLint)
                        .addMember("value", "$S", "NewApi")
                        .addMember("value", "$S", "ResourceType")
                        .build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(createShortcuts())
                .addMethod(callMethodShortcut())
                .build();

        JavaFile javaFile = JavaFile.builder("shortbread", shortbread)
                .indent("    ")
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MethodSpec createShortcuts() {
        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createShortcuts")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(listOfListOfShortcutInfo)
                .addParameter(context, "context")
                .addStatement("$T<$T> enabledShortcuts = new $T<>()", List.class, shortcutInfo, ArrayList.class)
                .addStatement("$T<$T> disabledShortcuts = new $T<>()", List.class, shortcutInfo, ArrayList.class);

        for (final ShortcutAnnotatedElement<? extends Element> annotatedElement : annotatedElements) {
            Shortcut shortcut = annotatedElement.getShortcut();
            methodBuilder.addCode("$L.add(", shortcut.enabled() ? "enabledShortcuts" : "disabledShortcuts");
            methodBuilder.addCode(createShortcut(annotatedElement));
            methodBuilder.addStatement(")");
        }

        return methodBuilder
                .addStatement("return $T.asList(enabledShortcuts, disabledShortcuts)", Arrays.class)
                .build();
    }

    private CodeBlock createShortcut(ShortcutAnnotatedElement<? extends Element> annotatedElement) {
        Shortcut shortcut = annotatedElement.getShortcut();

        final CodeBlock.Builder blockBuilder = CodeBlock.builder();

        blockBuilder.add("new $T.Builder(context, $S)\n", shortcutInfo, shortcut.id())
                .indent()
                .indent();

        if (!"".equals(shortcut.shortLabelResName())) {
            blockBuilder.add(".setShortLabel(context.getString(\n")
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"string\", context.getPackageName())))\n",
                            shortcut.shortLabelResName())
                    .unindent().unindent();
        } else if (!"".equals(shortcut.shortLabel())) {
            blockBuilder.add(".setShortLabel($S)\n", shortcut.shortLabel());
        } else if (shortcut.shortLabelRes() != 0) {
            blockBuilder.add(".setShortLabel(context.getString($L))\n", shortcut.shortLabelRes());
        } else {
            blockBuilder.add(".setShortLabel($T.getActivityLabel(context, $T.class))\n", shortcutUtils,
                    ClassName.bestGuess(annotatedElement.getClassName()));
        }

        if (!"".equals(shortcut.longLabelResName())) {
            blockBuilder.add(".setLongLabel(context.getString(\n")
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"string\", context.getPackageName())))\n",
                            shortcut.longLabelResName())
                    .unindent().unindent();
        } else if (!"".equals(shortcut.longLabel())) {
            blockBuilder.add(".setLongLabel($S)\n", shortcut.longLabel());
        } else if (shortcut.longLabelRes() != 0) {
            blockBuilder.add(".setLongLabel(context.getString($L))\n", shortcut.longLabelRes());
        }

        if (!"".equals(shortcut.iconResName())) {
            blockBuilder.add(".setIcon($T.createWithResource(context,\n", icon)
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"drawable\", context.getPackageName())))\n", shortcut.iconResName())
                    .unindent().unindent();
        } else if (shortcut.icon() != 0) {
            blockBuilder.add(".setIcon($T.createWithResource(context, $L))\n", icon, shortcut.icon());
        }

        if (!"".equals(shortcut.disabledMessageResName())) {
            blockBuilder.add(".setDisabledMessage(context.getString(\n")
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"string\", context.getPackageName())))\n",
                            shortcut.disabledMessageResName())
                    .unindent().unindent();
        } else if (!"".equals(shortcut.disabledMessage())) {
            blockBuilder.add(".setDisabledMessage($S)\n", shortcut.disabledMessage());
        } else if (shortcut.disabledMessageRes() != 0) {
            blockBuilder.add(".setDisabledMessage(context.getString($L))\n", shortcut.disabledMessageRes());
        }

        try {
            shortcut.activity();
        } catch (MirroredTypeException mte) {
            if (!(mte.getTypeMirror() instanceof NoType)) {
                DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
                String targetActivityClassName = classTypeElement.getQualifiedName().toString();
                blockBuilder.add(".setActivity(new $T(context, $T.class))\n", componentName,
                        ClassName.bestGuess(targetActivityClassName));
            }
        }

        blockBuilder.add(".setIntents(")
                .indent().indent()
                .add("$T.create(context)\n", taskStackBuilder);

        final ClassName activityClassName = ClassName.get((annotatedElement.getActivity()));
        blockBuilder.add(".addParentStack($T.class)\n", activityClassName);

        // because we can't just get an array of classes, we have to use AnnotationMirrors
        final List<? extends AnnotationMirror> annotationMirrors = annotatedElement.getElement().getAnnotationMirrors();
        for (final AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType().toString().equals(Shortcut.class.getName())) {
                for (Map.Entry<? extends Element, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
                    if ("backStack".equals(entry.getKey().getSimpleName().toString())) {
                        final String value = entry.getValue().getValue().toString();
                        if (!"".equals(value.trim())) {
                            for (final String backstackActivityClassName : value.split(",")) {
                                blockBuilder.add(".addNextIntent(new $T($T.ACTION_VIEW).setClass(context, $T.class))\n",
                                        intent, intent, ClassName.bestGuess(backstackActivityClassName.replace(".class", "")));
                            }
                        }
                    }
                }
            }
        }

        blockBuilder.add(".addNextIntent(new $T(context, $T.class)\n", intent, activityClassName);
        blockBuilder.indent().indent();
        if ("".equals(shortcut.action())) {
            blockBuilder.add(".setAction($T.ACTION_VIEW)", intent);
        } else {
            blockBuilder.add(".setAction($S)", shortcut.action());
        }
        if (annotatedElement instanceof ShortcutAnnotatedMethod) {
            blockBuilder.add("\n.putExtra($S, $S)", EXTRA_METHOD, ((ShortcutAnnotatedMethod) annotatedElement).getMethodName());
        }
        blockBuilder.unindent().unindent();

        return blockBuilder.add(")\n")
                .add(".getIntents())\n")
                .unindent().unindent()
                .add(".setRank($L)\n", shortcut.rank())
                .add(".build()")
                .unindent().unindent()
                .build();
    }

    private MethodSpec callMethodShortcut() {
        HashMap<String, List<String>> classMethodsMap = new HashMap<>();

        for (final ShortcutAnnotatedElement<? extends Element> annotatedElement : annotatedElements) {
            if (annotatedElement instanceof ShortcutAnnotatedMethod) {
                final ShortcutAnnotatedMethod annotatedMethod = (ShortcutAnnotatedMethod) annotatedElement;
                if (classMethodsMap.containsKey(annotatedMethod.getClassName())) {
                    classMethodsMap.get(annotatedElement.getClassName()).add(annotatedMethod.getMethodName());
                } else {
                    classMethodsMap.put(annotatedMethod.getClassName(), new ArrayList<String>() {{
                        add(annotatedMethod.getMethodName());
                    }});
                }
            }
        }

        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("callMethodShortcut")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(activity, "activity");

        for (final Map.Entry<String, List<String>> annotatedMethodName : classMethodsMap.entrySet()) {
            ClassName activityClassName = ClassName.bestGuess(annotatedMethodName.getKey());
            List<String> methodNames = annotatedMethodName.getValue();
            methodBuilder.beginControlFlow("if (activity instanceof $T)", activityClassName);
            for (final String methodName : methodNames) {
                methodBuilder.beginControlFlow("if ($S.equals(activity.getIntent().getStringExtra($S)))", methodName, EXTRA_METHOD);
                methodBuilder.addStatement("(($T) activity).$L()", activityClassName, methodName);
                methodBuilder.endControlFlow();
            }
            methodBuilder.endControlFlow();
        }

        return methodBuilder
                .build();
    }
}
