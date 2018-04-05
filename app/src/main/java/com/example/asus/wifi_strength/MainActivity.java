package com.example.asus.wifi_strength;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtwifiinfo;
    Button btnInfo;
    TextView capure_strength;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtwifiinfo = (TextView) findViewById(R.id.idTxt);
        btnInfo=(Button) findViewById(R.id.idBtn);


        capure_strength= (TextView) findViewById(R.id.textView2);
        Thread t=new Thread(){
            @Override
            public void run() {
                while(!isInterrupted())
                {
                    try
                    {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WifiManager wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                                WifiInfo wifiInfo= wifiManager.getConnectionInfo();
                                List<ScanResult> wifiList = wifiManager.getScanResults();
                                for (ScanResult scanResult : wifiList) {
                                    int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
                                    String net=String.valueOf(level);

                                }

                                int rssi1 = wifiManager.getConnectionInfo().getRssi();
                                int level = WifiManager.calculateSignalLevel(rssi1, 5);
                                String net=String.valueOf(rssi1);

                                net ="capturing wifi strength \n (interval of 1 sec) \n "+ net +" in dBm";

                                capure_strength.setText(net);


                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getWifiInformation(View view) {
        WifiManager wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo= wifiManager.getConnectionInfo();
        int ip=wifiInfo.getIpAddress();
        String mac= wifiInfo.getMacAddress();
        String bssid= wifiInfo.getBSSID();
        int rssi= wifiInfo.getRssi();
        int linkspeed= wifiInfo.getLinkSpeed();
        String ssid=wifiInfo.getSSID();
        int networkid=wifiInfo.getNetworkId();


        List<ScanResult> wifiList = wifiManager.getScanResults();
        for (ScanResult scanResult : wifiList) {
            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            String net=String.valueOf(level);
            // Toast.makeText(MainActivity.this,net,Toast.LENGTH_LONG).show();

        }

// Level of current connection.here rssi is the value of internet speed whose value
// can be -50,-60 and some others,you can find the speed values easily on internet.
        int rssi1 = wifiManager.getConnectionInfo().getRssi();
        int level = WifiManager.calculateSignalLevel(rssi1, 5);
        String net=String.valueOf(rssi1);
//        Toast.makeText(MainActivity.this,net, Toast.LENGTH_LONG).show();

        int ipAddress= wifiInfo.getIpAddress();
        int wififrequency = wifiInfo.getFrequency();
        String info =
                "\n " + "IPaddress :" + ipAddress +
                "\n " + "Mac Address: " + mac +
                "\n " + "BSSID: " +bssid +
                "\n " + "SSID: " + ssid +
                "\n " + "Network Frequency: " +wififrequency + "in MHz" +
                "\n " + "Link speed: " +linkspeed + "in Mbps" +
                "\n\n\t " + "wifistrength: " + net +"in dBm ";
        txtwifiinfo.setText(info);



    }


}
