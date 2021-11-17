package com.assignment.technews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> articleIDs = new ArrayList<>();
    ArrayList<String> articleTitles = new ArrayList<>();
    ArrayList<String> articleUrls = new ArrayList<>();
    ListView myListView;
    SQLiteDatabase database;
    public static String articleTitle;
    public static String articleUrl;
    public static String articleID;

    //Step 1
//    public class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String result = "";
//            URL url;
//            HttpURLConnection urlConnection;
//
//            try {
//                url = new URL(strings[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream inp = urlConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
//                String data = reader.readLine();
//
//                while (data != null) {
//                    result += data;
//                    data = reader.readLine();
//                }
//                return result;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//    }
//
//    public class DownloadTask2 extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String result = "";
//            URL url;
//            HttpURLConnection urlConnection;
//
//            try {
//                url = new URL(strings[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream inp = urlConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
//                String data = reader.readLine();
//
//                while (data != null) {
//                    result += data;
//                    data = reader.readLine();
//                }
//                return result;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONObject json = new JSONObject(s);
//                articleID = json.getString("id");
//                articleTitle = json.getString("title");
//                articleUrl = json.getString("url");
//                database.execSQL("INSERT INTO articles(id,title,url) VALUES (\'" + articleID + "\',\'" + articleTitle + "\',\'" + articleUrl + "\')");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        myListView = (ListView) findViewById(R.id.newsView);
//
//        //Initialize DB
//        database = this.openOrCreateDatabase("articlesDB", MODE_PRIVATE, null);
//
//        //Creating DB
//        //database.execSQL("DROP TABLE  articles");
//        database.execSQL("CREATE TABLE IF NOT EXISTS articles (id VARCHAR, title VARCHAR, url VARCHAR)");
//
//        DownloadTask task = new DownloadTask();
//        try {
//            String res = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json").get();
//            String[] temp = res.split(",");
//
//            for (int i = 0; i < 20; i++) {
//                articleIDs.add(temp[i + 1]);
//            }
//
//            //Inserting to DB
//            for (int i = 0; i < 20; i++) {
//                DownloadTask2 task2 = new DownloadTask2();
//
//                String articleID = articleIDs.get(i);
//
//                //Fetching Data
//                task2.execute("https://hacker-news.firebaseio.com/v0/item/" + articleID + ".json?print=pretty");
//            }
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//  Step2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) findViewById(R.id.newsView);
        database = this.openOrCreateDatabase("articlesDB", MODE_PRIVATE, null);

//        Creating cursor to choose
        Cursor c = database.rawQuery("SELECT * FROM articles", null);

        int titleIndex = c.getColumnIndex("title");
        int urlIndex = c.getColumnIndex("url");
        c.moveToFirst();

        for(int i=0;i<20;i++){
            //filling arrays from db
            articleTitles.add(c.getString(titleIndex));
            articleUrls.add(c.getString(urlIndex));
            c.moveToNext();
        }
            c.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articleTitles);
        myListView.setAdapter(adapter);

        //on click
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
               intent.putExtra("title", articleTitles.get(position));
               intent.putExtra("url", articleUrls.get(position));
                MainActivity.this.startActivity(intent);
            }
        });
    }

}