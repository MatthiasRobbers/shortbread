package com.example;

import android.app.Activity;

import shortbread.Shortcut;

@Shortcut(id = "ID_2", icon = R.drawable.ic_shortcut, shortLabel = "SHORT_LABEL", longLabel = "LONG_LABEL", rank = 1,
        disabledMessage = "DISABLED_MESSAGE", enabled = true, action = "ACTION")
public class AdvancedShortcutActivity extends Activity {

}
