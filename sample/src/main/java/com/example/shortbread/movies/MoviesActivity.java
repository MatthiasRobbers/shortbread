package com.example.shortbread.movies;

import android.app.Activity;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shortbread.MainActivity;
import com.example.shortbread.R;
import com.example.shortbread.books.BooksActivity;

import java.util.Collections;

import shortbread.Shortbread;
import shortbread.Shortcut;

@Shortcut(id = "movies", action = "movie_shortcut", icon = R.drawable.ic_shortcut_movies, rank = 3,
        backStack = {MainActivity.class, BooksActivity.class})
public class MoviesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        shortbreadWay();
    }

    private void frameworkWay() {
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            for (final ShortcutInfo shortcutInfo : shortcutManager.getDynamicShortcuts()) {
                if (shortcutInfo.getId().equals("continue_watching")) {
                    final ShortcutInfo shortcut = new ShortcutInfo.Builder(this, shortcutInfo.getId())
                            .setShortLabel("Manchester by the sea")
                            .setIcon(Icon.createWithResource(this, R.drawable.manchester_by_the_sea))
                            .build();
                    shortcutManager.updateShortcuts(Collections.singletonList(shortcut));
                    break;
                }
            }
        }
    }

    private void shortbreadWay() {
        Bitmap cover = BitmapFactory.decodeResource(getResources(), R.drawable.manchester_by_the_sea);
        Shortbread.getShortcut("continue_watching")
                .setShortLabel("Manchester by the Sea")
                .setIcon(cover)
                .publish();
    }

//    @Shortcut(id = "add_movie", icon = R.drawable.ic_shortcut_add, shortLabel = "Add movie", rank = 4, disabledMessageRes = R.string.label_books)
//    public void addMovie() {
//        Toast.makeText(this, "Add movie", Toast.LENGTH_SHORT).show();
//    }

    @Shortcut(id = "continue_watching", icon = R.drawable.ic_shortcut_add, shortLabel = "Continue watching", rank = 4)
    public void continueWatching() {
        Toast.makeText(this, "Continue watching", Toast.LENGTH_SHORT).show();
    }
}
