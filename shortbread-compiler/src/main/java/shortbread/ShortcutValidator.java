package shortbread;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

class ShortcutValidator {

    private final Messager messager;
    private Element element;

    ShortcutValidator(Messager messager) {
        this.messager = messager;
    }

    boolean validate(Element element) {
        this.element = element;

        if (element.getKind() == ElementKind.CLASS) {
            if (!classIsActivity()) {
                return false;
            }
        } else if (element.getKind() == ElementKind.METHOD) {
            if (!methodIsInActivity()
                    || methodIsAbstract()
                    || methodIsPrivate()
                    || methodHasParameters()) {
                return false;
            }
        }

        return noMultipleShortLabels()
                && noMultipleLongLabels()
                && noMultipleDisabledMessages()
                && noMultipleIcons();
    }

    private boolean classIsActivity() {
        final TypeElement typeElement = (TypeElement) element;
        if (isSubtypeOfActivity(typeElement.asType())) {
            return true;
        }

        error(element, "Only activities can be annotated with @%s", Shortcut.class.getSimpleName());
        return false;
    }

    private boolean methodIsInActivity() {
        final Element enclosingElement = element.getEnclosingElement();
        if (isSubtypeOfActivity(enclosingElement.asType())) {
            return true;
        }

        error(element, "Methods annotated with @%s must be part of activities", Shortcut.class.getSimpleName());
        return false;
    }

    private boolean methodIsAbstract() {
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            error(element, "Methods annotated with @%s must not be abstract", Shortcut.class.getSimpleName());
            return true;
        }

        return false;
    }

    private boolean methodIsPrivate() {
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            error(element, "Methods annotated with @%s must not be private", Shortcut.class.getSimpleName());
            return true;
        }

        return false;
    }

    private boolean methodHasParameters() {
        final ExecutableElement executableElement = (ExecutableElement) element;
        if (executableElement.getParameters().size() > 0) {
            error(element, "Methods annotated with @%s can't have parameters", Shortcut.class.getSimpleName());
            return true;
        }

        return false;
    }

    private boolean noMultipleShortLabels() {
        Shortcut shortcut = element.getAnnotation(Shortcut.class);

        int counter = 0;
        if (shortcut.shortLabelRes() != 0) {
            counter++;
        }
        if (!"".equals(shortcut.shortLabelResName())) {
            counter++;
        }
        if (!"".equals(shortcut.shortLabel())) {
            counter++;
        }
        if (counter < 2) {
            return true;
        }

        error(element, "Only one of shortLabelRes, shortLabelResName and shortLabel can be set");
        return false;
    }

    private boolean noMultipleLongLabels() {
        Shortcut shortcut = element.getAnnotation(Shortcut.class);

        int counter = 0;
        if (shortcut.longLabelRes() != 0) {
            counter++;
        }
        if (!"".equals(shortcut.longLabelResName())) {
            counter++;
        }
        if (!"".equals(shortcut.longLabel())) {
            counter++;
        }
        if (counter < 2) {
            return true;
        }

        error(element, "Only one of longLabelRes, longLabelResName and longLabel can be set");
        return false;
    }

    private boolean noMultipleDisabledMessages() {
        Shortcut shortcut = element.getAnnotation(Shortcut.class);

        int counter = 0;
        if (shortcut.disabledMessageRes() != 0) {
            counter++;
        }
        if (!"".equals(shortcut.disabledMessageResName())) {
            counter++;
        }
        if (!"".equals(shortcut.disabledMessage())) {
            counter++;
        }
        if (counter < 2) {
            return true;
        }

        error(element, "Only one of disabledMessageRes, disabledMessageResName and disabledMessage can be set");
        return false;
    }

    private boolean noMultipleIcons() {
        Shortcut shortcut = element.getAnnotation(Shortcut.class);

        int counter = 0;
        if (shortcut.icon() != 0) {
            counter++;
        }
        if (!"".equals(shortcut.iconResName())) {
            counter++;
        }
        if (counter < 2) {
            return true;
        }

        error(element, "Only one of icon and iconResName can be set");
        return false;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }

        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
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
}
