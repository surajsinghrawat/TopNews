package com.example.topnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ImageViewHolder> {
    private Context context;
    private Data data;
//    public String[] apiResult;
//    private List<Article> articles;
    public ListAdapter(Context context, Data data) {
        this.context = context;
        this.data=data;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_item,viewGroup,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder Holder, int i) {

        Data data1=data;
        List<Article> First=data1.getArticles();
       // Log.d("cod",First.toString());
        Holder.title.setText(First.get(i).getTitle());
        Holder.description.setText(First.get(i).getDescription());
        Holder.author.setText(First.get(i).getAuthor());
        Holder.content.setText(First.get(i).getContent());
        Glide.with(Holder.img.getContext()).load(First.get(i).getUrlToImage()).into(Holder.img);


    }

    @Override
    public int getItemCount() {
        return data.getArticles().size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title,description,author,content;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.news_title);
            description=itemView.findViewById(R.id.news_Description);
            author=itemView.findViewById(R.id.news_author);
            content=itemView.findViewById(R.id.news_content);
        }
    }
}
