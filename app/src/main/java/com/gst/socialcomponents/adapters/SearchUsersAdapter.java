/*
 * Copyright 2018 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gst.socialcomponents.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.UserViewHolder;
import com.gst.socialcomponents.managers.ProfileManager;
import com.gst.socialcomponents.managers.listeners.OnObjectChangedListenerSimple;
import com.gst.socialcomponents.model.Profile;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexey on 03.05.18.
 */

public class SearchUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = SearchUsersAdapter.class.getSimpleName();

    private List<Profile> itemsList = new ArrayList<>();
    private List<Profile> itemsListOfResidence = new ArrayList<>();
    String residence;




    private UserViewHolder.Callback callback;
    private Activity activity;

    public SearchUsersAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setCallback(UserViewHolder.Callback callback) {
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new UserViewHolder(inflater.inflate(R.layout.user_item_list_view, parent, false),
                callback, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        ((UserViewHolder) holder).bindData(itemsList.get(position));





    }



    public void setList(List<Profile> list) {

        SharedPreferences prefs = activity.getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);
        itemsListOfResidence.clear();

        for( int i=0;i<list.size();i++){
            if((list.get(i).getResidence().equals(residence))){
                itemsListOfResidence.add(list.get(i));
            }

        }

        itemsList.clear();
        itemsList.addAll(itemsListOfResidence);
        notifyDataSetChanged();

    }

    public void updateItem(int position) {
        Profile profile = getItemByPosition(position);
        ProfileManager.getInstance(activity.getApplicationContext()).getProfileSingleValue(profile.getId(), new OnObjectChangedListenerSimple<Profile>() {
            @Override
            public void onObjectChanged(Profile updatedProfile) {
                itemsList.set(position, updatedProfile);
                notifyItemChanged(position);
            }
        });
    }

    public Profile getItemByPosition(int position) {
        return itemsList.get(position);
    }
}
