package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/10/14.
 */

public class GameStatus{

    private int level;
    private int points;
    private User user;

    public GameStatus(int level, int points, User user) {
        this.level = level;
        this.points = points;
        this.user = user;
    }

    public GameStatus() {

    }


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

    public void setUser(User user) {
        this.user = user;
    }
}
