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

package com.gst.socialcomponents.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gst.socialcomponents.Constants;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.MainActivity;
import com.gst.socialcomponents.main.postDetails.PostDetailsActivity;
import com.gst.socialcomponents.managers.PostManager;
import com.gst.socialcomponents.room.DatabaseClient;
import com.gst.socialcomponents.room.Notif;
import com.gst.socialcomponents.utils.GlideApp;
import com.gst.socialcomponents.utils.ImageUtil;
import com.gst.socialcomponents.utils.LogUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by alexey on 13.04.17.
 */


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private static int notificationId = 0;


    private static final String POST_ID_KEY = "postId";
    private static final String AUTHOR_ID_KEY = "authorId";
    private static final String ACTION_TYPE_KEY = "actionType";
    private static final String TITLE_KEY = "title";
    private static final String BODY_KEY = "body";
    private static final String ICON_KEY = "icon";
    private static final String ACTION_TYPE_NEW_LIKE = "new_like";
    private static final String ACTION_TYPE_NEW_COMMENT = "new_comment";
    private static final String ACTION_TYPE_NEW_POST = "new_post";
    private static final String ACTION_TYPE_NEW_MEMBER = "new_member_activated";
    private static final String ACTION_TYPE_NEW_MODERATOR =  "new_moderator_activated";

    String residencepref;




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData() != null && remoteMessage.getData().get(ACTION_TYPE_KEY) != null) {
            handleRemoteMessage(remoteMessage);
        } else {
            LogUtil.logError("remotedmessagehandler", "onMessageReceived()", new RuntimeException("FCM remoteMessage doesn't contains Action Type"));
        }
    }

    private void handleRemoteMessage(RemoteMessage remoteMessage) {
        String receivedActionType = remoteMessage.getData().get(ACTION_TYPE_KEY);
        LogUtil.logDebug("remotedmessagehandler", "Message Notification Action Type: " + remoteMessage);



        switch (receivedActionType) {

            case ACTION_TYPE_NEW_LIKE:
                parseCommentOrLike(Channel.NEW_LIKE, remoteMessage);
                break;
            case ACTION_TYPE_NEW_COMMENT:
                parseCommentOrLike(Channel.NEW_COMMENT, remoteMessage);
                break;
            case ACTION_TYPE_NEW_POST:
                handleNewPostCreatedAction(remoteMessage);
                break;
            case ACTION_TYPE_NEW_MEMBER:
                handleactivatedaccount(Channel.NEW_COMMENT, remoteMessage);
                break;
            case ACTION_TYPE_NEW_MODERATOR:
                handleactivatedmoderatoraccount(Channel.NEW_COMMENT, remoteMessage);
                break;

        }
    }


    private void handleactivatedmoderatoraccount(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String status = remoteMessage.getData().get("statuactivity");

        if(status.equals("true")){
            notificationBody="Votre compte moderateur a été activé";
        }else if (status.equals("false")) {
            notificationBody = "Votre compte moderateur a été désactivé";
        }



        Intent backIntent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, postId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent);
        LogUtil.logDebug("remotedmessagehandler", "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));

    }


    private void handleactivatedaccount(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String status = remoteMessage.getData().get("statuactivity");

        if(status.equals("true")){
             notificationBody="Votre compte a été activé";
        }else if (status.equals("false")) {
             notificationBody = "Votre compte a été désactivé";
        }



        Intent backIntent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, postId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent);
        LogUtil.logDebug("remotedmessagehandler", "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));

    }

    private void handleNewPostCreatedAction(RemoteMessage remoteMessage) {
        String postAuthorId = remoteMessage.getData().get(AUTHOR_ID_KEY);
        String residencei =remoteMessage.getData().get("residencei");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        //Send notification for each users except author of post.
        if (firebaseUser != null && !firebaseUser.getUid().equals(postAuthorId)) {

            SharedPreferences prefs = getApplicationContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
            residencepref = prefs.getString("sharedprefresidence", null);
            Log.v("testingifitpass",residencei+"---"+residencepref);

            if(residencepref.equals(residencei)){
                Log.v("testingifitpass","done");
                PostManager.getInstance(this.getApplicationContext()).incrementNewPostsCounter();

            }
        }
    }

    private void parseCommentOrLike(Channel channel, RemoteMessage remoteMessage) {
        String notificationTitle = remoteMessage.getData().get(TITLE_KEY);
        String notificationBody = remoteMessage.getData().get(BODY_KEY);
        String notificationImageUrl = remoteMessage.getData().get(ICON_KEY);
        String postId = remoteMessage.getData().get(POST_ID_KEY);

        Intent backIntent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(this, PostDetailsActivity.class);
        intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, postId);

        Bitmap bitmap = getBitmapFromUrl(notificationImageUrl);

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, backIntent);
        LogUtil.logDebug("remotedmessagehandler", "Message Notification Body: " + remoteMessage.getData().get(BODY_KEY));
    }

    @Nullable
    public Bitmap getBitmapFromUrl(String imageUrl) {
        return ImageUtil.loadBitmap(GlideApp.with(this), imageUrl, Constants.PushNotification.LARGE_ICONE_SIZE, Constants.PushNotification.LARGE_ICONE_SIZE);
    }

    private void sendNotification(Channel channel, String notificationTitle, String notificationBody, Bitmap bitmap, Intent intent) {

        sendNotification(channel, notificationTitle, notificationBody, bitmap, intent, null);
    }

    private void sendNotification(Channel channel, String notificationTitle, String notificationBody, Bitmap bitmap, Intent intent, Intent backIntent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent;

        if (backIntent != null) {
            backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent[] intents = new Intent[]{backIntent, intent};
            pendingIntent = PendingIntent.getActivities(this, notificationId++, intents, PendingIntent.FLAG_ONE_SHOT);
        } else {
          pendingIntent = PendingIntent.getActivity(this, notificationId++, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel.id);
        notificationBuilder.setAutoCancel(true)   //Automatically delete the notification
                .setSmallIcon(R.drawable.ic_push_notification_small) //Notification icon
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setLargeIcon(bitmap)
                .setSound(defaultSoundUri);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channel.id, getString(channel.name), importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(this, R.color.primary));
            notificationChannel.enableVibration(true);
            notificationBuilder.setChannelId(channel.id);
            notificationManager.createNotificationChannel(notificationChannel);

         }

        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                double estimatedServerTimeMs = System.currentTimeMillis() + offset;
                saveNotif(notificationTitle,notificationBody,estimatedServerTimeMs,false);
             }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });


        notificationManager.notify(notificationId++, notificationBuilder.build());

    }

    enum Channel {
        NEW_LIKE("new_like_id", R.string.new_like_channel_name),
        NEW_COMMENT("new_comment_id", R.string.new_comment_channel_name);

        String id;
        @StringRes
        int name;

        Channel(String id, @StringRes int name) {
            this.id = id;
            this.name = name;
        }
    }


    private void saveNotif(String notiftitle,String notifbody,double currenttime,Boolean isseen) {


        class SaveNotif extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Notif notif = new Notif();
                notif.setTitle(notiftitle);
                notif.setDesc(notifbody);
                notif.setCurrenttime(currenttime);
                notif.setFinished(isseen);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .notifDao()
                        .insert(notif);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }

        SaveNotif sn = new SaveNotif();
        sn.execute();
    }



}
