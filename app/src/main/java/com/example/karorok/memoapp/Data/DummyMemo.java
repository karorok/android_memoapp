package com.example.karorok.memoapp.Data;

import java.util.ArrayList;
import java.util.Date;

public class DummyMemo {
    String title;
    Date lastDate;
    String text;
    ArrayList<DummyImage> images;

    public DummyMemo(){

    }

    public DummyMemo(String title, Date lastDate, String text) {
        this.title = title;
        this.lastDate = lastDate;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<DummyImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<DummyImage> images) {
        this.images = images;
    }
}
