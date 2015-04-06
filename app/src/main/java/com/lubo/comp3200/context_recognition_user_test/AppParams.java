package com.lubo.comp3200.context_recognition_user_test;

/**
 * Created by Lubo on 18.1.2015.
 *
 * This class defines constants used by the app
 *
 */
public class AppParams {

    // Used to track what type of geofence removal request was made.
    public enum REMOVE_TYPE {INTENT, LIST}

    // Used to track what type of request is in process
    public enum REQUEST_TYPE {ADD, REMOVE}

    // Types of activities
    public enum ACTIVITY {WALKING, RUNNING, CYCLING, COMMUTING, TRAVELLING, NONE}

    // Types of weather
    public enum WEATHER {RAINY, STORMY, SUNNY, CLOUDY, SNOW, NONE}

    // Types of Time objects
    public enum TYPE_OF_TIME {DAY_SECTION, TIME, DATE, TIME_DATE, TIME_RANGE, TIME_RANGE_DATE, NONE}

    // Pre-defined parts of the day for contexts
    public enum PART_OF_DAY {MORNING, NOON, AFTERNOON, EVENING, NIGHT}

    // Day sections
    public static int MORNING_START = 6;
    public static int MORNING_END = 11;
    public static int NOON_START = 12;
    public static int NOON_END = 13;
    public static int AFTERNOON_START = 14;
    public static int AFTERNOON_END = 18;
    public static int EVENING_START = 19;
    public static int EVENING_END = 23;
    public static int NIGHT_START = 00;
    public static int NIGHT_END = 5;

    // Pre-defined temperatures
    public enum TEMPERATURE {HOT, COLD, NONE}

    // Threshold that determine whether it's hot or cold
    public static double TEMP_THRESHOLD = 20.0;

    // Seasons
    public enum SEASON {SPRING, SUMMER, AUTUMN, WINTER, NONE}

    public static int SPRING_START = 2;
    public static int SPRING_END = 4;
    public static int SUMMER_START = 5;
    public static int SUMMER_END = 7;
    public static int AUTUMN_START = 8;
    public static int AUTUMN_END = 10;
    public static int WINTER_START = 11;
    public static int WINTER_END = 1;

    // Update interval for activity scanning
    public final static int ACTIVITY_UPDATE_INTERVAL = 30000;

    // Key for first time startup flag
    public final static String KEY_FIRST_START = "FIRST_TIME_START";

    // Intent extra name for the activity of the current context
    public final static String CURRENT_CONTEXT_ACTIVITY = "CURRENT_CONTEXT_ACTIVITY";
    public final static String CURRENT_CONTEXT_WEATHER = "CURRENT_CONTEXT_WEATHER";

    // Intent extra names for the current coordinates
    public final static String CURRENT_LAT = "CURRENT_LOCATION_LATITUDE";
    public final static String CURRENT_LNG = "CURRENT_LOCATION_LONGITUDE";

    // Confidence threshold for activities
    public final static int MINIMUM_CONFIDENCE = 70;

    // Base URL for REST calls to the Weather API
    public static String WEATHER_BASE_URL = "http://api.wunderground.com/api/";

    // Key needed to access the Weather API
    public static String WEATHER_API_KEY = "292e0ed8aab9e924/";

    // URL for weather conditions query
    public static String WEATHER_CONDITIONS_QUERY = "conditions/q/";

    // PendingIntent request codes
    public static final int MORNING_ALARM_INTENT = 0000;
    public static final int NOON_ALARM_INTENT = 1111;
    public static final int AFTERNOON_ALARM_INTENT = 2222;
    public static final int EVENING_ALARM_INTENT = 3333;
    public static final int NIGHT_ALARM_INTENT = 4444;

    // Intent actions
    public static final String ACTION_CONNECTION_ERROR = "ACTION_CONNECTION_ERROR";

    public static final String ACTION_CONNECTION_SUCCESS = "ACTION_CONNECTION_SUCCESS";

    public static final String ACTION_GEOFENCES_ADDED = "ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED = "ACTION_GEOFENCES_DELETED";

    public static final String ACTION_GEOFENCE_ERROR = "ACTION_GEOFENCES_ERROR";

    public static final String ACTION_GEOFENCE_TRANSITION = "ACTION_GEOFENCE_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR = "ACTION_GEOFENCE_TRANSITION_ERROR";

    // The Intent category used by the app
    public static final String CATEGORY_CONTEXT_ANALYSIS = "CATEGORY_CONTEXT_ANALYSIS";

    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE = "com.example.android.EXTRA_CONNECTION_CODE";

    public static final String EXTRA_CONNECTION_ERROR_CODE = "EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE = "EXTRA_CONNECTION_ERROR_MESSAGE";

    public static final String EXTRA_GEOFENCE_STATUS = "EXTRA_GEOFENCE_STATUS";


    // Keys for flattened geofences stored in SharedPreferences
    public static final String KEY_LATITUDE = "KEY_LATITUDE";

    public static final String KEY_LONGITUDE = "KEY_LONGITUDE";

    public static final String KEY_RADIUS = "KEY_RADIUS";

    public static final String KEY_EXPIRATION_DURATION = "KEY_EXPIRATION_DURATION";

    public static final String KEY_TRANSITION_TYPE = "KEY_TRANSITION_TYPE";

    // The prefix for flattened geofence keys
    public static final String KEY_PREFIX = "KEY";

    // Invalid values, used to test geofence storage when retrieving geofences
    public static final long INVALID_LONG_VALUE = -999l;

    public static final float INVALID_FLOAT_VALUE = -999.0f;

    public static final int INVALID_INT_VALUE = -999;

    public static final String INVALID_STRING_VALUE = null;

    // Constants used in verifying the correctness of input values
    public static final double MAX_LATITUDE = 90.d;

    public static final double MIN_LATITUDE = -90.d;

    public static final double MAX_LONGITUDE = 180.d;

    public static final double MIN_LONGITUDE = -180.d;

    public static final float MIN_RADIUS = 1f;

    // Request code to send to Google Play services
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;




}
