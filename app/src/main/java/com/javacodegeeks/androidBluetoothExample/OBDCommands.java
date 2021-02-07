package com.javacodegeeks.androidBluetoothExample;

public class OBDCommands {


    //Gets result from response and calculate Hex to Dec
    public static int calculate (String r, String pidEncoder, int resultLastIndex){
        r = r.substring(r.indexOf(pidEncoder) + 4);
        r = r.substring(0,resultLastIndex);
        return Integer.parseInt(r, 16);
    }

    public static String RPMCommand = "010C";
    public static String RPMPidEncoder = "410C";
    public static String currentRPM = "";
    public static void setRPM (String result){
        currentRPM = String.valueOf(calculate(result,RPMPidEncoder,4) / 4);
    }
    public static String getRPM (){
        return currentRPM;
    }


    public static String CoolantTempCommand = "0105";
    public static String CoolantTempPidEncoder = "4105";
    public static String currentCoolantTemp = "";
    public static void setCoolantTemp (String result){

        /*result = result.substring(result.indexOf("4105") + 4);
        result = result.substring(0,2);
        Integer dec = Integer.parseInt(result,16);*/
        currentCoolantTemp = String.valueOf(calculate(result,CoolantTempPidEncoder,2) - 40);
    }
    public static String getCoolantTemp(){return currentCoolantTemp; }


    public static String SpeedCommand = "010D";
    public static String SpeedPidEncoder = "410D";
    public static String currentSpeed = "";
    public static void setSpeed (String result){
        currentSpeed = String.valueOf(calculate(result,SpeedPidEncoder,2));
    }
    public static String getSpeed () {return currentSpeed; }


    public static String IntakeAirTempCommand = "010F";
    public static String IntakeAirTempPidEncoder = "410F";
    public static String currentIntakeAirTemp = "";
    public static void setIntakeAirTemp (String result){
        currentIntakeAirTemp = String.valueOf(calculate(result,IntakeAirTempPidEncoder,2)- 40);
    }
    public static String getIntakeAirTemp () {return currentIntakeAirTemp; }


    public static String MAFCommand = "0110";
    public static String MAFPidEncoder = "4110";
    public static String currentMAF = "";
    public static void setMAF (String result){
        currentMAF = String.valueOf(calculate(result,MAFPidEncoder,4) / 100);
    }
    public static String getMAF () {return currentMAF; }


    public static String ThrottlePositionCommand = "0111";
    public static String ThrottlePositionPidEncoder = "4111";
    public static String currentThrottlePosition = "";
    public static void setThrottlePosition (String result){
        currentThrottlePosition = String.valueOf(calculate(result,ThrottlePositionPidEncoder,2) * 100 / 255);
    }
    public static String getThrottlePosition () {return currentThrottlePosition; }


    public static String ControlModuleVoltageCommand = "0142";
    public static String ControlMpduleVoltagePidEncoder = "4142";
    public static String currentControlModuleVoltage = "";
    public static void setControlModuleVoltage (String result){
        currentControlModuleVoltage = String.valueOf(calculate(result,ControlMpduleVoltagePidEncoder,4) / 1000);
    }
    public static String getControlModuleVoltage () {return currentControlModuleVoltage; }



    public static String EngineLoadCommand = "0104";
    public static String EngineLoadPidEncoder = "4104";
    public static String currentEngineLoad = "";
    public static void setEngineLoad (String result){
        currentEngineLoad = String.valueOf(calculate(result,EngineLoadPidEncoder,2) * 100/255);
    }
    public static String getEngineLoad (){
        return currentEngineLoad;
    }

    public static String ShortBank1Command = "0106";
    public static String ShortBank1PidEncoder = "4106";
    public static String currentShortBank1 = "";
    public static void setShortBank1 (String result){
        currentShortBank1 = String.valueOf(calculate(result,ShortBank1PidEncoder,2) * 100/128-100);
    }
    public static String getShortBank1 (){
        return currentShortBank1;
    }

    public static String LongBank1Command = "0107";
    public static String LongBank1PidEncoder = "4107";
    public static String currentLongBank1 = "";
    public static void setLongBank1 (String result){
        currentLongBank1 = String.valueOf(calculate(result,LongBank1PidEncoder,2) * 100/128-100);
    }
    public static String getLongBank1 (){
        return currentLongBank1;
    }

    public static String ShortBank2Command = "0108";
    public static String ShortBank2PidEncoder = "4108";
    public static String currentShortBank2 = "";
    public static void setShortBank2 (String result){
        currentShortBank2= String.valueOf(calculate(result,ShortBank2PidEncoder,2) * 100/128-100);
    }
    public static String getShortBank2 (){
        return currentShortBank2;
    }

    public static String LongBank2Command = "0109";
    public static String LongBank2PidEncoder = "4109";
    public static String currentLongBank2 = "";
    public static void setLongBank2 (String result){
        currentLongBank2 = String.valueOf(calculate(result,LongBank2PidEncoder,2) * 100/128-100);
    }
    public static String getLongBank2 (){
        return currentLongBank2;
    }

