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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.pickImageBase.PickImageActivity;
import com.gst.socialcomponents.utils.GlideApp;
import com.gst.socialcomponents.utils.ImageUtil;

public class EditProfileActivity<V extends EditProfileView, P extends EditProfilePresenter<V>> extends PickImageActivity<V, P> implements EditProfileView {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    // UI references.
    private EditText nameEditText;
    private EditText residenceEditText;
    private EditText numresidenceEditText;
    private EditText numtelEditText;
    protected ImageView imageView;
    private ProgressBar avatarProgressBar;
    private  Spinner spin;

    String[] residencesNames={"Votre residence","Alpes","Arcades","Chott-maryem","Ennakhil","Houda","Houda 2","Jasmins","Jasmins 2","Jasmins 3","Jura","K2","Lotissement Msaken","Maarouf","Palms","Prestige","Pyrenées","Sierra","Vosges"};
    String selecteditem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        avatarProgressBar = findViewById(R.id.avatarProgressBar);
        imageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.nameEditText);
        numresidenceEditText=findViewById(R.id.numresidenceEditText);
         spin = findViewById(R.id.simpleSpinner);
        numtelEditText=findViewById(R.id.numtelEditText);


        imageView.setOnClickListener(this::onSelectImageClick);

         ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,residencesNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                residencesNames[0] = "Alpes";
                selecteditem=residencesNames[position];
                SharedPreferences.Editor editor = getSharedPreferences("Myprefsfile",MODE_PRIVATE).edit();
                editor.putString("sharedprefresidence", residencesNames[position]);
                Log.v("testingmodifresid",residencesNames[position]);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setNumresidenceError("selectionne ton residence");

            }
        });

        initContent();
    }











    @NonNull
    @Override
    public P createPresenter() {
        if (presenter == null) {
            return (P) new EditProfilePresenter(this);
        }
        return presenter;
    }

    protected void initContent() {
        presenter.loadProfile();
    }

    @Override
    public ProgressBar getProgressView() {
        return avatarProgressBar;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void onImagePikedAction() {
        startCropImageActivity();
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        handleCropImageResult(requestCode, resultCode, data);
    }

    @Override
    public void setName(String username) {
        nameEditText.setText(username);
    }

    @Override
    public void setToken(String token) {

    }

    @Override
    public void setResidence(String residencename) {
       // residenceEditText.setText(residencename);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,residencesNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        residencesNames[0] = residencename;
        spin.setAdapter(aa);
     }

    @Override
    public void setNumresidence(String numresidencetext) {
        numresidenceEditText.setText(numresidencetext);

    }

    @Override
    public void setMobile(String numtel) {
        numtelEditText.setText(numtel);
    }

    @Override
    public void setProfilePhoto(String photoUrl) {
        ImageUtil.loadImage(GlideApp.with(this), photoUrl, imageView, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                avatarProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                avatarProgressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }

    @Override
    public String getNameText() {
        return nameEditText.getText().toString();
    }

    @Override
    public String getResidenceText() {
        return selecteditem;
                //residenceEditText.getText().toString();
    }

    @Override
    public String getNumresidenceText() {
        return numresidenceEditText.getText().toString();
    }

    @Override
    public String getMobileText() {
        return numtelEditText.getText().toString();
    }



    @Override
    public void setNameError(@Nullable String string) {
        nameEditText.setError(string);
        nameEditText.requestFocus();
    }

    @Override
    public void setResidenceError(String string) {
        residenceEditText.setError(string);
        residenceEditText.requestFocus();
    }

    @Override
    public void setNumresidenceError(String string) {
        numresidenceEditText.setError(string);
        numresidenceEditText.requestFocus();
    }

    @Override
    public void setMobileError(String string) {
        numtelEditText.setError(string);
        numtelEditText.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                presenter.attemptCreateProfile(imageUri);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

