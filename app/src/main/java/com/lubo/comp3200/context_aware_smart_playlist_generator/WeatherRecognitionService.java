package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.IntentService;
import android.content.Intent;

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
            AppParams.Temperature temperature = determineTemperature(JSONSubObj.getString("temp_c"));
            AppParams.Weather weather = determineWeather(JSONSubObj.getString("weather"));

            AppParams.Weather mCurrentWeather = AppParams.Weather.valueOf(intent.getStringExtra(AppParams.CURRENT_CONTEXT_WEATHER));
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

    private AppParams.Weather determineWeather(String weather) {
        if(weather.contains("Clear") || weather.contains("Scattered") || weather.equals("Partly Cloudy")) {
            return AppParams.Weather.valueOf("SUNNY");
        }else if(weather.contains("Cloudy") || weather.equals("Partly Sunny")
                || weather.contains("Mist") || weather.contains("Fog")){
            return AppParams.Weather.valueOf("CLOUDY");
        }else if(weather.contains("Rain") || weather.contains("Drizzle")){
            return AppParams.Weather.valueOf("RAINY");
        }else if(weather.contains("storm")){
            return AppParams.Weather.valueOf("STORMY");
        }else if(weather.contains("Snow")){
            return AppParams.Weather.valueOf("SNOW");
        }
        return AppParams.Weather.valueOf("NONE");
    }

    private AppParams.Temperature determineTemperature(String temp) {
        Double tempConverted = Double.parseDouble(temp);
        if (tempConverted > AppParams.TEMP_THRESHOLD) {
            return AppParams.Temperature.valueOf("HOT");
        }else if (tempConverted < AppParams.TEMP_THRESHOLD){
            return AppParams.Temperature.valueOf("COLD");
        }
        return AppParams.Temperature.valueOf("NONE");
    }


}
