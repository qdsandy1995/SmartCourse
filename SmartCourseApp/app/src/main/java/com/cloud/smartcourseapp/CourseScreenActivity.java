package com.cloud.smartcourseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CourseScreenActivity extends AppCompatActivity {

    private TextView title;
    private TextView credit;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String t = "",c = "",d = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_screen);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            t = extras.getString("t");
            c = extras.getString("c");
            d = extras.getString("d");
       }
        title = (TextView)findViewById(R.id.title);
        credit = (TextView)findViewById(R.id.credit);
        description = (TextView)findViewById(R.id.description);
        title.setText("Course_Title: "+t);
        credit.setText("Credit: " + c);
        description.setText(d);
    }
}
