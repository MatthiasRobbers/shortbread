package com.example.shortbread.movies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shortbread.MainActivity;
import com.example.shortbread.R;
import com.example.shortbread.books.BooksActivity;

import shortbread.Shortcut;

@Shortcut(id = "movies", action = "movie_shortcut", icon = R.drawable.ic_shortcut_movies, rank = 3,
        backStack = {MainActivity.class, BooksActivity.class})
public class MoviesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    @Shortcut(id = "add_movie", icon = R.drawable.ic_shortcut_add, shortLabel = "Add movie", rank = 4, disabledMessageRes = R.string.label_books)
    public void addMovie() {
        Toast.makeText(this, "Add movie", Toast.LENGTH_SHORT).show();
    }
}
