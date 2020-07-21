package com.example;

import android.app.Activity;
import android.os.Bundle;

import shortbread.Shortcut;

public class TwoMethodShortcutsActivity extends Activity {

    @Shortcut(id = "ID")
    public void shortcutMethod1() {

    }

    @Shortcut(id = "ID_2")
    public void shortcutMethod2() {

    }
}
