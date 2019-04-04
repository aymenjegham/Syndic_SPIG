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
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.data.PostProfile;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.main.base.BaseView;
import com.gst.socialcomponents.main.editProfile.createProfile.CreateProfileActivity;
import com.gst.socialcomponents.main.login.LoginActivity;
import com.gst.socialcomponents.main.pickImageBase.PickImagePresenter;
import com.gst.socialcomponents.managers.ProfileManager;
import com.gst.socialcomponents.managers.listeners.OnObjectChangedListenerSimple;
import com.gst.socialcomponents.model.Profile;
import com.gst.socialcomponents.utils.ValidationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Headers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexey on 03.05.18.
 */

public class EditProfilePresenter<V extends EditProfileView> extends PickImagePresenter<V> {

    protected Profile profile;
    protected ProfileManager profileManager;
    private APIService mAPIService;




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

                    mAPIService = ApiUtils.getAPIService();
                    sendPost(profile.isActive(), profile.getEmail(),profile.getId(),profile.getLikesCount(),profile.getMobile(),profile.getNumresidence(),profile.getPhotoUrl(),profile.getResidence(),profile.getToken(),profile.isType(),profile.getUsername());


                    SharedPreferences.Editor editor = context.getSharedPreferences("Myprefsfile",MODE_PRIVATE).edit();
                    editor.putString("sharedprefresidence", residence);
                }
            });
        }
    }

    public void sendPost(Boolean active, String email,String id,Long likescount,String mobile,String numresidence,String photoUrl,String residence,String token,Boolean typeuser,String username) {

        mAPIService.savePost(active, email, id,likescount,mobile,numresidence,photoUrl,residence,token,typeuser,username).enqueue(new Callback<PostProfile>() {

            @Headers("Content-Type: application/json")
            @Override
            public void onResponse(Call<PostProfile> call, Response<PostProfile> response) {

                if(response.isSuccessful()) {
                    Log.v("loggingresponse", "post submitted to API." + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<PostProfile> call, Throwable t) {
                Log.v("loggingresponse","error"+ t.getMessage());
            }
        });
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
