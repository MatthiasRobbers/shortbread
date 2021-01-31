package shortbread;

import javax.lang.model.element.TypeElement;

class ShortcutAnnotatedClass extends ShortcutAnnotatedElement<TypeElement> {

    ShortcutAnnotatedClass(final TypeElement element, final Shortcut shortcut) {
        super(element, shortcut);
    }

    @Override
    String getClassName() {
        return element.getQualifiedName().toString();
    }

    @Override
    TypeElement getActivity() {
        return element;
    }
}
