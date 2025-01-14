package com.example.myapplication;

public class CardItem {
    private int imageResource;
    private String text;

    public CardItem(int imageResource, String text) {
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }
}
