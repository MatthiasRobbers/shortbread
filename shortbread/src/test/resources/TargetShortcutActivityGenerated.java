package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import com.example.EmptyActivity1;
import com.example.TargetShortcutActivity;
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
                .setShortLabel(ShortcutUtils.getActivityLabel(context, TargetShortcutActivity.class))
                .setActivity(new ComponentName(context, EmptyActivity1.class))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(TargetShortcutActivity.class)
                        .addNextIntent(new Intent(context, TargetShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
    }
}
