package com.PechPech.pechpechprivate;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghamardaraghrab.Activities.ActivityArticle;
import com.example.ghamardaraghrab.Activities.ActivityShowVideo;
import com.example.ghamardaraghrab.Model.CatDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapterCat extends  RecyclerView.Adapter<RecyclerViewAdapterCat.ViewHolder>{

    private List< CatDataModel > ListData;
    private String activity;
    private int Layout;

    public RecyclerViewAdapterCat(List< CatDataModel > ListData , int Layout , String activity ) {
        this.Layout =Layout;
        this.ListData = ListData;
        this.activity=activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem= layoutInflater.inflate( Layout , parent, false);

        return new ViewHolder(listItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( @NonNull final ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewDes;
        ViewHolder ( View itemView ) {
            super(itemView);

            this.mTextViewDes= itemView.findViewById(R.id.txt_des);
        }
    }
}
