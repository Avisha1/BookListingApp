package com.example.android.booklisting.networking;

import com.example.android.booklisting.data_objects.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.text.TextUtils;

public class GoogleBooksApiHelper {

    public static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            System.out.println("Error with creating URL");
            exception.printStackTrace();
            return null;
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                System.out.println("Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            System.out.println("Problem retrieving the JSON results.");
            e.printStackTrace();
            // no matter what - do this
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                //noinspection ThrowFromFinallyBlock
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Book> parseJsonToBooks(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            ArrayList<Book> bookList = new ArrayList<>();
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {
                //if parsing one book fails, continue to parse others
                try {
                    String title;
                    String authors;
                    String publishedDate;

                    String description = "";

                    //get info specific to the book
                    JSONObject volumeInfo = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                    if (volumeInfo.has("title") && volumeInfo.has("publishedDate") && volumeInfo.has("authors")) {

                        title = volumeInfo.getString("title");
                        publishedDate = volumeInfo.getString("publishedDate");

                        //get the authors from the array (there might be several)
                        StringBuilder authorsBuilder = new StringBuilder();
                        JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authorsBuilder.append(authorsArray.get(j));
                            authorsBuilder.append(" ");
                        }
                        authors = authorsBuilder.toString();

                        try {
                            description = volumeInfo.getString("description");
                        } catch (JSONException e) {
                            System.out.println("JSONException thrown - there's no description");
                            e.printStackTrace();
                        }
                        bookList.add(new Book(title, authors, publishedDate, description));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //if all went well - return the books list
            return bookList;
        } catch (JSONException e) {
            System.out.println("Problem parsing the JSON results");
            e.printStackTrace();
        }
        //return null if something went wrong
        return null;
    }
}
