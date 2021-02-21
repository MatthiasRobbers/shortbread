package shortbread;

import com.squareup.javapoet.CodeBlock;

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

    public CodeBlock shortLabelRes() {
        if (shortcut.shortLabelRes() != 0 && resourceIds.containsKey(shortcut.shortLabelRes())) {
            return resourceIds.get(shortcut.shortLabelRes()).code;
        } else {
            return null;
        }
    }

    public String shortLabelResName() {
        return shortcut.shortLabelResName();
    }

    public String shortLabel() {
        return shortcut.shortLabel();
    }

    public CodeBlock longLabelRes() {
        if (shortcut.longLabelRes() != 0 && resourceIds.containsKey(shortcut.longLabelRes())) {
            return resourceIds.get(shortcut.longLabelRes()).code;
        } else {
            return null;
        }
    }

    public String longLabelResName() {
        return shortcut.longLabelResName();
    }

    public String longLabel() {
        return shortcut.longLabel();
    }

    public CodeBlock icon() {
        if (shortcut.icon() != 0 && resourceIds.containsKey(shortcut.icon())) {
            return resourceIds.get(shortcut.icon()).code;
        } else {
            return null;
        }
    }

    public String iconResName() {
        return shortcut.iconResName();
    }

    public CodeBlock disabledMessageRes() {
        if (shortcut.disabledMessageRes() != 0
                && resourceIds.containsKey(shortcut.disabledMessageRes())) {
            return resourceIds.get(shortcut.disabledMessageRes()).code;
        } else {
            return null;
        }
    }

    public String disabledMessageResName() {
        return shortcut.disabledMessageResName();
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
