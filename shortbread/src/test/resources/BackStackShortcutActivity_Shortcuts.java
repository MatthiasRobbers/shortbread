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
import shortbread.internal.Shortcuts;

public final class BackStackShortcutActivity_Shortcuts implements Shortcuts {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
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
        }};
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return Collections.emptyList();
    }
}