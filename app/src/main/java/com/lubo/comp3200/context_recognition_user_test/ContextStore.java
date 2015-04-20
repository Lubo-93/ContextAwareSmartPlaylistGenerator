package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for all contexts. Provides methods to read from and write to SharedPreferences.
 * Stores the currently active context so that the context recognition service can look it up.
 *
 * Created by Lyubomir on 16/03/2015.
 */
public final class ContextStore {

    private static ContextStore instance;
    private static SharedPreferences mPrefs;
    // SharedPreferences name
    private static final String SHARED_PREFERENCES = "ContextStorePrefs";
    // Currently active context
    private static Context mActiveContext;
    private static Logger mLog;
    private static final String APPTAG = "ContextStore";
    // All contexts
    private static List<Context> mAllContexts;
    // Categorized contexts
    private static List<Context> mWalkingContexts;
    private static List<Context> mRunningContexts;
    private static List<Context> mCyclingContexts;
    private static List<Context> mCommutingContexts;
    private static List<Context> mTravellingContexts;
    private static List<Context> mHomeContexts;
    private static List<Context> mWorkContexts;
    private static List<Context> mRainyContexts;
    private static List<Context> mSunnyContexts;
    private static List<Context> mCloudyContexts;
    private static List<Context> mStormyContexts;
    private static List<Context> mSnowContexts;
    private static List<Context> mMorningContexts;
    private static List<Context> mNoonContexts;
    private static List<Context> mAfternoonContexts;
    private static List<Context> mEveningContexts;
    private static List<Context> mNightContexts;
    private static List<Context> mHotContexts;
    private static List<Context> mColdContexts;
    private static List<Context> mSpringContexts;
    private static List<Context> mSummerContexts;
    private static List<Context> mAutumnContexts;
    private static List<Context> mWinterContexts;


    private ContextStore () {
        mAllContexts = new ArrayList<Context>();
        mWalkingContexts = new ArrayList<Context>();
        mRunningContexts = new ArrayList<Context>();
        mCyclingContexts = new ArrayList<Context>();
        mCommutingContexts = new ArrayList<Context>();
        mTravellingContexts = new ArrayList<Context>();
        mHomeContexts = new ArrayList<Context>();
        mWorkContexts = new ArrayList<Context>();
        mRainyContexts = new ArrayList<Context>();
        mSunnyContexts = new ArrayList<Context>();
        mCloudyContexts = new ArrayList<Context>();
        mStormyContexts = new ArrayList<Context>();
        mSnowContexts = new ArrayList<Context>();
        mMorningContexts = new ArrayList<Context>();
        mNoonContexts = new ArrayList<Context>();
        mAfternoonContexts = new ArrayList<Context>();;
        mEveningContexts = new ArrayList<Context>();
        mNightContexts = new ArrayList<Context>();
        mHotContexts = new ArrayList<Context>();
        mColdContexts = new ArrayList<Context>();
        mSpringContexts = new ArrayList<Context>();
        mSummerContexts = new ArrayList<Context>();
        mAutumnContexts = new ArrayList<Context>();
        mWinterContexts = new ArrayList<Context>();
    }

    public static ContextStore getInstance() {
        if(instance == null) {
            instance = new ContextStore();
        }
        return instance;
    }

    // Save a Context to SharedPreferences
    public void saveContext(Context context) {
        mAllContexts.add(context);
        categorizeContext(context);
        Message status = new Message(APPTAG, "Saving context to SharedPreferences");
        mLog.addMessage(status);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(getContextKey(context), getContextValue(context));
        editor.apply();
        status.setContent("Contexts saved successfully");
        mLog.addMessage(status);
    }

    // Categorize context and save it to the according ArrayList
    private void categorizeContext(Context context) {
        // Sort by activity
        if (context.getActivity() != AppParams.Activity.NONE) {
            if (context.getActivity() == AppParams.Activity.WALKING) {
                mWalkingContexts.add(context);
            } else if (context.getActivity() == AppParams.Activity.RUNNING) {
                mRunningContexts.add(context);
            } else if (context.getActivity() == AppParams.Activity.CYCLING) {
                mCyclingContexts.add(context);
            } else if (context.getActivity() == AppParams.Activity.COMMUTING) {
                mCommutingContexts.add(context);
            } else if (context.getActivity() == AppParams.Activity.TRAVELLING) {
                mTravellingContexts.add(context);
            }
        }
       /* // Sort by location
        if (context.getLocation(). == AppParams.LOCATION.HOME")) {
            mHomeContexts.add(context);
        }else if (context.getActivity() == AppParams.Activity.WORK")) {
            mWorkContexts.add(context);
        }*/
        // Sort by time
        if (context.getTime().getType() == AppParams.TypeOfTime.DAY_SECTION) {
            if (context.getPartOfDay() != AppParams.PartOfDay.NONE) {
                if (context.getPartOfDay() == AppParams.PartOfDay.MORNING) {
                    mMorningContexts.add(context);
                } else if (context.getPartOfDay() == AppParams.PartOfDay.NOON) {
                    mNoonContexts.add(context);
                } else if (context.getPartOfDay() == AppParams.PartOfDay.AFTERNOON) {
                    mAfternoonContexts.add(context);
                } else if (context.getPartOfDay() == AppParams.PartOfDay.EVENING) {
                    mNightContexts.add(context);
                } else if (context.getPartOfDay() == AppParams.PartOfDay.NIGHT) {
                    mEveningContexts.add(context);
                }
            }
        }else if (context.getTime().getType() == AppParams.TypeOfTime.TIME) {

        }
        // Sort by weather
        if (context.getWeather() != AppParams.Weather.NONE) {
            if (context.getWeather() == AppParams.Weather.SUNNY) {
                mSunnyContexts.add(context);
            } else if (context.getWeather() == AppParams.Weather.CLOUDY) {
                mCloudyContexts.add(context);
            } else if (context.getWeather() == AppParams.Weather.RAINY) {
                mRainyContexts.add(context);
            } else if (context.getWeather() == AppParams.Weather.STORMY) {
                mStormyContexts.add(context);
            } else if (context.getWeather() == AppParams.Weather.SNOW) {
                mSnowContexts.add(context);
            }
        }

    }

