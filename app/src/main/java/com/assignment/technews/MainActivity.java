package com.assignment.technews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
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
    ListView myListView;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inp = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
                String data = reader.readLine();

                while (data != null) {
                    result += data;
                    data = reader.readLine();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        try {
            String res = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json").get();
            String[] temp = res.split(",");
            for (int i=0;i<20;i++){
                articleIDs.add(temp[i+1]);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        myListView = (ListView) findViewById(R.id.newsView);
        Log.i("Size of Array Articles", String.valueOf(articleIDs.size()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articleIDs);
        myListView.setAdapter(adapter);
    }
}