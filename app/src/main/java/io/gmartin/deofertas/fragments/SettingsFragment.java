package io.gmartin.deofertas.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.gmartin.deofertas.R;

public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment(){}

    public interface OnExtendedPreferenceChaneListener extends Preference.OnPreferenceChangeListener {
        void setPrefix(String prefix);
        void setSuffix(String suffix);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.
        bindPreferenceSummaryToValue(findPreference("pref_URL_API"));
    }

    private static void bindPreferenceSummaryToValue(String prefix,
                                                     Preference preference,
                                                     String suffix) {

        // Set the listener to watch for value changes.
        OnExtendedPreferenceChaneListener sBindPreferenceSummaryToValueListener =
                new OnExtendedPreferenceChaneListener() {
                    String mPrefix = "";
                    String mSuffix = "";

                    public void setPrefix(String prefix) {
                        mPrefix = prefix + " ";
                    }
                    public void setSuffix(String suffix) {
                        mSuffix = " " + suffix;
                    }

                    public boolean onPreferenceChange(Preference preference, Object value) {
                        String stringValue = value.toString();

                        if (preference instanceof ListPreference) {
                            // For list preferences, look up the correct display value in
                            // the preference's 'entries' list.
                            ListPreference listPreference = (ListPreference) preference;
                            int index = listPreference.findIndexOfValue(stringValue);

                            // Set the summary to reflect the new value.
                            preference.setSummary(index >= 0 ? mPrefix+listPreference.getEntries()[index]+mSuffix : null);

                        }else{
                            // For all other preferences, set the summary to the value's
                            // simple string representation.
                            preference.setSummary(mPrefix+stringValue+mSuffix);
                        }
                        return true;
                    }
                };

        sBindPreferenceSummaryToValueListener.setPrefix(prefix);
        sBindPreferenceSummaryToValueListener.setSuffix(suffix);

        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        bindPreferenceSummaryToValue("", preference, "");
    }

    private static void bindPreferenceSummaryToValue(Preference preference, String suffix) {
        bindPreferenceSummaryToValue("", preference, suffix);
    }

    private static void bindPreferenceSummaryToValue(String preffix, Preference preference) {
        bindPreferenceSummaryToValue(preffix, preference, "");
    }
}
