package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Storage for all contexts. Provides methods to read from and write to SharedPreferences.
 * Stores the currently active context so that the context recognition service can look it up.
 *
 * Created by Lyubomir on 16/03/2015.
 */
public class ContextStore {

    private final SharedPreferences mPrefs;
    // SharedPreferences name
    private static final String SHARED_PREFERENCES = "ContextStorePrefs";
    // Calling activity
    private final Activity mActivity;
    // Currently active context
    private static Context mActiveContext;
    private Logger mLog;
    private final String APPTAG = "ContextStore";

    public ContextStore (Activity activityContext) {
        mActivity = activityContext;
        mPrefs = mActivity.getSharedPreferences(SHARED_PREFERENCES, mActivity.MODE_PRIVATE);
        mLog = new Logger(mActivity);
    }

    // Save a Context to SharedPreferences
    public void saveContext(Context context) {
        Message status = new Message(APPTAG, "Saving contexts to SharedPreferences");
        mLog.addMessage(status);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(getContextKey(context), getContextValue(context));
        editor.apply();
        status.setContent("Contexts saved successfully");
        mLog.addMessage(status);
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



    public static Context getActiveContext() {
        return mActiveContext;
    }

    public static void setActiveContext(Context mActiveContext) {
        ContextStore.mActiveContext = mActiveContext;
    }
}
