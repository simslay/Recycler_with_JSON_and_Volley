package com.example.recyclerwithjsonandvolley;

import static com.example.recyclerwithjsonandvolley.Nodes.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(JSON_IMAGE_URL);
        String creator = intent.getStringExtra(JSON_CREATOR);
        int likes = intent.getIntExtra(JSON_LIKES, 0);

        ImageView ivImage = findViewById(R.id.iv_image);
        TextView tvCreator = findViewById(R.id.tv_creator);
        TextView tvLikes = findViewById(R.id.tv_likes);

        tvCreator.setText(creator);
        tvLikes.setText("Likes: " + likes);

        Context context1 = ivImage.getContext();

        Glide.with(context1)
                .load(imageUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImage);
    }
}