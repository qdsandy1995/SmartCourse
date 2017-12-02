package com.cloud.smartcourseapp;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DashboardFragment extends Fragment {

    public DashboardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LoadData load = new LoadData();
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.dashboard_swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("log", "onRefreshFromSwipeRefreshLayout");
                load.refreshDashboard();
            }
        });

        return view;
    }
    // TODO load data
    public class LoadData extends readData {

        private void refreshDashboard() {
            ((readData)getActivity()).ButtonChanged();
        }
    }

}
