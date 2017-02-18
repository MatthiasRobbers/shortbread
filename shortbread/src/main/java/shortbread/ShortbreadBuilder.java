package shortbread;

import android.content.Context;
import android.content.pm.ShortcutInfo;

import java.util.ArrayList;
import java.util.List;

public class ShortbreadBuilder {

    private Context context;
    private List<ShortcutInfo> allShortcuts = new ArrayList<>();
    private boolean[] shortcutStatus;

    ShortbreadBuilder(Context context, List<ShortcutInfo> allShortcuts) {

        this.allShortcuts = allShortcuts;
        this.shortcutStatus = new boolean[allShortcuts.size()];
        // enable all shortcuts by default
        for (int i = 0; i < shortcutStatus.length; i++) {
            shortcutStatus[i] = true;
        }

        this.context = context;
    }

    /**
     * Disable all available app shortcuts.
     *
     * @return shortbread builder.
     */
    public ShortbreadBuilder disableAll() {
        for (int i = 0; i < shortcutStatus.length; i++) {
            shortcutStatus[i] = false;
        }
        return this;
    }

    /**
     * Change the status for the app shortcut with the given {@code id}.
     *
     * @param id      the app shortcut id
     * @param enabled true, if the shortcut should be enabled, otherwise false.
     * @return shortbread builder
     */
    public ShortbreadBuilder setEnabled(int id, boolean enabled) {
        shortcutStatus[getIndex(id)] = enabled;
        return this;
    }

    public void build() {
        Creator.create(context, allShortcuts, shortcutStatus);
    }

    private int getIndex(int id) {
        for (int i = 0; i < allShortcuts.size(); i++) {
            if (Integer.valueOf(allShortcuts.get(i).getId()) == id) {
                return i;
            }
        }
        return -1;
    }
}
