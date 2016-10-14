package com.nalaneholdings.sesothotrivia.model;

import com.nalaneholdings.sesothotrivia.model.bean.Game;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntholi.nkhatho on 2016/08/30.
 */
public class GameDAO {


    public void save(Game game) {
    }

    /**
     * Update Game values, without updating game questions and coreIncrementValue
     * @param game
     */
    public void updateGame(Game game){

    }

    public Game getGame(String id) {
        Game game = null;

        return game;
    }

    private List<Question> getQuestions(Game game) {
        List<Question> questions = new ArrayList<>();

        return questions;
    }

    public List<Game> getGames() {
        List<Game> gameList = new ArrayList<Game>();

        return gameList;
    }

    public int getGamesCount() {
        int count = 0;
        return count;
    }
}
