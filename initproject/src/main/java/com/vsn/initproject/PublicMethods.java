package com.vsn.initproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static com.vsn.initproject.LibraryConfig.oAuthKey;

/**
 * Created by prasad on 15/08/17.
 */

@SuppressWarnings("JavaDoc")
public class PublicMethods {

    private static final String TAG = "PublicMethods";
    private Context mContext;

    /**
     * mContext could be
     * getApplicationContext() of Activity,
     * Activity,
     * getActivity from Fragment
     * @param mContext
     */
    public PublicMethods(@NonNull Context mContext, String key) throws Exception {
        if (!key.equals(oAuthKey))
            throw null;
        this.mContext = mContext;
    }

    public PublicMethods(String key) throws Exception {
        if (!key.equals(oAuthKey))
            throw null;
    }

    /**
     * returns true or false
     * @param email could be abc@xyz.com
     * @return
     */
    public boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * @param value its a string, but to be only integer
     * @return
     * @exception if exception will return 0
     */
    public int parseInt(String value) {
        if (value!=null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                return 0;
            }
        } else
            return 0;
    }

    /**
     * return will be true or false
     * @param phone
     * @return boolean
     */
    public boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    /**
     *
     * @param strDate - expected as "yyyy-MM-dd'T'HH:mm:ss+HH:mm" or "yyyy-MM-dd"
     * @param fromFormat
     * @param toFormat
     * @return
     * @throws Exception
     */
    public String convertW3CTODeviceTimeZone(@NonNull String strDate, @NonNull String fromFormat, @NonNull String toFormat) throws Exception {


        String t = strDate;
        SimpleDateFormat formatter;
        if (fromFormat.equals("yyyy-MM-dd'T'HH:mm:ss+HH:mm")) {
            t = strDate.substring(0, strDate.length() - 6);
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        } else {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        }

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(t);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat(toFormat);
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(value);

        return dt;
    }

    /**
     * return will be 01-30-1988
     * @param dateString
     * @param frmoFormat
     * @return
     */
    public Date stringToDate(@NonNull String dateString, @NonNull String frmoFormat) {
        Date returnDate = null;
        String inputPattern = frmoFormat;
        String outputPattern = "MM-dd-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        try {
            returnDate = inputFormat.parse(dateString);
        } catch (Exception e) {
            Log.e(TAG, "stringToDate: ", e);
        }
        return returnDate;

    }

    /**
     * return as 01-30-1988 18:59
     * @param dateString
     * @param frmoFormat
     * @return
     */
    public Date stringToDateTime(@NonNull String dateString, @NonNull String frmoFormat) {
        Date returnDate = null;
        String inputPattern = frmoFormat;
        String outputPattern = "MM-dd-yyyy HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        try {
            returnDate = inputFormat.parse(dateString);
        } catch (Exception e) {
            Log.e(TAG, "stringToDateTime: ", e);
        }
        return returnDate;

    }

    /**
     * return as current date and time(01-30-1988 18:59)
     * @return
     */
    public Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date todayDt = calendar.getTime();
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        try {
            String temp = outputFormat.format(todayDt);
            todayDt = stringToDateTime(temp, "MM-dd-yyyy HH:mm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todayDt;
    }

    /**
     * current date as 01-30-1988
     * @return
     */
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date todayDt = calendar.getTime();
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
        String temp = outputFormat.format(todayDt);
        return temp;
    }

    /**
     * return as 01-30-1988
     * @param date
     * @return
     */
    public String getDateToString(@NonNull Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
        String temp = outputFormat.format(date);
        return temp;
    }


    /**
     * return format => Thu, Jan 30, 1988
     * @param date Should not be null
     * @return
     */
    public String getDateInWords(@NonNull Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, MMM dd, yyyy");
        String temp = outputFormat.format(date);
        return temp;
    }

    /**
     * return in hours
     * @param startDate
     * @param endDate
     * @return
     */
    public long timeDifferenceInHrs(Date startDate, Date endDate) {

        long elapsedHours = 0;
        try {
            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
        } catch (Exception e) {
            Log.e("Suvi", "timeDifferenceInHrs: " + e.toString());
        }

        return elapsedHours;

    }

    /**
     * return in days
     * @param startDate
     * @param endDate
     * @return
     */
    public long timeDifferenceInDays(Date startDate, Date endDate) {

        //milliseconds
        long elapsedDays = 0;
        try {
            long different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            elapsedDays = different / daysInMilli;
            different = different % daysInMilli;
            different = different % hoursInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
        } catch (Exception e) {
            Log.e("Suvi", "timeDifferenceInHrs: " + e.toString());
        }
        return elapsedDays;

    }

    /**
     * return in minutes
     * @param startDate
     * @param endDate
     * @return
     */
    public long timeDifferenceInMins(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        different = different % minutesInMilli;

        return elapsedHours;

    }

    public double round(String decimalFmtStr, Double aDouble) {
// double i = 2;
        DecimalFormat twoDForm = new DecimalFormat(decimalFmtStr);
        String aString = twoDForm.format(aDouble);
        aDouble = Double.parseDouble(aString);
        return aDouble;
    }

    public Date combineDateTime(Date date, String time) {

        String temp = getDateToString(date);
        temp = temp + " " + time;

        date = stringToDateTime(temp, "MM-dd-yyyy hh:mm a");

        return date;
    }

    public String abbrName(String fullName) {
        String abbrName = "";
        if (fullName.split("\\w+").length > 1) {
            String lastName = fullName.substring(fullName.lastIndexOf(" ") + 1);
            String firstName = fullName.substring(0, fullName.lastIndexOf(' '));
            abbrName = firstName.toUpperCase().charAt(0) + "." + lastName;
        } else {
            abbrName = fullName;
        }
        return abbrName;
    }

    public Object fetchObject(String body, Class<?> cls) {
        Gson gson = new Gson();
        if (body != null)
            return gson.fromJson(body, cls);
        else
            return null;
    }

    public static String authenticate(String key, String body) throws Exception {
        byte[] raw = key.getBytes();
        byte[] value = body.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(value);
        return new String(encrypted);
    }

    public String encrypt(String key, String body) throws Exception {
        byte[] raw = key.getBytes();
        byte[] value = body.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(value);
        return new String(encrypted);
    }

    public String decrypt(String key, String value) throws Exception {
        byte[] raw = key.getBytes();
        byte[] encrypted = value.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }
}
