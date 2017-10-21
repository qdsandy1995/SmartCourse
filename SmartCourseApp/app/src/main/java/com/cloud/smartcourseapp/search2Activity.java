package com.cloud.smartcourseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class search2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
    }

    /** Called when the user taps the Send button */
    public void sendMessageToSearch2(View view) {
        Intent intent = new Intent(this, CourseScreenActivity.class);
        startActivity(intent);
    }
}
