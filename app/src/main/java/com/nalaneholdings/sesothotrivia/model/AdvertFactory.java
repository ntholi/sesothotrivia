package com.nalaneholdings.sesothotrivia.model;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.model.bean.Advert;
import com.nalaneholdings.sesothotrivia.model.bean.PlayerFactory;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ntholi.nkhatho on 2016/12/14.
 */

public class AdvertFactory {

    private static List<Advert> adverts;

    public static Advert getAdvert(){
        if (adverts == null) {
            throw new ExceptionInInitializerError("Adverts not initialized");
        }
        int random = new Random().nextInt(adverts.size());
        return adverts.get(random);
    }

    public static void initialize(){
        adverts = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Advert.NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Advert advert = dataSnapshot.getValue(Advert.class);
                adverts.add(advert);
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
}
