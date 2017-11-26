package com.cloud.smartcourseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        EditText editText = findViewById(R.id.editTextGpa);
        editText.setFilters(new InputFilter[]{new GpaFilter()});
    }
    public void submitClick(View view) {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }
}
