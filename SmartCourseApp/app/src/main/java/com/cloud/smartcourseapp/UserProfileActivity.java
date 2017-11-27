package com.cloud.smartcourseapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserProfileActivity extends AppCompatActivity implements ApiService.Call_back{
    private static final int LOADER_ACCESS_TOKEN = 1;
    private EditText mInput;
    private ApiService task;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // The icon button is clicked; start analyzing the input.
                case R.id.buttonSubmit:
                    start_analyze();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        EditText editText = findViewById(R.id.editTextGpa);
        editText.setFilters(new InputFilter[]{new GpaFilter()});
        mInput = (EditText) findViewById(R.id.editTextFutureCareer);
        mInput.setHorizontallyScrolling(false);
        mInput.setMaxLines(Integer.MAX_VALUE);

        // Bind event listeners
        findViewById(R.id.buttonSubmit).setOnClickListener(mOnClickListener);
        task = new ApiService(UserProfileActivity.this);

        prepareApi(task);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void prepareApi(final ApiService task) {
        // Initiate token refresh
        getSupportLoaderManager().initLoader(LOADER_ACCESS_TOKEN, null,
                new LoaderManager.LoaderCallbacks<String>() {
                    @Override
                    public Loader<String> onCreateLoader(int id, Bundle args) {
                        return new TokenLoader(UserProfileActivity.this);
                    }

                    @Override
                    public void onLoadFinished(Loader<String> loader, String token) {
                        //             ApiFragment temp = getApiFragment();
                        //              temp.setAccessToken(token);
                        task.setAccessToken(token);
                    }

                    @Override
                    public void onLoaderReset(Loader<String> loader) {
                    }
                });
    }

    private void start_analyze() {
        final String text = mInput.getText().toString();
        task.execute(text);
    }

    public void onEntitiesReady(EntityInfo[] entities) {
        //Search database with entities

        task.cancel(true);
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

    public void onSentimentReady(SentimentInfo  sentiment) {}

}
