package com.example.annah.cityguide.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.annah.cityguide.Helper.Config;
import com.example.annah.cityguide.R;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreanActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want
    private static final String TAG = MainActivity.class.getSimpleName();
    public static int APP_REQUEST_CODE = 99;
    String[] permissions= new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
    };
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(pref.contains("logged_in")){
                    if(pref.getBoolean("logged_in",true)){
                        Intent i = new Intent(SplashScreanActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashScreanActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Intent i = new Intent(SplashScreanActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Wave wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    String token = FirebaseInstanceId.getInstance().getToken();
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };
//        ChangeActivity();

    }

    private void displayFirebaseRegId() {
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){
            //txtRegId.setText("Firebase Reg Id: " + regId);

        } else {
            Toast.makeText(getApplicationContext(), "Reg id has not received", Toast.LENGTH_LONG).show();
            //reg id is not recieved yet
        }

    }

//    private void ChangeActivity() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Write code for your refresh logic
//                /*if (checkPermissions()){
//                //  permissions  granted.
//                    phoneLogin();
//                    finish();
//            }*/
//
//
//
//
//            }
//        }, 1000);
//    }


}
