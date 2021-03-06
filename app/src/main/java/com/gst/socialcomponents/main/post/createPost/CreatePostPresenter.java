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

package com.gst.socialcomponents.main.post.createPost;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.post.BaseCreatePostPresenter;
import com.gst.socialcomponents.model.Post;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexey on 03.05.18.
 */

public class CreatePostPresenter extends BaseCreatePostPresenter<CreatePostView> {



    public CreatePostPresenter(Context context) {

        super(context);

    }

    @Override
    protected int getSaveFailMessage() {
        return R.string.error_fail_create_post;
    }

    @Override
    protected void savePost(String title, String description,String ismod,String reside) {
        ifViewAttached(view -> {
            view.showProgress(R.string.message_creating_post);
            Post post = new Post();
            post.setTitle(title);
            post.setModerator(ismod);
            post.setResidence(reside);
            post.setIsvideo(false);
             post.setDescription(description);
            post.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            postManager.createOrUpdatePostWithImage(view.getImageUri(), this, post);

        });
    }

    @Override
    protected boolean isImageRequired() {
        return true;
    }
}
