package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.coronatracker.model.City;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    EditText messageView;
    ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageView = (EditText)findViewById(R.id.message);
        cityList = (ListView) findViewById(R.id.city_list);
    }

    public void onSendMessage(View view) {

        String messageText = messageView.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent
                .EXTRA_TEXT, messageText);
        String chooserTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);

        startActivity(chosenIntent
        );

    }

    public void loadCities(View view) {
        List<City> cities = createCities();
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, cities);
        cityList.setAdapter(adapter);

        getCitiesfromLocalServer();

    }

    private List<City> createCities() {
        City sp = new City(Long.valueOf(1), "Sao Paulo", "SP");
        City rj = new City(Long.valueOf(2), "Rio de Janeiro", "RJ");
        City bh = new City(Long.valueOf(1), "Belo Horizonte", "MG");
        City fl = new City(Long.valueOf(1), "Florianopolis", "SC");
        City sa = new City(Long.valueOf(1), "Salvador", "BA");

        List<City> cities = new ArrayList<>();
        cities.add(sp);
        cities.add(rj);
        cities.add(bh);
        cities.add(fl);
        cities.add(sa);

        return cities;
    }

    private void getCitiesfromServer() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create URL
                URL githubEndpoint = null;
                try {
                    githubEndpoint = new URL("https://api.github.com/");

                    // Create connection
                    HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

                    System.out.println("response code: " + myConnection.getResponseCode());
                    System.out.println("response message: " + myConnection.getResponseMessage());

                    InputStream responseBody = myConnection.getInputStream();

                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    jsonReader.beginObject(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys

                        System.out.println(jsonReader.nextName());
                        System.out.println(jsonReader.nextString());
                    }

                    jsonReader.close();
                    myConnection.disconnect();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void getCitiesfromLocalServer() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create URL
                URL endpoint = null;
                try {
                    endpoint = new URL("http://192.168.0.10:8080/cities");
                    System.err.println("1");

                    // Create connection
                    HttpURLConnection myConnection = (HttpURLConnection) endpoint.openConnection();
                    System.err.println("2 " + myConnection);

                    System.out.println("response code: " + myConnection.getResponseCode());
                    //System.out.println("response message: " + myConnection.getResponseMessage());


                    InputStream responseBody = myConnection.getInputStream();
                    System.err.println("3");

                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    System.err.println("4");

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    System.err.println("5");

                    jsonReader.beginArray(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys

                        jsonReader.beginObject();
                        String id = jsonReader.nextName();
                        String idValue = jsonReader.nextString();
                        String name = jsonReader.nextName();
                        String nameValue = jsonReader.nextString();
                        String state = jsonReader.nextName();
                        String stateValue = jsonReader.nextString();
                        jsonReader.nextName();
                        jsonReader.skipValue();

                        City city = new City(Long.valueOf(idValue), nameValue, stateValue);
                        System.err.println(city);

                        jsonReader.endObject();
                    }

                    jsonReader.close();
                    myConnection.disconnect();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}