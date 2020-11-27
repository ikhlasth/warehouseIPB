package com.ikhlast.warehouseipb.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;
import com.ikhlast.warehouseipb.Main.Home;
import com.ikhlast.warehouseipb.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    DatabaseReference db;

    @Override
    public void onNewToken(String s) {
        Log.e("NEW_TOKEN", s);
        db = FirebaseDatabase.getInstance().getReference();
        db.child("notif").setValue(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

}
