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

package com.gst.socialcomponents.main.followPosts;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.FollowPostsAdapter;
import com.gst.socialcomponents.main.base.BaseActivity;
import com.gst.socialcomponents.main.editProfile.EditProfileActivity;
import com.gst.socialcomponents.main.main.GalleryActivity;
import com.gst.socialcomponents.main.main.NotifActivity;
import com.gst.socialcomponents.main.postDetails.PostDetailsActivity;
import com.gst.socialcomponents.main.profile.ProfileActivity;
import com.gst.socialcomponents.model.FollowingPost;
import com.gst.socialcomponents.model.Profilefire;

import java.util.List;

public class FollowingPostsActivity extends BaseActivity<FollowPostsView, FollowingPostsPresenter> implements FollowPostsView {

    private FollowPostsAdapter postsAdapter;
    private RecyclerView recyclerView;

    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    private TextView message_following_posts_empty;

    Toolbar toolbar;

    public Boolean typeuser;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_posts);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initContentView();

        presenter.loadFollowingPosts();





        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference("profiles").child(firebaseUser.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Profilefire profile = dataSnapshot.getValue(Profilefire.class);
                    typeuser=profile.isType();

                    if(typeuser== true){
                        changesetup();
                    }
                    if(typeuser== false){
                        changesetuptodefault();
                    }
                    if (!profile.isActive()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try{
                                    showalert();
                                    Looper.loop();
                                    Looper.myLooper().quit();

                                }catch (WindowManager.BadTokenException e){

                                }
                            }
                        });
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }

    }



    private void changesetup() {

        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
    }
    private void changesetuptodefault() {

        toolbar.setTitle("Syndic SPIG");
        toolbar.setBackgroundColor(getResources().getColor(R.color.send_button_color));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PostDetailsActivity.UPDATE_POST_REQUEST) {
            postsAdapter.updateSelectedItem();
        }
    }

    @NonNull
    @Override
    public FollowingPostsPresenter createPresenter() {
        if (presenter == null) {
            return new FollowingPostsPresenter(this);
        }
        return presenter;
    }

    @Override
    public void onFollowingPostsLoaded(List<FollowingPost> list) {
        postsAdapter.setList(list);
    }

    @Override
    public void showLocalProgress() {
        if (!swipeContainer.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLocalProgress() {
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyListMessage(boolean show) {
        message_following_posts_empty.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initContentView() {
        if (recyclerView == null) {
            progressBar = findViewById(R.id.progressBar);
            message_following_posts_empty = findViewById(R.id.message_following_posts_empty);
            swipeContainer = findViewById(R.id.swipeContainer);
            swipeContainer.setOnRefreshListener(() -> presenter.onRefresh());

            initPostListRecyclerView();
        }
    }

    private void initPostListRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        postsAdapter = new FollowPostsAdapter(this);
        postsAdapter.setCallBack(new FollowPostsAdapter.CallBack() {
            @Override
            public void onItemClick(FollowingPost followingPost, View view) {
                presenter.onPostClicked(followingPost.getPostId(), view);
            }

            @Override
            public void onAuthorClick(int position, View view) {
                String postId = postsAdapter.getItemByPosition(position).getPostId();
                presenter.onAuthorClick(postId, view);
            }
        });
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(postsAdapter);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openPostDetailsActivity(String postId, View v) {
        Intent intent = new Intent(FollowingPostsActivity.this, PostDetailsActivity.class);
        intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, postId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View imageView = v.findViewById(R.id.postImageView);
            View authorImageView = v.findViewById(R.id.authorImageView);

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(FollowingPostsActivity.this,
                            new android.util.Pair<>(imageView, getString(R.string.post_image_transition_name)),
                            new android.util.Pair<>(authorImageView, getString(R.string.post_author_image_transition_name))
                    );
            startActivityForResult(intent, PostDetailsActivity.UPDATE_POST_REQUEST, options.toBundle());
        } else {
            startActivityForResult(intent, PostDetailsActivity.UPDATE_POST_REQUEST);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openProfileActivity(String userId, View view) {
        Intent intent = new Intent(FollowingPostsActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER_ID_EXTRA_KEY, userId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {

            View authorImageView = view.findViewById(R.id.authorImageView);

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(FollowingPostsActivity.this,
                            new android.util.Pair<>(authorImageView, getString(R.string.post_author_image_transition_name)));
            startActivityForResult(intent, ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST, options.toBundle());
        } else {
            startActivityForResult(intent, ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST);
        }
    }

    void   showalert(){
        new AlertDialog.Builder(FollowingPostsActivity.this,R.xml.styles)
                .setTitle("En attente de vérification")
                .setMessage("Votre adhésion est en attente de confirmation d'un modérateur" +
                        ",vous serez notifié de l'activation de votre compte")
                .setCancelable(false)
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(FollowingPostsActivity.this, GalleryActivity.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("Modifier votre profile", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(FollowingPostsActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        // intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // intent1.addCategory(Intent.CATEGORY_HOME);
                        // startActivity(intent1);

                        finishAffinity();

                    }
                })
                .show();
    }
}
