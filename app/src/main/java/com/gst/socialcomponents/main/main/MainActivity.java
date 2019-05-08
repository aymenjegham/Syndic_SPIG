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

package com.gst.socialcomponents.main.main;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.Application;
import com.gst.socialcomponents.Constants;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.CustomFacturesMonthsAdapter;
import com.gst.socialcomponents.adapters.PostsAdapter;
import com.gst.socialcomponents.data.GetFacture;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.main.base.BaseActivity;
import com.gst.socialcomponents.main.editProfile.EditProfileActivity;
import com.gst.socialcomponents.main.followPosts.FollowingPostsActivity;
import com.gst.socialcomponents.main.post.createPost.CreatePostActivity;
import com.gst.socialcomponents.main.postDetails.PostDetailsActivity;
import com.gst.socialcomponents.main.profile.ProfileActivity;
import com.gst.socialcomponents.main.search.SearchActivity;
import com.gst.socialcomponents.model.Factureitemdata;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.Post;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.SoldeAppartement;
import com.gst.socialcomponents.utils.AnimationUtils;
import com.gst.socialcomponents.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Headers;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    private PostsAdapter postsAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private TextView newPostsCounterTextView;
    private boolean counterAnimationInProgress = false;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    Constants constants;
    final Date[] strtodate = new Date[1];



    FirebaseUser firebaseUser;
    DatabaseReference reference,reference2,reference3;
    public Boolean typeuser;
    Toolbar toolbar ;

    String residence;
    String token;
    String email;
    boolean isModerator;
    String numresidence;
    private Menu menu;
    private Menu menuDr;
    private APIService mAPIService;
    private DrawerLayout drawerLayout;
    private  NavigationView navigationView;
    private ImageView drawerImage;
    private  TextView drawerUsername;
    private  TextView drawerresidence;


    private TextView horizontalTextView;
    private LinearLayout  horizontalOuterLayout;
    private HorizontalScrollView horizontalScrollview;

    private int scrollMax;
    private int scrollPos =	0;
    private TimerTask clickSchedule;
    private TimerTask scrollerSchedule;
    private TimerTask faceAnimationSchedule;
    private Button clickedButton	=	null;
    private Timer scrollTimer		=	null;
    private Timer clickTimer		=	null;
    private Timer faceTimer         =   null;
    private Boolean isFaceDown      =   true;
    private String[]      nameArray = {"ad","ad2", "ad3", "ad4", "ad5", "ad6","ad7", "ad","ad2", "ad3", "ad4", "ad5", "ad6","ad7"};
    private String[] imageNameArray = {"ad","ad2", "ad3", "ad4", "ad5", "ad6","ad7", "ad","ad2", "ad3", "ad4", "ad5", "ad6","ad7"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.onProfileMenuActionClicked();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        horizontalTextView    = (TextView)findViewById(R.id.horizontal_textview_id);

        horizontalScrollview  = (HorizontalScrollView) findViewById(R.id.horiztonal_scrollview_id);
        horizontalOuterLayout =	(LinearLayout)findViewById(R.id.horiztonal_outer_layout_id);
        horizontalScrollview.setHorizontalScrollBarEnabled(false);
        addImagesToView();

        ViewTreeObserver vto 	=	horizontalOuterLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                horizontalOuterLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                getScrollMaxAmount();
                startAutoScrolling();
            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

               switch (menuItem.getItemId()) {
                    case R.id.profile:
                        presenter.onProfileMenuActionClicked();
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                   case R.id.config:
                       Intent config = new Intent(getApplicationContext(), SettingsActivity.class);
                       startActivity(config);
                       menuItem.setChecked(true);
                       drawerLayout.closeDrawers();
                       return true;

                    case R.id.followingPosts:
                        Intent followingPosts = new Intent(getApplicationContext(), FollowingPostsActivity.class);
                        startActivity(followingPosts);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.search:
                        Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(searchIntent);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.ticketing:


                        if (typeuser ==null){
                            Intent ticket = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(ticket);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }
                        if(typeuser != null && typeuser==false){
                            Intent ticket = new Intent(getApplicationContext(), TicketActivity.class);
                            startActivity(ticket);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }else if (typeuser != null && typeuser==true){
                            Intent ticket = new Intent(getApplicationContext(), TicketActivityMod.class);
                            startActivity(ticket);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.notif:
                        Intent notif = new Intent(getApplicationContext(), NotifActivity.class);
                        startActivity(notif);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.tools:
                        if (typeuser ==null){

                            Intent ticket = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(ticket);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }
                        if(typeuser != null && typeuser==false){

                            Intent tools = new Intent(getApplicationContext(), ToolActivity.class);
                            startActivity(tools);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }else if (typeuser != null && typeuser==true){

                            Intent tools = new Intent(getApplicationContext(), ToolsActivityMod.class);
                            startActivity(tools);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();

                        }
                        return true;

                    case R.id.gallerie:
                        Intent gallery = new Intent(getApplicationContext(), GalleryActivity.class);
                        startActivity(gallery);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.about:
                        Intent apropos = new Intent(getApplicationContext(), About.class);
                        startActivity(apropos);
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.calendar:
                        if (typeuser ==null){

                            Intent tomain = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(tomain);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }
                        if(typeuser != null && typeuser==false){

                            Intent tocalendar = new Intent(getApplicationContext(), CalendarActivity.class);
                            startActivity(tocalendar);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();
                        }else if (typeuser != null && typeuser==true){

                            Intent calendaractivitymod = new Intent(getApplicationContext(), CalendarActivityMod.class);
                            startActivity(calendaractivitymod);
                            menuItem.setChecked(true);
                            drawerLayout.closeDrawers();

                        }
                        return true;
                }
                return true;
            }
        });

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mAPIService = ApiUtils.getAPIService();



        initContentView();



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

             reference = FirebaseDatabase.getInstance().getReference("profiles").child(firebaseUser.getUid());

            getdateofpay();


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Profilefire profile = dataSnapshot.getValue(Profilefire.class);


                    residence =profile.getResidence();
                    isModerator =profile.isType();
                    token=profile.gettoken();
                    email=profile.getEmail();
                    numresidence=profile.getNumresidence();

                    SharedPreferences.Editor editor = getSharedPreferences("Myprefsfile",MODE_PRIVATE).edit();
                    editor.putString("sharedprefresidence", residence);
                    editor.putBoolean("sharedprefismoderator",isModerator);
                    editor.putString("sharedprefemail",email);
                    editor.putString("sharedprefnumresidence",numresidence);
                    editor.apply();






                    typeuser=profile.isType();



                     if(typeuser== true){

                         changesetup(profile);

                         reference3 = FirebaseDatabase.getInstance().getReference().child("moderators").child(residence);
                         reference3.child(token).setValue(true);


                    }
                    if(typeuser== false){
                        reference3 = FirebaseDatabase.getInstance().getReference().child("moderators").child(residence).child(token);
                        Task<Void> task = reference3.removeValue();
                        task.addOnCompleteListener(task1 -> LogUtil.logDebug(TAG, "removeRegistrationToken, success: " + task1.isSuccessful()));

                        changesetuptodefault(profile);

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

                    //pd.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


        }








    }

    public void addImagesToView(){
        for (int i=0;i<imageNameArray.length;i++){
            final Button imageButton =	new Button(this);
            int imageResourceId =getResources().getIdentifier(imageNameArray[i], "drawable",getPackageName());
            Drawable image  =this.getResources().getDrawable(imageResourceId);
            imageButton.setBackgroundDrawable(image);
            imageButton.setTag(i);
            imageButton.setTextSize(50);
            imageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    if(isFaceDown){
                        if(clickTimer!= null){
                            clickTimer.cancel();
                            clickTimer			=	null;
                        }
                        clickedButton			=	(Button)arg0;
                        stopAutoScrolling();
                        clickedButton.startAnimation(scaleFaceUpAnimation());
                        clickedButton.setSelected(true);
                        clickTimer				=	new Timer();

                        if(clickSchedule != null) {
                            clickSchedule.cancel();
                            clickSchedule = null;
                        }

                        clickSchedule = new TimerTask(){
                            public void run() {
                                startAutoScrolling();
                            }
                        };

                        clickTimer.schedule( clickSchedule, 1500);
                    }
                }
            });

            LinearLayout.LayoutParams params 	=	new LinearLayout.LayoutParams(980,300);
            params.setMargins(25, 0, 25, 0);
            imageButton.setLayoutParams(params);
            horizontalOuterLayout.addView(imageButton);
        }
    }

    public void getScrollMaxAmount(){
        int actualWidth = (horizontalOuterLayout.getMeasuredWidth()-512);
        scrollMax   = actualWidth;
    }

    public void startAutoScrolling(){
        if (scrollTimer == null) {
            scrollTimer	=	new Timer();
            final Runnable Timer_Tick 	= 	new Runnable() {
                public void run() {
                    moveScrollView();
                }
            };

            if(scrollerSchedule != null){
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask(){
                @Override
                public void run(){
                    runOnUiThread(Timer_Tick);
                }
            };

            scrollTimer.schedule(scrollerSchedule, 30, 30);
        }
    }


    public void stopAutoScrolling(){
        if (scrollTimer != null) {
            scrollTimer.cancel();
            scrollTimer	=	null;
        }
    }

    public void moveScrollView(){
        scrollPos= 	(int) (horizontalScrollview.getScrollX() + 1.0);
        if(scrollPos >= scrollMax){
            scrollPos=	0;
        }
        horizontalScrollview.scrollTo(scrollPos, 0);

    }

    public Animation scaleFaceUpAnimation(){
        Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleFace.setDuration(500);
        scaleFace.setFillAfter(true);
        scaleFace.setInterpolator(new AccelerateInterpolator());
        Animation.AnimationListener	scaleFaceAnimationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                horizontalTextView.setText(nameArray[(Integer) clickedButton.getTag()]);
                isFaceDown = false;
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {}
            @Override
            public void onAnimationEnd(Animation arg0) {
                if(faceTimer != null){
                    faceTimer.cancel();
                    faceTimer = null;
                }

                faceTimer = new Timer();
                if(faceAnimationSchedule != null){
                    faceAnimationSchedule.cancel();
                    faceAnimationSchedule = null;
                }
                faceAnimationSchedule = new TimerTask() {
                    @Override
                    public void run() {
                        faceScaleHandler.sendEmptyMessage(0);
                    }
                };

                faceTimer.schedule(faceAnimationSchedule, 750);
            }
        };
        scaleFace.setAnimationListener(scaleFaceAnimationListener);
        return scaleFace;
    }

    private Handler faceScaleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(clickedButton.isSelected() == true)
                clickedButton.startAnimation(scaleFaceDownAnimation(500));
        }
    };

    public Animation scaleFaceDownAnimation(int duration){
        Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleFace.setDuration(duration);
        scaleFace.setFillAfter(true);
        scaleFace.setInterpolator(new AccelerateInterpolator());
        Animation.AnimationListener	scaleFaceAnimationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {}
            @Override
            public void onAnimationRepeat(Animation arg0) {}
            @Override
            public void onAnimationEnd(Animation arg0) {
                horizontalTextView.setText("");
                isFaceDown = true;
            }
        };
        scaleFace.setAnimationListener(scaleFaceAnimationListener);
        return scaleFace;
    }
