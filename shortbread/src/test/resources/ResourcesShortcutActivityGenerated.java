package shortbread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import com.example.ResourcesShortcutActivity;
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
                .setShortLabel(context.getString(34))
                .setLongLabel(context.getString(56))
                .setIcon(Icon.createWithResource(context, 12))
                .setDisabledMessage(context.getString(78))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(ResourcesShortcutActivity.class)
                        .addNextIntent(new Intent(context, ResourcesShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }

    public static void callMethodShortcut(Activity activity) {
    }
}
