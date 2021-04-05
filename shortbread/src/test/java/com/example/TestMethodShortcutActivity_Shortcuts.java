package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ShortcutInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shortbread.internal.MethodShortcuts;
import shortbread.internal.Shortcuts;

public final class TestMethodShortcutActivity_Shortcuts implements Shortcuts, MethodShortcuts<TestMethodShortcutActivity> {

    public static final List<ShortcutInfo> shortcutInfos = new ArrayList<>();
    public static Activity activityThatWasPassedToCallMethodShortcut;

    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return shortcutInfos;
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return Collections.emptyList();
    }

    @Override
    public Class<TestMethodShortcutActivity> getActivityClass() {
        return TestMethodShortcutActivity.class;
    }

    @Override
    public void callMethodShortcut(Activity activity, String methodName) {
        activityThatWasPassedToCallMethodShortcut = activity;
    }
}
