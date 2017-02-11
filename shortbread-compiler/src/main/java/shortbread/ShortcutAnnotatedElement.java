package shortbread;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

abstract class ShortcutAnnotatedElement<T extends Element> {

    T element;

    Shortcut shortcut;

    ShortcutAnnotatedElement(final T element) {
        this.element = element;
        this.shortcut = element.getAnnotation(Shortcut.class);
    }

    Shortcut getShortcut() {
        return shortcut;
    }

    abstract String getClassName();

    abstract TypeElement getActivity();

    T getElement() {
        return element;
    }
}
