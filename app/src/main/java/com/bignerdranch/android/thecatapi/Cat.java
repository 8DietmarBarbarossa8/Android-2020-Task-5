package com.bignerdranch.android.thecatapi;

public class Cat {
    long id;
    String title;
    int imageURL;

    Cat(long id, String title, int imageURL){
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
    }
}