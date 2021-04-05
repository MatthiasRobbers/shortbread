// Generated code from Shortbread. Do not modify!
package com.example;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import shortbread.internal.Shortcuts;

public final class AdvancedShortcutActivity_Shortcuts implements Shortcuts {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID_2")
                    .setShortLabel("SHORT_LABEL")
                    .setLongLabel("LONG_LABEL")
                    .setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut))
                    .setDisabledMessage("DISABLED_MESSAGE")
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(AdvancedShortcutActivity.class)
                            .addNextIntent(new Intent(context, AdvancedShortcutActivity.class)
                                    .setAction("ACTION"))
                            .getIntents())
                    .setRank(1)
                    .build());
        }};
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return Collections.emptyList();
    }
}