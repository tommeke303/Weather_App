package com.example.thomas.weather_thuis_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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
import java.net.URLConnection;


public class MyActivity extends Activity {
    private ListView listView1;
    private Weather[] weather_data = new Weather[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        listView1 = (ListView)findViewById(R.id.listView1);
        new DownloadImageTask().execute("http://api.openweathermap.org/data/2.5/weather?q=gent");


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        /** Wordt aangeroepen en zal uitgevoerd worden in worker thread */
        protected Bitmap doInBackground(String... urls) {
            openConnection(urls[0]);
            return null;
        }
        /** Retourneert de resultaten nadat doInBackground afgelopen is */
        protected void onPostExecute(Bitmap result) {
            WeatherAdapter adapter = new WeatherAdapter(MyActivity.this,
                    R.layout.listview_item_row, weather_data);




            View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
            listView1.addHeaderView(header);

            listView1.setAdapter(adapter);
        }

    }

    private void openConnection(String urlSite){
        try{


                URL url = new URL(urlSite);
                //Create new HTTP URL connection
                URLConnection connection = url.openConnection();
                HttpURLConnection httpURLConnection =
                        (HttpURLConnection) connection;
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = httpURLConnection.getInputStream();
                     weather_data[0] = readAnsParseJSON(getStringFromInputStream(in));


                    System.out.println("test");
            }


        }
        catch(MalformedURLException e){
            Log.d("MyActivity", "Malformed URL exception");
            e.printStackTrace();
        }
        catch(IOException e){
            Log.d("MyActivity", "IO Exception");
            e.printStackTrace();
        }
        catch(JSONException e){
            Log.d("MyActivity", "JSON Exception");
            e.printStackTrace();
        }
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public Weather readAnsParseJSON (String in) throws JSONException {
        Weather w = new Weather();
        JSONObject reader = new JSONObject(in);
        JSONArray weatherTag = reader.getJSONArray("weather");
        JSONObject weatherTitle = weatherTag.getJSONObject(0);

        w.setTitle(weatherTitle.getString("description"));
        w.setIcon(Uri.parse("http://openweathermap.org/img/w/" + weatherTitle.getString("icon") + ".png"));
        return w;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