/*
    public void loadFactures() {
        mAPIService.getFacture(4).enqueue(new Callback<GetFacture>() {

            @Headers("Content-Type: application/json")
            @Override
            public void onResponse(Call<GetFacture> call, Response<GetFacture> response) {

                if(response.isSuccessful()) {
                     Log.d("testinggetinmain", "posts loaded from API"+response.body().getTitle()+" "+response.body().getCompleted()+" "+response.body().getId()+" "+response.body().getUserId());
                }else {
                    int statusCode  = response.code();
                 }
            }

            @Override
            public void onFailure(Call<GetFacture> call, Throwable t) {
                 Log.d("testinggetinmain", "error loading from API"+t.getMessage());

            }
        });
    }  */

    private void changesetup(Profilefire profile) {

       //toolbar.setTitle("Moderateur");
       toolbar.setBackgroundColor(0xffB22222);
       if(menu != null){
           menu.getItem(4).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_key));
           menu.getItem(3).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_signali_white));
           menu.getItem(3).setTitle("Publications signalés");

       }
        View header = navigationView.getHeaderView(0);
        header.setBackgroundColor(0xffB22222);
         menuDr = navigationView.getMenu();
        menuDr.getItem(5).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_key));
        menuDr.getItem(6).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_signali));
        menuDr.getItem(6).setTitle("Publications signalés");


        drawerImage = (ImageView) header.findViewById(R.id.drawer_img);
        drawerUsername = (TextView) header.findViewById(R.id.drawernameTv);
        drawerresidence = (TextView) header.findViewById(R.id.residenceheadertv);

        drawerUsername.setText(profile.getUsername());
        drawerresidence.setText(profile.getResidence());
        Glide.with(getApplicationContext()).load(profile.getPhotoUrl()).into(drawerImage);



    }
    private void changesetuptodefault(Profilefire profile) {

        //toolbar.setTitle("Syndic IG");
        toolbar.setBackgroundColor(getResources().getColor(R.color.send_button_color));

        View header = navigationView.getHeaderView(0);
        header.setBackgroundColor(getResources().getColor(R.color.send_button_color));
        menuDr = navigationView.getMenu();
        menuDr.getItem(5).setTitle("Reglements");


        drawerImage = (ImageView) header.findViewById(R.id.drawer_img);
        drawerUsername = (TextView) header.findViewById(R.id.drawernameTv);
        drawerresidence = (TextView) header.findViewById(R.id.residenceheadertv);

        drawerUsername.setText(profile.getUsername());
        drawerresidence.setText(profile.getResidence());
        Glide.with(getApplicationContext()).load(profile.getPhotoUrl()).into(drawerImage);

    }



    void   showalert(){
        WebView myWebView = new WebView(MainActivity.this);
        myWebView.loadUrl("http://google.com/");
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

         new AlertDialog.Builder(MainActivity.this,R.xml.styles)
                .setTitle("En attente de vérification")
                .setMessage("Votre adhésion est en attente de confirmation d'un modérateur" +
                        ",vous serez notifié de l'activation de votre compte")
                .setCancelable(false)
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      Intent intent =new Intent(MainActivity.this,GalleryActivity.class);
                      startActivity(intent);
                    }
                })
                .setNeutralButton("Modifier votre profile", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(MainActivity.this, EditProfileActivity.class);
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
                 //.setView(myWebView)


                 .show();
     }







    @Override
    protected void onResume() {
        super.onResume();

        initContentView();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if (firebaseUser != null) {


            reference = FirebaseDatabase.getInstance().getReference("profiles").child(firebaseUser.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Profilefire profile = dataSnapshot.getValue(Profilefire.class);
                    residence =profile.getResidence();
                    isModerator =profile.isType();
                    numresidence=profile.getNumresidence();

                    SharedPreferences.Editor editor = getSharedPreferences("Myprefsfile",MODE_PRIVATE).edit();
                    editor.putString("sharedprefresidence", residence);
                    editor.putBoolean("sharedprefismoderator",isModerator);
                    editor.putString("sharedprefnumresidence",numresidence);
                    editor.apply();

                    if (!profile.isActive()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showalert();
                            }
                        });
                    }



                    typeuser=profile.isType();

                    if(typeuser== true){

                        changesetup(profile);

                    }
                    else if(typeuser == false){
                        changesetuptodefault(profile);

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }

    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        if (presenter == null) {
            return new MainPresenter(this);
        }
        return presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST:
                   refreshPostList();
                    break;
                case CreatePostActivity.CREATE_NEW_POST_REQUEST:
                    presenter.onPostCreated();
                    break;

                case PostDetailsActivity.UPDATE_POST_REQUEST:
                    presenter.onPostUpdated(data);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        attemptToExitIfRoot(floatingActionButton);
     }

    public void refreshPostList() {
        postsAdapter.loadFirstPage();
        if (postsAdapter.getItemCount() > 0) {
            recyclerView.scrollToPosition(0);
        }

    }


    @Override
    public void removePost() {
        postsAdapter.removeSelectedPost();
    }

    @Override
    public void updatePost() {
        postsAdapter.updateSelectedPost();
    }

    @Override
    public void showCounterView(int count) {
        AnimationUtils.showViewByScaleAndVisibility(newPostsCounterTextView);
        String counterFormat = getResources().getQuantityString(R.plurals.new_posts_counter_format, count, count);
        newPostsCounterTextView.setText(String.format(counterFormat, count));
    }

    private void initContentView() {
        if (recyclerView == null) {
             progressBar = findViewById(R.id.progressBar);
            swipeContainer = findViewById(R.id.swipeContainer);

            initFloatingActionButton();
            initPostListRecyclerView();
           initPostCounter();
        }
    }

    private void initFloatingActionButton() {
        floatingActionButton = findViewById(R.id.addNewPostFab);
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(v -> presenter.onCreatePostClickAction(floatingActionButton));
        }
    }

    private void initPostListRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        postsAdapter = new PostsAdapter(this, swipeContainer);
        postsAdapter.setCallback(new PostsAdapter.Callback() {
            @Override
            public void onItemClick(final Post post, final View view) {

                presenter.onPostClicked(post, view);
            }

            @Override
            public void onListLoadingFinished() {

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAuthorClick(String authorId, View view) {
                openProfileActivity(authorId, view);
            }

            @Override
            public void onCanceled(String message) {
                progressBar.setVisibility(View.GONE);
                showToast(message);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
       recyclerView.setAdapter(postsAdapter);
         postsAdapter.loadFirstPage();




    }

    private void initPostCounter() {
        newPostsCounterTextView = findViewById(R.id.newPostsCounterTextView);
       newPostsCounterTextView.setOnClickListener(v -> refreshPostList());

        presenter.initPostCounter();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                hideCounterView();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void hideCounterView() {
        if (!counterAnimationInProgress && newPostsCounterTextView.getVisibility() == View.VISIBLE) {
            counterAnimationInProgress = true;
            AlphaAnimation alphaAnimation = AnimationUtils.hideViewByAlpha(newPostsCounterTextView);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    counterAnimationInProgress = false;
                    newPostsCounterTextView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            alphaAnimation.start();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openPostDetailsActivity(Post post, View v) {
        Intent intent = new Intent(MainActivity.this, PostDetailsActivity.class);
        intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, post.getId());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View imageView = v.findViewById(R.id.postImageView);
            View authorImageView = v.findViewById(R.id.authorImageView);

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(MainActivity.this,
                            new android.util.Pair<>(imageView, getString(R.string.post_image_transition_name)),
                            new android.util.Pair<>(authorImageView, getString(R.string.post_author_image_transition_name))
                    );
            startActivityForResult(intent, PostDetailsActivity.UPDATE_POST_REQUEST, options.toBundle());
        } else {
            startActivityForResult(intent, PostDetailsActivity.UPDATE_POST_REQUEST);
        }
    }

    public void showFloatButtonRelatedSnackBar(int messageId) {
        showSnackBar(floatingActionButton, messageId);
    }

    @Override
    public void openCreatePostActivity() {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivityForResult(intent, CreatePostActivity.CREATE_NEW_POST_REQUEST);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openProfileActivity(String userId, View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER_ID_EXTRA_KEY, userId);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {

            View authorImageView = view.findViewById(R.id.authorImageView);

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(MainActivity.this,
                            new android.util.Pair<>(authorImageView, getString(R.string.post_author_image_transition_name)));
            startActivityForResult(intent, ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST, options.toBundle());
        } else {
            startActivityForResult(intent, ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.profile:
                presenter.onProfileMenuActionClicked();
                return true;

            case R.id.followingPosts:
                Intent followingPosts = new Intent(this, FollowingPostsActivity.class);
                startActivity(followingPosts);
                return true;

            case R.id.search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.ticketing:


                if (typeuser ==null){
                     Intent ticket = new Intent(this, MainActivity.class);
                    startActivity(ticket);
                }
                if(typeuser != null && typeuser==false){
                    Intent ticket = new Intent(this, TicketActivity.class);
                    startActivity(ticket);
                }else if (typeuser != null && typeuser==true){
                    Intent ticket = new Intent(this, TicketActivityMod.class);
                    startActivity(ticket);
                }
                return true;
            case R.id.notif:
                Intent notif = new Intent(this, NotifActivity.class);
                startActivity(notif);
                return true;

            case R.id.tools:
                if (typeuser ==null){

                     Intent ticket = new Intent(this, MainActivity.class);
                    startActivity(ticket);
                }
                if(typeuser != null && typeuser==false){

                    Intent tools = new Intent(this, ToolActivity.class);
                    startActivity(tools);
                }else if (typeuser != null && typeuser==true){

                    Intent tools = new Intent(this, ToolsActivityMod.class);
                    startActivity(tools);

                }
                return true;

            case R.id.gallerie:
                Intent gallery = new Intent(this, GalleryActivity.class);
                startActivity(gallery);
                return true;

            case R.id.about:
                Intent apropos = new Intent(this, About.class);
                startActivity(apropos);
                return true;

            case R.id.config:
                Intent config = new Intent(this, SettingsActivity.class);
                startActivity(config);
                return true;

            case R.id.calendar:
                if (typeuser ==null){

                    Intent tomain = new Intent(this, MainActivity.class);
                    startActivity(tomain);
                }
                if(typeuser != null && typeuser==false){

                    Intent tocalendar = new Intent(this, CalendarActivity.class);
                    startActivity(tocalendar);
                }else if (typeuser != null && typeuser==true){

                    Intent calendaractivitymod = new Intent(this, CalendarActivityMod.class);
                    startActivity(calendaractivitymod);

                }
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


void getdateofpay() {
    reference2 = FirebaseDatabase.getInstance().getReference().child("profiles").child(firebaseUser.getUid());//.child("numresidence");
    reference2.keepSynced(true);
    reference2.addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     Profilefire profilefire=dataSnapshot.getValue(Profilefire.class);
                     String numappart= profilefire.getNumresidence();
                    String intituleResidence =profilefire.getResidence();

                    mAPIService = ApiUtils.getAPIService();
                    mAPIService.getNumChantier(intituleResidence).enqueue(new Callback<NumChantier>() {
                        @Override
                        public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                            Integer numchantier=Integer.valueOf(response.body().getCbmarq());
                            mAPIService.getNumOfAppartements(numappart,numchantier).enqueue(new Callback<NumAppart>() {
                                @Override
                                public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                                      Integer numap=Integer.valueOf(response.body().getCbmarq());



                                    mAPIService.getSoldeappartement(Integer.valueOf(response.body().getCbmarq())).enqueue(new Callback<SoldeAppartement>() {

                                        @Override
                                        public void onResponse(Call<SoldeAppartement> call, Response<SoldeAppartement> response) {
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                            try {
                                                strtodate[0] = format.parse (response.body().getDate());
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                            Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

                                            cal.setTime(strtodate[0]);
                                            int year = cal.get(Calendar.YEAR);
                                            int month = cal.get(Calendar.MONTH);
                                            int day = cal.get(Calendar.DAY_OF_MONTH);
                                            int yearactual=cal2.get(Calendar.YEAR);
                                            int monthactual=cal2.get(Calendar.MONTH);


                                            if((year ==yearactual && month<monthactual) || (year <yearactual)){
                                                mAPIService.getInfoSyndic(numap).enqueue(new Callback<InfoSyndic>() {

                                                    @Override
                                                    public void onResponse(Call<InfoSyndic> call, Response<InfoSyndic> response) {

                                                        int periode=12;

                                                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                                        alertDialog.setTitle("Frais Syndic");
                                                        alertDialog.setMessage("votre solde pour les frais syndic est epuisée,veuillez consulter votre rubrique reglement et payez le montant adéquat");//pour les "+periode+" mois prochains");

                                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                        alertDialog.show();

                                                    }

                                                    @Override
                                                    public void onFailure(Call<InfoSyndic> call, Throwable t) {
                                                        Log.v("tesstingvalues",t.getMessage()+"  1");

                                                    }
                                                });
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<SoldeAppartement> call, Throwable t) {
                                            Log.v("tesstingvalues",t.getMessage()+"  2");

                                        }
                                    });
                                }
                                @Override
                                public void onFailure(Call<NumAppart> call, Throwable t) {
                                    Log.v("tesstingvalues",t.getMessage()+"  3");

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<NumChantier> call, Throwable t) {
                            Log.v("tesstingvalues",t.getMessage()+"  4");

                        }
                    });

                    }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.v("tesstingvalues",databaseError.getMessage());

                }

            });
}
}
