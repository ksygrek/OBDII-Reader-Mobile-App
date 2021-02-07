package com.javacodegeeks.androidBluetoothExample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javacodegeeks.R;

public class Faults extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fauts_view,container,false);
    }
}

    /*          <item
                        android:id="@+id/read_errors_menu_button"
                        android:icon="@drawable/ic_error_outline_black_24dp"
                        android:title="@string/read_faults"
                        app:showAsAction="never" />*/
