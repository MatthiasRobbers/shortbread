package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint({
        "NewApi",
        "ResourceType"
})
public final class ShortbreadGenerated {

    private static List<List<ShortcutInfo>> shortcuts;
    public static Activity activityThatWasPassedToCallMethodShortcut;

    public static List<List<ShortcutInfo>> createShortcuts(Context context) {
        if (shortcuts == null) {
            List<ShortcutInfo> enabledShortcuts = new ArrayList<>();
            List<ShortcutInfo> disabledShortcuts = new ArrayList<>();
            enabledShortcuts.add(new ShortcutInfo.Builder(context, "ID")
                    .setShortLabel("Short label")
                    .setIntents(TaskStackBuilder.create(context)
                            .addNextIntent(new Intent(Intent.ACTION_VIEW))
                            .getIntents())
                    .setRank(0)
                    .build());

            shortcuts = Arrays.asList(enabledShortcuts, disabledShortcuts);
        }

        return shortcuts;
    }

    public static void callMethodShortcut(Activity activity) {
        activityThatWasPassedToCallMethodShortcut = activity;
    }
}

