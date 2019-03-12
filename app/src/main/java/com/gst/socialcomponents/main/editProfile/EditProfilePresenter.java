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

package com.gst.socialcomponents.main.editProfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.base.BaseView;
import com.gst.socialcomponents.main.editProfile.createProfile.CreateProfileActivity;
import com.gst.socialcomponents.main.login.LoginActivity;
import com.gst.socialcomponents.main.pickImageBase.PickImagePresenter;
import com.gst.socialcomponents.managers.ProfileManager;
import com.gst.socialcomponents.managers.listeners.OnObjectChangedListenerSimple;
import com.gst.socialcomponents.model.Profile;
import com.gst.socialcomponents.utils.ValidationUtil;

/**
 * Created by Alexey on 03.05.18.
 */

public class EditProfilePresenter<V extends EditProfileView> extends PickImagePresenter<V> {

    protected Profile profile;
    protected ProfileManager profileManager;

    protected EditProfilePresenter(Context context) {
        super(context);
        profileManager = ProfileManager.getInstance(context.getApplicationContext());

    }

    public void loadProfile() {
        ifViewAttached(BaseView::showProgress);
        profileManager.getProfileSingleValue(getCurrentUserId(), new OnObjectChangedListenerSimple<Profile>() {
            @Override
            public void onObjectChanged(Profile obj) {
                profile = obj;
                ifViewAttached(view -> {
                    if (profile != null) {
                        view.setName(profile.getUsername());
                        view.setResidence(profile.getResidence());
                        view.setNumresidence(profile.getNumresidence());
                        view.setMobile(profile.getMobile());
                        view.setToken(profile.getToken());

                        if (profile.getPhotoUrl() != null) {
                            view.setProfilePhoto(profile.getPhotoUrl());
                        }
                    }

                    view.hideProgress();
                    view.setNameError(null);
                });
            }
        });
    }

    public void attemptCreateProfile(Uri imageUri) {
        if (checkInternetConnection()) {
            ifViewAttached(view -> {
                view.setNameError(null);

                String name = view.getNameText().trim();
                String residence = view.getResidenceText().trim();
                String numresidence=view.getNumresidenceText().trim();
                String mobile=view.getMobileText().trim();

                boolean cancel = false;

                if ((TextUtils.isEmpty(name))) {
                    view.setNameError(context.getString(R.string.error_field_required));
                    cancel = true;
                }
                else if(TextUtils.isEmpty(residence)){
                    view.setResidenceError(context.getString(R.string.error_field_required));
                    cancel = true;

                }else if(TextUtils.isEmpty(numresidence)){
                    view.setNumresidenceError(context.getString(R.string.error_field_required));
                    cancel = true;
                }
                else if(TextUtils.isEmpty(mobile)){
                    view.setMobileError(context.getString(R.string.error_field_required));
                    cancel = true;
                }
                else if (!ValidationUtil.isNameValid(name)) {
                    view.setNameError(context.getString(R.string.error_profile_name_length));
                    cancel = true;
                }

                if (!cancel) {
                    view.showProgress();
                    profile.setUsername(name);
                    profile.setResidence(residence);
                    profile.setNumresidence(numresidence);
                    profile.setMobile(mobile);
                    profile.setToken( FirebaseInstanceId.getInstance().getToken());
                    createOrUpdateProfile(imageUri);
                }
            });
        }
    }

    private void createOrUpdateProfile(Uri imageUri) {
        profileManager.createOrUpdateProfile(profile, imageUri, success -> {
            ifViewAttached(view -> {
                view.hideProgress();
                if (success) {
                    onProfileUpdatedSuccessfully();
                } else {
                    view.showSnackBar(R.string.error_fail_create_profile);
                }
            });
        });
    }

    protected void onProfileUpdatedSuccessfully() {
        Log.v("profileupdateddcreated","here we go ");
       ifViewAttached(BaseView::finish);


    }

}