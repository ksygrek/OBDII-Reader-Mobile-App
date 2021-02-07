package com.javacodegeeks.androidBluetoothExample;

public class Communication extends Thread implements Runnable {
    private Object mPauseLock;
    private boolean mPaused;
    private boolean mFinished;

    public Communication() {
        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;
    }

    public static int time = 200;

    public static void delay() {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        BluetoothChat.sendMessage("ATSP0");
        delay();
        /*BluetoothChat.sendMessage("ATH1");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        BluetoothChat.sendMessage(OBDCommands.VINCommand);
        delay();


        while (!mFinished) {
            BluetoothChat.sendMessage(OBDCommands.RPMCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.CoolantTempCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.SpeedCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.MAFCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.ThrottlePositionCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.IntakeAirTempCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.ControlModuleVoltageCommand);
            delay();

            BluetoothChat.sendMessage((OBDCommands.EngineLoadCommand));
            delay();
            BluetoothChat.sendMessage(OBDCommands.ShortBank1Command);
            delay();
            BluetoothChat.sendMessage(OBDCommands.LongBank1Command);
            delay();
            BluetoothChat.sendMessage(OBDCommands.ShortBank2Command);
            delay();
            BluetoothChat.sendMessage(OBDCommands.LongBank2Command);
            delay();
            BluetoothChat.sendMessage(OBDCommands.FuelPressureCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.IntakeManifoldAbsolutePressureCommand);
            delay();


            BluetoothChat.sendMessage(OBDCommands.RPMCommand);
            delay();


            BluetoothChat.sendMessage(OBDCommands.TimingAdvanceCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.EngineTimeStartCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.DistanceTraveledCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.FuelRailPressureCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.EGRCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.FuelLevelCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.AbsoluteBarometricPressureCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.RelativeThrottlePositionCommand);
            delay();
            BluetoothChat.sendMessage(OBDCommands.EngineOilTempCommand);
            delay();

            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }


    }

     //Call this on pause
    public void onPause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    //Call this on resume
    public void onResume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }
}
