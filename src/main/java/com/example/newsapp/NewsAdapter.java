package com.example.newsapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {
    Context context;
    List<Articles> articlesList;
    RecyclerOnClickListener recyclerOnClickListener;

    public void setOnItemClickListener(NewsAdapter.RecyclerOnClickListener recyclerOnClickListener) {
        this.recyclerOnClickListener = recyclerOnClickListener;
    }

    public NewsAdapter(Context context, List<Articles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public NewsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.example_item,parent,false);
        return new Holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.Holder holder, int position) {
        holder.author.setText(articlesList.get(position).getAuthor());
        holder.title.setText(articlesList.get(position).getTitle());
        holder.source.setText(articlesList.get(position).getSource().getName());
        holder.desc.setText(articlesList.get(position).getDescription());
        String date1 = DateFormat(articlesList.get(position).getPublishedAt());
        holder.date.setText(date1);
        String time1 = DateToTimeFormat(articlesList.get(position).getPublishedAt());
        holder.time.setText(time1);

        Picasso.get().load(articlesList.get(position)
                .getUrlToImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView,imageViewShadow,backgroundDate,imageViewDate;
        TextView author,title,desc,source,time,date;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    recyclerOnClickListener.onClick(position);
                }
            });
        }
    }
    public static String DateFormat(String oldstringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale("en"));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldstringDate);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }
    public static String DateToTimeFormat(String oldstringDate){
        PrettyTime p = new PrettyTime(new Locale("en"));
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH);
            Date date = sdf.parse(oldstringDate);
            isTime = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTime;
    }
    public interface RecyclerOnClickListener{
        void onClick(int position);
    }
}
