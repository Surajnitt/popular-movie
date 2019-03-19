package com.example.popularmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieArrayAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private List<Moviedetails> movieList;

    public MovieArrayAdapter(Context context, int resource, List<Moviedetails> movieDetails) {
        super(context, resource, movieDetails);

        this.context=context;
        this.resource=resource;
        this.movieList=movieDetails;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        Moviedetails details=movieList.get(position);

        View view= LayoutInflater.from(context).inflate(resource,parent,false);
        TextView movieName=(TextView)view.findViewById(R.id.textView);
        ImageView image=(ImageView)view.findViewById(R.id.imageView);
        movieName.setText(details.getOriginal_title());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+details.getPoster_path()).placeholder(R.drawable.icon).into(image);
         return  view;
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }
}
