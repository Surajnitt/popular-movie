package com.example.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView text;
    ListView listView;
    ArrayList<Moviedetails> movieList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.text);
        listView=findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        for(int j=1;j<20;j++) {
            new checkconnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=87439866e8b3bce13c28bb4bedd1d60f&language=en-US&page="+j);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(this,MovieDetailsActivity.class);
        intent.putExtra("moviedetails",(Moviedetails)parent.getItemAtPosition(position));
        startActivity(intent);

    }

    class checkconnectionStatus extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url=null;
            try {
                url=new URL(strings[0]);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            try {
                HttpsURLConnection urlConnection=(HttpsURLConnection)url.openConnection();

                InputStream inputStream=urlConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String s=bufferedReader.readLine();
                bufferedReader.close();
                return s;
            }catch (IOException e){
                Log.e("Error:",e.getMessage(),e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject=null;
            try{
                jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("results");

                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject object=jsonArray.getJSONObject(i);
                    Moviedetails moviedetails=new Moviedetails();
                    moviedetails.setOriginal_title(object.getString("original_title"));
                    moviedetails.setOverview(object.getString("overview"));
                    moviedetails.setVote_average(object.getDouble("vote_average"));
                    moviedetails.setRelease_date(object.getString("release_date"));
                    moviedetails.setPoster_path(object.getString("poster_path"));
                    movieList.add(moviedetails);
                }
                MovieArrayAdapter movieArrayAdapter=new MovieArrayAdapter(MainActivity.this,R.layout.movie_item,movieList);
                listView.setAdapter(movieArrayAdapter);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
