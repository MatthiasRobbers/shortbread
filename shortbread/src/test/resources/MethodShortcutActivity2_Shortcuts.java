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

public final class MethodShortcutActivity2_Shortcuts implements Shortcuts, MethodShortcuts<MethodShortcutActivity2> {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
                    .setShortLabel(ShortcutUtils.getActivityLabel(context, MethodShortcutActivity2.class))
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(MethodShortcutActivity2.class)
                            .addNextIntent(new Intent(context, MethodShortcutActivity2.class)
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
    public Class<MethodShortcutActivity2> getActivityClass() {
        return MethodShortcutActivity2.class;
    }

    @Override
    public void callMethodShortcut(Activity activity, String methodName) {
        if ("shortcutMethod".equals(methodName)) {
            ((MethodShortcutActivity2) activity).shortcutMethod();
        }
    }
}