package shortbread;

import java.util.Map;

class ShortcutData {

    private final Shortcut shortcut;
    private final Map<Integer, Id> resourceIds;

    public ShortcutData(Shortcut shortcut, Map<Integer, Id> resourceIds) {
        this.shortcut = shortcut;
        this.resourceIds = resourceIds;
    }

    public String id() {
        return shortcut.id();
    }

    public String shortLabelResName() {
        if (shortcut.shortLabelRes() != 0
                && resourceIds.containsKey(shortcut.shortLabelRes())) {
            return resourceIds.get(shortcut.shortLabelRes()).resourceName;
        } else {
            return shortcut.shortLabelResName();
        }
    }

    public String shortLabel() {
        return shortcut.shortLabel();
    }

    public String longLabelResName() {
        if (shortcut.longLabelRes() != 0
                && resourceIds.containsKey(shortcut.longLabelRes())) {
            return resourceIds.get(shortcut.longLabelRes()).resourceName;
        } else {
            return shortcut.longLabelResName();
        }
    }

    public String longLabel() {
        return shortcut.longLabel();
    }

    public String iconDrawableResName() {
        if (shortcut.icon() != 0
                && resourceIds.containsKey(shortcut.icon())
                && "drawable".equals(resourceIds.get(shortcut.icon()).type)) {
            return resourceIds.get(shortcut.icon()).resourceName;
        } else {
            return shortcut.iconDrawableResName();
        }
    }

    public String iconMipmapResName() {
        if (shortcut.icon() != 0
                && resourceIds.containsKey(shortcut.icon())
                && "mipmap".equals(resourceIds.get(shortcut.icon()).type)) {
            return resourceIds.get(shortcut.icon()).resourceName;
        } else {
            return shortcut.iconMipmapResName();
        }
    }

    public String disabledMessageResName() {
        if (shortcut.disabledMessageRes() != 0
                && resourceIds.containsKey(shortcut.disabledMessageRes())) {
            return resourceIds.get(shortcut.disabledMessageRes()).resourceName;
        } else {
            return shortcut.disabledMessageResName();
        }
    }

    public String disabledMessage() {
        return shortcut.disabledMessage();
    }

    public int rank() {
        return shortcut.rank();
    }

    public boolean enabled() {
        return shortcut.enabled();
    }

    public Class activity() {
        return shortcut.activity();
    }

    public String action() {
        return shortcut.action();
    }
}
