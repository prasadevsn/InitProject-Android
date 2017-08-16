package com.vsn.initproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import static com.vsn.initproject.LibraryConfig.oAuthKey;

/**
 * Created by prasad on 15/08/17.
 */

public class PublicPreference {
    // Shared preferences file name
    @SerializedName("PREF_NAME")
    private String PREF_NAME = "PrasadPref";
    @SerializedName("pref")
    SharedPreferences pref;
    @SerializedName("editor")
    SharedPreferences.Editor editor;
    @SerializedName("mContext")
    Context mContext;

    // shared pref mode
    private int PRIVATE_MODE;

    public PublicPreference(@NonNull Context context, String PREF_NAME, String key) throws Exception {
        if (!key.equals(oAuthKey))
            throw null;
        if (PREF_NAME!=null && !PREF_NAME.isEmpty())
        this.PREF_NAME = PREF_NAME;
        this.mContext = context;
        PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(this.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * stores string value for a specific key
     * @param key must be unique
     * @param value
     * @return
     */
    @SerializedName("setString")
    public boolean setString(@NonNull String key, @NonNull String value) {
        if (!key.isEmpty()) {
            editor.putString(key, value);
            editor.commit();
            return true;
        } else
            return false;
    }

    /**
     * stores int value for a specific key
     * @param key
     * @param value
     * @return
     */
    @SerializedName("setInt")
    public boolean setInt(@NonNull String key, int value) {
        if (!key.isEmpty()) {
            editor.putInt(key, value);
            editor.commit();
            return true;
        } else
            return false;
    }

    /**
     * stores string boolean for a specific key
     * @param key
     * @param value
     * @return
     */
    @SerializedName("setBoolean")
    public boolean setBoolean(@NonNull String key, @NonNull Boolean value) {
        if (!key.isEmpty()) {
            editor.putBoolean(key, value);
            editor.commit();
            return true;
        } else
            return false;
    }

    /**
     * returns string value from a specific key
     * @param key
     * @return
     */
    @SerializedName("getString")
    public String getString(@NonNull String key) {
        return pref.getString(key, "");
    }

    /**
     * returns int value from a specific key
     * @param key
     * @return
     */
    @SerializedName("getInt")
    public int getInt(@NonNull String key) {
        return pref.getInt(key, 0);
    }

    /**
     * returns boolean value from a specific key
     * @param key
     * @return
     */
    @SerializedName("getBoolean")
    public boolean getBoolean(@NonNull String key) {
        return pref.getBoolean(key, false);
    }

    @SerializedName("getUserId")
    public int getUserId() {
        return pref.getInt("user_id", 0);
    }

    @SerializedName("setUserId")
    public void setUserId(int user_id) {
        editor.putInt("user_id", user_id);
        editor.commit();
    }

    /**
     * stores response for a specific request
     * @param requestObject api request
     * @param responseObject api response
     * @return
     */
    @SerializedName("storeTempApi")
    public boolean storeTempApi(@NonNull Object requestObject, Object responseObject) {

        Gson gson = new Gson();
        String requestJson = gson.toJson(requestObject);
        String responseJson = null;
        if (responseObject != null) {
            responseJson = gson.toJson(responseObject);
        }
        editor.putString(requestJson, responseJson);
        editor.commit();
        return true;
    }

    /**
     * retrieve a response from request and returns in a object need to cast
     * @param requestObject
     * @param mClass response class name
     * @return
     */
    @SerializedName("fetchTempApi")
    public Object fetchTempApi(@NonNull Object requestObject, Class<?> mClass) {
        Gson gson = new Gson();
        String requestJson = gson.toJson(requestObject);
        String json = pref.getString(requestJson, null);
        if (json != null)
            return gson.fromJson(json, mClass);
        else
            return null;
    }

    /**
     * Stores a object for a specific key
     * @param key
     * @param valueObject
     * @return
     */
    @SerializedName("storeTempObject")
    public boolean storeTempObject(@NonNull String key, Object valueObject) {

        Gson gson = new Gson();
        String responseJson = null;
        if (valueObject != null) {
            responseJson = gson.toJson(valueObject);
        }
        editor.putString(key, responseJson);
        editor.commit();
        return true;
    }

    /**
     * Returns a Object need to specify a class for a specific key
     * @param key
     * @param aClass
     * @return
     */
    @SerializedName("fetchTempObject")
    public Object fetchTempObject(@NonNull String key, Class<?> aClass) {
        Gson gson = new Gson();
        String json = pref.getString(key, null);
        if (json != null)
            return gson.fromJson(json, aClass);
        else
            return null;
    }

}
