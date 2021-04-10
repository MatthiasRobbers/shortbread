package shortbread;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private final ClassName context = ClassName.get("android.content", "Context");
    private final ClassName shortcutInfo = ClassName.get("android.content.pm", "ShortcutInfo");
    private final ClassName intent = ClassName.get("android.content", "Intent");
    private final ClassName icon = ClassName.get("android.graphics.drawable", "Icon");
    private final ClassName activity = ClassName.get("android.app", "Activity");
    private final ClassName componentName = ClassName.get("android.content", "ComponentName");
    private final TypeName listOfShortcutInfo = ParameterizedTypeName.get(ClassName.get(List.class), shortcutInfo);
    private final TypeName arrayListOfShortcutInfo = ParameterizedTypeName.get(ClassName.get(ArrayList.class), shortcutInfo);
    private final ClassName taskStackBuilder = ClassName.get("android.app", "TaskStackBuilder");
    private final ClassName shortcuts = ClassName.get("shortbread.internal", "Shortcuts");
    private final ClassName methodShortcuts = ClassName.get("shortbread.internal", "MethodShortcuts");
    private final ClassName shortcutUtils = ClassName.get("shortbread.internal", "ShortcutUtils");

    private TypeElement activityElement;
    private List<ShortcutAnnotatedElement<? extends Element>> annotatedElements;

    JavaFile generate(final String packageName,
                      final TypeElement activityElement,
                      final List<ShortcutAnnotatedElement<? extends Element>> annotatedElements) {
        this.activityElement = activityElement;
        this.annotatedElements = annotatedElements;

        TypeSpec.Builder shortcutsClassBuilder = TypeSpec.classBuilder(activityElement.getSimpleName() + "_Shortcuts")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(shortcuts)
                .addMethod(getShortcuts())
                .addMethod(getDisabledShortcutIds());

        if (containsMethodShortcuts()) {
            shortcutsClassBuilder
                    .addSuperinterface(ParameterizedTypeName.get(methodShortcuts, ClassName.get(activityElement)))
                    .addMethod(getActivityClass())
                    .addMethod(callMethodShortcut());
        }

        return JavaFile.builder(packageName, shortcutsClassBuilder.build())
                .addFileComment("Generated code from Shortbread. Do not modify!")
                .indent("    ")
                .build();
    }

    private MethodSpec getShortcuts() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getShortcuts")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(context, "context")
                .returns(listOfShortcutInfo);

        CodeBlock.Builder blockBuilder = CodeBlock.builder()
                .add("return new $T() {{\n", arrayListOfShortcutInfo)
                .indent();

        for (final ShortcutAnnotatedElement<? extends Element> annotatedElement : annotatedElements) {
            if (annotatedElement.getShortcutData().enabled) {
                blockBuilder
                        .add("add(")
                        .add(createShortcut(annotatedElement))
                        .addStatement(")");
            }
        }

        blockBuilder.unindent()
                .addStatement("}}");

        return methodBuilder
                .addCode(blockBuilder.build())
                .build();
    }

    private CodeBlock createShortcut(ShortcutAnnotatedElement<? extends Element> annotatedElement) {
        ShortcutData shortcutData = annotatedElement.getShortcutData();

        final CodeBlock.Builder blockBuilder = CodeBlock.builder()
                .add("new $T.Builder(context, $S)\n", shortcutInfo, shortcutData.id)
                .indent().indent();

        if (shortcutData.shortLabelRes != null) {
            blockBuilder.add(".setShortLabel(context.getString(")
                    .add(shortcutData.shortLabelRes.code)
                    .add("))\n");
        } else if (!"".equals(shortcutData.shortLabelResName)) {
            blockBuilder.add(".setShortLabel(context.getString(\n")
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"string\", context.getPackageName())))\n",
                            shortcutData.shortLabelResName)
                    .unindent().unindent();
        } else if (!"".equals(shortcutData.shortLabel)) {
            blockBuilder.add(".setShortLabel($S)\n", shortcutData.shortLabel);
        } else {
            blockBuilder.add(".setShortLabel($T.getActivityLabel(context, $T.class))\n", shortcutUtils,
                    ClassName.bestGuess(annotatedElement.getClassName()));
        }

        if (shortcutData.longLabelRes != null) {
            blockBuilder.add(".setLongLabel(context.getString(")
                    .add(shortcutData.longLabelRes.code)
                    .add("))\n");
        } else if (!"".equals(shortcutData.longLabelResName)) {
            blockBuilder.add(".setLongLabel(context.getString(\n")
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"string\", context.getPackageName())))\n",
                            shortcutData.longLabelResName)
                    .unindent().unindent();
        } else if (!"".equals(shortcutData.longLabel)) {
            blockBuilder.add(".setLongLabel($S)\n", shortcutData.longLabel);
        }

        if (shortcutData.icon != null) {
            blockBuilder.add(".setIcon($T.createWithResource(context, ", icon)
                    .add(shortcutData.icon.code)
                    .add("))\n");
        } else if (!"".equals(shortcutData.iconResName)) {
            blockBuilder.add(".setIcon($T.createWithResource(context,\n", icon)
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"drawable\", context.getPackageName())))\n", shortcutData.iconResName)
                    .unindent().unindent();
        }

        if (shortcutData.disabledMessageRes != null) {
            blockBuilder.add(".setDisabledMessage(context.getString(")
                    .add(shortcutData.disabledMessageRes.code)
                    .add("))\n");
        } else if (!"".equals(shortcutData.disabledMessageResName)) {
            blockBuilder.add(".setDisabledMessage(context.getString(\n")
                    .indent().indent()
                    .add("context.getResources().getIdentifier($S, \"string\", context.getPackageName())))\n",
                            shortcutData.disabledMessageResName)
                    .unindent().unindent();
        } else if (!"".equals(shortcutData.disabledMessage)) {
            blockBuilder.add(".setDisabledMessage($S)\n", shortcutData.disabledMessage);
        }

        try {
            annotatedElement.getElement().getAnnotation(Shortcut.class).activity();
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

        final ClassName activityClassName = ClassName.get(annotatedElement.getActivity());
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

        blockBuilder.add(".addNextIntent(new $T(context, $T.class)\n", intent, activityClassName)
                .indent().indent();
        if ("".equals(shortcutData.action)) {
            blockBuilder.add(".setAction($T.ACTION_VIEW)", intent);
        } else {
            blockBuilder.add(".setAction($S)", shortcutData.action);
        }
        if (annotatedElement instanceof ShortcutAnnotatedMethod) {
            blockBuilder.add("\n.putExtra($S, $S)", EXTRA_METHOD, ((ShortcutAnnotatedMethod) annotatedElement).getMethodName());
        }

        return blockBuilder
                .unindent().unindent()
                .add(")\n")
                .add(".getIntents())\n")
                .unindent().unindent()
                .add(".setRank($L)\n", shortcutData.rank)
                .add(".build()")
                .unindent().unindent()
                .build();
    }

    private MethodSpec getDisabledShortcutIds() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getDisabledShortcutIds")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(List.class, String.class));

        final List<String> disabledShortcutIds = new ArrayList<>();
        for (final ShortcutAnnotatedElement<? extends Element> annotatedElement : annotatedElements) {
            if (!annotatedElement.getShortcutData().enabled) {
                disabledShortcutIds.add(annotatedElement.shortcutData.id);
            }
        }

        if (disabledShortcutIds.isEmpty()) {
            methodBuilder.addStatement("return $T.emptyList()", Collections.class);
        } else {
            CodeBlock.Builder blockBuilder = CodeBlock.builder();
            blockBuilder.add("return new $T() {{\n", ParameterizedTypeName.get(ArrayList.class, String.class))
                    .indent();

            for (String disabledShortcutId : disabledShortcutIds) {
                blockBuilder.addStatement("add($S)", disabledShortcutId);
            }

            blockBuilder.unindent()
                    .addStatement("}}");
            methodBuilder.addCode(blockBuilder.build());
        }

        return methodBuilder.build();
    }

    private MethodSpec getActivityClass() {
        return MethodSpec.methodBuilder("getActivityClass")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), ClassName.get(activityElement)))
                .addStatement("return $T.class", ClassName.get(activityElement))
                .build();
    }

    private MethodSpec callMethodShortcut() {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("callMethodShortcut")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(activity, "activity")
                .addParameter(String.class, "methodName");

        for (ShortcutAnnotatedElement<? extends Element> annotatedElement : annotatedElements) {
            if (annotatedElement instanceof ShortcutAnnotatedMethod) {
                String methodName = ((ShortcutAnnotatedMethod) annotatedElement).getMethodName();
                methodBuilder.beginControlFlow("if ($S.equals(methodName))", methodName)
                        .addStatement("(($T) activity).$L()", ClassName.get(activityElement), methodName)
                        .endControlFlow();
            }
        }

        return methodBuilder.build();
    }

    private boolean containsMethodShortcuts() {
        for (ShortcutAnnotatedElement<? extends Element> annotatedElement : annotatedElements) {
            if (annotatedElement instanceof ShortcutAnnotatedMethod) {
                return true;
            }
        }

        return false;
    }
}
