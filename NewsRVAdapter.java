package com.example.newsify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.viewHolder> {

    private ArrayList<Articles> articlesArrayList;

    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new NewsRVAdapter.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.viewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.tittleTV.setText(articles.getTittle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,NewsDetailActivity.class);
                i.putExtra("title",articles.getTittle());
                i.putExtra("content",articles.getContent());
                i.putExtra("desc",articles.getDescription());
                i.putExtra("image",articles.getUrlToImage());
                i.putExtra("url",articles.getUrl());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tittleTV,subTitleTV;
        private ImageView newsIV;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tittleTV = itemView.findViewById(R.id.idTVNewsHeading);
            subTitleTV= itemView.findViewById(R.id.idTVSubTitle);
            newsIV= itemView.findViewById(R.id.idIVNews);

        }
    }
}
