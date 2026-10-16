package dev.tinelix.irc.android.legacy.core.activities.base;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.preference.PreferenceManager;

import dev.tinelix.holoexperience.library.app.HoloCompatActivity;

public class BaseFragmentActivity extends HoloCompatActivity {

    private SharedPreferences appPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void setActionBar() {

    }

    public SharedPreferences getAppPreferences() {
        return appPrefs;
    }
}
