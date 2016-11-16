package com.nalaneholdings.sesothotrivia.model;

import android.content.Context;

import com.nalaneholdings.sesothotrivia.R;
import com.nalaneholdings.sesothotrivia.model.bean.Game;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

/**
 * This class governs the rules of playing a game
 * Created by ntholi.nkhatho on 2016/09/12.
 */
public class GamePlayer {

    private Game game;
    private String message;
    private Context context;

    public GamePlayer(Context context, Game game){
        this.context = context;
        this.game = game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame(){return game;}

    public String getMessage() {
        return message;
    }

//    /**
//     *
//     * @return returns true if level has been incremented
//     */
//    public boolean increaseLevel() {
//        boolean incremented = false;
//        int level = game.getLevel();
//        int points = game.getPoints();
//
//        if(level == 1 && points >= 20){
//            ++level;
//            incremented = true;
//        }
//        else if(level >= 2 && points >= 30){
//            ++level;
//            incremented = true;
//        }
//
//        game.setPoints(points);
//        return incremented;
//    }
//
//    /**
//     * Decrease points based on question attempts, the number of times the user attempted to answer
//     * the question
//     * @param attempts Question attempt
//     */
//    public void decreasePoints(int attempts){
//        int level = game.getLevel();
//        int points = game.getPoints();
//
//        if(attempts >= 2 && level <= 5){
//            points -= 3; // TODO: 2016/09/12 Are you sure it doesn't cover level 6 to 10?
//        }
//        else if(level >= 11 && level <= 19){
//            points -= 4;
//        }
//
//        game.setPoints(points);
//    }
//
//    public void increasePoints() {
//        int level = game.getLevel();
//        int points = game.getPoints();
//
//        if(level == 1){
//            points += 5;
//        }
//        else if(level >= 2 && level <= 4){
//            points += 6;
//        }
//        else if(level >= 5 && level <= 9){
//            points += 8;
//        }
//        else if(level >= 10 && level <= 19){
//            points += 3;
//        }
//        else if(level >= 20){
//            points += 1;
//        }
//        game.setPoints(points);
//    }
//
//    public boolean nextLevel(){
//        boolean proceed = false;
//        int level = game.getLevel();
//        int points = game.getPoints();
//
//        if(level == 1 && points >= 20){
//            proceed = true;
//        }else message = context.getString(R.string.insufficient_points, 20);
//
//        if(level == 2 && points >= 30){
//            proceed = true;
//        } else message = context.getString(R.string.insufficient_points, 30);
//
//        return proceed;
//    }
//
//    public boolean isAnswerCorrect(Question question, String answer) {
//        boolean correct = false;
//        if(question.isAnswerCorrect(answer)){
//           increasePoints();
//            correct = true;
//        }
//        else decreasePoints(question.getAttempts());
//
//        return correct;
//    }
//
//    public boolean isEndOfGame() {
//        return game.endOfGame();
//    }

}
