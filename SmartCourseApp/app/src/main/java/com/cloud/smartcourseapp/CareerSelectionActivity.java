package com.cloud.smartcourseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CareerSelectionActivity extends Activity {

    private String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_selection);

    }


        public void onRadioButtonClicked(View view){
            boolean checked = ((RadioButton) view).isChecked();
            switch(view.getId()) {
                case R.id.radioButton:
                    if (checked)
                        msg = "system";
                        break;
                case R.id.radioButton2:
                    if (checked)
                        msg = "network";
                        break;
                case R.id.radioButton3:
                    if (checked)
                        msg = "data";
                        break;
                case R.id.radioButton4:
                    if (checked)
                        msg = "A.I.";
                        break;
                case R.id.radioButton5:
                    if (checked)
                        msg = "algorithm";
                        break;
            }
        }

    public void onNextButtonClicked(View view){
            Intent intent = new Intent(CareerSelectionActivity.this, UserProfileActivity.class);
            intent.putExtra("msg",msg);
            startActivity(intent);
    }
    }

