package name.dickie.android.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import name.dickie.android.demo.ContextHolder;

public class NetWorks {
    private static final String TAG = "wxd";

    public static void showInfo() {
        Context context = ContextHolder.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo==null)
            return;
        int type = activeNetworkInfo.getType();
        String typeName = activeNetworkInfo.getTypeName();
        Log.e(TAG, String.format("type:%s,typeName:%s", type, typeName));
        int subtype = activeNetworkInfo.getSubtype();
        String subtypeName = activeNetworkInfo.getSubtypeName();
        Log.e(TAG, String.format("subType:%d,subTypeName:%s", subtype, subtypeName));
        showMobileInfo();

        boolean connectedOrConnecting = activeNetworkInfo.isConnectedOrConnecting();
        Log.e(TAG, String.format("connecedOrConnectding %b",connectedOrConnecting));
        boolean connected = activeNetworkInfo.isConnected();
        Log.e(TAG, String.format("connected %b",connected));





    }

    public static boolean isNetWorkConnected(){
        Context context = ContextHolder.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();  // 没有 wifi 也没有移动网，会返回null
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }

    public static boolean isMobileConnectdType(){
        Context context = ContextHolder.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        int type = activeNetworkInfo.getType();
        return type==ConnectivityManager.TYPE_MOBILE;
    }

    public static boolean isWifiConnectedType(){
        Context context = ContextHolder.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        int type = activeNetworkInfo.getType();
        return type==ConnectivityManager.TYPE_WIFI;
    }

    public static void showMobileInfo(){
        Context context = ContextHolder.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        int type = activeNetworkInfo.getType();
        int subtype = activeNetworkInfo.getSubtype();
        String subtypeName = activeNetworkInfo.getSubtypeName();
        Log.e(TAG, String.format("subType:%d,subTypeName:%s", subtype, subtypeName));

        switch (subtype){
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                Log.e(TAG, String.format("2g"));
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                /**
                 From this link https://en.wikipedia.org/wiki/Evolution-Data_Optimized ..NETWORK_TYPE_EVDO_0 & NETWORK_TYPE_EVDO_A
                 EV-DO is an evolution of the CDMA2000 (IS-2000) standard that supports high data rates.

                 Where CDMA2000 https://en.wikipedia.org/wiki/CDMA2000 .CDMA2000 is a family of 3G[1] mobile technology standards for sending voice,
                 data, and signaling data between mobile phones and cell sites.
                 */
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                //Log.d("Type", "3g");
                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
                //Some cases are added after  testing(real) in device with 3g enable data
                //and speed also matters to decide 3g network type
                //https://en.wikipedia.org/wiki/4G#Data_rate_comparison
                Log.e(TAG, String.format("3g"));
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                //No specification for the 4g but from wiki
                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
                //https://en.wikipedia.org/wiki/LTE_(telecommunication)
                Log.e(TAG, String.format("4g!!"));
                break;
            default:
                Log.e(TAG, String.format("unkown!!!"));
                break;
        }
    }



}
