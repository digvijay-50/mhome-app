package com.propertybuysell.mhome.ModelClasses;

public class WelcomeModel {
    int image;
    String title;
    String message;

    public WelcomeModel(int image, String title, String message) {
        this.image = image;
        this.title = title;
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
