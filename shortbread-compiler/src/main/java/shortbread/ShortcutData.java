package shortbread;

import java.util.Map;

class ShortcutData {

    String id;
    Id shortLabelRes;
    String shortLabelResName;
    String shortLabel;
    Id longLabelRes;
    String longLabelResName;
    String longLabel;
    Id icon;
    String iconResName;
    Id disabledMessageRes;
    String disabledMessageResName;
    String disabledMessage;
    int rank;
    boolean enabled;
    String action;

    ShortcutData(Shortcut shortcut, Map<Integer, Id> resourceIds) {
        id = shortcut.id();

        if (shortcut.shortLabelRes() != 0 && resourceIds.containsKey(shortcut.shortLabelRes())) {
            shortLabelRes = resourceIds.get(shortcut.shortLabelRes());
        }
        shortLabelResName = shortcut.shortLabelResName();
        shortLabel = shortcut.shortLabel();

        if (shortcut.longLabelRes() != 0 && resourceIds.containsKey(shortcut.longLabelRes())) {
            longLabelRes = resourceIds.get(shortcut.longLabelRes());
        }
        longLabelResName = shortcut.longLabelResName();
        longLabel = shortcut.longLabel();

        if (shortcut.icon() != 0 && resourceIds.containsKey(shortcut.icon())) {
            icon = resourceIds.get(shortcut.icon());
        }
        iconResName = shortcut.iconResName();

        if (shortcut.disabledMessageRes() != 0
                && resourceIds.containsKey(shortcut.disabledMessageRes())) {
            disabledMessageRes = resourceIds.get(shortcut.disabledMessageRes());
        }
        disabledMessageResName = shortcut.disabledMessageResName();
        disabledMessage = shortcut.disabledMessage();

        rank = shortcut.rank();

        enabled = shortcut.enabled();

        action = shortcut.action();
    }
}
