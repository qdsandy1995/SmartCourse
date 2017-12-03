package com.cloud.smartcourseapp;

/**
 * Created by fengjie on 11/26/17.
 */
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.view.View;


import java.util.StringJoiner;
import com.google.cloud.AuthCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryError;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.DatasetId;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryRequest;
import com.google.cloud.bigquery.QueryResponse;
import com.google.cloud.bigquery.QueryResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

// import static java.security.AccessController.getContext;

/**
 * Created by fengjie on 11/15/17.
 */

public class readData extends AppCompatActivity {
    private Context mContext;
    private static final String CREDENTIALS_FILE = "credential.json";
    private static final String PROJECT_ID = "smartcourse-e4806";

    public interface call_back{
        void onQuery_Ready(List<String> rows);
    }

    private call_back mCallback;

    public readData() {}
    public readData(call_back activityContext){
        this.mCallback = activityContext;
    }

    private TextView Course_title, Credits, Description, Difficulty, Popularity, Professor_Rating, Field;

    public void ButtonChanged() {
        /*String newRow = "{\"Course_title\": " + Course_title.getText().toString() +
                ", \"Credits\": " + Integer.parseInt(Credits.getText().toString()) +
                ", \"Description\": " + Description.getText().toString() +
                ", \"Difficulty\": " + Integer.parseInt(Difficulty.getText().toString()) +
                ", \"Popularity\": " + Popularity.getText().toString() +
                ", \"Professor_Rating\": " + Integer.parseInt(Professor_Rating.getText().toString()) +
                ", \"Field\": " + Field.getText().toString() +
                "}";
        */
        final String newRow = "{\"Course_title\": " + "cot5504" +   //Test Data
                ", \"Credits\": " + 0 +
                ", \"Description\": " + "good" +
                ", \"Difficulty\": " + 1 +
                ", \"Popularity\": " + "not bed" +
                ", \"Professor_Rating\": " + 1 +
                ", \"Field\": " + "cs" +
                "}";

        new readData.BigQueryTask(mContext).execute();
    }


    public class BigQueryTask extends AsyncTask<String, Integer, String> {




        public BigQueryTask (Context context){
            mContext = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Main", "Launching BigQuery API request");
        }


        @Override
        protected String doInBackground(String... contents) {
            try {
                AssetManager am = mContext.getAssets();
                InputStream isCredentialsFile = am.open(CREDENTIALS_FILE);
                BigQuery bigquery = BigQueryOptions.builder()
                        .authCredentials(AuthCredentials.createForJson(isCredentialsFile))
                        .projectId(PROJECT_ID)
                        .build().service();

                /*
                QueryRequest request =  QueryRequest.Builder.query("SELECT field FROM table")
                        .defaultDataset(DatasetId.of("dataset"))
                        .maxWaitTime(60000L)
                        .pageSize(1000L)
                        .build();
                */
                String search = "SELECT Course_title FROM Course_Data WHERE Description CONTAINS " + "'" + contents[0] + "'";
                QueryRequest.Builder qb = QueryRequest.builder(search);
                qb.defaultDataset(DatasetId.of("test_data"));
                qb.maxWaitTime(60000L);
                qb.pageSize(1000L);
                QueryRequest request =  qb.build();
                QueryResponse response = bigquery.query(request);
                while (!response.jobCompleted()) {
                    Thread.sleep(1000);
                    response = bigquery.getQueryResults(response.jobId());
                }
                //List<FieldValue> row = new ArrayList<>();;
                List<String> row = new ArrayList<>() ;
                List<BigQueryError> executionErrors = response.executionErrors();
                // look for errors in executionErrors
                QueryResult result = response.result();
                Iterator<List<FieldValue>> rowIterator = result.iterateAll();
                while(rowIterator.hasNext()) {
                    String temp = rowIterator.next().toString();
                    temp = temp.substring(temp.lastIndexOf("=") + 1);
                    String[] seg = temp.split(Pattern.quote("}"));
                    row.add(seg[0]);
                 //   System.out.println(row);
                    // do something with row
                }
                if (mCallback != null) {
                    mCallback.onQuery_Ready(row);
                }
            }catch (Exception e) {
                Log.d("Main", "Exception: " + e.toString());
            }
            return "Done";
        }

    }


}