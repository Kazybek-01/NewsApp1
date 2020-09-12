package com.example.newsapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageViewDetails,imageViewShadowDetails,imageViewDate;
    TextView titleOfNews, timeAgo, dateDetails;
    WebView webView;
    ProgressBar progressBar;
    String url, image, title, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewDetails = findViewById(R.id.imageViewDetails);
        imageViewShadowDetails = findViewById(R.id.imageViewShadowDetails);
        imageViewDate = findViewById(R.id.imageViewDate);
        titleOfNews = findViewById(R.id.titleOfNews);
        timeAgo = findViewById(R.id.timeAgo);
        dateDetails = findViewById(R.id.dateDetails);



        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();

        url = (String) intent.getStringExtra("url");
        image = (String) intent.getStringExtra("image");
        title = (String) intent.getStringExtra("title");
        date = (String) intent.getStringExtra("publishedAt");


//        titleOfNews.setText(articles.getTitleOfNews());
//        timeAgo.setText(articles.getTimeAgo());
//        dateDetails.setText(articles.getDateDetails());

        titleOfNews.setText(title);
        timeAgo.setText(NewsAdapter.DateToTimeFormat(date));
        dateDetails.setText(NewsAdapter.DateFormat(date));
        Picasso.get().load(image).into(imageViewDetails);

        progressBar = findViewById(R.id.progressBar);

        webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


    }
}