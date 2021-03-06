package com.example;

import android.app.Activity;

import shortbread.Shortcut;

public class NoPublicMethod extends Activity {

    @Shortcut(id = "ID")
    void startActivity() {

    }
}
