package com.cloud.smartcourseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /** Called when the user taps the Send button */
    public void sendMessageToSearch(View view) {
        Intent intent = new Intent(this, search2Activity.class);
        startActivity(intent);
    }
}
