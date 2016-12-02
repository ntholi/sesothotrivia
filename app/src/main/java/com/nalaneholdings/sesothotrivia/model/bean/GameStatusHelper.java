package com.nalaneholdings.sesothotrivia.model.bean;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.model.GamePlayer;

/**
 * Created by ntholi.nkhatho on 2016/12/01.
 */

public class GameStatusHelper {
    private static GameStatus gameStatus;

    public static void initialize(final GameStatusLoadable gameStatusLoader){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = new User();
        if(firebaseUser != null) {
            gameStatus = new GameStatus();
            user.setPhotoURL(firebaseUser.getPhotoUrl().toString());
            user.setUserID(firebaseUser.getUid());
            user.setEmail(firebaseUser.getEmail());
            user.setDisplayName(firebaseUser.getDisplayName());
            gameStatus.setUser(user);
        }


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(GameStatus.NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                gameStatus = dataSnapshot.getValue(GameStatus.class);
                if(gameStatus == null ){
                    gameStatus = new GameStatus();
                }
                else if (!user.getUserID().equals(dataSnapshot.getKey())){
                    gameStatus = new GameStatus();
                }
                gameStatusLoader.onGameStatusLoaded();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public boolean isInitialized(){
        return gameStatus != null;
    }

    public static GameStatus getGameStatus(){
        return gameStatus;
    }

    /**
     * a class that implements this interface will only access only when they are fully downloaded
     * from the Firebase
     */
    public interface GameStatusLoadable{
        public void onGameStatusLoaded();
    }

}
