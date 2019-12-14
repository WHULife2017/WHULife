package com.example.WhuLife.subscription;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WhuLife.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private  List<MNews> newsList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        TextView newsTitle;
        public ViewHolder(View view){
            super(view);
            newsView =view;
            newsTitle=view.findViewById(R.id.news_title);
        }
    }

    public NewsAdapter(List<MNews> objects){
        newsList = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        final ViewHolder holder =  new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int pos = holder.getAdapterPosition();
                MNews m = newsList.get(pos);
                Log.d("VIEW",String.valueOf(v.getContext()));
                Intent intent=new Intent(v.getContext(),PushNewsActivity.class);
                intent.putExtra("URL",m.getUrl());
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(),"url:"+m.getUrl(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MNews m = newsList.get(position);
        holder.newsTitle.setText(m.getTitle());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}