package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import com.example.MethodShortcutActivity;
import com.example.MethodShortcutActivity2;
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
                .setShortLabel(ShortcutUtils.getActivityLabel(context, MethodShortcutActivity.class))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(MethodShortcutActivity.class)
                        .addNextIntent(new Intent(context, MethodShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW)
                                .putExtra("shortbread_method", "shortcutMethod"))
                        .getIntents())
                .setRank(0)
                .build());
        enabledShortcuts.add(new ShortcutInfo.Builder(context, "ID")
                .setShortLabel(ShortcutUtils.getActivityLabel(context, MethodShortcutActivity2.class))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(MethodShortcutActivity2.class)
                        .addNextIntent(new Intent(context, MethodShortcutActivity2.class)
                                .setAction(Intent.ACTION_VIEW)
                                .putExtra("shortbread_method", "shortcutMethod"))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
        if (activity instanceof MethodShortcutActivity) {
            if (activity.getIntent().getStringExtra("shortbread_method").equals("shortcutMethod")) {
                ((MethodShortcutActivity) activity).shortcutMethod();
            }
        }
        if (activity instanceof MethodShortcutActivity2) {
            if (activity.getIntent().getStringExtra("shortbread_method").equals("shortcutMethod")) {
                ((MethodShortcutActivity2) activity).shortcutMethod();
            }
        }
    }
}
