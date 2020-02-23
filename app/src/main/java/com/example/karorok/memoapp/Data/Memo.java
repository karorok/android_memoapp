package com.example.karorok.memoapp.Data;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Memo extends RealmObject {

    @PrimaryKey
    int id;

    Date lastDate;

    String title;

    String text;

    RealmList<MemoImage> images;

    public Memo(){

    }

    public Memo(int id, Date lastDate, String title, String text) {
        this.id = id;
        this.lastDate = lastDate;
        this.title = title;
        this.text = text;
    }

    public Memo(int id, Date lastDate, String title, String text, RealmList<MemoImage> images) {
        this.id = id;
        this.lastDate = lastDate;
        this.title = title;
        this.text = text;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RealmList<MemoImage> getImages() {
        return images;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public void setImages(RealmList<MemoImage> images) {
        this.images = images;
    }
}
