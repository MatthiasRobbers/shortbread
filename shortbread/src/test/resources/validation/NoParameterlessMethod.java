package com.example;

import android.app.Activity;

import shortbread.Shortcut;

public class NoParameterlessMethod extends Activity {

    @Shortcut(id = "ID")
    void startActivity(Object parameter) {

    }
}
