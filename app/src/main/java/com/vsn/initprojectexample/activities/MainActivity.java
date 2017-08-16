package com.vsn.initprojectexample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vsn.initproject.PublicMethods;
import com.vsn.initproject.PublicPreference;
import com.vsn.initprojectexample.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
//            String t = PublicMethods.encrypt("AndroidMasterVDV", "King of All Developers");
            String t = PublicMethods.authenticate("AndroidMasterVDV", "Authentication");
            Log.d("encryption", "onCreate:" + t);
            PublicPreference publicPreference = new PublicPreference(this, "", t);
            PublicMethods publicMethods = new PublicMethods(t);
//          key -> 1234567890123456
        } catch (Exception e) {
            Log.e("error", "onCreate: ", e);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
