package com.example;

import android.content.Context;
import android.content.pm.ShortcutInfo;

import java.util.ArrayList;
import java.util.List;

import shortbread.internal.Shortcuts;

public final class TestShortcutActivity_Shortcuts implements Shortcuts {

    public static final List<ShortcutInfo> shortcutInfos = new ArrayList<>();
    public static final List<String> disabledShortcutIds = new ArrayList<>();

    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return shortcutInfos;
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return disabledShortcutIds;
    }
}
