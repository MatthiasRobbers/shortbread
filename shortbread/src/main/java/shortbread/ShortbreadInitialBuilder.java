package shortbread;

import android.content.Context;
import android.content.pm.ShortcutInfo;

import java.util.List;

public class ShortbreadInitialBuilder extends ShortbreadBuilder {

    ShortbreadInitialBuilder(Context context, List<ShortcutInfo> allShortcuts, boolean[] shortcutStatus) {
        super(context, allShortcuts, shortcutStatus);
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
        return new ShortbreadBuilder(context, allShortcuts, shortcutStatus);
    }


    @Override
    public ShortbreadBuilder setEnabled(int id, boolean enabled) {
        return super.setEnabled(id, enabled);
    }

    @Override
    public void build() {
        super.build();
    }
}
