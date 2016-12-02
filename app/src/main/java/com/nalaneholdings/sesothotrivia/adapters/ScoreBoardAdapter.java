/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nalaneholdings.sesothotrivia.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nalaneholdings.sesothotrivia.R;
import com.nalaneholdings.sesothotrivia.model.bean.GameStatus;
import com.nalaneholdings.sesothotrivia.model.bean.User;

import java.io.InputStream;
import java.util.List;


public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardAdapter.MyViewHolder> {

    private static final String TAG = ScoreBoardAdapter.class.getSimpleName();

    private List<GameStatus> gameStatusList;
    private Context context;

    public ScoreBoardAdapter(List<GameStatus> gameStatusList){
        this.gameStatusList = gameStatusList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView avatar;
        TextView points;
        RelativeLayout layout;

        MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            points = (TextView) view.findViewById(R.id.points);

            layout = (RelativeLayout) view.findViewById(R.id.score_list_row);
            context = view.getContext();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_game_score, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GameStatus gameStatus = gameStatusList.get(position);
        User user = gameStatus.getUser();
        holder.name.setText(user.getDisplayName());
        holder.points.setText(String.valueOf(gameStatus.getPoints()));
        new DownloadImageTask(holder.avatar)
                .execute(user.getPhotoURL());

    }

    @Override
    public int getItemCount() {
        return gameStatusList.size();
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