    public static String FuelPressureCommand = "010A";
    public static String FuelPressurePidEncoder = "410A";
    public static String currentFuelPressure = "";
    public static void setFuelPressure (String result){
        currentFuelPressure = String.valueOf(calculate(result,FuelPressurePidEncoder,2) * 3);
    }
    public static String getFuelPressure (){ return currentFuelPressure;
    }

    public static String IntakeManifoldAbsolutePressureCommand = "010B";
    public static String IntakeManifoldAbsolutePressurePidEncoder = "410B";
    public static String currentIntakeManifoldAbsolutePressure = "";
    public static void setIntakeManifoldAbsolutePressure (String result){
        currentIntakeManifoldAbsolutePressure = String.valueOf(calculate(result,IntakeManifoldAbsolutePressurePidEncoder,2));
    }
    public static String getIntakeManifoldAbsolutePressure (){ return currentIntakeManifoldAbsolutePressure;
    }

    public static String TimingAdvanceCommand = "010E";
    public static String TimingAdvancePidEncoder = "410E";
    public static String currentTimingAdvance = "";
    public static void setTimingAdvance (String result){
        currentTimingAdvance = String.valueOf(calculate(result,TimingAdvancePidEncoder,2)*1/2-64);
    }
    public static String getTimingAdvance (){ return currentTimingAdvance;
    }

    public static String EngineTimeStartCommand = "011F";
    public static String EngineTimeStartPidEncoder = "411F";
    public static String currentEngineTimeStart = "";
    public static void setEngineTimeStart (String result){
        currentEngineTimeStart = String.valueOf(calculate(result,EngineTimeStartPidEncoder,4)/1000);
    }
    public static String getEngineTimeStart (){ return currentEngineTimeStart;
    }

    //Distance traveled with malfunction indicator lamp (MIL) on
    public static String DistanceTraveledCommand = "0121";
    public static String DistanceTraveledPidEncoder = "4121";
    public static String currentDistanceTraveled = "";
    public static void setDistanceTraveled (String result){
        currentDistanceTraveled = String.valueOf(calculate(result,DistanceTraveledPidEncoder,4));
    }
    public static String getDistanceTraveled (){ return currentDistanceTraveled;
    }

    public static String FuelRailPressureCommand = "0122";
    public static String FuelRailPressurePidEncoder = "4122";
    public static String currentFuelRailPressure = "";
    public static void setFuelRailPressure (String result){
        currentFuelRailPressure = String.valueOf(calculate(result,FuelRailPressurePidEncoder,4) * 0.079);
    }
    public static String getFuelRailPressure (){ return currentFuelRailPressure;
    }

    public static String EGRCommand = "012C";
    public static String EGRPidEncoder = "412C";
    public static String currentEGR = "";
    public static void setEGR (String result){
        currentEGR = String.valueOf(calculate(result,EGRPidEncoder,2) * 100/255);
    }
    public static String getEGR (){ return currentEGR;
    }

    public static String FuelLevelCommand = "012F";
    public static String FuelLevelPidEncoder = "412F";
    public static String currentFuelLevel = "";
    public static void setFuelLevel (String result){
        currentFuelLevel = String.valueOf(calculate(result,FuelLevelPidEncoder,2) * 100/255);
    }
    public static String getFuelLevel (){ return currentFuelLevel;
    }

    public static String AbsoluteBarometricPressureCommand = "0133";
    public static String AbsoluteBarometricPressurePidEncoder = "4133";
    public static String currentAbsoluteBarometricPressure = "";
    public static void setAbsoluteBarometricPressure (String result){
        currentAbsoluteBarometricPressure = String.valueOf(calculate(result,AbsoluteBarometricPressurePidEncoder,2));
    }
    public static String getAbsoluteBarometricPressure (){ return currentAbsoluteBarometricPressure;
    }

    public static String RelativeThrottlePositionCommand = "0145";
    public static String RelativeThrottlePositionPidEncoder = "4145";
    public static String currentRelativeThrottlePosition = "";
    public static void setRelativeThrottlePosition (String result){
        currentRelativeThrottlePosition = String.valueOf(calculate(result,RelativeThrottlePositionPidEncoder,2) * 100/255);
    }
    public static String getRelativeThrottlePosition (){ return currentRelativeThrottlePosition;
    }

    public static String EngineOilTempCommand = "015C";
    public static String EngineOilTempPidEncoder = "415C";
    public static String currentEngineOilTemp = "";
    public static void setEngineOilTemp (String result){
        currentEngineOilTemp = String.valueOf(calculate(result,EngineOilTempPidEncoder,2) - 40);
    }
    public static String getEngineOilTemp (){ return currentEngineOilTemp;
    }

    public static String VINCommand = "0902";
    public static String VINPidEncoder = "4202";
    public static String currentVIN = "";
    public static void setVIN (String result){
        currentVIN = String.valueOf(calculate(result,VINPidEncoder,4));
    }
    public static String getVIN (){ return currentVIN;
    }


}

