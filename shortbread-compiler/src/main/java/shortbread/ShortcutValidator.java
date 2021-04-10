package shortbread;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

class ShortcutValidator {

    private Element element;
    private Shortcut shortcut;
    String error;

    boolean validate(Element element) {
        this.element = element;
        shortcut = element.getAnnotation(Shortcut.class);
        error = null;

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

        error = "Only activities can be annotated with @" + Shortcut.class.getSimpleName();
        return false;
    }

    private boolean methodIsInActivity() {
        final Element enclosingElement = element.getEnclosingElement();
        if (isSubtypeOfActivity(enclosingElement.asType())) {
            return true;
        }

        error = "Methods annotated with @" + Shortcut.class.getSimpleName() + " must be part of activities";
        return false;
    }

    private boolean methodIsAbstract() {
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            error = "Methods annotated with @" + Shortcut.class.getSimpleName() + " must not be abstract";
            return true;
        }

        return false;
    }

    private boolean methodIsPrivate() {
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            error = "Methods annotated with @" + Shortcut.class.getSimpleName() + " must not be private";
            return true;
        }

        return false;
    }

    private boolean methodHasParameters() {
        final ExecutableElement executableElement = (ExecutableElement) element;
        if (executableElement.getParameters().size() > 0) {
            error = "Methods annotated with @" + Shortcut.class.getSimpleName() + " can't have parameters";
            return true;
        }

        return false;
    }

    private boolean noMultipleShortLabels() {
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

        error = "Only one of shortLabelRes, shortLabelResName and shortLabel can be set";
        return false;
    }

    private boolean noMultipleLongLabels() {
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

        error = "Only one of longLabelRes, longLabelResName and longLabel can be set";
        return false;
    }

    private boolean noMultipleDisabledMessages() {
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

        error = "Only one of disabledMessageRes, disabledMessageResName and disabledMessage can be set";
        return false;
    }

    private boolean noMultipleIcons() {
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

        error = "Only one of icon and iconResName can be set";
        return false;
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
