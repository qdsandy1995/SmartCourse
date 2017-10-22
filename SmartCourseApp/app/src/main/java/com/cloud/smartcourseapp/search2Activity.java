package com.cloud.smartcourseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class search2Activity extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
    }

    public void search()
    {
        spinner1 = (Spinner) findViewById(R.id.spinner8);
        String University_Selection = spinner1.getSelectedItem().toString();
        spinner2 = (Spinner) findViewById(R.id.spinner9);
        String Course_Number = spinner2.getSelectedItem().toString();
    }
}
