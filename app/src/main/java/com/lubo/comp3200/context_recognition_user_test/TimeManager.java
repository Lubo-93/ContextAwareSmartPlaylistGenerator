package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

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
    private PendingIntent mSpecificTimeIntent;


    public TimeManager(Activity activityContext) {
        mCalendar = Calendar.getInstance();
        mActivity = activityContext;
        mAlarmManager = (AlarmManager)mActivity.getSystemService(mActivity.ALARM_SERVICE);
    }

    public void setPartOfDay() {
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= AppParams.MORNING_START && hour <= AppParams.MORNING_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.MORNING;
        }else if (hour >= AppParams.NOON_START && hour <= AppParams.NOON_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.NOON;
        }else if (hour >= AppParams.AFTERNOON_START && hour <= AppParams.AFTERNOON_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.AFTERNOON;
        }else if (hour >= AppParams.EVENING_START && hour <= AppParams.EVENING_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.EVENING;
        }else if (hour >= AppParams.NIGHT_START && hour <= AppParams.NIGHT_END) {
            ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.NIGHT;
        }
    }

    public void setSeason() {
        int season = mCalendar.get(Calendar.MONTH) % 11;
        if (season >= AppParams.SPRING_START && season <= AppParams.SPRING_END) {
            ContextParams.CURRENT_SEASON = AppParams.Season.SPRING;
        }else if (season >= AppParams.SUMMER_START && season <= AppParams.SUMMER_END) {
            ContextParams.CURRENT_SEASON = AppParams.Season.SUMMER;
        }else if (season >= AppParams.AUTUMN_START && season <= AppParams.AUTUMN_END) {
            ContextParams.CURRENT_SEASON = AppParams.Season.AUTUMN;
        }else if (season >= AppParams.WINTER_START && season <= AppParams.WINTER_END) {
            ContextParams.CURRENT_SEASON = AppParams.Season.WINTER;
        }
    }

    public void startAlarmUpdates() {
        Intent intent = new Intent(mActivity, AlarmReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 24 hours in milliseconds
        int dayInMillis = 1000 * 60 * 60 * 24;
        // Set up morning alarm
        intent.setAction(AppParams.MORNING_ALARM_FILTER);
        mMorningIntent = PendingIntent.getBroadcast(mActivity, AppParams.MORNING_ALARM_CODE, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.MORNING_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mMorningIntent);
        // Set up noon alarm
        intent.setAction(AppParams.NOON_ALARM_FILTER);
        mNoonIntent = PendingIntent.getBroadcast(mActivity, AppParams.NOON_ALARM_CODE, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.NOON_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mNoonIntent);
        // Set up afternoon alarm
        intent.setAction(AppParams.AFTERNOON_ALARM_FILTER);
        mAfternoonIntent = PendingIntent.getBroadcast(mActivity, AppParams.AFTERNOON_ALARM_CODE, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.AFTERNOON_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mAfternoonIntent);
        // Set up evening alarm
        intent.setAction(AppParams.EVENING_ALARM_FILTER);
        mEveningIntent = PendingIntent.getBroadcast(mActivity, AppParams.EVENING_ALARM_CODE, intent, 0);
        calendar.set(Calendar.HOUR_OF_DAY, AppParams.EVENING_START);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mEveningIntent);
        // Set up night alarm
        intent.setAction(AppParams.NIGHT_ALARM_FILTER);
        mNightIntent = PendingIntent.getBroadcast(mActivity, AppParams.NIGHT_ALARM_CODE, intent, 0);
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

    public void addAlarm(Context context) {
        Intent intent = new Intent(mActivity, AlarmReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 24 hours in milliseconds
        int dayInMillis = 1000 * 60 * 60 * 24;
        Time contextTime = context.getTime();
        AppParams.TypeOfTime typeOfTime = contextTime.getType();
        int year = contextTime.getYear();
        int month = contextTime.getMonth();
        int day = contextTime.getDay();
        if (typeOfTime == AppParams.TypeOfTime.TIME || typeOfTime == AppParams.TypeOfTime.DATE
                || typeOfTime == AppParams.TypeOfTime.TIME_DATE) {
            intent.setAction(AppParams.TIME_ALARM_FILTER);
            mSpecificTimeIntent = PendingIntent.getBroadcast(mActivity, AppParams.TIME_ALARM_CODE, intent, 0);

            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), dayInMillis, mSpecificTimeIntent);
            context.setIsActive(true);
        }

    }



}
