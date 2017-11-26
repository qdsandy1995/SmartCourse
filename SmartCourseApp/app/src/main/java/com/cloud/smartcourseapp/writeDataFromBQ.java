package com.cloud.smartcourseapp;
/**
 * Created by fengjie on 11/12/17.
 */

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.cloud.AuthCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FormatOptions;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.WriteChannelConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


public class writeDataFromBQ extends AppCompatActivity {


    private static final String CREDENTIALS_FILE = "/Users/fengjie/Downloads/CloudPlatform-3d208c60b41c.json";
    private static final String PROJECT_ID = "cloudplatform-185115";
    private final int ROW_INTERVAL = 10;
    private int num_rows = 0;
    private static TextView Course_title, Credits, Description, Difficulty, Popularity, Professor_Rating, Field;




    public static void ButtonActivity() {
        //Course_title = (TextView) findViewById();  //string
        //Credits = (TextView) findViewById();       //integer
        //Description = (TextView) findViewById();   //string
        //Difficulty = (TextView) findViewById();    //integer
        //Popularity = (TextView) findViewById();    //integer
        //Professor_Rating = (TextView) findViewById();  //integer
        //Field = (TextView) findViewById();          //string

        /*final String newRow = "{\"Course_title\": " + Course_title.getText().toString() +
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

        new BigQueryTask().execute(newRow);


    }

    public static void main(String []args) {
        new writeDataFromBQ().ButtonActivity();
    }



    private static class BigQueryTask extends AsyncTask<String, Integer, String>{

        protected void onPreExecute() {
            super.onPreExecute();
           // Log.d("readDataFromBQ", "Launching BigQuery API request ("+Integer.toString(num_rows)+" rows)");
        }


        @Override
        protected String doInBackground(String... contents) {

            String JSON_CONTENT = contents[0];
            try{
                //AssetManager am = readDataFromBQ.this.getAssets();
                File file = new File(CREDENTIALS_FILE);
                InputStream isCredentialsFile = new FileInputStream(file);

                BigQuery bigquery = BigQueryOptions.builder()
                        .authCredentials(AuthCredentials.createForJson(isCredentialsFile))
                        .projectId( PROJECT_ID )
                        .build().service();

                TableId tableId = TableId.of("uf_cs", "example");
                Table table = bigquery.getTable(tableId);
                int num = 0;
                Log.d("Main", "Sending JSON: " + JSON_CONTENT);
                WriteChannelConfiguration configuration = WriteChannelConfiguration.builder(tableId)
                        .formatOptions(FormatOptions.json())
                        .build();
                try (WriteChannel channel = bigquery.writer(configuration)) {
                    num = channel.write(ByteBuffer.wrap(JSON_CONTENT.getBytes(StandardCharsets.UTF_8)));
                    channel.close();
                } catch (IOException e) {
                    Log.d("Main", e.toString());
                }
                Log.d("Main", "Loading " + Integer.toString(num) + " bytes into table " + tableId);

            } catch (Exception e) {
                Log.d("Main", "Exception: " + e.toString());
            }
            return "Done";
        }
    }
}




