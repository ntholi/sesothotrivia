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

    public Player(String email, String displayName, String photoURL, GameStatus gameStatus) {
        this.email = email;
        this.displayName = displayName;
        this.photoURL = photoURL;
        this.gameStatus = gameStatus;
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
}
