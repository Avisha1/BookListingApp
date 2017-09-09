package com.example.android.booklisting.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.booklisting.R;
import com.example.android.booklisting.data_objects.Book;
import com.example.android.booklisting.networking.BooksLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText edtTextSearch;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.button);
        edtTextSearch = (EditText)findViewById(R.id.editTextSearchBook);

        context = this;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBook(v);
            }
        });
    }


    public void searchBook(View view){

        if(checkInternetConnection()) {
            //get text from EditText
            String query = edtTextSearch.getText().toString();

            //send it to async task
            new SearchBookAsync().execute(query, null, "");
        }
        else{
            Toast.makeText(this, "Can't perform this action when there is no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean checkInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
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
