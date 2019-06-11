/*
 *  Copyright 2017 Rozdoum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gst.socialcomponents.Constants;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.controllers.LikeController;
import com.gst.socialcomponents.main.base.BaseActivity;
import com.gst.socialcomponents.main.main.PublicationPicture;
import com.gst.socialcomponents.managers.PostManager;
import com.gst.socialcomponents.managers.ProfileManager;
import com.gst.socialcomponents.managers.listeners.OnObjectChangedListener;
import com.gst.socialcomponents.managers.listeners.OnObjectChangedListenerSimple;
import com.gst.socialcomponents.managers.listeners.OnObjectExistListener;
import com.gst.socialcomponents.model.Like;
import com.gst.socialcomponents.model.Post;
import com.gst.socialcomponents.model.Profile;
import com.gst.socialcomponents.utils.FormatterUtil;
import com.gst.socialcomponents.utils.GlideApp;
import com.gst.socialcomponents.utils.ImageUtil;

import static android.content.Context.MODE_PRIVATE;


public class PostViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = PostViewHolder.class.getSimpleName();

    protected Context context;
    private ImageView postImageView;
    private TextView titleTextView;
    private TextView detailsTextView;
    private TextView likeCounterTextView;
    private ImageView likesImageView;
    private TextView commentsCountTextView;
    private TextView watcherCounterTextView;
    private TextView dateTextView;
    private ImageView authorImageView;
    private ViewGroup likeViewGroup;
    private CardView cardview;
    private LinearLayout linearlayout;
    private VideoView postvideoview ;
    private ImageView watcherimageview;
    private  ImageView commentcountimageview;
    private TextView montanttv,montant,datepaytv,datepay;

    private ProfileManager profileManager;
    protected PostManager postManager;

    private LikeController likeController;
    private BaseActivity baseActivity;
    private ProgressBar progressBar ;



    public PostViewHolder(View view, final OnClickListener onClickListener, BaseActivity activity) {
        this(view, onClickListener, activity, true);
    }

    public PostViewHolder(View view, final OnClickListener onClickListener, BaseActivity activity, boolean isAuthorNeeded) {
        super(view);
        this.context = view.getContext();
        this.baseActivity = activity;

        postImageView = view.findViewById(R.id.postImageView);
        likeCounterTextView = view.findViewById(R.id.likeCounterTextView);
        likesImageView = view.findViewById(R.id.likesImageView);
        commentsCountTextView = view.findViewById(R.id.commentsCountTextView);
        watcherCounterTextView = view.findViewById(R.id.watcherCounterTextView);
        watcherimageview=view.findViewById(R.id.watcherImageView);
        commentcountimageview=view.findViewById(R.id.commentsCountImageView);
        dateTextView = view.findViewById(R.id.dateTextView);
        titleTextView = view.findViewById(R.id.titleTextView);
        detailsTextView = view.findViewById(R.id.detailsTextView);
        authorImageView = view.findViewById(R.id.authorImageView);
        likeViewGroup = view.findViewById(R.id.likesContainer);
        cardview=view.findViewById(R.id.card_view);
        linearlayout =view.findViewById(R.id.linearlayoutpost);
        postvideoview=view.findViewById(R.id.postvideoview);
        progressBar=view.findViewById(R.id.progressBar3);
        montant=view.findViewById(R.id.montant);
        montanttv=view.findViewById(R.id.montanttextv);
        datepay=view.findViewById(R.id.datepay);
        datepaytv=view.findViewById(R.id.datepaytv);




        authorImageView.setVisibility(isAuthorNeeded ? View.VISIBLE : View.GONE);
        postImageView.setVisibility(View.VISIBLE);

        profileManager = ProfileManager.getInstance(context.getApplicationContext());
        postManager = PostManager.getInstance(context.getApplicationContext());

        view.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (onClickListener != null && position != RecyclerView.NO_POSITION) {
                onClickListener.onItemClick(getAdapterPosition(), v);
            }
        });

        likeViewGroup.setOnClickListener(view1 -> {
            int position = getAdapterPosition();
            if (onClickListener != null && position != RecyclerView.NO_POSITION) {
                onClickListener.onLikeClick(likeController, position);
            }
        });

        authorImageView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (onClickListener != null && position != RecyclerView.NO_POSITION) {
                onClickListener.onAuthorClick(getAdapterPosition(), v);
            }
        });


    }

    public void bindData(Post post) {






        if(!post.getAuthorId().equals("ADMIN")){

            likeController = new LikeController(context, post, likeCounterTextView, likesImageView, true);
            montant.setVisibility(View.GONE);
            montanttv.setVisibility(View.GONE);
            datepay.setVisibility(View.GONE);
            datepaytv.setVisibility(View.GONE);


        String title = removeNewLinesDividers(post.getTitle());
        titleTextView.setText(title);
        String description = removeNewLinesDividers(post.getDescription());
        detailsTextView.setText(description);
        likeCounterTextView.setText(String.valueOf(post.getLikesCount()));
        commentsCountTextView.setText(String.valueOf(post.getCommentsCount()));
        watcherCounterTextView.setText(String.valueOf(post.getWatchersCount()));

        CharSequence date = FormatterUtil.getRelativeTimeSpanStringShort(context, post.getCreatedDate());
        dateTextView.setText(date);


        postManager.loadImageMediumSize(GlideApp.with(baseActivity), post.getImageTitle(), postImageView);

        if (post.getAuthorId() != null) {

            profileManager.getProfileSingleValue(post.getAuthorId(), createProfileChangeListener(authorImageView));

        }

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            postManager.hasCurrentUserLikeSingleValue(post.getId(), firebaseUser.getUid(), createOnLikeObjectExistListener());


        }


        if (post.isIsvideo()) {



            progressBar.setVisibility(View.VISIBLE);
            postImageView.setVisibility(View.GONE);


            String url = post.getImageTitle();

            postvideoview.seekTo(100);
            postvideoview.setVisibility(View.VISIBLE);
            postvideoview.setVideoURI(Uri.parse("http://syndicspig.gloulougroupe.com/VideoUpload/Upload/" + url));
            postvideoview.requestFocus();



            postvideoview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postvideoview.setMediaController(new MediaController(context));

                }
            });


            postvideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {

                    progressBar.setVisibility(View.GONE);
                    postvideoview.start();
                    mp.setVolume(0f, 0f);

                }
            });

            postvideoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    postvideoview.start();
                    mp.setVolume(0f, 0f);


                }
            });


        }

            if(post.getModerator().equals("true")){
                // linearlayout.setBackgroundResource(R.drawable.stripes2);
                linearlayout.setBackgroundColor(0xffB22222);

                if (!post.isIsvideo()){
                    postvideoview.setVisibility(View.GONE);
                    postImageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);


                }

            }else {
                linearlayout.setBackgroundColor(Color.WHITE);
            }


    }
    else  if(post.getAuthorId().equals("ADMIN") ){
            authorImageView.setVisibility(View.GONE);
        if(post.getPublier() == 1) {

            likeController = new LikeController(context, post, likeCounterTextView, likesImageView, true);

            authorImageView.setVisibility(View.GONE);
            String title = removeNewLinesDividers(post.getTitle());
            titleTextView.setText(title);
            String description = removeNewLinesDividers(post.getDescription());
            detailsTextView.setText(description);
            likeCounterTextView.setText(String.valueOf(post.getLikesCount()));
            commentsCountTextView.setText(String.valueOf(post.getCommentsCount()));
            watcherCounterTextView.setText(String.valueOf(post.getWatchersCount()));





             String url="http://testeasysyndic.gloulougroupe.com/uploads/documents/";
             Glide.with(postImageView.getContext()).load(url+post.getImageTitle()).into(postImageView);



            CharSequence date = FormatterUtil.getRelativeTimeSpanStringShort(context, post.getCreatedDate());
            dateTextView.setText(date);


            datepay.setVisibility(View.VISIBLE);
            datepaytv.setVisibility(View.VISIBLE);
            CharSequence date2= FormatterUtil.getRelativeTimeSpanStringShort(context, post.getDatefacture());
            datepay.setText(date2);

            montant.setVisibility(View.VISIBLE);
            montanttv.setVisibility(View.VISIBLE);
            montant.setText(String.valueOf(post.getMontant())+" TND");









            if (post.getModerator().equals("true")) {
                // linearlayout.setBackgroundResource(R.drawable.stripes2);
                linearlayout.setBackgroundColor(0xff33C4FF);

                if (!post.isIsvideo()) {
                    postvideoview.setVisibility(View.GONE);
                    postImageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);


                }

            } else {
                linearlayout.setBackgroundColor(Color.WHITE);

            }
          }
        }







    }

    private String removeNewLinesDividers(String text) {
        int decoratedTextLength = text.length() < Constants.Post.MAX_TEXT_LENGTH_IN_LIST ?
                text.length() : Constants.Post.MAX_TEXT_LENGTH_IN_LIST;
        return text.substring(0, decoratedTextLength).replaceAll("\n", " ").trim();
    }

    private OnObjectChangedListener<Profile> createProfileChangeListener(final ImageView authorImageView) {
        return new OnObjectChangedListenerSimple<Profile>() {
            @Override
            public void onObjectChanged(Profile obj) {
                if (obj != null && obj.getPhotoUrl() != null) {
                    if (!baseActivity.isFinishing() && !baseActivity.isDestroyed()) {
                        ImageUtil.loadImage(GlideApp.with(baseActivity), obj.getPhotoUrl(), authorImageView);
                    }
                }
            }
        };
    }

    private OnObjectExistListener<Like> createOnLikeObjectExistListener() {
        return exist -> likeController.initLike(exist);
    }

    public interface OnClickListener {
        void onItemClick(int position, View view);

        void onLikeClick(LikeController likeController, int position);

        void onAuthorClick(int position, View view);
    }
}