package com.nalaneholdings.sesothotrivia.model.bean;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ntholi.nkhatho on 2016/12/01.
 */

public class PlayerFactory {
    private static Player player;

    public static void initialize(final PlayerLoadable playerLoader){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null){
            throw new ExceptionInInitializerError("Player not logged in, Player cannot be initialized");
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Player.NAME);
        mDatabase.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                player = dataSnapshot.getValue(Player.class);
                if(player == null){
                    player = userFromFirebase(firebaseUser);
                }
                if(player.getGameStatus() == null){
                    player.setGameStatus(new GameStatus());
                }
                playerLoader.onPlayerLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(this.getClass().getName(), databaseError.toString());
            }
        });

    }

    @NonNull
    private static Player userFromFirebase(FirebaseUser firebaseUser) {
        Player player = new Player();
        player.setPhotoURL(firebaseUser.getPhotoUrl().toString());
        player.setEmail(firebaseUser.getEmail());
        player.setDisplayName(firebaseUser.getDisplayName());
        return player;
    }

    public boolean isInitialized(){
        return player != null;
    }

    public static Player getPlayer(){
        return player;
    }

    /**
     * a class that implements this interface will only access only when they are fully downloaded
     * from the Firebase
     */
    public interface PlayerLoadable{
        public void onPlayerLoaded();
    }

}
