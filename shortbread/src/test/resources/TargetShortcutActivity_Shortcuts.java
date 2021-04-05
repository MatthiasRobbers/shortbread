// Generated code from Shortbread. Do not modify!
package com.example;

import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import shortbread.internal.ShortcutUtils;
import shortbread.internal.Shortcuts;

public final class TargetShortcutActivity_Shortcuts implements Shortcuts {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
                    .setShortLabel(ShortcutUtils.getActivityLabel(context, TargetShortcutActivity.class))
                    .setActivity(new ComponentName(context, EmptyActivity1.class))
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(TargetShortcutActivity.class)
                            .addNextIntent(new Intent(context, TargetShortcutActivity.class)
                                    .setAction(Intent.ACTION_VIEW))
                            .getIntents())
                    .setRank(0)
                    .build());
        }};
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return Collections.emptyList();
    }
}
