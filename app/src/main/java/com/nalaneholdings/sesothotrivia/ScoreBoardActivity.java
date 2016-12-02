package com.nalaneholdings.sesothotrivia;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.adapters.ScoreBoardAdapter;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity {
    private static final String TAG = "ScoreBoardActivity";
    private List<GameStatus> gameStatusList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ScoreBoardAdapter adapter;
    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ScoreBoardAdapter(gameStatusList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        showProgressDialog();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(GameStatus.NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GameStatus gameStatus = dataSnapshot.getValue(GameStatus.class);
                gameStatusList.add(gameStatus);

                Log.d(TAG, gameStatus.getUser().getDisplayName()+ "'s status loaded");
                hideProgressDialog();
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

        adapter.notifyDataSetChanged();
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
