package com.nalaneholdings.sesothotrivia.model.bean;

import java.util.List;

/**
 * Created by ntholi.nkhatho on 2016/07/29.
 */
public class Question {

    public static final String NAME = "questions";

    private String question;
    private String hint;
    private String answer;
    private List<String> possibleAnswers;
    private int points;
    private int level;
    private int time;

    /**
     * id and timePassed values are not stored in the database, they are only used when the application runs to track
     * each question as needed by the GamePlayer class
     */
    private String id;
    private long timePassed;

    public Question(){
        // Default constructor required for calls to DataSnapshot.getValue(Player.class)
    }

    public Question(String question, String hint, String correctAnswer, int points, int level,
                    List<String> possibleAnswers, int time) {
        this.question = question;
        this.hint = hint;
        this.answer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
        this.points = points;
        this.level = level;
        this.time = time;
    }


//    public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    /**
     * this is equivalent to the getQuestion() method
     * @return returns the question
     */
    @Override
    public String toString() {
        return question;
    }

    public boolean isAnswerCorrect(String answer){
        answer = answer.trim();
        return this.answer.toLowerCase().contains(answer.toLowerCase());
    }

    public boolean isMultipleChoice() {
        return (possibleAnswers != null) &&
                (possibleAnswers.size() > 2);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTimePassed(long timePassed) {
        this.timePassed = timePassed;
    }

    public long getTimePassed() {
        return timePassed;
    }
}
