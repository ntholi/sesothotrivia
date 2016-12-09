package com.nalaneholdings.sesothotrivia;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nalaneholdings.sesothotrivia.adapters.ScoreBoardAdapter;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;
import com.nalaneholdings.sesothotrivia.model.bean.User;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardActivity extends AppCompatActivity {
    private static final String TAG = "ScoreBoardActivity";
    private List<GameStatus> gameStatusList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ScoreBoardAdapter adapter;
    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    private FloatingActionButton fab;

    ScaleAnimation shrinkAnim;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //Initializing our Recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Say Hello to our new FirebaseUI android Element, i.e., FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<GameStatus, MyViewHolder> adapter = new FirebaseRecyclerAdapter<GameStatus, MyViewHolder>(
                GameStatus.class,
                R.layout.list_item_game_score,
                MyViewHolder.class,
                //referencing the node where we want the database to store the data from our Object
                mDatabase.child(GameStatus.NAME).getRef()) {
            @Override
            protected void populateViewHolder(MyViewHolder holder, GameStatus gameStatus, int position) {
                User user = gameStatus.getUser();
                holder.name.setText(user.getDisplayName());
                holder.points.setText(String.valueOf(gameStatus.getPoints()));
//                new ScoreBoardAdapter.DownloadImageTask(holder.avatar)
//                        .execute(user.getPhotoURL());
//                Picasso.with(MainActivity.this).load(model.getMoviePoster()).into(viewHolder.ivMoviePoster);
            }
        };

        mRecyclerView.setAdapter(adapter);

    }

//    private void prepareMovieData() {
//        showProgressDialog();
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child(GameStatus.NAME).orderByChild("points").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                GameStatus gameStatus = dataSnapshot.getValue(GameStatus.class);
//                gameStatusList.add(gameStatus);
//
//                adapter.notifyDataSetChanged();
//
//                hideProgressDialog();
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {}
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });
//    }
//
//    protected void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }
//
//    protected void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
//    }
//
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView avatar;
        TextView points;

        MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            points = (TextView) view.findViewById(R.id.points);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}
