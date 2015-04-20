package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Parses the JSON file containing the pre-defined context definitions
 *
 * Created by Lyubomir on 16/03/2015.
 */


public class ContextParser {

    private Gson mGson;
    // Converted JSON file to String
    private String mJsonFile;
    // Calling activity
    private Activity mActivity;
    // List of extracted contexts
    private ArrayList<Context> mContexts;
    private Logger mLog;
    private final String APPTAG = "ContextParser";

    public ContextParser(Activity activityContext) {
        mActivity = activityContext;
        mGson = new Gson();
        mLog = new Logger(mActivity);
    }

    // Reads the JSON contexts file from the assets folder and converts it to a string
    public void loadJSONAssets() {
        Message status = new Message(APPTAG, "Reading in JSON file containing the pre-defined contexts");
        mLog.addMessage(status);
        try {
            InputStream is = mActivity.getAssets().open("predefined_contexts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            mJsonFile = new String(buffer, "UTF-8");
            status.setContent("JSON converted to String successfully");
            mLog.addMessage(status);
        } catch (IOException e) {
            status.setContent("Read JSON error");
            status.setExtra(Log.getStackTraceString(e));
            mLog.addMessage(status);
        }
    }

    // Extracts the Context objects from the json string
    public void extractContexts() {
        Type collectionType = new TypeToken<ArrayList<Context>>(){}.getType();
        mContexts = mGson.fromJson(mJsonFile, collectionType);
    }

    // Saves the extracted context in SharedPreferences
    public void saveContexts() {
        for (Context c : mContexts){
            ContextStore.getInstance().saveContext(c);
        }
    }





}
