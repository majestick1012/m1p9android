package com.example.educatif.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.educatif.controller.LessonController;

import com.example.educatif.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }





    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        private Context mContext;
        private Activity mActivity;

        private LessonController lessonController = LessonController.getInstance();
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            mContext = this.getActivity();
            mActivity = this.getActivity();


            final SwitchPreference Mode = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.sp_mode));


            final SwitchPreference policySizeSmall = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.sp_policy_small));

            final SwitchPreference policySizeNormal = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.sp_policy_normal));

            final SwitchPreference policySizeBig = (SwitchPreference) findPreference(this.getResources()
                    .getString(R.string.sp_policy_big));

            Mode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(Mode.isChecked()){
                        Toast.makeText(mActivity,"Unchecked",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        Mode.setChecked(false);
                        lessonController.preference.setBackgroundColor(Color.WHITE);
                        lessonController.preference.setForegroundColor(Color.BLACK);
                    }else {
                        Toast.makeText(mActivity,"Checked",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        Mode.setChecked(true);
                        lessonController.preference.setBackgroundColor(Color.BLACK);
                        lessonController.preference.setForegroundColor(Color.WHITE);
                    }
                    return false;
                }
            });

            policySizeSmall.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(policySizeSmall.isChecked()){
                        Toast.makeText(mActivity,"Small Unchecked",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        policySizeSmall.setChecked(false);
                    }else {
                        Toast.makeText(mActivity,"Small Checked",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        policySizeSmall.setChecked(true);
                        policySizeNormal.setChecked(false);
                        policySizeBig.setChecked(false);
                    }
                    return false;
                }
            });

            policySizeNormal.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(policySizeNormal.isChecked()){
                        Toast.makeText(mActivity,"Normal Unchecked",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        policySizeNormal.setChecked(false);
                    }else {
                        Toast.makeText(mActivity,"Normal Checked",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        policySizeNormal.setChecked(true);
                        policySizeSmall.setChecked(false);
                        policySizeBig.setChecked(false);
                    }
                    return false;
                }
            });

            policySizeBig.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(policySizeBig.isChecked()){
                        Toast.makeText(mActivity,"Big Unchecked",Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        policySizeBig.setChecked(false);
                    }else {
                        Toast.makeText(mActivity,"Big Checked",Toast.LENGTH_SHORT).show();

                        // Unchecked the switch programmatically
                        policySizeBig.setChecked(true);
                        policySizeSmall.setChecked(false);
                        policySizeNormal.setChecked(false);
                    }
                    return false;
                }
            });

        }
    }

}