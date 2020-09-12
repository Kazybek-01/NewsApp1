package com.example.newsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView top;
    private SwipeRefreshLayout refreshLayout;
    NestedScrollView nestedScrollView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top = findViewById(R.id.top);
        recyclerView = findViewById(R.id.recyclerView);

        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews("");
            }
        });

        loadNews("");

    }
    public void loadNews(String keyword){
        refreshLayout.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi api = retrofit.create(NewsApi.class);

        Call<News> call;

        if(keyword.length() > 0){
            call = api.getSearchNews(keyword,"en","publishedAt", "ca592a3100014670822c8464778625fb");
        } else {
            call = api.getNews("us","ca592a3100014670822c8464778625fb");
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                refreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    News news = response.body();
                    List<Articles> articles = news.getArticles();
                    NewsAdapter adapter = new NewsAdapter(MainActivity.this,articles);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new NewsAdapter.RecyclerOnClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                            Bundle bundle = new Bundle();
                            intent.putExtra("url",  articles.get(position).getUrl());
                            intent.putExtra("title", articles.get(position).getTitle());
                            intent.putExtra("image",articles.get(position).getUrlToImage());
                            intent.putExtra("publishedAt",articles.get(position).getPublishedAt());
                            intent.putExtras(intent);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Error", t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search news");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadNews(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadNews(s);
                return false;
            }
        });

        return true;
    }
}
