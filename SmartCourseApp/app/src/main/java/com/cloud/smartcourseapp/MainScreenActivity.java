package com.cloud.smartcourseapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity implements readData.call_back {

    private TextView mTextMessage;
    private readData task;
    private readData.BigQueryTask queryTask;
    private int flag = 0;
    private  RecommendationFragment frag;
    public void onQuery_Ready(List<String> titles, List<String> credits, List<String> descriptions)
    {
        frag = new RecommendationFragment();
        Bundle b = new Bundle();
        b.putStringArrayList("title",new ArrayList<>(titles));
        b.putStringArrayList("credit",new ArrayList<>(credits));
        b.putStringArrayList("description",new ArrayList<>(descriptions));
        frag.setArguments(b);
        flag = 1;
        //System.out.println(result);
        // queryTask.cancel(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.navigation_recommendation:
                    while(flag == 0) {}
                    loadFragment(frag);
                    return true;
                case R.id.navigation_dashboard:
                    loadFragment(new DashboardFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent = getIntent();
        task = new readData(MainScreenActivity.this);
        queryTask = task.new BigQueryTask(MainScreenActivity.this);
        queryTask.execute(intent.getStringExtra("msg"));
        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    private void loadFragment(Fragment fragment) {
        if(fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(fragmentTransaction != null){
                fragmentTransaction.replace(R.id.homeEmptyFrame, fragment);
                fragmentTransaction.commit();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_newRequest) {
            Intent intent = new Intent(this, CourseRequestActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
