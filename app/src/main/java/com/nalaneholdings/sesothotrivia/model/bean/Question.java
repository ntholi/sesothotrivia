package com.nalaneholdings.sesothotrivia.model.bean;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by ntholi.nkhatho on 2016/07/29.
 */
public class Question {

    private String questionString;
    private String hint;
    private String correctAnswer;
    private List<String> possibleAnswers;
    private int attempts;

    public Question(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Question(String questionString, String hint, String correctAnswer,
                    List<String> possibleAnswers, int attempts) {
        this.questionString = questionString;
        this.hint = hint;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
        this.attempts = attempts;
    }


    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    /**
     * this is equivalent to the getQuestionString() method
     * @return returns the questionString
     */
    @Override
    public String toString() {
        return questionString;
    }

    public boolean isAnswerCorrect(String answer){
        answer = answer.trim();
        correctAnswer = correctAnswer.trim();
        return answer.toLowerCase().contains(correctAnswer.toLowerCase());
    }

    public boolean isMultipleChoice() {
        return (possibleAnswers != null) &&
                (possibleAnswers.size() > 1);
    }
}
