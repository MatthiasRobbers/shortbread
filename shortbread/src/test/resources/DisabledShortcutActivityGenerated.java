package shortbread;

import android.annotation.SuppressLint;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import com.example.DisabledShortcutActivity;
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
        disabledShortcuts.add(new ShortcutInfo.Builder(context, "ID")
                .setShortLabel(ShortcutUtils.getActivityLabel(context, DisabledShortcutActivity.class))
                .setDisabledMessage("DISABLED_MESSAGE")
                .setIntents(TaskStackBuilder.create(context)
                        .addParentStack(DisabledShortcutActivity.class)
                        .addNextIntent(new Intent(context, DisabledShortcutActivity.class)
                                .setAction(Intent.ACTION_VIEW))
                        .getIntents())
                .setRank(0)
                .build());
        return Arrays.asList(enabledShortcuts, disabledShortcuts);
    }
}
