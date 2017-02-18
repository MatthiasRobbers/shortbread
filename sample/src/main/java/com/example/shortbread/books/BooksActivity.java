package com.example.shortbread.books;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shortbread.R;

import shortbread.Shortcut;

@Shortcut(id = R.id.books, icon = R.drawable.ic_shortcut_books, shortLabelRes = R.string.label_books, rank = 1)
public class BooksActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
    }

    @Shortcut(id = R.id.favorite_books, icon = R.drawable.ic_shortcut_favorite, shortLabel = "Favorite books", rank = 2,
            disabledMessage = "You have no favorite books")
    public void showFavoriteBooks() {
        Toast.makeText(this, "Favorite books", Toast.LENGTH_SHORT).show();
    }
}
