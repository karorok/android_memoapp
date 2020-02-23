package com.example.karorok.memoapp.Data;

public class DummyImage {

    String imagePath;

    boolean isUrl;

    public DummyImage(String imagePath, boolean isUrl) {
        this.imagePath = imagePath;
        this.isUrl = isUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isUrl() {
        return isUrl;
    }

    public void setUrl(boolean url) {
        isUrl = url;
    }
}
