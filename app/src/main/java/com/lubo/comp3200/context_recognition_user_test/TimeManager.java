package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Lyubomir on 06/04/2015.
 */
public class TimeManager {

    private Calendar mCalendar;
    private final Activity mActivity;
    private AlarmManager mAlarmManager;
    private PendingIntent mMorningIntent;
    private PendingIntent mNoonIntent;
    private PendingIntent mAfternoonIntent;
    private PendingIntent mEveningIntent;
    private PendingIntent mNightIntent;


    public TimeManager(Activity activityContext) {
        mCalendar = Calendar.getInstance();
        mActivity = activityContext;
    }

    public void setPartOfDay() {
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= AppParams.MORNING_START && hour <= AppParams.MORNING_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PART_OF_DAY.valueOf("MORNING");
        }else if (hour >= AppParams.NOON_START && hour <= AppParams.NOON_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PART_OF_DAY.valueOf("NOON");
        }else if (hour >= AppParams.AFTERNOON_START && hour <= AppParams.AFTERNOON_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PART_OF_DAY.valueOf("AFTERNOON");
        }else if (hour >= AppParams.EVENING_START && hour <= AppParams.EVENING_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PART_OF_DAY.valueOf("EVENING");
        }else if (hour >= AppParams.NIGHT_START && hour <= AppParams.NIGHT_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PART_OF_DAY.valueOf("NIGHT");
        }
    }

    public void setSeason() {
        int season = mCalendar.get(Calendar.MONTH) % 11;
        if (season >= AppParams.SPRING_START && season <= AppParams.SPRING_END) {
            ContextParams.CURRENT_SEASON = AppParams.SEASON.valueOf("SPRING");
        }else if (season >= AppParams.SUMMER_START && season <= AppParams.SUMMER_END) {
            ContextParams.CURRENT_SEASON = AppParams.SEASON.valueOf("SUMMER");
        }else if (season >= AppParams.AUTUMN_START && season <= AppParams.AUTUMN_END) {
            ContextParams.CURRENT_SEASON = AppParams.SEASON.valueOf("AUTUMN");
        }else if (season >= AppParams.WINTER_START && season <= AppParams.WINTER_END) {
            ContextParams.CURRENT_SEASON = AppParams.SEASON.valueOf("WINTER");
        }
    }

    public void startAlarmUpdates() {
        mAlarmManager = (AlarmManager)mActivity.getSystemService(mActivity.ALARM_SERVICE);
        Intent intent = new Intent(mActivity, AlarmReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 24 hours in milliseconds
        int dayInMillis = 1000 * 60 * 60 * 24;
        // Set up morning alarm
        mMorningIntent = PendingIntent.getBroadcast(mActivity, AppParams.MORNING_ALARM_INTENT, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.MORNING_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mMorningIntent);
        // Set up noon alarm
        mNoonIntent = PendingIntent.getBroadcast(mActivity, AppParams.NOON_ALARM_INTENT, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.NOON_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mNoonIntent);
        // Set up afternoon alarm
        mAfternoonIntent = PendingIntent.getBroadcast(mActivity, AppParams.AFTERNOON_ALARM_INTENT, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.AFTERNOON_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mAfternoonIntent);
        // Set up evening alarm
        mEveningIntent = PendingIntent.getBroadcast(mActivity, AppParams.EVENING_ALARM_INTENT, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.EVENING_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mEveningIntent);
        // Set up night alarm
        mNightIntent = PendingIntent.getBroadcast(mActivity, AppParams.NIGHT_ALARM_INTENT, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.NIGHT_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mNightIntent);
    }

    public void stopAlarmUpdates() {
        if (mAlarmManager != null){
            mAlarmManager.cancel(mMorningIntent);
            mAlarmManager.cancel(mNoonIntent);
            mAlarmManager.cancel(mAfternoonIntent);
            mAlarmManager.cancel(mEveningIntent);
            mAlarmManager.cancel(mNightIntent);
        }
    }



}
