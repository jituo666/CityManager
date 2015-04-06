package com.xmkj.citymanager.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @Author Jituo.Xuan
 * @Date 8:18:24 PM Jul 24, 2014
 * @Comments:null
 */
public class GlobalPreference {

    private static SharedPreferences sPrefs = null;
    private static final String PREFS_KEY_USER_NAME = "user_name";
    private static final String PREFS_KEY_USER_PWD = "user_pwd";
    private static final String PREFS_KEY_USER_SCORE = "user_score";
    private static final String PREFS_KEY_REPORT_COUNT = "report_count";

    private static SharedPreferences initSharedPreferences(Context ctx) {
        if (sPrefs == null) {
            sPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        }
        return sPrefs;
    }

    public static String getUserName(Context ctx) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        return prefs.getString(PREFS_KEY_USER_NAME, "");
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        SharedPreferencesCompat.apply(prefs.edit().putString(PREFS_KEY_USER_NAME, userName));
    }

    public static String getUserPwd(Context ctx) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        return prefs.getString(PREFS_KEY_USER_PWD, "");
    }

    public static void setUserPwd(Context ctx, String userPwd) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        SharedPreferencesCompat.apply(prefs.edit().putString(PREFS_KEY_USER_PWD, userPwd));
    }

    public static int getReportCount(Context ctx) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        return prefs.getInt(PREFS_KEY_REPORT_COUNT, 0);
    }

    public static void setReportCount(Context ctx, int reportCount) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        SharedPreferencesCompat.apply(prefs.edit().putInt(PREFS_KEY_REPORT_COUNT, reportCount));
    }

    public static int getUserScore(Context ctx) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        return prefs.getInt(PREFS_KEY_USER_SCORE, 0);
    }

    public static void setUserScore(Context ctx, int userScore) {
        SharedPreferences prefs = initSharedPreferences(ctx);
        SharedPreferencesCompat.apply(prefs.edit().putInt(PREFS_KEY_USER_SCORE, userScore));
    }
}
