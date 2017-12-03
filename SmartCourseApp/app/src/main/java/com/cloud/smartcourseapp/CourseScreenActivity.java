package com.cloud.smartcourseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CourseScreenActivity extends readData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_screen);
        readData outer = new readData();
        outer.new BigQueryTask(getApplicationContext()).execute();
    }
}
