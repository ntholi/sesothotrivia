package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/12/01.
 */

public class User {

    private String userID;
    private String email;
    private String displayName;
    private String photoURL;
    private boolean disabled;

    public User(String userID, String email, String displayName, String photoURL, boolean disabled) {
        this.userID = userID;
        this.email = email;
        this.displayName = displayName;
        this.photoURL = photoURL;
        this.disabled = disabled;
    }

    public User(){}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
