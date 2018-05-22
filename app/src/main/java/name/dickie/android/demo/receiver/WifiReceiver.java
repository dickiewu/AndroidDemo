package name.dickie.android.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by wuxiaodong on 18/3/21.
 */

public class WifiReceiver extends BroadcastReceiver {
    private static final String TAG = "WXD";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //android.net.conn.CONNECTIVITY_CHANGE
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.e(TAG, String.format("network connectivity change"));
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                onInternetAccessable();
            }
        }

        //android.net.wifi.STATE_CHANGE
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {

            } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            }
        }

        //android.net.wifi.WIFI_STATE_CHANGED
        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {

            } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {

            }
        }
    }

    private void onInternetAccessable() {

    }
}