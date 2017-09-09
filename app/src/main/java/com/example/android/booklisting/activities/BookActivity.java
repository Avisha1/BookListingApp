package com.example.android.booklisting.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.android.booklisting.R;
import com.example.android.booklisting.adapters.BookAdapter;
import com.example.android.booklisting.data_objects.Book;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private static final String KEY_SAVED_INSTANCE = "saved_instance";
    ArrayList<Book> listBooks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            listBooks = savedInstanceState.getParcelableArrayList(KEY_SAVED_INSTANCE);
        } else {
            Bundle b = this.getIntent().getExtras();
            if (b != null) {
                listBooks = b.getParcelableArrayList(getString(R.string.BookObjectIntentKey));
            }
        }
        if (listBooks == null) {
            setContentView(R.layout.no_data);
        }
        else{
            setContentView(R.layout.book_list);

            BookAdapter adapter = new BookAdapter(this, listBooks);
            ListView lstView = (ListView) findViewById(R.id.list);
            lstView.setAdapter(adapter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_SAVED_INSTANCE, listBooks);
    }
}
