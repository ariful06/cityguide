package com.example.annah.cityguide.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.annah.cityguide.R;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberVerificationActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;
    String[] permissions = new String[]{
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
    };
    ProgressDialog prog_dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);

        pref = getSharedPreferences("MyPref",MODE_PRIVATE);
        editor = pref.edit();
        phoneLogin();
    }
    public void phoneLogin() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        UIManager uiManager = new SkinManager(SkinManager.Skin.CONTEMPORARY, getResources().getColor(R.color.colorPrimary), R.drawable.background,
                SkinManager.Tint.WHITE,
                0.10);


        configurationBuilder.setUIManager(uiManager);
        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                //showErrorActivity(loginResult.getError());
                Toast.makeText(this, loginResult.getError() + "", Toast.LENGTH_LONG).show();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.
                // Success! Start your next activity...
                getPhoneNumber();
            }
            // Surface the result to your user in an appropriate way.
        }
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    public void getPhoneNumber() {

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();
                Log.e("Login", phoneNumberString);
                editor.putString("user_phn",phoneNumberString);
                editor.commit();
                showLoading();


                //here will be an api for checking the user and than it will go to the main activity
                Intent intent = new Intent(PhoneNumberVerificationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


                //checkUser(phoneNumberString,pref.getString("regId",""));
                // Get email
                //String email = account.getEmail();
            }
            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
                Log.e("Login", error.toString());
            }
        });
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), SplashScreanActivity.MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SplashScreanActivity.MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    Toast.makeText(this, "Thanks for allowing the permissions", Toast.LENGTH_LONG).show();
                    if (isInternetAvailable()) {
                        phoneLogin();

                    } else {
                        Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // no permissions granted.
                    Toast.makeText(this, "Sorry you can't proceed without allowing the permissions", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void showLoading(){
        prog_dialog = ProgressDialog.show(PhoneNumberVerificationActivity.this, "",
                "Please wait...", true);
        prog_dialog.setCancelable(false);
        prog_dialog.show();
    }


}
