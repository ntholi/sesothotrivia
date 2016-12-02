package com.nalaneholdings.sesothotrivia.model.bean;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nalaneholdings.sesothotrivia.model.GamePlayer;

/**
 * Created by ntholi.nkhatho on 2016/12/01.
 */

public class GameStatusHelper {
    private static GameStatus gameStatus;

    public static void initialize(final GameStatusLoadable gameStatusLoader){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null){
            throw new ExceptionInInitializerError("User not logged in, GameStatus cannot be initialized");
        }
        gameStatus = new GameStatus();
        User user = userFromFirebase(firebaseUser);
        gameStatus.setUser(user);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(GameStatus.NAME);
        Query queryRef = mDatabase.orderByChild("user/userID").equalTo(firebaseUser.getUid());
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameStatus = dataSnapshot.getValue(GameStatus.class);
                if(gameStatus == null){
                    gameStatus = new GameStatus();
                    User user = userFromFirebase(firebaseUser);
                    gameStatus.setUser(user);
                }
                gameStatusLoader.onGameStatusLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                
            }
        });

    }

    @NonNull
    private static User userFromFirebase(FirebaseUser firebaseUser) {
        User user = new User();
        user.setPhotoURL(firebaseUser.getPhotoUrl().toString());
        user.setUserID(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setDisplayName(firebaseUser.getDisplayName());
        return user;
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
