package com.example.anis.practicerecycle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.anis.practicerecycle.network.network;

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
import java.nio.charset.Charset;


public class MainActivity extends AppCompatActivity {
    TextView movieName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieName = (TextView) findViewById(R.id.movies);
        AsyncTasks task = new AsyncTasks();
        task.execute();
    }
    public class AsyncTasks extends AsyncTask<URL, Void, String> {


        public AsyncTasks()
        {

        }
        protected String doInBackground(URL... urls) {
            // Create URL object
            String sd = makeQuery(network.BASE_URL);



            // Extract relevant fields from the JSON response and create an {@link Event} object
            String title = extractFeatureFromJson(sd);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return title;
        }



        protected void onPostExecute(String sd) {
            if (sd == null) {
                return;
            }

            movieName.setText(sd);
        }

    }




    private String makeQuery(String urlS)
    {
        URL url = null ;
        String jsonResponse = "";
        try {
            url = new URL(urlS);
            jsonResponse = httpRequest(url);
        } catch (MalformedURLException exception) {
            Log.e("ER", "Error with creating URL", exception);

        }catch (IOException exception)
        {
            Log.e("ERROR", "Error with creating URL", exception);
        }
        return jsonResponse;

    }
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private String httpRequest(URL url) throws IOException
    {
        String Json = "";

        if(url == null)
        {
            return Json;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputS = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputS = urlConnection.getInputStream();
                Json = readFromStream(inputS);
            } else {
                Log.e("ERROR", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("ERROR", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputS != null) {
                // function must handle java.io.IOException here
                inputS.close();
            }
        }
        return Json;

    }
    private String extractFeatureFromJson(String JSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(JSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("results");


            if (featureArray.length() > 0) {

                StringBuilder movies = new StringBuilder();

                for(int i = 0; i < featureArray.length(); i++)
                {
                    JSONObject Feature = featureArray.getJSONObject(i);


                    // Extract out the title, time, and tsunami values
                    String title = Feature.getString("title");
                    movies.append(title + "\n\n");

                }



                return movies.toString();
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
