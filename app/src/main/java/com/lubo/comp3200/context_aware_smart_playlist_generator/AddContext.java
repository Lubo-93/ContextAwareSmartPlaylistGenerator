package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;


public class AddContext extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    // UI handles
    private EditText mNameInput;
    private Spinner mActivitySpinner;
    private Spinner mLocationSpinner;
    private Spinner mTimeSpinner;
    private EditText mDurationInput;
    private Spinner mWeatherSpinner;
    private Spinner mTemperatureSpinner;
    private Spinner mSeasonSpinner;
    private TableLayout mTable;
    private EditText mTimeView;
    private EditText mRangeView;
    private EditText mDateView;
    // Variables to store the selected date and time
    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedDay;
    private int mSelectedHour;
    private int mSelectedMinutes;
    // Flag to indicate whether the time picker or the range picker is active
    private boolean isRange;
    // Location store to retrieve the names of all the locations
    private LocationStore mLocationStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_context);
        setTitle("Add New Context");
        mLocationStore = new LocationStore(this);
        setupUI();
        // Set range false initially
        isRange = false;
    }

    // Initial UI setup
    private void setupUI() {
        // Retrieve views
        mTable = (TableLayout) findViewById(R.id.add_context_table);
        mNameInput = (EditText) findViewById(R.id.name_input);
        mActivitySpinner = (Spinner) findViewById(R.id.activity_spinner);
        mLocationSpinner = (Spinner) findViewById(R.id.location_spinner);
        mTimeSpinner = (Spinner) findViewById(R.id.time_spinner);
        mTimeSpinner.setOnItemSelectedListener(this);
        mDurationInput = (EditText) findViewById(R.id.duration_input);
        mWeatherSpinner = (Spinner) findViewById(R.id.weather_spinner);
        mTemperatureSpinner = (Spinner) findViewById(R.id.temperature_spinner);
        mSeasonSpinner = (Spinner) findViewById(R.id.season_spinner);
        // Populate spinners
        mActivitySpinner.setAdapter(new ArrayAdapter<AppParams.Activity>(this, android.R.layout.simple_spinner_item, AppParams.Activity.values()));
        // Get the ids of all locations to populate the location spinner
        ArrayList<String> locationIds = mLocationStore.getAllLocationsIs();
        locationIds.add(AppParams.ADD_NEW_LOCATION);
        locationIds.add(AppParams.NO_LOCATION);
        mLocationSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locationIds));
        // ArrayAdapter for the time spinner; contains the parts of day with two extra options
        ArrayList<String> timeValues = new ArrayList<String>(7);
        AppParams.PartOfDay[] partsOfDay = AppParams.PartOfDay.values();
        for (int i = 0; i < AppParams.PartOfDay.values().length - 1; i++) {
            timeValues.add(partsOfDay[i].toString());
        }
        timeValues.add(AppParams.SPECIFIC_TIME);
        timeValues.add(AppParams.SPECIFIC_TIME_RANGE);
        timeValues.add(AppParams.PartOfDay.NONE.toString());
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeValues);
        mTimeSpinner.setAdapter(timeAdapter);
        mWeatherSpinner.setAdapter(new ArrayAdapter<AppParams.Weather>(this, android.R.layout.simple_spinner_item, AppParams.Weather.values()));
        mTemperatureSpinner.setAdapter(new ArrayAdapter<AppParams.Temperature>(this, android.R.layout.simple_spinner_item, AppParams.Temperature.values()));
        mSeasonSpinner.setAdapter(new ArrayAdapter<AppParams.Season>(this, android.R.layout.simple_spinner_item, AppParams.Season.values()));
        // Get the current date and time
        Calendar c = Calendar.getInstance();
        mSelectedYear = c.get(Calendar.YEAR);
        mSelectedMonth = c.get(Calendar.MONTH);
        mSelectedDay = c.get(Calendar.DAY_OF_MONTH);
        mSelectedHour = c.get(Calendar.HOUR_OF_DAY);
        mSelectedMinutes = c.get(Calendar.MINUTE);
    }

    public void addContext() {
        // Retrieve the user input from the views
        String name = mNameInput.getText().toString();
        AppParams.Activity activity = AppParams.Activity.valueOf(mActivitySpinner.getSelectedItem().toString());
        AppParams.Weather weather = AppParams.Weather.valueOf(mWeatherSpinner.getSelectedItem().toString());
        AppParams.Temperature temperature = AppParams.Temperature.valueOf(mTemperatureSpinner.getSelectedItem().toString());
        AppParams.Season season = AppParams.Season.valueOf(mSeasonSpinner.getSelectedItem().toString());

    }
    // ItemSelected listener for the time and location spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals(AppParams.ADD_NEW_LOCATION)) {

        }
        if (item.equals(AppParams.SPECIFIC_TIME) || item.equals(AppParams.SPECIFIC_TIME_RANGE)) {
            // Add a table row for time input
            TableRow timeRow = new TableRow(this);
            timeRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            mTimeView = new EditText(this);
            mTimeView.generateViewId();
            mTimeView.setId(R.id.add_context_time_view);
            mTimeView.setFocusable(false);
            // Open the time picker on user click
            mTimeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePickerDialog(mSelectedHour, mSelectedMinutes, true, mOnTimeSetListener);
                }
            });
            timeRow.addView(mTimeView);
            mTable.addView(timeRow, 4);
            // Add a table row for date input
            TableRow dateRow = new TableRow(this);
            dateRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            mDateView = new EditText(this);
            mDateView.generateViewId();
            mDateView.setId(R.id.add_context_date_view);
            mDateView.setFocusable(false);
            // Open the date picker on user click
            mDateView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(mSelectedYear, mSelectedMonth, mSelectedDay, mOnDateSetListener);
                }
            });
            dateRow.addView(mDateView);
            // If specific time was selected, add the date row after the time row
            if(item.equals(AppParams.SPECIFIC_TIME)){
                mTable.addView(dateRow, 5);
            }else{
                // If specfic range was selected, add a table row for it and add the date row after
                TableRow rangeRow = new TableRow(this);
                rangeRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                mRangeView = new EditText(this);
                mRangeView.generateViewId();
                mRangeView.setId(R.id.add_context_range_view);
                mRangeView.setFocusable(false);
                // Open the time picker on user click
                mRangeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Indicate that the range picker was selected
                        isRange = true;
                        showTimePickerDialog(mSelectedHour, mSelectedMinutes, true, mOnTimeSetListener);
                    }
                });
                rangeRow.addView(mRangeView);
                mTable.addView(rangeRow, 5);
                mTable.addView(dateRow, 6);
            }
        }
    }
    // Method from OnItemSelected interface; not used
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Callback for DatePicker
    private DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Update variables
            mSelectedMonth = monthOfYear;
            mSelectedDay = dayOfMonth;
            // Update the UI
            updateDateUI();
        }
    };
    // Callback for TimePicker
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Update variables
            mSelectedHour = hourOfDay;
            mSelectedMinutes = minute;
            // Update UI
            updateTimeUI();
        }
    };

    // Display the DatePicker
    private DatePickerDialog showDatePickerDialog(int initialYear, int initialMonth, int initialDay, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog dialog = new DatePickerDialog(this, listener, initialYear, initialMonth, initialDay);
        dialog.show();
        return dialog;
    }
    // Display the TimePicker
    private TimePickerDialog showTimePickerDialog(int initialHour, int initialMinutes, boolean is24Hour, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog dialog = new TimePickerDialog(this, listener, initialHour, initialMinutes, is24Hour);
        dialog.show();
        return dialog;
    }

    // Updates the time selected by the user from the time picker
    public void updateTimeUI() {
        // Convert time to proper 24hour format
        String hour = (mSelectedHour > 9) ? "" + mSelectedHour: "0" + mSelectedHour ;
        String minutes = (mSelectedMinutes > 9) ? "" + mSelectedMinutes : "0" + mSelectedMinutes;
        // Determine which view to update
        if (isRange == false) {
            mTimeView.setText(hour + ":" + minutes);
        }else{
            mRangeView.setText(hour + ":" + minutes);
            isRange = false;
        }
    }

    // Updates the date selected by the user from the date picker
    public void updateDateUI() {
        // Convert date to proper format
        String month = ((mSelectedMonth + 1) > 9) ? "" + (mSelectedMonth + 1) : "0" + (mSelectedMonth + 1) ;
        String day = ((mSelectedDay) < 10) ? "0" + mSelectedDay : "" + mSelectedDay;
        mDateView.setText(day + "/" + month + "/" + mSelectedYear);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_context, menu);
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
