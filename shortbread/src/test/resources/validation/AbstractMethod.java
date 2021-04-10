package com.example;

import android.app.Activity;

import shortbread.Shortcut;

public class AbstractMethod extends Activity {

    @Shortcut(id = "ID")
    abstract void startActivity() {

    }
}
