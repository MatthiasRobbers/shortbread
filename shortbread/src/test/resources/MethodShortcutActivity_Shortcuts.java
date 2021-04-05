// Generated code from Shortbread. Do not modify!
package com.example;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import shortbread.internal.MethodShortcuts;
import shortbread.internal.ShortcutUtils;
import shortbread.internal.Shortcuts;

public final class MethodShortcutActivity_Shortcuts implements Shortcuts, MethodShortcuts<MethodShortcutActivity> {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
                    .setShortLabel(ShortcutUtils.getActivityLabel(context, MethodShortcutActivity.class))
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(MethodShortcutActivity.class)
                            .addNextIntent(new Intent(context, MethodShortcutActivity.class)
                                    .setAction(Intent.ACTION_VIEW)
                                    .putExtra("shortbread_method", "shortcutMethod"))
                            .getIntents())
                    .setRank(0)
                    .build());
        }};
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return Collections.emptyList();
    }

    @Override
    public Class<MethodShortcutActivity> getActivityClass() {
        return MethodShortcutActivity.class;
    }

    @Override
    public void callMethodShortcut(Activity activity, String methodName) {
        if ("shortcutMethod".equals(methodName)) {
            ((MethodShortcutActivity) activity).shortcutMethod();
        }
    }
}