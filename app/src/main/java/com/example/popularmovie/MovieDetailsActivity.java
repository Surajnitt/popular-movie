package com.example.popularmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title;
    private TextView releaseDate;
    private TextView overview;
    private TextView rating;
    private ImageView movie_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title=findViewById(R.id.movie_title);
        releaseDate=findViewById(R.id.release_date);
        overview =findViewById(R.id.movie_overview);
        movie_poster=findViewById(R.id.poster);
        rating=findViewById(R.id.rating);
        overview.setMovementMethod(new ScrollingMovementMethod());

        Moviedetails details=(Moviedetails)getIntent().getExtras().getSerializable("moviedetails");
        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+details.getPoster_path()).placeholder(R.drawable.icon).into(movie_poster);
        title.setText(details.getOriginal_title());
        releaseDate.setText(details.getRelease_date());
        overview.setText(details.getOverview());
        rating.setText(Double.toString(details.getVote_average()));

    }
}
