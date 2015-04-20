package com.lubo.comp3200.context_recognition_user_test;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class IndividualContextView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_context_view);
        displayContextInfo();

    }

    private void displayContextInfo() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(AppParams.SELECTED_CONTEXT_NAME);
        setTitle(name);
        Context context = ContextStore.getInstance().getContext(name);

        final TextView activity = (TextView) findViewById(R.id.individual_context_activity);
        final TextView location = (TextView) findViewById(R.id.individual_context_location);
        final TextView time = (TextView) findViewById(R.id.individual_context_time);
        final TextView duration = (TextView) findViewById(R.id.individual_context_duration);
        final TextView weather = (TextView) findViewById(R.id.individual_context_weather);
        final TextView temperature = (TextView) findViewById(R.id.individual_context_temperature);
        final TextView season = (TextView) findViewById(R.id.individual_context_season);

        String activityName = context.getActivity().toString();
        String locationName = context.getLocation().toString();
        String timeName = context.getTime().toString();
        int durationValue = context.getDuration();
        String weatherName = context.getWeather().toString();
        String temperatureName = context.getTemperature().toString();
        String seasonName = context.getSeason().toString();

        activity.setText(activityName);
        location.setText(locationName);
        time.setText(timeName);
        duration.setText(String.valueOf(durationValue));
        weather.setText(weatherName);
        temperature.setText(temperatureName);
        season.setText(seasonName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_individual_context_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
