package com.example.songs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shortbread.songs.R;
import com.example.shortbread.songs.R2;

import shortbread.Shortcut;

@Shortcut(id = "songs", icon = R2.drawable.ic_shortcut_songs, shortLabelRes = R2.string.label_songs, rank = 5)
public class SongsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ((TextView) findViewById(R.id.text)).setText("Songs");
    }
}
