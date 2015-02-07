package com.avoupavou.newtonsdisc;

import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.view.Menu;



public class Settings extends PreferenceActivity {
    //public static final String KEY_PREF_SYNC_CONN = "pref_syncConnectionType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_settings));
        addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }
}
