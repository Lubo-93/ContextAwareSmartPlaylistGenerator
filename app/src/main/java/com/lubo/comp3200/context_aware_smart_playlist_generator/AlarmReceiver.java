package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.content.Context;

/**
 * Created by Lyubomir on 06/04/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        switch (action) {
            case AppParams.MORNING_ALARM_FILTER:
                ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.MORNING;
            case AppParams.NOON_ALARM_FILTER:
                ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.NOON;
            case AppParams.AFTERNOON_ALARM_FILTER:
                ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.AFTERNOON;
            case AppParams.EVENING_ALARM_FILTER:
                ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.EVENING;
            case AppParams.NIGHT_ALARM_FILTER:
                ContextParams.CURRENT_DAY_PART = AppParams.PartOfDay.NIGHT;
            case AppParams.TIME_ALARM_FILTER:

            case AppParams.TIME_DATE_ALARM_FILTER:

            case AppParams.TIME_RANGE_ALARM_FILTER:

            case AppParams.TIME_RANGE_DATE_FILTER:

            case AppParams.REMOVE_ALARM_FILTER:
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, AppParams.REMOVE_ALARM_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(alarmIntent);
        }

    }
}
