package com.example.shortbread;

import android.app.Application;

import shortbread.Shortbread;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // white listed
        Shortbread.generate(this)
                .disableAll()
                .setEnabled(R.id.books, true)
                .setEnabled(R.id.favorite_books, true)
                .build();

        // black listed
//        Shortbread.generate(this)
//                .setEnabled(R.id.books, false)
//                .setEnabled(R.id.favorite_books, false)
//                .build();
    }
}
