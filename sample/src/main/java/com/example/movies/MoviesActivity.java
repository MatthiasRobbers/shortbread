package com.example.movies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.MainActivity;
import com.example.books.BooksActivity;
import com.example.shortbread.R;
import com.example.shortbread.R2;

import shortbread.Shortcut;

@Shortcut(id = "movies", action = "movie_shortcut", icon = R2.drawable.ic_shortcut_movies, rank = 3,
        backStack = {MainActivity.class, BooksActivity.class})
public class MoviesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ((TextView) findViewById(R.id.text)).setText("Movies");
    }

    @Shortcut(id = "add_movie", icon = R2.drawable.ic_shortcut_add, shortLabelRes = R2.string.label_movies_method, rank = 4)
    void addMovie() {
        ((TextView) findViewById(R.id.text)).setText("Add movie");
    }
}
