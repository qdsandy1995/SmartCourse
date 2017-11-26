package com.cloud.smartcourseapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class RecommendationFragment extends Fragment {

    private static final int REQ_CODE_RECOM = 9002;
    private ListView courseList;
    private String[] courses = new String[]{
         "Data structure", "Cloud computing", "Algorithm"
    };

    public RecommendationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        courseList = view.findViewById(R.id.courseList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, courses);

        courseList.setAdapter(adapter);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                String itemValue = (String) courseList.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), CourseScreenActivity.class);
                Toast.makeText( getActivity().getApplicationContext(),
                        "Click Item",Toast.LENGTH_LONG).show();
                startActivityForResult(intent, REQ_CODE_RECOM);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the activity result was received from the "Get Car" request
        if (REQ_CODE_RECOM == requestCode) {
            // If the activity confirmed a selection
            if (Activity.RESULT_OK == resultCode) {
                // Grab whatever data identifies that car that was sent in
                // setResult(int, Intent)
                //final int someData = data.getIntExtra(CourseScreenActivity.EXTRA_DATA, -1);
                Toast.makeText( getActivity().getApplicationContext(),
                        "Back from course recommendation",Toast.LENGTH_LONG).show();
            } else {
                // You can handle a case where no selection was made if you want
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
