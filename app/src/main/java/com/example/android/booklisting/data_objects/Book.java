package com.example.android.booklisting.data_objects;

/**
 * Created by avishai on 9/9/2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    private String mTitle;
    private String mAuthor;
    private String mPublishedDate;
    private String mDescription;

    public Book(String title, String author, String publishedDate, String description) {
        mTitle = title;
        mAuthor = author;
        mPublishedDate = publishedDate;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    // Parcelling part
    public Book(Parcel in){
        this.mTitle = in.readString();
        this.mAuthor = in.readString();
        this.mPublishedDate =  in.readString();
        this.mDescription =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getAuthor());
        dest.writeString(this.getTitle());
        dest.writeString(this.getDescription());
        dest.writeString(this.getPublishedDate());
    }
}