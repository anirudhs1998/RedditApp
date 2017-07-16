package com.example.anirudhs.redditapp;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.anirudhs.redditapp.MyDBHandler.COLUMN_COMM;
import static com.example.anirudhs.redditapp.MyDBHandler.COLUMN_DESCR;
import static com.example.anirudhs.redditapp.MyDBHandler.TABLE_REDDIT;

public class MainActivity extends AppCompatActivity {
    private static final String URL_DATA = "https://www.reddit.com/r/all.json";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Listitem> listitems;
    private ProgressDialog progressDialog;
    private String title;
    private String thumbnail;
    private int comments;

    private Listitem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listitems = new ArrayList<>();


        loadRecyclerViewData();




        new RedditTask().execute(URL_DATA);









    }






    public void loadRecyclerViewData(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }

  public class RedditTask extends AsyncTask<String,String,String>{

      @Override
      protected String doInBackground(String... params) {

          BufferedReader reader = null;

          HttpURLConnection connection = null;


       try{
           URL url = new URL(params[0]);
           connection = (HttpURLConnection) url.openConnection();
           if(connection.getResponseCode()==200){
               connection.connect();
           }
           InputStream stream = connection.getInputStream();
           reader = new BufferedReader(new InputStreamReader(stream));
           StringBuilder buffer = new StringBuilder();
           String line = "";
           while ((line = reader.readLine()) != null) {
               buffer.append(line);

           }
           String finalJSON = buffer.toString();
           try{
               JSONObject parentObject = new JSONObject(finalJSON);
               JSONObject data = parentObject.getJSONObject("data");
               JSONArray children = data.getJSONArray("children");

               for(int i=0;i<children.length();i++) {
                   JSONObject childrenObject = children.getJSONObject(i);
                   JSONObject data2 = childrenObject.getJSONObject("data");
                   title = data2.getString("title");
                   thumbnail = data2.getString("thumbnail");
                   comments = data2.getInt("num_comments");
                   if(thumbnail.length()>10)
                   sendToListItem();
                   else
                       continue;
               }

           } catch (JSONException e) {
               e.printStackTrace();
           }


       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }


          return null;
      }

      @Override
      protected void onPostExecute(String s) {

          super.onPostExecute(s);
          progressDialog.dismiss();
          recyclerView.setAdapter(adapter);
      }
  }

    private void sendToListItem() {
        item = new Listitem(title,thumbnail,comments);
        adapter = new MyAdapter(listitems, getApplicationContext());
        listitems.add(item);





    }


}
