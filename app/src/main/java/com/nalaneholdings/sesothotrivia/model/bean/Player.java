package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/12/01.
 */

public class Player {

    public static final String NAME = "players";

    private String email;
    private String displayName;
    private String photoURL;
    private GameStatus gameStatus;
    private int reverseRating;

    public Player(String email, String displayName, String photoURL, GameStatus gameStatus, int reverseRating) {
        this.email = email;
        this.displayName = displayName;
        this.photoURL = photoURL;
        this.gameStatus = gameStatus;
        this.reverseRating = reverseRating;
    }

    public Player(){}


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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getReverseRating() {
        return reverseRating;
    }

    public void setReverseRating(int reverseRating) {
        this.reverseRating = reverseRating;
    }

    public void applyReverseRating(){
        reverseRating = 0 - gameStatus.getPoints();
    }
}
