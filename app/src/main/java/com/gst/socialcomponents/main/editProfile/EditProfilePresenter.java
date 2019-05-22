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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.data.PostProfile;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.main.base.BaseView;
import com.gst.socialcomponents.main.editProfile.createProfile.CreateProfileActivity;
import com.gst.socialcomponents.main.login.LoginActivity;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.pickImageBase.PickImagePresenter;
import com.gst.socialcomponents.managers.ProfileManager;
import com.gst.socialcomponents.managers.listeners.OnObjectChangedListenerSimple;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumBloc;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.Profile;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.utils.ValidationUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Headers;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Alexey on 03.05.18.
 */

public class EditProfilePresenter<V extends EditProfileView> extends PickImagePresenter<V> {

    protected Profile profile;
    protected ProfileManager profileManager;
    private APIService mAPIService;
    String numresidence;
    String cbresidence;
    Boolean ismoderator;
    private DatabaseReference reference;






    protected EditProfilePresenter(Context context) {
        super(context);
         profileManager = ProfileManager.getInstance(context.getApplicationContext());
        SharedPreferences prefs = context.getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        numresidence=prefs.getString("sharedprefnumresidence",null);
        ismoderator=prefs.getBoolean("sharedprefismoderator",false);

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
                        view.setBloc(profile.getBloc());


                        if (profile.getPhotoUrl() != null) {
                            view.setProfilePhoto(profile.getPhotoUrl());
                        }
                    }
                    mAPIService = ApiUtils.getAPIService();


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
                String bloc=view.getBlocText().trim();

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
                /*    profile.setUsername(name);
                    profile.setResidence(residence);
                    profile.setNumresidence(numresidence);
                    profile.setMobile(mobile);
                    profile.setToken( FirebaseInstanceId.getInstance().getToken());
                    profile.setActive(ismoderator);
                    profile.setBloc(bloc);  */



                    mAPIService = ApiUtils.getAPIService();
                     mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
                        @Override
                        public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                           Integer numchantier=Integer.valueOf(response.body().getCbmarq());


                             mAPIService.getbloc(bloc,numchantier).enqueue(new Callback<NumBloc>() {
                                @Override
                                public void onResponse(Call<NumBloc> call, Response<NumBloc> response) {

                                    Integer numbloc =Integer.valueOf(response.body().getCbmarq());

                                    mAPIService.getNumOfAppartements(numresidence,numbloc).enqueue(new Callback<NumAppart>() {
                                        @Override
                                        public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                                            reference = FirebaseDatabase.getInstance().getReference().child("profiles");
                                            reference.keepSynced(true);
                                            reference.addListenerForSingleValueEvent(
                                                    new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                Profilefire profilefire = ds.getValue(Profilefire.class);
                                                                if((profilefire.getId().equals(profile.getId()))){
                                                                    reference.child(profile.getId()).child("username").setValue(name);
                                                                    reference.child(profile.getId()).child("residence").setValue(residence);
                                                                    reference.child(profile.getId()).child("numresidence").setValue(numresidence);
                                                                    reference.child(profile.getId()).child("mobile").setValue(mobile);
                                                                    reference.child(profile.getId()).child("token").setValue(FirebaseInstanceId.getInstance().getToken());
                                                                    reference.child(profile.getId()).child("active").setValue(ismoderator);
                                                                    reference.child(profile.getId()).child("bloc").setValue(bloc);

                                                                }

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                            Toast.makeText(getApplicationContext(), "Error connexion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });





                                            cbresidence=response.body().getCbmarq();
                                            sendPost(profile.getPhotoUrl(),profile.isActive(), profile.getEmail(),profile.getId(),mobile,name,residence,Integer.valueOf(cbresidence),ismoderator);

                                        }

                                        @Override
                                        public void onFailure(Call<NumAppart> call, Throwable t) {

                                            Log.v("checkingprobnull","failed");
                                            Toast.makeText(context, "Appartement non existante ou mauvaise connexion", Toast.LENGTH_SHORT).show();
                                            return;

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<NumBloc> call, Throwable t) {

                                }
                            });




                        }

                        @Override
                        public void onFailure(Call<NumChantier> call, Throwable t) {

                        }
                    });



                    createOrUpdateProfile(imageUri);

                    mAPIService = ApiUtils.getAPIService();
                    //sendPost(profile.isActive(), profile.getEmail(),profile.getId(),profile.getLikesCount(),profile.getMobile(),profile.getNumresidence(),profile.getPhotoUrl(),profile.getResidence(),profile.getToken(),profile.isType(),profile.getUsername());
               //     sendPost(profile.getPhotoUrl(),profile.isActive(), profile.getEmail(),profile.getId(),profile.getMobile(),profile.getUsername(),profile.getResidence(),1,ismoderator);

                    SharedPreferences.Editor editor = context.getSharedPreferences("Myprefsfile",MODE_PRIVATE).edit();
                    editor.putString("sharedprefresidence", residence);


                }
            });
        }
    }

    public void sendPost(String photoUrl,Boolean active, String email,String id,String mobile,String username,String residence,Integer numresidence,boolean type) {
        mAPIService.savePost(photoUrl,active, email, id,mobile,username,residence,numresidence,type,true).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()) {
                     if(response.body().equals("New record created successfully")){
                         Toast.makeText(context, "Profile sauvegardé avec succées", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(context, "Propleme connection", Toast.LENGTH_SHORT).show();
                         Log.v("testingcreateprofile",response.body());

                     }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Erreur connectivité,réessayer ultérieurement", Toast.LENGTH_SHORT).show();

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
