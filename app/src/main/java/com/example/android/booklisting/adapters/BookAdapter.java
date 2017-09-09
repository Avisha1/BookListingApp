package com.example.android.booklisting.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.booklisting.R;
import com.example.android.booklisting.data_objects.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Book currentBook = getItem(position);

        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentBook.getAuthor());

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());

        TextView description = (TextView) listItemView.findViewById(R.id.description);
        description.setText(currentBook.getDescription());

        TextView publishedDate = (TextView) listItemView.findViewById(R.id.published_date);
        publishedDate.setText(currentBook.getPublishedDate());

        return listItemView;
    }
}
