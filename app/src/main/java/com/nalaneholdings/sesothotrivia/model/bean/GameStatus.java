package com.nalaneholdings.sesothotrivia.model.bean;

/**
 * Created by ntholi.nkhatho on 2016/10/14.
 */

public class GameStatus extends ID {

    private Game game;
    private int level;
    private int points;

    public GameStatus(Game game, int level, int points) {
        this.game = game;
        this.level = level;
        this.points = points;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
        return level >= game.getQuestions().size();
    }

}
