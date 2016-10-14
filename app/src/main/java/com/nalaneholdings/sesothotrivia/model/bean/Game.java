package com.nalaneholdings.sesothotrivia.model.bean;

import java.util.List;
import java.util.UUID;

/**`
 * Created by ntholi.nkhatho on 2016/07/29.
 */

public class Game extends ID {

    private String name;
    private float rating;
    private int imageResourceId;
    private List<Question> questions;

    public Game(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Game(String name, float rating,  int imageResourceId,
                List<Question> questions) {
        this.name = name;
        this.rating = rating;
        this.imageResourceId = imageResourceId;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
