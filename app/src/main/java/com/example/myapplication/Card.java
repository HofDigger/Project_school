package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
    private String title;
    private String author;
    private String publicationYear;
    private String description;
    private String imageUrl;  // כאן נשמור את ה-URL של התמונה

    public Card(String title, String author, String publicationYear, String description, String imageUrl) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Parcelable implementation
    protected Card(Parcel in) {
        title = in.readString();
        author = in.readString();
        publicationYear = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publicationYear);
        dest.writeString(description);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}



