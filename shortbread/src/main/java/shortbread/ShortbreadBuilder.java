package shortbread;

import android.content.Context;
import android.content.pm.ShortcutInfo;

import java.util.ArrayList;
import java.util.List;

public class ShortbreadBuilder {

    Context context;
    List<ShortcutInfo> allShortcuts = new ArrayList<>();
    boolean[] shortcutStatus;

    ShortbreadBuilder(Context context, List<ShortcutInfo> allShortcuts, boolean[] shortcutStatus) {

        this.allShortcuts = allShortcuts;
        this.shortcutStatus = shortcutStatus;
        this.context = context;
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

    /**
     * Build the shortcuts
     */
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
