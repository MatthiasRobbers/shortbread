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

public final class TwoMethodShortcutsActivity_Shortcuts implements Shortcuts, MethodShortcuts<TwoMethodShortcutsActivity> {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
            add(new ShortcutInfo.Builder(context, "ID")
                    .setShortLabel(ShortcutUtils.getActivityLabel(context, TwoMethodShortcutsActivity.class))
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(TwoMethodShortcutsActivity.class)
                            .addNextIntent(new Intent(context, TwoMethodShortcutsActivity.class)
                                    .setAction(Intent.ACTION_VIEW)
                                    .putExtra("shortbread_method", "shortcutMethod1"))
                            .getIntents())
                    .setRank(0)
                    .build());
            add(new ShortcutInfo.Builder(context, "ID_2")
                    .setShortLabel(ShortcutUtils.getActivityLabel(context, TwoMethodShortcutsActivity.class))
                    .setIntents(TaskStackBuilder.create(context)
                            .addParentStack(TwoMethodShortcutsActivity.class)
                            .addNextIntent(new Intent(context, TwoMethodShortcutsActivity.class)
                                    .setAction(Intent.ACTION_VIEW)
                                    .putExtra("shortbread_method", "shortcutMethod2"))
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
    public Class<TwoMethodShortcutsActivity> getActivityClass() {
        return TwoMethodShortcutsActivity.class;
    }

    @Override
    public void callMethodShortcut(Activity activity, String methodName) {
        if ("shortcutMethod1".equals(methodName)) {
            ((TwoMethodShortcutsActivity) activity).shortcutMethod1();
        }
        if ("shortcutMethod2".equals(methodName)) {
            ((TwoMethodShortcutsActivity) activity).shortcutMethod2();
        }
    }
}