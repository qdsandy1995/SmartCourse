package com.cloud.smartcourseapp;

/**
 * Created by Owner on 11/5/2017.
 */

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.language.v1.CloudNaturalLanguageScopes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public class TokenLoader extends AsyncTaskLoader<String> {

    private static final String TAG = "TokenLoader";

    private static final String PREFS = "TokenLoader";
    private static final String PREF_ACCESS_TOKEN = "access_toke n";

     public TokenLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {

        final SharedPreferences prefs =
                getContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String currentToken = prefs.getString(PREF_ACCESS_TOKEN, null);

        // Check if the current token is still valid for a while
        if (currentToken != null) {
            final GoogleCredential credential = new GoogleCredential()
                    .setAccessToken(currentToken)
                    .createScoped(CloudNaturalLanguageScopes.all());
            final Long seconds = credential.getExpiresInSeconds();
            if (seconds != null && seconds > 3600) {
                return currentToken;
            }
        }
            final InputStream stream = getContext().getResources().openRawResource(R.raw.credential);
            try {
                final GoogleCredential credential = GoogleCredential.fromStream(stream)
                        .createScoped(CloudNaturalLanguageScopes.all());
                credential.refreshToken();
                final String accessToken = credential.getAccessToken();
                prefs.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
                return accessToken;
            } catch (IOException e) {
                Log.e(TAG, "Failed to obtain access token.", e);
            }
            return null;
        }
    }
