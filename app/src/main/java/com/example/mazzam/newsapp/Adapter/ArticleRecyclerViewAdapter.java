package com.example.mazzam.newsapp.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mazzam.newsapp.Api.NewsResponse.ArticlesItem;
import com.example.mazzam.newsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {
    List<ArticlesItem>articlesItems;
    OnItemclicklistner onArticleclicklistner;
    OnItemclicklistner onImageclick;

    public void setOnImageclick(OnItemclicklistner onImageclick) {
        this.onImageclick = onImageclick;
    }

    public void setOnArticleclicklistner(OnItemclicklistner onArticleclicklistner) {
        this.onArticleclicklistner = onArticleclicklistner;
    }

    public ArticleRecyclerViewAdapter(List<ArticlesItem> articlesItems) {
        this.articlesItems = articlesItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_item_article,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final ArticlesItem articlesItem=articlesItems.get(position);
        if (articlesItem.getSource()==null)
        {
            viewHolder.sourceName.setText(articlesItem.getSourceName());
        }
        else
        viewHolder.sourceName.setText(articlesItem.getSource().getName());
        viewHolder.title.setText(articlesItem.getTitle());
        String date= parseDateToddMMyyyy(articlesItem.getPublishedAt());
        viewHolder.date.setText(date);
        Glide.with(viewHolder.itemView).load(articlesItem.getUrlToImage()).into(viewHolder.articleImage);
        if (onArticleclicklistner!=null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onArticleclicklistner.onItemClick(position,articlesItem);
                }
            });
        }
        if (onImageclick!=null)
        {
            viewHolder.likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageclick.onItemClick(position,articlesItem);
                }
            });

        }
    }
    @Override
    public int getItemCount() {
        if (articlesItems==null)
            return 0;
        return articlesItems.size();
    }

    public void changeData(List<ArticlesItem> articlesItems)
    {
        this.articlesItems=articlesItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleImage,likeImage;
        TextView   sourceName,title,date;
        public ViewHolder(View view){
            super(view);
            articleImage=view.findViewById(R.id.articleImage);
            sourceName=view.findViewById(R.id.source_name);
            title=view.findViewById(R.id.title);
            date=view.findViewById(R.id.date);
            likeImage=view.findViewById(R.id.like_image);
        }
    }
    public static String parseDateToddMMyyyy(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC+"));
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "dd-MM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public interface OnItemclicklistner{
        public void onItemClick(int pos ,ArticlesItem articlesItem);
    }
}
