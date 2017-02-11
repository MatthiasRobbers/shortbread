package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import com.example.BackStackShortcutActivity;
import com.example.EmptyActivity1;
import com.example.EmptyActivity2;
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
                .setShortLabel("SHORT_LABEL")
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(BackStackShortcutActivity.class)
                        .addNextIntent(new Intent(Intent.ACTION_VIEW).setClass(context, EmptyActivity1.class))
                        .addNextIntent(new Intent(Intent.ACTION_VIEW).setClass(context, EmptyActivity2.class))
                        .addNextIntent(new Intent(context, BackStackShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
    }
}
