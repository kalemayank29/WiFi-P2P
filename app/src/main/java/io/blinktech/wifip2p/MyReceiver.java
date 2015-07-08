package io.blinktech.wifip2p;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayank on 7/7/15.
 */
public class MyReceiver extends BroadcastReceiver {
   // Activity activity;

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MainActivity mActivity;
    private List peers = new ArrayList();

    public MyReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }




    @Override
    public void onReceive(Context context, Intent intent){
            String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //activity.setIsWifiP2pEnabled(true);

                Toast.makeText(context, "WiFiP2P enabled", Toast.LENGTH_LONG).show();
               // if (mManager != null) {
                 //   mManager.requestPeers(mChannel, peerListListener);
                //}
                //Log.e("YO YO", "P2P peers changed");

            } else {
                //activity.setIsWifiP2pEnabled(false);
                Toast.makeText(context, "WiFiP2P not enabled", Toast.LENGTH_LONG).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed!  We should probably do something about
            // that.
            Toast.makeText(context, "Changed Peer list", Toast.LENGTH_LONG).show();

            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }
                Log.e("YO YO", "P2P peers changed");

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed!  We should probably do something about
            // that.
            Toast.makeText(context, "Connection Changed", Toast.LENGTH_LONG).show();

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            /*    DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
                    .findFragmentById(R.id.frag_list);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));*/
            Toast.makeText(context, "Changed Device Config", Toast.LENGTH_LONG).show();

        }

    }

    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            // Out with the old, in with the new.
            peers.clear();
            peers.addAll(peerList.getDeviceList());

            // If an AdapterView is backed by this data, notify it
            // of the change.  For instance, if you have a ListView of available
            // peers, trigger an update.
           // ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
            Log.e("YES", "Function called");
            if (peers.size() == 0) {
                Log.e("DAMN", "No devices found");
               // Toast.makeText(context, "No Devices Found", Toast.LENGTH_LONG).show();
                return;
            }
            else{
                Log.e("YES", "Devices Found");
                WifiP2pConfig config = new WifiP2pConfig();
                WifiP2pDevice device = (WifiP2pDevice) peers.get(0);
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;

                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("Connected to Kyles Tab", "YES");
                    }

                    @Override
                    public void onFailure(int i) {
                        Log.e("Not connected to Kyle", "Damn");
                    }
                });
                Log.e("Kyle Tablet", device.deviceAddress);
            }

        }
    };



}
