package com.example.karorok.memoapp.Data;

import io.realm.RealmObject;

public class MemoImage extends RealmObject {

    String imageData;

    boolean isUrl;

    public MemoImage(){

    }

    public MemoImage(String imageData) {
        this.imageData = imageData;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public boolean isUrl() {
        return isUrl;
    }

    public void setUrl(boolean url) {
        isUrl = url;
    }
}
