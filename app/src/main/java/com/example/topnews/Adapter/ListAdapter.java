package com.example.topnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.topnews.Activity.WebPage;
import com.example.topnews.Classes.Article;
import com.example.topnews.Classes.Data;
import com.example.topnews.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ImageViewHolder> {
    private Context context;
    public Data data;
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
        View view=inflater.inflate(R.layout.card_list_item,viewGroup,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder Holder, final int i) {

        Data data1=data;
        final List<Article> First=data1.getArticles();
       // Log.d("cod",First.toString());
        Holder.title.setText(First.get(i).getTitle());
        Holder.description.setText(First.get(i).getDescription());
        Holder.author.setText(First.get(i).getAuthor());
        //Holder.content.setText(First.get(i).getContent());
        Glide.with(Holder.img.getContext()).load(First.get(i).getUrlToImage()).into(Holder.img);
        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String web=First.get(i).getUrl();
//                WebView webView=new WebView(context);
//                webView.loadUrl(First.get(i).getUrl());
              //  openWebPage(web);

                Intent intent=new Intent(context, WebPage.class);
                intent.putExtra("webPage",First.get(i).getUrl());
                context.startActivity(intent);
            }
        });


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
            img=itemView.findViewById(R.id.imageView1);
            title=itemView.findViewById(R.id.n_title);
            description=itemView.findViewById(R.id.n_description);
            author=itemView.findViewById(R.id.n_author);
            //content=itemView.findViewById(R.id.news_content);
        }
    }


}