    // Return a Context from SharedPreferences by its name if it exists
    public Context getContext(String name) {
        Message status = new Message(APPTAG, "Searching for " + name + " context");
        mLog.addMessage(status);
        if (checkContextStore(name)) {
            Context context = getContextFromJSON(mPrefs.getString(name, AppParams.INVALID_STRING_VALUE));
            status.setContent("Context retrieved successfully");
            mLog.addMessage(status);
            return context;
        }
        status.setContent("Context not found");
        mLog.addMessage(status);
        return null;
    }

    // Remove a Context from SharedPreferences
    public void removeContext(String name){
        Message status = new Message(APPTAG, "Removing " + name + " context");
        mLog.addMessage(status);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(name);
        editor.apply();
        status.setContent("Context removed successfully");
        mLog.addMessage(status);
    }

    // Remove all Contexts
    public void removeAllContexts() {
        Message status = new Message(APPTAG, "Removing all contexts");
        mLog.addMessage(status);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.apply();
        status.setContent("All contexts removed successfully");
        mLog.addMessage(status);
    }

    // Get a Context's key value for storage in SharedPreferences
    public String getContextKey(Context context) {
        String name = context.getName();
        return name;
    }

    // Convert a Context to JSON so it can be stored in SharedPreferences
    public String getContextValue(Context context){
        Gson gson = new Gson();
        String converted = gson.toJson(context);
        return converted;
    }

    // Convert a Context from JSON to normal Java object
    public Context getContextFromJSON(String context){
        Gson gson = new Gson();
        Context converted = (Context) gson.fromJson(context, Context.class);
        return converted;
    }

    // Check whether a Context with the given name exists in SharedPreferences
    public boolean checkContextStore(String name){
        if (mPrefs.getString(name, AppParams.INVALID_STRING_VALUE) != AppParams.INVALID_STRING_VALUE){
            return true;
        }
        return false;
    }

    public static void initialSetup(Activity activityContext) {
        mPrefs = activityContext.getSharedPreferences(SHARED_PREFERENCES, activityContext.MODE_PRIVATE);
        mLog = new Logger(activityContext);
    }

    public static Context getActiveContext() {
        return mActiveContext;
    }

    public static void setActiveContext(Context mActiveContext) {
        ContextStore.mActiveContext = mActiveContext;
    }

    public static List<Context> getAllContexts() {
        return mAllContexts;
    }

    public static List<Context> getWalkingContexts() {
        return mWalkingContexts;
    }

    public static List<Context> getRunningContexts() {
        return mRunningContexts;
    }

    public static List<Context> getCyclingContexts() {
        return mCyclingContexts;
    }

    public static List<Context> getCommutingContexts() {
        return mCommutingContexts;
    }

    public static List<Context> getTravellingContexts() {
        return mTravellingContexts;
    }

    public static List<Context> getHomeContexts() {
        return mHomeContexts;
    }

    public static List<Context> getWorkContexts() {
        return mWorkContexts;
    }

    public static List<Context> getRainyContexts() {
        return mRainyContexts;
    }

    public static List<Context> getSunnyContexts() {
        return mSunnyContexts;
    }

    public static List<Context> getCloudyContexts() {
        return mCloudyContexts;
    }

    public static List<Context> getStormyContexts() {
        return mStormyContexts;
    }

    public static List<Context> getSnowContexts() {
        return mSnowContexts;
    }

    public static List<Context> getMorningContexts() {
        return mMorningContexts;
    }

    public static List<Context> getNoonContexts() {
        return mNoonContexts;
    }

    public static List<Context> getAfternoonContexts() {
        return mAfternoonContexts;
    }

    public static List<Context> getEveningContexts() {
        return mEveningContexts;
    }

    public static List<Context> getNightContexts() {
        return mNightContexts;
    }

    public static List<Context> getHotContexts() {
        return mHotContexts;
    }

    public static List<Context> getColdContexts() {
        return mColdContexts;
    }

    public static List<Context> getSpringContexts() {
        return mSpringContexts;
    }

    public static List<Context> getSummerContexts() {
        return mSummerContexts;
    }

    public static List<Context> getAutumnContexts() {
        return mAutumnContexts;
    }

    public static List<Context> getWinterContexts() {
        return mWinterContexts;
    }
}
