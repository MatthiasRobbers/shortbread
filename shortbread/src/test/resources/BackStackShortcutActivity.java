package com.example;

import android.app.Activity;

import shortbread.Shortcut;

@Shortcut(id = "ID", shortLabel = "SHORT_LABEL", backStack = {EmptyActivity1.class, EmptyActivity2.class})
public class BackStackShortcutActivity extends Activity {

}
