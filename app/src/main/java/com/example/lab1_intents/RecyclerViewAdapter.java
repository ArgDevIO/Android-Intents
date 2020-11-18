package com.example.lab1_intents;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mItemNames;
    private ArrayList<Drawable> mItemIcon;
    private ArrayList<ComponentName> mComponentNames;
    private String layout_type;
    private Context mContext;

    RecyclerViewAdapter(ArrayList<String> mItemNames, ArrayList<Drawable> mItemIcon, ArrayList<ComponentName> mComponentNames, String layout_type, Context mContext) {
        this.mItemNames = mItemNames;
        this.mItemIcon = mItemIcon;
        this.mComponentNames = mComponentNames;
        this.layout_type = layout_type;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if ("grid".equals(layout_type)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_grid, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_linear, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(mItemNames.get(position));
        holder.iconImage.setImageDrawable(mItemIcon.get(position));

        final Animation animTranslate = AnimationUtils.loadAnimation(mContext, R.anim.anim_translate2);


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animTranslate);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setComponent(mComponentNames.get(position));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView iconImage;
        RelativeLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            iconImage = itemView.findViewById(R.id.item_icon);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
