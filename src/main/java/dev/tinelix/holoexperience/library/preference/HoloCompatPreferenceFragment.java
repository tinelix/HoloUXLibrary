package dev.tinelix.holoexperience.library.preference;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.tinelix.holoexperience.library.HoloCompatSupport;

public class HoloCompatPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        float dp = getResources().getDisplayMetrics().scaledDensity;
        setDividerHeight(0);

        if(view != null && HoloCompatSupport.isTablet(getContext()))
            view.setPadding((int)(60 * dp), 0, (int)(60 * dp), 0);

        return view;
    }
}
