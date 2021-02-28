package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import com.example.AdvancedShortcutActivity;
import com.example.R;
import com.example.SimpleShortcutActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint({
        "NewApi",
        "ResourceType"
})
public final class ShortbreadGenerated {
    public static List<List<ShortcutInfo>> createShortcuts(Context context) {
        List<ShortcutInfo> enabledShortcuts = new ArrayList<>();
        List<ShortcutInfo> disabledShortcuts = new ArrayList<>();
        enabledShortcuts.add(new ShortcutInfo.Builder(context, "ID")
                .setShortLabel(ShortcutUtils.getActivityLabel(context, SimpleShortcutActivity.class))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(SimpleShortcutActivity.class)
                        .addNextIntent(new Intent(context, SimpleShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW))
                        .getIntents())
                .setRank(0)
                .build());
        enabledShortcuts.add(new ShortcutInfo.Builder(context, "ID_2")
                .setShortLabel("SHORT_LABEL")
                .setLongLabel("LONG_LABEL")
                .setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut))
                .setDisabledMessage("DISABLED_MESSAGE")
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(AdvancedShortcutActivity.class)
                        .addNextIntent(new Intent(context, AdvancedShortcutActivity.class)
                                .setAction("ACTION"))
                        .getIntents())
                .setRank(1)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
    }
}
