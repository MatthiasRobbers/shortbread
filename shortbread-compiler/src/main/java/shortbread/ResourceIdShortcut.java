package shortbread;

import java.lang.annotation.Annotation;
import java.util.Map;

@SuppressWarnings("ClassExplicitlyAnnotation")
public class ResourceIdShortcut implements Shortcut {

    private final Shortcut shortcut;
    private final Map<Integer, Id> resourceIds;

    public ResourceIdShortcut(Shortcut shortcut, Map<Integer, Id> resourceIds) {
        this.shortcut = shortcut;
        this.resourceIds = resourceIds;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Shortcut.class;
    }

    @Override
    public String id() {
        return shortcut.id();
    }

    @Override
    public int shortLabelRes() {
        if (shortcut.shortLabelRes() != 0
                && resourceIds.containsKey(shortcut.shortLabelRes())) {
            return resourceIds.get(shortcut.shortLabelRes()).value;
        }

        return shortcut.shortLabelRes();
    }

    @Override
    public String shortLabelResName() {
        return shortcut.shortLabelResName();
    }

    @Override
    public String shortLabel() {
        return shortcut.shortLabel();
    }

    @Override
    public int longLabelRes() {
        if (shortcut.longLabelRes() != 0
                && resourceIds.containsKey(shortcut.longLabelRes())) {
            return resourceIds.get(shortcut.longLabelRes()).value;
        }

        return shortcut.longLabelRes();
    }

    @Override
    public String longLabelResName() {
        return shortcut.longLabelResName();
    }

    @Override
    public String longLabel() {
        return shortcut.longLabel();
    }

    @Override
    public int icon() {
        if (shortcut.icon() != 0
                && resourceIds.containsKey(shortcut.icon())) {
            return resourceIds.get(shortcut.icon()).value;
        }

        return shortcut.icon();
    }

    @Override
    public String iconResName() {
        return shortcut.iconResName();
    }

    @Override
    public int disabledMessageRes() {
        if (shortcut.disabledMessageRes() != 0
                && resourceIds.containsKey(shortcut.disabledMessageRes())) {
            return resourceIds.get(shortcut.disabledMessageRes()).value;
        }

        return shortcut.disabledMessageRes();
    }

    @Override
    public String disabledMessageResName() {
        return shortcut.disabledMessageResName();
    }

    @Override
    public String disabledMessage() {
        return shortcut.disabledMessage();
    }

    @Override
    public int rank() {
        return shortcut.rank();
    }

    @Override
    public boolean enabled() {
        return shortcut.enabled();
    }

    @Override
    public Class[] backStack() {
        return shortcut.backStack();
    }

    @Override
    public Class activity() {
        return shortcut.activity();
    }

    @Override
    public String action() {
        return shortcut.action();
    }
}
