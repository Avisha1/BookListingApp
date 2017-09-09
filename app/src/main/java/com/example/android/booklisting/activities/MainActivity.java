package com.example.android.booklisting.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.booklisting.R;
import com.example.android.booklisting.data_objects.Book;
import com.example.android.booklisting.networking.BooksLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button searchButton;
    EditText edtTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.button);
        edtTextSearch = (EditText)findViewById(R.id.editTextSearchBook);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBook(v);
            }
        });
    }


    public void searchBook(View view){
        //get text from EditText
        String query = edtTextSearch.getText().toString();

        //send it to async task
        new SearchBookAsync().execute(query, null, "");
    }

    public void goToBookActivity(ArrayList<Book> list){
        Intent intent = new Intent(this, BookActivity.class);
        intent.putParcelableArrayListExtra(getString(R.string.BookObjectIntentKey), list);

        startActivity(intent);
    }

    private class SearchBookAsync extends AsyncTask<String, Void, String>{

        private ArrayList<Book> bookList = null;
        @Override
        protected String doInBackground(String... params) {
            BooksLoader loader = new BooksLoader(params[0]);
            bookList = loader.Load();

            return "";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            goToBookActivity(bookList);
        }
    }
}
