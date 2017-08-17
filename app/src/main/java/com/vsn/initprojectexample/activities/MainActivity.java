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
            PublicMethods.authenticate("!P@$$w0rd1234567", "King of All Developers");
            PublicPreference publicPreference = new PublicPreference(this, "");
            PublicMethods publicMethods = new PublicMethods();
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
