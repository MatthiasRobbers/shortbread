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

public final class ResourcesShortcutActivity_Shortcuts implements Shortcuts {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
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
        }};
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return Collections.emptyList();
    }
}
