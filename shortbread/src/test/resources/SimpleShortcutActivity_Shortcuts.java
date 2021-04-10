// Generated code from Shortbread. Do not modify!
package com.example;

import android.app.TaskStackBuilder;
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

public final class SimpleShortcutActivity_Shortcuts implements Shortcuts {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
                    .setShortLabel(ShortcutUtils.getActivityLabel(context, SimpleShortcutActivity.class))
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(SimpleShortcutActivity.class)
                            .addNextIntent(new Intent(context, SimpleShortcutActivity.class)
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
