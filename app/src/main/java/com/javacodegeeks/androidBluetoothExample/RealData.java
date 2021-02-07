package com.javacodegeeks.androidBluetoothExample;

import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.util.OnSpeedChangeListener;
import com.javacodegeeks.R;

import org.w3c.dom.Text;

public class RealData extends Fragment {

    public static SpeedView speedView;
    public static SpeedView rpmView;
    public static SpeedView coolantTempView;
    public static SpeedView intakeAirTemp;
    public static SpeedView voltageView;
    public static SpeedView engineLoadView;
    public static TextView speedTextView;
    public static TextView rpmTextView;
    public static TextView coolantTextView;
    public static TextView engineoiltempView;
    public static TextView mafView;
    public static TextView intakeAirTempTextView;
    public static TextView sbank1View;
    public static TextView lbank1View;
    public static TextView sbank2View;
    public static TextView lbank2View;
    public static TextView fuelpressureView;
    public static TextView intakepressureView;
    public static TextView timeingadvanceView;
    public static TextView enginetimestartView;
    public static TextView distancetraveledView;
    public static TextView fuelrailpressureView;
    public static TextView egrView;
    public static TextView fuellevelView;
    public static TextView absolutebarometricView;
    public static TextView throttlepositionView;
    public static TextView relativethrottlepositionView;
    public static TextView vinView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.real_data_view, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        speedView = getView().findViewById(R.id.speedView);
        speedView.setTicks(0f, 20.f, 40.f, 60.f, 80.f, 100.f, 120.f, 140.f, 160.f, 180.f, 200.f, 220.f);

        rpmView = getView().findViewById(R.id.rpmView);
        rpmView.setTicks(0f, 1000.f, 2000.f, 3000.f, 4000.f, 5000.f, 6000.f);

        coolantTempView = getView().findViewById(R.id.coolantTempView);
        coolantTempView.setTicks(60f, 70.f, 80f, 90.f, 100.f, 115.f, 130.f);

        intakeAirTemp = getView().findViewById(R.id.intakeAirTempView);
        intakeAirTemp.setTicks(-30.f, -15.f, 0.f, 15.f, 30.f);

        voltageView = getView().findViewById(R.id.voltageView);
        voltageView.setTicks(0.f, 10.f, 12.f, 14.f, 16.f);

        engineLoadView = getView().findViewById(R.id.engineLoadView);
        engineLoadView.setTicks(0.f, 25.f, 50.f, 75.f, 100.f);

        speedTextView = getView().findViewById(R.id.speedTextView);
        rpmTextView = getView().findViewById(R.id.rpmTextView);
        coolantTextView = getView().findViewById(R.id.coolantTextView);
        engineoiltempView = getView().findViewById(R.id.engineoiltempView);
        mafView = getView().findViewById(R.id.mafView);
        intakeAirTempTextView = getView().findViewById(R.id.intakeAirTempTextView);
        sbank1View = getView().findViewById(R.id.sbank1View);
        lbank1View = getView().findViewById(R.id.lbank1View);
        sbank2View = getView().findViewById(R.id.sbank2View);
        lbank2View = getView().findViewById(R.id.lbank2View);
        fuelpressureView = getView().findViewById(R.id.fuelpressureView);
        intakepressureView = getView().findViewById(R.id.intakepressureView);
        timeingadvanceView = getView().findViewById(R.id.timeingadvanceView);
        enginetimestartView = getView().findViewById(R.id.enginetimestartView);
        distancetraveledView = getView().findViewById(R.id.distancetraveledView);
        fuelrailpressureView = getView().findViewById(R.id.fuelrailpressureView);
        egrView = getView().findViewById(R.id.egrView);
        fuellevelView = getView().findViewById(R.id.fuellevelView);
        absolutebarometricView = getView().findViewById(R.id.absolutebarometricView);
        throttlepositionView = getView().findViewById(R.id.throttlepositionView);
        relativethrottlepositionView = getView().findViewById(R.id.relativethrottlepositionView);
        vinView = getView().findViewById(R.id.vinView);

    }

}