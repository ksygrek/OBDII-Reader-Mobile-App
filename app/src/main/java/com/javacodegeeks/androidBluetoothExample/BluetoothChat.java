package com.javacodegeeks.androidBluetoothExample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.javacodegeeks.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidRecyclerView.MessageAdapter;


public class BluetoothChat extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    Communication communication;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public String TAG = "ttt";
    public static String termninalMessage;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // String buffer for outgoing messages
    private static StringBuffer mOutStringBuffer;

    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;

    // Member object for the chat services
    private static BluetoothChatService mChatService = null;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MessageAdapter mAdapter;
    private DrawerLayout mDrawerLayout;

    private List<androidRecyclerView.Message> messageList = new ArrayList<androidRecyclerView.Message>();

    private static BluetoothChat _Instance;

    public static void SetLanguage(String lang) {
        if(GetLanguage() == lang) { return; }
        Locale myLocale = new Locale(lang);
        Resources res = _Instance.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(_Instance.getBaseContext(), BluetoothChat.class);
        _Instance.startActivity(refresh);
        _Instance.finish();
    }
    public static String GetLanguage()
    {
        Locale x = _Instance.getResources().getConfiguration().locale;
        return x.getLanguage();
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        _Instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MessageAdapter(getBaseContext(), messageList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        communication = new Communication();
        mDrawerLayout = findViewById(R.id.drawer);
        mDrawerLayout.openDrawer(Gravity.LEFT);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            // This method will trigger on item Click of navigation menu
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.connect_menu_button:
                        if(communication.isAlive()){
                            communication.onPause();
                        }
                        connect(menuItem.getActionView());
                        break;
                    case R.id.start_stream_menu_button:
                        if (!communication.isAlive()){
                            communication.start();
                        } else {
                            communication.onResume();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RealData()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.terminal_menu_button:
                        if(communication.isAlive()){
                            communication.onPause();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Terminal()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.settings_menu_button:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Settings()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.info_menu_button:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Info()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        return true;
                }
                return false;
            }
        });


        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mChatService == null) setupChat();
        }

    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (mChatService != null) {
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                mChatService.start();
            }
        }
    }

    private void setupChat() {

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();

    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    public static void sendMessage(String message) {

        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            String r = "\r";
            message = message + r;

            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);

        }
    }

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        sendMessage(message);
                    }
                    return true;
                }
            };


    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    mAdapter.notifyDataSetChanged();

                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    mAdapter.notifyDataSetChanged();
                    Log.d(TAG, "handleMessage: " + readMessage);

                    try {
                        readMessage = readMessage.replace(" ", "");
                        if (readMessage.length() >= 10 && readMessage.contains("41")) {

                            termninalMessage = readMessage;

                                //RPM - ok
                            if (readMessage.contains(OBDCommands.RPMPidEncoder)) {
                                OBDCommands.setRPM(readMessage);
                                RealData.rpmView.speedTo(Integer.parseInt(OBDCommands.getRPM()), 500);
                                RealData.rpmTextView.setText(OBDCommands.getRPM());

                                //Coolant Temp - ok
                            } else if (readMessage.contains(OBDCommands.CoolantTempPidEncoder)) {
                                OBDCommands.setCoolantTemp(readMessage);
                                RealData.coolantTempView.speedTo(Integer.parseInt(OBDCommands.getCoolantTemp()));
                                RealData.coolantTextView.setText(OBDCommands.getCoolantTemp() + " °C");

                                //Speed - ok
                            } else if (readMessage.contains(OBDCommands.SpeedPidEncoder)) {
                                OBDCommands.setSpeed(readMessage);
                                RealData.speedView.speedTo(Integer.parseInt(OBDCommands.getSpeed()));
                                RealData.speedTextView.setText(OBDCommands.getSpeed() + " km/h");

                                //MAF - ok
                            } else if (readMessage.contains(OBDCommands.MAFPidEncoder)) {
                                OBDCommands.setMAF(readMessage);
                                RealData.mafView.setText(OBDCommands.getMAF() + " grams/sec");

                                //Engine Load - ok
                            } else if (readMessage.contains(OBDCommands.EngineLoadPidEncoder)) {
                                OBDCommands.setEngineLoad(readMessage);
                                Log.d(TAG, "Engine Load: " + OBDCommands.getEngineLoad());

                                RealData.engineLoadView.speedTo(Integer.parseInt(OBDCommands.getEngineLoad()));

                                //Throttle Position - ok
                            } else if (readMessage.contains(OBDCommands.ThrottlePositionPidEncoder)) {
                                OBDCommands.setThrottlePosition(readMessage);
                                RealData.throttlepositionView.setText(OBDCommands.getThrottlePosition() + " %");

                                //Intake Air Temp - ok
                            } else if (readMessage.contains(OBDCommands.IntakeAirTempPidEncoder)) {
                                OBDCommands.setIntakeAirTemp(readMessage);
                                RealData.intakeAirTemp.speedTo(Integer.parseInt(OBDCommands.getIntakeAirTemp()));
                                RealData.intakeAirTempTextView.setText(OBDCommands.getIntakeAirTemp() + " °C");

                                //Voltage - ok
                            } else if (readMessage.contains(OBDCommands.ControlMpduleVoltagePidEncoder)) {
                                OBDCommands.setControlModuleVoltage(readMessage);
                                RealData.voltageView.speedTo(Integer.parseInt(OBDCommands.getControlModuleVoltage()));

                                //Engine Oil Temp - ok
                            } else if (readMessage.contains(OBDCommands.EngineOilTempPidEncoder)) {
                                OBDCommands.setEngineOilTemp(readMessage);
                                RealData.engineoiltempView.setText(OBDCommands.getEngineOilTemp());

                                //SBank1 - ok
                            } else if (readMessage.contains(OBDCommands.ShortBank1PidEncoder)) {
                                OBDCommands.setShortBank1(readMessage);
                                RealData.sbank1View.setText(OBDCommands.getShortBank1() + " %");

                                //LBank1 - ok
                            } else if (readMessage.contains(OBDCommands.LongBank1PidEncoder)) {
                                OBDCommands.setLongBank1(readMessage);
                                RealData.lbank1View.setText(OBDCommands.getLongBank1() + " %");

                                //SBank2 - ok
                            } else if (readMessage.contains(OBDCommands.ShortBank2PidEncoder)) {
                                OBDCommands.setShortBank2(readMessage);
                                RealData.sbank2View.setText(OBDCommands.getShortBank2() + " %");

                                //LBank2 - ok
                            } else if (readMessage.contains(OBDCommands.LongBank2PidEncoder)) {
                                OBDCommands.setLongBank2(readMessage);
                                RealData.lbank2View.setText(OBDCommands.getLongBank2() + " %");

                                //Fuel Pressure - ok
                            } else if (readMessage.contains(OBDCommands.FuelPressurePidEncoder)) {
                                OBDCommands.setFuelPressure(readMessage);
                                RealData.fuelpressureView.setText(OBDCommands.getFuelPressure() + " kPa");

                                //Timing Advance - ok
                            } else if (readMessage.contains(OBDCommands.TimingAdvancePidEncoder)) {
                                OBDCommands.setTimingAdvance(readMessage);
                                RealData.timeingadvanceView.setText(OBDCommands.getTimingAdvance() + " °");

                                //Run time since engine start - ok
                            } else if (readMessage.contains(OBDCommands.EngineTimeStartPidEncoder)) {
                                OBDCommands.setEngineTimeStart(readMessage);
                                RealData.enginetimestartView.setText(OBDCommands.getEngineTimeStart() + " sec");

                                //Distance traveled with malfunction indicator lamp (MIL) on - ok
                            } else if (readMessage.contains(OBDCommands.DistanceTraveledPidEncoder)) {
                                OBDCommands.setDistanceTraveled(readMessage);
                                RealData.distancetraveledView.setText(OBDCommands.getDistanceTraveled() + " km");

                                //Fuel Rail Pressure - ok
                            } else if (readMessage.contains(OBDCommands.FuelRailPressurePidEncoder)) {
                                OBDCommands.setFuelRailPressure(readMessage);
                                RealData.fuelrailpressureView.setText(OBDCommands.getFuelRailPressure() + " kPa");

                                //EGR - ok
                            } else if (readMessage.contains(OBDCommands.EGRPidEncoder)) {
                                OBDCommands.setEGR(readMessage);
                                RealData.egrView.setText(OBDCommands.getEGR() + " %");

                                //Fuel Level - ok
                            } else if (readMessage.contains(OBDCommands.FuelLevelPidEncoder)) {
                                OBDCommands.setFuelLevel(readMessage);
                                RealData.fuellevelView.setText(OBDCommands.getFuelLevel() + " %");

                                //Absolute Barometric Pressure - ok
                            } else if (readMessage.contains(OBDCommands.AbsoluteBarometricPressurePidEncoder)) {
                                OBDCommands.setAbsoluteBarometricPressure(readMessage);
                                RealData.absolutebarometricView.setText(OBDCommands.getAbsoluteBarometricPressure() + " kPa");

                                //Relative Throttle Position - ok
                            } else if (readMessage.contains(OBDCommands.RelativeThrottlePositionPidEncoder)) {
                                OBDCommands.setRelativeThrottlePosition(readMessage);
                                RealData.relativethrottlepositionView.setText(OBDCommands.getRelativeThrottlePosition() + " %");

                                //VIN - nope
                            } else if (readMessage.contains(OBDCommands.VINCommand)) {
                                OBDCommands.setVIN(readMessage);
                                RealData.vinView.setText(OBDCommands.getVIN());

                                //Intake Manifold Absolute Pressure - ok
                            } else if (readMessage.contains(OBDCommands.IntakeManifoldAbsolutePressurePidEncoder)) {
                                OBDCommands.setIntakeManifoldAbsolutePressure(readMessage);
                                RealData.intakepressureView.setText(OBDCommands.getIntakeManifoldAbsolutePressure() + " kPa");
                            }

                        }
                    } catch (Exception e) {
                    }

                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public void connect(View v) {
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    public void discoverable(View v) {
        ensureDiscoverable();
    }

}