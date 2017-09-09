package com.example.android.booklisting.networking;

import com.example.android.booklisting.data_objects.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BooksLoader {

    private static final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String URL_SUFFIX = "&maxResults=25";

    //query parameters for the loader
    private String mQueryParams;

    public BooksLoader(String searchQuery) {
        mQueryParams = searchQuery;
    }

    public ArrayList<Book> Load(){
        ArrayList<Book> answer = null;
        URL url = GoogleBooksApiHelper.createUrl(URL_BASE + mQueryParams + URL_SUFFIX);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = GoogleBooksApiHelper.makeHttpRequest(url);
        } catch (IOException e) {
            System.out.println("Oops. here was a problem with the HTTP request.");
            e.printStackTrace();
        }
        if (jsonResponse != null) {
            answer = GoogleBooksApiHelper.parseJsonToBooks(jsonResponse);
        } else {
            System.out.println("response is null :/ ");
        }
        return answer;
    }

}
