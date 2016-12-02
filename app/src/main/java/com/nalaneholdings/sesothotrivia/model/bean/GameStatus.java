package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/10/14.
 */

public class GameStatus{
    public static final String NAME = "GameStatus";
    private int level;
    private int points;
    private User user;

    GameStatus() {}


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean endOfGame() {
        // TODO: 2016/11/29 Implement this
        return false;
    }

    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }
}
