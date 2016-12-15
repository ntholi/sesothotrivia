package com.nalaneholdings.sesothotrivia.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntholi.nkhatho on 2016/10/14.
 */

public class GameStatus{
    private int level;
    private int points;
    private List<String> answeredQuestions = new ArrayList<>();

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

    public List<String> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<String> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    /**
     * Increase level and clear answered question for this level
     */
    public void increaseLevel() {
        ++level;
        answeredQuestions = new ArrayList<>();
    }

    public void addAnsweredQuestion(String questionId) {
        answeredQuestions.add(questionId);
    }
}
