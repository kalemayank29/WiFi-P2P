package io.blinktech.wifip2p;

import android.net.wifi.p2p.WifiP2pInfo;

/**
 * Created by mayank on 7/8/15.
 */
public interface MyReceiver {
    void onConnectionInfoAvailable(WifiP2pInfo info);
}
