package shortbread;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

abstract class ShortcutAnnotatedElement<T extends Element> {

    T element;

    ShortcutData shortcutData;

    ShortcutAnnotatedElement(final T element, final ShortcutData shortcutData) {
        this.element = element;
        this.shortcutData = shortcutData;
    }

    ShortcutData getShortcutData() {
        return shortcutData;
    }

    abstract String getClassName();

    abstract TypeElement getActivity();

    T getElement() {
        return element;
    }
}
