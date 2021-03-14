package com.example.shortbread.books;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shortbread.R;
import com.example.shortbread.R2;

import shortbread.Shortcut;

@Shortcut(id = "books", icon = R2.drawable.ic_shortcut_books, shortLabelRes = R2.string.label_books, rank = 1)
public class BooksActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
    }

    @Shortcut(id = "favorite_books", icon = R2.drawable.ic_shortcut_favorite, shortLabelRes = R2.string.label_books_method,
            rank = 2, disabledMessage = "You have no favorite books")
    public void showFavoriteBooks() {
        ((TextView) findViewById(R.id.text)).setText("Favorite books");
    }
}
