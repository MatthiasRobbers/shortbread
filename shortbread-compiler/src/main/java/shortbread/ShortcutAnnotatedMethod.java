package shortbread;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

class ShortcutAnnotatedMethod extends ShortcutAnnotatedElement<ExecutableElement> {

    ShortcutAnnotatedMethod(final ExecutableElement element) {
        super(element);
    }

    @Override
    String getClassName() {
        return ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
    }

    @Override
    TypeElement getActivity() {
        return (TypeElement) element.getEnclosingElement();
    }

    String getMethodName() {
        return element.getSimpleName().toString();
    }
}
