package com.cloud.smartcourseapp;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Created by Owner on 11/6/2017.
 */

public class Test_NLB extends AppCompatActivity implements ApiFragment.Callback {

    private static final int API_ENTITY = 0;
    private static final String FRAGMENT_API = "api";
    private static final int LOADER_ACCESS_TOKEN = 1;

    private static final String STATE_SHOWING_RESULTS = "showing_results";

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // The icon button is clicked; start analyzing the input.
                case R.id.send:
                    start_analyze();
                    break;
            }
        }
    };


    private View mResults;

    private EditText mInput;

    private ViewPager mViewPager;

    private ApiFragment test;

    /**
     * Whether the result view is animating to hide.
     */
    private boolean mHidingResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_screen);

        mResults = findViewById(R.id.results);

        final FragmentManager fm = getSupportFragmentManager();

        // Set up the input EditText so that it accepts multiple lines
        mInput = (EditText) findViewById(R.id.input);
        mInput.setHorizontallyScrolling(false);
        mInput.setMaxLines(Integer.MAX_VALUE);

        if (savedInstanceState == null) {
            // The app has just launched; handle share intent if it is necessary
            handleShareIntent();
        } else {
            // Configuration changes; restore UI states
            boolean results = savedInstanceState.getBoolean(STATE_SHOWING_RESULTS);
            if (results) {
                mResults.setVisibility(View.VISIBLE);
            } else {
                mResults.setVisibility(View.INVISIBLE);
            }
        }

        // Bind event listeners
        findViewById(R.id.send).setOnClickListener(mOnClickListener);

        // Prepare the API

        if (getApiFragment() == null) {
            fm.beginTransaction().add(new ApiFragment(), FRAGMENT_API).commit();
        }

        prepareApi();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SHOWING_RESULTS, mResults.getVisibility() == View.VISIBLE);
    }

    private void handleShareIntent() {
        final Intent intent = getIntent();
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_SEND)
                && TextUtils.equals(intent.getType(), "text/plain")) {
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (text != null) {
                mInput.setText(text);
            }
        }
    }

    private ApiFragment getApiFragment() {
        return (ApiFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_API);
    }

    private void prepareApi() {
        // Initiate token refresh
        getSupportLoaderManager().initLoader(LOADER_ACCESS_TOKEN, null,
                new LoaderManager.LoaderCallbacks<String>() {
                    @Override
                    public Loader<String> onCreateLoader(int id, Bundle args) {
                        return new TokenLoader(Test_NLB.this);
                    }

                    @Override
                    public void onLoadFinished(Loader<String> loader, String token) {
                        ApiFragment temp = getApiFragment();
                        temp.setAccessToken(token);
                    }

                    @Override
                    public void onLoaderReset(Loader<String> loader) {
                    }
                });
    }

    private void start_analyze() {

        final String text = mInput.getText().toString();
        getApiFragment().analyzeEntities(text);
        getApiFragment().analyzeSentiment(text);
    }
    public void onEntitiesReady(EntityInfo[] entities) {

    }

    public void onSentimentReady(SentimentInfo  sentiment) {
    }

    }

