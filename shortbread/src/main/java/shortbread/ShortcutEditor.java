package shortbread;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.support.annotation.DrawableRes;

import java.util.Collections;

public class ShortcutEditor {

    private Context context;
    private ShortcutInfo.Builder shortcutInfoBuilder;
    private ShortcutManager shortcutManager;

    @TargetApi(25)
    ShortcutEditor(Context context, String id) {
        this.context = context;
        shortcutManager = context.getSystemService(ShortcutManager.class);
        for (final ShortcutInfo shortcutInfo : shortcutManager.getDynamicShortcuts()) {
            if (shortcutInfo.getId().equals(id)) {
                shortcutInfoBuilder = new ShortcutInfo.Builder(context, shortcutInfo.getId());
                break;
            }
        }
    }

    @TargetApi(25)
    public ShortcutEditor setShortLabel(String shortLabel) {
        shortcutInfoBuilder.setShortLabel(shortLabel);
        return this;
    }

    @TargetApi(25)
    public ShortcutEditor setIcon(Bitmap bitmap) {
        shortcutInfoBuilder.setIcon(Icon.createWithBitmap(bitmap));
        return this;
    }

    @TargetApi(25)
    public ShortcutEditor setIcon(@DrawableRes int drawable) {
        shortcutInfoBuilder.setIcon(Icon.createWithResource(context, drawable));
        return this;
    }

    @TargetApi(25)
    public void publish() {
        final ShortcutInfo shortcutInfo = shortcutInfoBuilder.build();
        shortcutManager.updateShortcuts(Collections.singletonList(shortcutInfo));
    }
}
