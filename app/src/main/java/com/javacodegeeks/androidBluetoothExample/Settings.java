package com.javacodegeeks.androidBluetoothExample;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.javacodegeeks.R;

import java.util.Locale;

public class Settings extends Fragment {

    Context thiscontext;
    private Spinner mLanguage;
    private ArrayAdapter<String> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        thiscontext = container.getContext();
        return inflater.inflate(R.layout.settings_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mLanguage = getView().findViewById(R.id.spLanguage);
        mAdapter = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));

        mLanguage.setAdapter(mAdapter);
        mLanguage.setSelection(BluetoothChat.GetLanguage() == "en" ? 0 : 1);

        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                BluetoothChat.SetLanguage(i == 0 ? "en" : "pl");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
