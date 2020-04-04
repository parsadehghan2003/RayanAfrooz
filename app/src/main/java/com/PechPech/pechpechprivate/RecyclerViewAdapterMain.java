package com.PechPech.pechpechprivate;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

public class RecyclerViewAdapterMain extends RecyclerView.Adapter<RecyclerViewAdapterMain.ViewHolder> {

    private List<RecyclerViewDataModel> ListData;
    private CheckBox checkBox;
    private FloatingActionButton actionButton;

    RecyclerViewAdapterMain(List<RecyclerViewDataModel> ListData, CheckBox checkBox, FloatingActionButton actionButton) {
        this.ListData = ListData;
        this.checkBox = checkBox;
        this.actionButton = actionButton;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyler_view_main, parent, false));

    }
    private int visibility = View.GONE;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        View.OnLongClickListener ON = new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (visibility != View.VISIBLE) {
                    visibility = View.VISIBLE;
                    checkBox.setVisibility(View.VISIBLE);
                    holder.checkBox.setChecked(true);
                    actionButton.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
                return false;
            }
        };
        final RecyclerViewDataModel myListData = ListData.get(position);
        if (this.actionButton.getVisibility() == View.VISIBLE) {
            holder.itemView.setOnLongClickListener(ON);
        }
        holder.mTextViewDes.setText(myListData.getTitle());
        holder.mTextViewDate.setText(myListData.getDate());
        holder.checkBox.setChecked(myListData.isSelected());
        this.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (RecyclerViewAdapterMain.this.checkBox.isChecked()) {
                    for (int i = View.VISIBLE; i < RecyclerViewAdapterMain.this.ListData.size(); i++) {
                        RecyclerViewAdapterMain.this.ListData.get(i).setSelected(true);
                        RecyclerViewAdapterMain.this.ListData.get(i).setRead(new DataBase(G.context).getAllMessages(View.VISIBLE).get(i).isRead());
                    }
                    RecyclerViewAdapterMain.this.notifyDataSetChanged();
                    return;
                }
                for (int i2 = View.VISIBLE; i2 < RecyclerViewAdapterMain.this.ListData.size(); i2++) {
                    RecyclerViewAdapterMain.this.ListData.get(i2).setSelected(false);
                    RecyclerViewAdapterMain.this.ListData.get(i2).setRead(new DataBase(G.context).getAllMessages(View.VISIBLE).get(i2).isRead());
                }
                RecyclerViewAdapterMain.this.notifyDataSetChanged();
            }
        });
        holder.checkBox.setChecked(myListData.isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myListData.setSelected(true);
                    return;
                }
                myListData.setSelected(false);
                RecyclerViewAdapterMain.this.checkBox.setChecked(false);
            }
        });
        if (new DataBase(G.context).getAllMessages(View.VISIBLE).get(position).isRead() == View.VISIBLE) {
            holder.mImageViewIsRead.setVisibility(View.VISIBLE);
        }
        if (new DataBase(G.context).getAllMessages(View.VISIBLE).get(position).isRead() == 1) {
            holder.mImageViewIsRead.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MessageActivity.class);
                intent.putExtra("messageId", myListData.getDescription());
                view.getContext().startActivity(intent);
                myListData.setRead(1);
                new DataBase(G.context).setRead(myListData);
                ShortcutBadger.applyCount(G.context, G.MessageNotReadCount());
                holder.mImageViewIsRead.setVisibility(View.INVISIBLE);
            }
        });
        holder.itemView.setOnLongClickListener(ON);
        holder.checkBox.setVisibility(this.visibility);
        if (this.checkBox.getVisibility() == View.VISIBLE && holder.checkBox.getVisibility() != View.VISIBLE) {
            holder.checkBox.setVisibility(this.checkBox.getVisibility());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.FLAVOR);
        sb.append(holder.checkBox.isChecked());
        Log.i("checked", sb.toString());
    }

    List<RecyclerViewDataModel> onSelected(){


        return ListData;
    }
    void DeleteOnSelected(List<RecyclerViewDataModel> recyclerViewDataModels , int visibility){

        this.visibility = visibility;
        for (int i = View.VISIBLE; i < ListData.size(); i++) {
            if (ListData.get(i).isSelected())
                ListData.remove(i);

        }
        ListData = recyclerViewDataModels;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewDes , mTextViewDate;
        ConstraintLayout LinearLayout;
        ImageView mImageViewIsRead;
        CheckBox checkBox;

        ViewHolder ( View itemView ) {
            super(itemView);
            this.checkBox = itemView.findViewById(R.id.checkBox);
            this.mTextViewDate=itemView.findViewById ( R.id.txt_date );
            this.mTextViewDes= itemView.findViewById(R.id.txt_des);
            this.mImageViewIsRead = itemView.findViewById(R.id.imageView12);
            LinearLayout =  itemView.findViewById(R.id.linearlayoutRow);
        }
    }
}
