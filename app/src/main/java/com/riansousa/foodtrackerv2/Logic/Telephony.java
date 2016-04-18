package com.riansousa.foodtrackerv2.Logic;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

/**
 * This code is excerpted from the lecture notes Module 5
 */
public class Telephony {

    /** declare global variables */
    private static final String TAG = "FoodTracker";

    /**
     * this is a method to check the telephony stats and report via post
     * @param context
     * @return
     */
    public String showTelStatus(android.content.Context context) {
        String result = "";
        try {
            /** instantiate telephony manager */
            final TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            /** callState = state of the call (idle, ringing, off the hook etc.) */
            int callState = telMgr.getCallState();
            String callStateString = "NA";
            switch (callState) {
                case TelephonyManager.CALL_STATE_IDLE:
                    callStateString = "IDLE";
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    callStateString = "OFFHOOK";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    callStateString = "RINGING";
                    break;
            }

            /** cellLocation = location of cell in latitude/longitude */
            CellLocation cellLocation = telMgr.getCellLocation();
            String cellLocationString = null;
            if (cellLocation instanceof GsmCellLocation)
            {
                cellLocationString = ((GsmCellLocation)cellLocation).getLac()
                        + " " + ((GsmCellLocation)cellLocation).getCid();
            }
            else if (cellLocation instanceof CdmaCellLocation)
            {
                cellLocationString = ((CdmaCellLocation)cellLocation).
                        getBaseStationLatitude() + " " +
                        ((CdmaCellLocation)cellLocation).getBaseStationLongitude();
            }

            /**
             * deviceId, deviceSoftwareVersion, line1Number, networkCountryIso,
             * networkOperator, networkOperatorName
             * */
            String deviceId = telMgr.getDeviceId();
            String deviceSoftwareVersion = telMgr.getDeviceSoftwareVersion();
            String line1Number = telMgr.getLine1Number();
            String networkCountryIso = telMgr.getNetworkCountryIso();
            String networkOperator = telMgr.getNetworkOperator();
            String networkOperatorName = telMgr.getNetworkOperatorName();

            /** phoneType (GSM, CDMA, etc.) */
            int phoneType = telMgr.getPhoneType();
            String phoneTypeString = "NA";
            switch (phoneType) {
                case TelephonyManager.PHONE_TYPE_GSM:
                    phoneTypeString = "GSM";
                    break;
                case TelephonyManager.PHONE_TYPE_CDMA:
                    phoneTypeString = "CDMA";
                    break;
                case TelephonyManager.PHONE_TYPE_NONE:
                    phoneTypeString = "NONE";
                    break;
            }

            /** simCountryIso, simOperator, simOperatorName, simSerialNumber, simSubscriberId */
            String simCountryIso = telMgr.getSimCountryIso();
            String simOperator = telMgr.getSimOperator();
            String simOperatorName = telMgr.getSimOperatorName();
            String simSerialNumber = telMgr.getSimSerialNumber();
            String simSubscriberId = telMgr.getSubscriberId();

            /** simState = state of sim card (pin requires, ready, etc.) */
            int simState = telMgr.getSimState();
            String simStateString = "NA";
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    simStateString = "ABSENT";
                    break;
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    simStateString = "NETWORK_LOCKED";
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    simStateString = "PIN_REQUIRED";
                    break;
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    simStateString = "PUK_REQUIRED";
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    simStateString = "STATE_READY";
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    simStateString = "STATE_UNKNOWN";
                    break;
            }

            /** return concatenation of the above data, with labels */
            result = "Telephone Manager:  " +
                    "  \ncallState = " + callStateString +
                    "  \ncellLocationString = " + cellLocationString +
                    "  \ndeviceId = " + deviceId +
                    "  \ndeviceSoftwareVersion = " + deviceSoftwareVersion +
                    "  \nline1Number = " + line1Number +
                    "  \nnetworkCountryIso = " + networkCountryIso +
                    "  \nnetworkOperator = " + networkOperator +
                    "  \nnetworkOperatorName = " + networkOperatorName +
                    "  \nphoneTypeString = " + phoneTypeString +
                    "  \nsimCountryIso = " + simCountryIso +
                    "  \nsimOperator = " + simOperator +
                    "  \nsimOperatorName = " + simOperatorName +
                    "  \nsimSerialNumber = " + simSerialNumber +
                    "  \nsimSubscriberId = " + simSubscriberId +
                    "  \nsimStateString = " + simStateString;

            /** Toast message */
            Toast.makeText(context.getApplicationContext(), result, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.i(TAG, "ReportingActivity.showTelStatus() - Error: " + e.getMessage());
        }
        return result;
    }

}
