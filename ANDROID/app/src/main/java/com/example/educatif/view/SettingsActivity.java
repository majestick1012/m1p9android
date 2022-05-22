package com.example.educatif.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.educatif.model.Preferences;

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
            actionBar.setTitle(R.string.settings);
        }
        redirectIfNotAuthenticated();
    }

    private void redirectIfNotAuthenticated(){
        SharedPreferences sp1 = getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp1.getString("token", null);
        String userId = sp1.getString("id", null);
        if(token == null || token.isEmpty() || userId == null || userId.isEmpty()){
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
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
                        Mode.setChecked(false);
                        lessonController.preference.setBackgroundColor(Color.WHITE);
                        lessonController.preference.setForegroundColor(Color.BLACK);
                    }else {
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
                        policySizeSmall.setChecked(false);
                    }else {
                        policySizeSmall.setChecked(true);
                        policySizeNormal.setChecked(false);
                        policySizeBig.setChecked(false);

                        lessonController.preference = new Preferences(lessonController.preference.getForegroundColor(),lessonController.preference.getBackgroundColor(),
                                200,
                                200,
                                250,
                                250,
                                200,
                                200,
                                4);

                    }
                    return false;
                }
            });

            policySizeNormal.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(policySizeNormal.isChecked()){
                        policySizeNormal.setChecked(false);
                    }else {
                        policySizeNormal.setChecked(true);
                        policySizeSmall.setChecked(false);
                        policySizeBig.setChecked(false);

                        lessonController.preference = new Preferences(lessonController.preference.getForegroundColor(),lessonController.preference.getBackgroundColor(),
                                300,
                                300,
                                250,
                                200,
                                300,
                                300,
                                3);
                    }
                    return false;
                }
            });

            policySizeBig.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if(policySizeBig.isChecked()){
                        policySizeBig.setChecked(false);
                    }else {
                        policySizeBig.setChecked(true);
                        policySizeSmall.setChecked(false);
                        policySizeNormal.setChecked(false);

                        lessonController.preference = new Preferences(lessonController.preference.getForegroundColor(),lessonController.preference.getBackgroundColor(),
                                1000,
                                500,
                                250,
                                130,
                                1000,
                                500,
                                1);
                    }
                    return false;
                }
            });

        }
    }

}