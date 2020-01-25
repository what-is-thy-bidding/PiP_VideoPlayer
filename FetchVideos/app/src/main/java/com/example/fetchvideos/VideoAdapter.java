package com.example.fetchvideos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    Context context;
    ArrayList<VideoModel> arrayListVideos;
    Activity activity;

    public VideoAdapter(Context context, ArrayList<VideoModel> arrayListVideos, Activity activity){
        this.activity=activity;
        this.context=context;
        this.arrayListVideos=arrayListVideos;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_video,parent,false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(context).load("file://"+arrayListVideos.get(position).getStr_thumb())   //getStr_thumb()
                .skipMemoryCache(false).into(holder.imageView);

        holder.r1_select.setBackgroundColor(Color.DKGRAY);
        holder.r1_select.setAlpha(0);
        holder.r1_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,VideoPlayerActivity.class);
                i.putExtra("video",arrayListVideos.get(position).getStr_path());
                activity.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {

        return arrayListVideos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        RelativeLayout r1_select;

        public ViewHolder(View itemView){
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.iv_image);
            r1_select = (RelativeLayout) itemView.findViewById(R.id.r1_select);
        }
    }



}
