package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import com.example.TwoMethodShortcutsActivity;
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
                .setShortLabel(ShortcutUtils.getActivityLabel(context, TwoMethodShortcutsActivity.class))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(TwoMethodShortcutsActivity.class)
                        .addNextIntent(new Intent(context, TwoMethodShortcutsActivity.class)
                                .setAction(Intent.ACTION_VIEW)
                                .putExtra("shortbread_method", "shortcutMethod1"))
                        .getIntents())
                .setRank(0)
                .build());
        enabledShortcuts.add(new ShortcutInfo.Builder(context, "ID_2")
                .setShortLabel(ShortcutUtils.getActivityLabel(context, TwoMethodShortcutsActivity.class))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(TwoMethodShortcutsActivity.class)
                        .addNextIntent(new Intent(context, TwoMethodShortcutsActivity.class)
                                .setAction(Intent.ACTION_VIEW)
                                .putExtra("shortbread_method", "shortcutMethod2"))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
        if (activity instanceof TwoMethodShortcutsActivity) {
            if (activity.getIntent().getStringExtra("shortbread_method").equals("shortcutMethod1")) {
                ((TwoMethodShortcutsActivity) activity).shortcutMethod1();
            }
            if (activity.getIntent().getStringExtra("shortbread_method").equals("shortcutMethod2")) {
                ((TwoMethodShortcutsActivity) activity).shortcutMethod2();
            }
        }
    }
}