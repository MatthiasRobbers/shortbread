// Generated code from Shortbread. Do not modify!
package com.example;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import shortbread.internal.Shortcuts;

public final class DisabledShortcutActivity_Shortcuts implements Shortcuts {
    @Override
    public List<ShortcutInfo> getShortcuts(Context context) {
        return new ArrayList<ShortcutInfo>() {{
        }};
    }

    @Override
    public List<String> getDisabledShortcutIds() {
        return new ArrayList<String>() {{
            add("ID");
        }};
    }
}