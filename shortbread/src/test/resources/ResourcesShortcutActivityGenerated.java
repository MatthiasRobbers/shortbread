package shortbread;

import android.annotation.SuppressLint;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import com.example.R;
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
                .setShortLabel(context.getString(R.string.short_label))
                .setLongLabel(context.getString(R.string.long_label))
                .setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut))
                .setDisabledMessage(context.getString(R.string.disabled_message))
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(ResourcesShortcutActivity.class)
                        .addNextIntent(new Intent(context, ResourcesShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }
}
