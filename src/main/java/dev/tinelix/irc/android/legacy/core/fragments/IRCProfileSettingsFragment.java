package dev.tinelix.irc.android.legacy.core.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import dev.tinelix.holoexperience.library.app.HoloAlertDialog;
import dev.tinelix.irc.android.legacy.R;

public class IRCProfileSettingsFragment extends PreferenceFragmentCompat {

    private String mPrefsName;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.profile_settings);

        if(getArguments() != null) {
            Bundle data = getArguments();

            if(data.containsKey("prefsName")) {
                mPrefsName = getArguments().getString("prefsName");
                mPreferences = getContext().getSharedPreferences(mPrefsName, 0);
            }
        }

        setupPreferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setDividerHeight(0);
        return view;
    }

    public void setupPreferences() {
        final String profileName = mPreferences.getString("profileName", "");

        final Preference profileNamePref = findPreference("profile_name");
        profileNamePref.setSummary(profileName);

        profileNamePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                View view = View.inflate(getContext(), R.layout.dialog_enter_text, null);

                HoloAlertDialog dialog = new HoloAlertDialog.Builder(getContext())
                        .setTitle(R.string.profile_name)
                        .setView(view)
                        .setPositiveButton(R.string.ok_button, null)
                        .setNegativeButton(R.string.cancel_button, null)
                        .show();

                EditText editText = dialog.findViewById(R.id.edittext);

                editText.setText(profileName);

                return true;
            }
        });

        final String authMethod = mPreferences.getString("loginMethod", "None");
        final Preference authMethodPref = findPreference("login_method");

        authMethodPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                HoloAlertDialog dialog = new HoloAlertDialog.Builder(getContext())
                        .setTitle(R.string.pref_login_method)
                        .setSingleChoiceItems(R.array.auth_method, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(R.string.ok_button, null)
                        .setNegativeButton(R.string.cancel_button, null)
                        .show();
                return true;
            }
        });
    }
}
