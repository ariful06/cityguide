package com.example.annah.cityguide.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.annah.cityguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anik on 4/9/2018.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_btn)
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PhoneNumberVerificationActivity.class);
                startActivity(intent);
            }
        });


    }
}
