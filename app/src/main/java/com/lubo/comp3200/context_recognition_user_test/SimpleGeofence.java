package com.lubo.comp3200.context_recognition_user_test;


/**
 * A single Geofence object, defined by its center and radius.
 * Code heavily based on Google's examples
 */
public class SimpleGeofence {
    // Instance variables
    private final String mId;
    private final double mLatitude;
    private final double mLongitude;
    private final float mRadius;
    private long mExpirationDuration;
    private int mTransitionType;

    public SimpleGeofence(String geofenceId, double latitude, double longitude, float radius,
                          long expiration,
                          int transition) {
        // Set the instance fields from the constructor
        this.mId = geofenceId;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRadius = radius;
        this.mExpirationDuration = expiration;
        this.mTransitionType = transition;
    }
    // Instance field getters
    public String getId() {
        return mId;
    }
    public double getLatitude() {
        return mLatitude;
    }
    public double getLongitude() {
        return mLongitude;
    }
    public float getRadius() {
        return mRadius;
    }
    public long getExpirationDuration() {
        return mExpirationDuration;
    }
    public int getTransitionType() {
        return mTransitionType;
    }

    // Creates a Location Services Geofence object from a SimpleGeofence.
    public com.google.android.gms.location.Geofence toGeofence() {
        // Build a new Geofence object
        return new com.google.android.gms.location.Geofence.Builder().setRequestId(getId())
                .setTransitionTypes(mTransitionType)
                .setCircularRegion(getLatitude(), getLongitude(), getRadius())
                .setExpirationDuration(mExpirationDuration)
                .build();
    }
}