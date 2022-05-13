package com.v_chek_host.vcheckhost;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceManager {

    private static final String FRONT_TOP = "FRONT_TOP";
    private static final String FRONT_BOTTOM = "FRONT_BOTTOM";
    private static final String LEFT_BACK = "LEFT_BACK";
    private static final String LEFT_FRONT = "LEFT_FRONT";
    private static final String RIGHT_BACK = "RIGHT_BACK";
    private static final String RIGHT_FRONT = "RIGHT_FRONT";
    private static final String TOP = "TOP";
    private static final String LANGUAGECODE = "LANGUAGECODE";
    private static final String USERID = "USERID";
    private static final String TENANT_ID = "TENANT_ID";
    private static final String PARENT_ID = "PARENT_ID";
    private static final String SITE_ID = "SITE_ID";
    private static final String VIN_MANDATE = "VIN_MANDATE";
    private static final String VIN_MAX_LENGTH = "VIN_MAX_LENGTH";
    private static final String SITE_CODE = "SITE_CODE";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LASTNAME";
    private static final String DISPLAY_NAME = "DISPLAY_NAME";
    private static final String EMAIL_ID = "EMAIL_ID";
    private static final String LANGUAGE_ID = "LANGUAGE_ID";
    private static final String TIMEZONE_DURATION = "TIMEZONE_DURATION";
    private static final String TIMEZONE = "TIMEZONE";
    private static final String VIDEOTHRESHOLD = "VIDEOTHRESHOLD";
    private static final String ALLOW_EMPLOYEE_ID = "ALLOW_EMPLOYEE_ID";
    private static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ISLOGGED = "ISLOGGED";
    private static final String CONN_STRING = "CONN_STRING";
    private static final String ACTIVITY_NAME = "ACTIVITY_NAME";
    private static final String MODEL_ID= "MODEL_ID";
    private static final String LOG_MODEL_ID= "LOG_MODEL_ID";
    private static final String ACTIVITY_ID= "ACTIVITY_ID";
    private static final String MODEL_IMAGE_WIDTH= "MODEL_IMAGE_WIDTH";
    private static final String MODEL_IMAGE_HEIGHT= "MODEL_IMAGE_HEIGHT";
    private static final String MODEL_BOTTOM_CROP_HEIGHT= "MODEL_BOTTOM_CROP_HEIGHT";
    private static final String MODEL_OVERLAY= "MODEL_OVERLAY";
    private static final String MODEL_VIDEO_WIDTH= "MODEL_VIDEO_WIDTH";
    private static final String MODEL_VIDEO_HEIGHT= "MODEL_VIDEO_HEIGHT";
    private static final String MODEL_VIDEO_MAX_TIME= "MODEL_VIDEO_MAX_TIME";

    public static void setFrontTop(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(FRONT_TOP, facilityList).commit();
    }

    public static String getFrontTop(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String frontTopImagePath = sp.getString(FRONT_TOP, null);
        return frontTopImagePath;
    }

    public static void setFrontBottom(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(FRONT_BOTTOM, facilityList).commit();
    }

    public static String getFrontBottom(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String frontBottomImagePath = sp.getString(FRONT_BOTTOM, null);
        return frontBottomImagePath;
    }

    public static void setLeftBack(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LEFT_BACK, facilityList).commit();
    }

    public static String getLeftBack(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String leftBackImagePath = sp.getString(LEFT_BACK, null);
        return leftBackImagePath;
    }

    public static void setLeftFront(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LEFT_FRONT, facilityList).commit();
    }

    public static String getLeftFront(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String leftFrontImagePath = sp.getString(LEFT_FRONT, null);
        return leftFrontImagePath;
    }

    public static void setRightBack(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(RIGHT_BACK, facilityList).commit();
    }

    public static String getRightBack(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String rightBackImagePath = sp.getString(RIGHT_BACK, null);
        return rightBackImagePath;
    }

    public static void setRightFront(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(RIGHT_FRONT, facilityList).commit();
    }

    public static String getRightFront(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String rightFrontImagePath = sp.getString(RIGHT_FRONT, null);
        return rightFrontImagePath;
    }

    public static void setTop(Context context, String facilityList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TOP, facilityList).commit();
    }

    public static String getTop(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String topImagePath = sp.getString(TOP, null);
        return topImagePath;
    }

    public static void setLanguagecode(Context context, String languageCode) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LANGUAGECODE, languageCode).commit();
    }

    public static String getLanguagecode(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String topImagePath = sp.getString(LANGUAGECODE, "en");
        return topImagePath;
    }

    public static void setUserid(Context context, String userId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(USERID, userId).commit();
    }

    public static String getUserid(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String userId = sp.getString(USERID, null);
        return userId;
    }

    public static void setTenantId(Context context, String tenantId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TENANT_ID, tenantId).commit();
    }

    public static String getTenantId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String tenantId = sp.getString(TENANT_ID, null);
        return tenantId;
    }

    public static void setParentId(Context context, String parentId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PARENT_ID, parentId).commit();
    }

    public static String getParentId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String tenantId = sp.getString(PARENT_ID, null);
        return tenantId;
    }

    public static void setFirstName(Context context, String firstName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(FIRST_NAME, firstName).commit();
    }

    public static String getFirstName(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String firstName = sp.getString(FIRST_NAME, null);
        return firstName;
    }

    public static void setLastName(Context context, String lastName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LAST_NAME, lastName).commit();
    }

    public static String getLastName(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String lastName = sp.getString(LAST_NAME, null);
        return lastName;
    }

    public static void setDisplayName(Context context, String displayName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(DISPLAY_NAME, displayName).commit();
    }

    public static String getDisplayName(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String displayName = sp.getString(DISPLAY_NAME, null);
        return displayName;
    }

    public static void setEmailId(Context context, String email_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(EMAIL_ID, email_id).commit();
    }

    public static String getEmailId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String email_id = sp.getString(EMAIL_ID, null);
        return email_id;
    }

    public static void setLanguageId(Context context, String language_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LANGUAGE_ID, language_id).commit();
    }

    public static String getLanguageId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String language_id = sp.getString(LANGUAGE_ID, null);
        return language_id;
    }

    public static void setSiteId(Context context, String site_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(SITE_ID, site_id).commit();
    }

    public static String getSiteId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String site_id = sp.getString(SITE_ID, null);
        return site_id;
    }

    public static void setVinMandate(Context context, String vinMandate) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(VIN_MANDATE, vinMandate).commit();
    }

    public static String getVinMandate(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String site_id = sp.getString(VIN_MANDATE, null);
        return site_id;
    }

    public static void setVinMaxLength(Context context, String vinMaxLength) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(VIN_MAX_LENGTH, vinMaxLength).commit();
    }

    public static String getVinMaxLength(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String site_id = sp.getString(VIN_MAX_LENGTH, null);
        return site_id;
    }

    public static void setSiteCode(Context context, String site_code) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(SITE_CODE, site_code).commit();
    }

    public static String getSiteCode(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String site_id = sp.getString(SITE_CODE, null);
        return site_id;
    }


    public static String getTimezoneDuration(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String timeZoneDuration = sp.getString(TIMEZONE_DURATION, null);
        return timeZoneDuration;
    }

    public static void setTimezoneDuration(Context context, String timeZoneDuration) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TIMEZONE_DURATION, timeZoneDuration).commit();
    }

    public static String getTimezone(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String timeZone = sp.getString(TIMEZONE, null);
        return timeZone;
    }

    public static void setTimezone(Context context, String timeZone) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(TIMEZONE, timeZone).commit();
    }

    public static String getVideothreshold(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String videoThreshold = sp.getString(VIDEOTHRESHOLD, null);
        return videoThreshold;
    }

    public static void setVideothreshold(Context context, String videoThreshold) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(VIDEOTHRESHOLD, videoThreshold).commit();
    }

    public static String getAllowEmployeeId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String allow_employee_id = sp.getString(ALLOW_EMPLOYEE_ID, null);
        return allow_employee_id;
    }

    public static void setAllowEmployeeId(Context context, String allow_employee_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(ALLOW_EMPLOYEE_ID, allow_employee_id).commit();
    }

    public static String getEmployeeId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String employee_id = sp.getString(EMPLOYEE_ID, null);
        return employee_id;
    }

    public static void setEmployeeId(Context context, String employee_id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(EMPLOYEE_ID, employee_id).commit();
    }

    public static String getUsername(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String username = sp.getString(USERNAME, null);
        return username;
    }

    public static void setUsername(Context context, String username) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(USERNAME, username).commit();
    }

    public static String getPassword(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String password = sp.getString(PASSWORD, null);
        return password;
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PASSWORD, password).commit();
    }


    public static boolean getIslogged(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean islogged = sp.getBoolean(ISLOGGED, false);
        return islogged;
    }

    public static void setIslogged(Context context, boolean islogged) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(ISLOGGED, islogged).commit();
    }

    public static String getConnString(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String connString = sp.getString(CONN_STRING, null);
        return connString;
    }

    public static void setConnString(Context context, String connStr) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(CONN_STRING, connStr).commit();
    }

    public static String getActivityName(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String activityName = sp.getString(ACTIVITY_NAME, null);
        return activityName;
    }

    public static void setActivityName(Context context, String activityName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(ACTIVITY_NAME, activityName).commit();
    }


    public static int getModelId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int modelId = sp.getInt(MODEL_ID, 0);
        return modelId;
    }

    public static void setModelId(Context context, int modelId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_ID,modelId).commit();
    }

    public static int getLogModelId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int modelId = sp.getInt(LOG_MODEL_ID, 0);
        return modelId;
    }

    public static void setLogModelId(Context context, int modelId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(LOG_MODEL_ID,modelId).commit();
    }

    public static String getActivityId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String activityId = sp.getString(ACTIVITY_ID, "0");
        return activityId;
    }

    public static void setActivityId(Context context, String activityId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(ACTIVITY_ID,activityId).commit();
    }

    public static int getModelImageHeight(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int imageHeight = sp.getInt(MODEL_IMAGE_HEIGHT, 480);
        return imageHeight;
    }

    public static void setModelImageHeight(Context context, int modelImageHeight) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_IMAGE_HEIGHT,modelImageHeight).commit();
    }

    public static int getModelImageWidth(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int imageWidth = sp.getInt(MODEL_IMAGE_WIDTH, 640);
        return imageWidth;
    }

    public static void setModelImageWidth(Context context, int modelImageWidth) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_IMAGE_WIDTH,modelImageWidth).commit();
    }

    public static int getModelBottomCropHeight(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int cropHeight = sp.getInt(MODEL_BOTTOM_CROP_HEIGHT, 100);
        return cropHeight;
    }

    public static void setModelBottomCropHeight(Context context, int bottomCropHeight) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_BOTTOM_CROP_HEIGHT,bottomCropHeight).commit();
    }

    public static String getOverlayUrl(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String activityName = sp.getString(MODEL_OVERLAY, null);
        return activityName;
    }

    public static void setOverlayUrl(Context context, String overlay) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(MODEL_OVERLAY, overlay).commit();
    }

    public static int getModelVideoWidth(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int videoWidth = sp.getInt(MODEL_VIDEO_WIDTH, 1280);
        return videoWidth;
    }

    public static void setModelVideoWidth(Context context, int videoWidth) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_VIDEO_WIDTH,videoWidth).commit();
    }


    public static int getModelVideoHeight(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int videoHeight = sp.getInt(MODEL_VIDEO_HEIGHT, 720);
        return videoHeight;
    }

    public static void setModelVideoHeight(Context context, int videoHeight) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_VIDEO_HEIGHT,videoHeight).commit();
    }

    public static int getModelVideoTime(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int maxTime = sp.getInt(MODEL_VIDEO_MAX_TIME, 20000);
        return maxTime;
    }

    public static void setModelVideoTime(Context context, int maxTime) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(MODEL_VIDEO_MAX_TIME,maxTime).commit();
    }
}

