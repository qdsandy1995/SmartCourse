package com.cloud.smartcourseapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Owner on 11/6/2017.
 */

public class Entity_Fragment extends Fragment {

    private static final String ARG_ENTITIES = "entities";
    private EntitiesAdapter mAdapter;

    public static Entity_Fragment newInstance() {
        final Entity_Fragment fragment = new Entity_Fragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView list = (RecyclerView) view.findViewById(R.id.item_list);
        final Context context = getContext();
        list.setLayoutManager(new LinearLayoutManager(context));
        EntityInfo[] entities = (EntityInfo[]) getArguments()
                .getParcelableArray(ARG_ENTITIES);
        mAdapter = new EntitiesAdapter(context, entities);
        list.setAdapter(mAdapter);
    }

    public void setEntities(EntityInfo[] entities) {
        mAdapter.setEntities(entities);
        getArguments().putParcelableArray(ARG_ENTITIES, entities);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView type;
        public TextView salience;
        public TextView wikipediaUrl;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.entity, parent, false));
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            salience = (TextView) itemView.findViewById(R.id.salience);
            wikipediaUrl = (TextView) itemView.findViewById(R.id.wikipedia_url);
        }

    }

    private static class EntitiesAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final Context mContext;
        private EntityInfo[] mEntities;

        public EntitiesAdapter(Context context, EntityInfo[] entities) {
            mContext = context;
            mEntities = entities;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            EntityInfo entity = mEntities[position];
            holder.name.setText(entity.name);
            holder.type.setText(entity.type);
            holder.salience.setText(mContext.getString(R.string.salience_format, entity.salience));
            holder.wikipediaUrl.setText(entity.wikipediaUrl);
            Linkify.addLinks(holder.wikipediaUrl, Linkify.WEB_URLS);
        }

        @Override
        public int getItemCount() {
            return mEntities == null ? 0 : mEntities.length;
        }

        public void setEntities(EntityInfo[] entities) {
            mEntities = entities;
            notifyDataSetChanged();
        }

    }

}
