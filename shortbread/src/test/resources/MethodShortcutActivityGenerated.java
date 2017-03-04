package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import com.example.MethodShortcutActivity;
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
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
        if (activity instanceof MethodShortcutActivity) {
            if ("shortcutMethod".equals(activity.getIntent().getStringExtra("shortbread_method"))) {
                ((MethodShortcutActivity) activity).shortcutMethod();
            }
        }
    }
}
