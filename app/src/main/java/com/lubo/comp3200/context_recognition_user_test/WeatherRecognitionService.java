package com.lubo.comp3200.context_recognition_user_test;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lyubomir on 25/03/2015.
 */
public class WeatherRecognitionService extends IntentService{

    // Http connection for GET requests to the weather server
    HttpURLConnection mHttpConnection;

    public WeatherRecognitionService(){
        super("WeatherRecognitionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Double lat = intent.getDoubleExtra(AppParams.CURRENT_LAT, 0);
        Double lng = intent.getDoubleExtra(AppParams.CURRENT_LNG, 0);
        String address = AppParams.WEATHER_BASE_URL + AppParams.WEATHER_API_KEY + AppParams.WEATHER_CONDITIONS_QUERY +
                                lat + "," + lng + ".json";
        try{
            URL url = new URL(address);
            mHttpConnection = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(mHttpConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            // Build a String from the InputStream
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
            // Create a JSON object from the result String
            JSONObject JSONResponse = new JSONObject(result);
            // Traverse the JSON stream
            JSONObject JSONSubObj = new JSONObject(JSONResponse.getString("current_observation"));
            // Get the temperature and weather
            AppParams.TEMPERATURE temperature = determineTemperature(JSONSubObj.getString("temp_c"));
            AppParams.WEATHER weather = determineWeather(JSONSubObj.getString("weather"));

            AppParams.WEATHER mCurrentWeather = AppParams.WEATHER.valueOf(intent.getStringExtra(AppParams.CURRENT_CONTEXT_WEATHER));
            if (mCurrentWeather != null){

            }else {
                Context initialContext = Main.mScheduler.getInitialContext();
                initialContext.setWeather(weather);
                initialContext.setTemperature(temperature);
                ContextParams.CURRENT_WEATHER = weather;
                ContextParams.CURRENT_TEMPERATURE = temperature;
            }
        }catch (Exception e){

        }finally {
            mHttpConnection.disconnect();
        }
    }

    private AppParams.WEATHER determineWeather(String weather) {
        if(weather.contains("Clear") || weather.contains("Scattered") || weather.equals("Partly Cloudy")) {
            return AppParams.WEATHER.valueOf("SUNNY");
        }else if(weather.contains("Cloudy") || weather.equals("Partly Sunny")
                || weather.contains("Mist") || weather.contains("Fog")){
            return AppParams.WEATHER.valueOf("CLOUDY");
        }else if(weather.contains("Rain") || weather.contains("Drizzle")){
            return AppParams.WEATHER.valueOf("RAINY");
        }else if(weather.contains("storm")){
            return AppParams.WEATHER.valueOf("STORMY");
        }else if(weather.contains("Snow")){
            return AppParams.WEATHER.valueOf("SNOW");
        }
        return AppParams.WEATHER.valueOf("NONE");
    }

    private AppParams.TEMPERATURE determineTemperature(String temp) {
        Double tempConverted = Double.parseDouble(temp);
        if (tempConverted > AppParams.TEMP_THRESHOLD) {
            return AppParams.TEMPERATURE.valueOf("HOT");
        }else if (tempConverted < AppParams.TEMP_THRESHOLD){
            return AppParams.TEMPERATURE.valueOf("COLD");
        }
        return AppParams.TEMPERATURE.valueOf("NONE");
    }


}
