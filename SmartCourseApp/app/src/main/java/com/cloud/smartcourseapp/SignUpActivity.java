package com.cloud.smartcourseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
    private Button signUPButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUPButton = findViewById(R.id.buttonRegister);

    }
    public void registerClick(View view)
    {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

}
