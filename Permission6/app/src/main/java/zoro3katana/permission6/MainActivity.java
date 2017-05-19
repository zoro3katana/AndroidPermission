package zoro3katana.permission6;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static zoro3katana.permission6.AccessPermissions.initPermission;

public class MainActivity extends AppCompatActivity {

    Switch swWifi, swBluetooth, sw3g, swGps, swRotate, swSilent;
    Button btnShowApp, btnWifiInfo, btnRunningApp, btnStorage;
    TextView tvChangeSize;
    SeekBar brightbar, fonsize;
    private int brightness;
    // Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    // Window object, that will store a reference to the current window
    private Window window;

    private boolean wifiEnabled;
    private boolean cellcularEnabled;
    private boolean gpsEnabled;
    private boolean rotated;

    WifiManager wifi;
    HashMap<String, String> hashMapWifiInfo = null;

    BluetoothAdapter bluetoothAdapter;
    TelephonyManager telephonyManager;
    Method setMobileDataEnabledMethod;
    LocationManager locationManager;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        turnWifi();

        turnBluetooth();

        turnCellular();

        turnGps();

        rotate();

        turnAudio();

        turnAudio();

        adjustBrightness();

        changeFontSize();

        btnWifiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Must access location to get type of wifi security
                initPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                initPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                getWifiInfo();
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("Wifi information");
                dialog.setMessage("BSSID: " + hashMapWifiInfo.get("BSSID") + "\n"
                        + "MacAddress: " + hashMapWifiInfo.get("MacAddress") + "\n"
                        + "SSID: " + hashMapWifiInfo.get("SSID") + "\n"
                        + "Frequency: " + hashMapWifiInfo.get("Frequency") + "\n"
                        + "HiddenSSID: " + hashMapWifiInfo.get("HiddenSSID") + "\n"
                        + "IpAddress: " + hashMapWifiInfo.get("IpAddress") + "\n"
                        + "LinkSpeed: " + hashMapWifiInfo.get("LinkSpeed") + "\n"
                        + "NetworkId: " + hashMapWifiInfo.get("NetworkId") + "\n"
                        + "Rssi: " + hashMapWifiInfo.get("Rssi") + "\n"
                        + "SupplicantState: " + hashMapWifiInfo.get("SupplicantState") + "\n"
                        + "Security: " + hashMapWifiInfo.get("Security") + "\n");

                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        btnShowApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppInfoActivity.class);
                startActivity(intent);
            }
        });
        btnRunningApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RunningAppActivity.class);
                startActivity(intent);
            }
        });
        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExternalStorageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        swWifi = (Switch) findViewById(R.id.sw_wifi);
        swBluetooth = (Switch) findViewById(R.id.sw_bluetooth);
        sw3g = (Switch) findViewById(R.id.sw_3g);
        swGps = (Switch) findViewById(R.id.sw_gps);
        swRotate = (Switch) findViewById(R.id.sw_rotate);
        swSilent = (Switch) findViewById(R.id.sw_silent);
        btnShowApp = (Button) findViewById(R.id.btn_showapp);
        fonsize = (SeekBar) findViewById(R.id.sb_fontsize);
        tvChangeSize = (TextView) findViewById(R.id.tv_fontsize);
        btnWifiInfo = (Button) findViewById(R.id.btn_wifiinfo);
        btnRunningApp = (Button) findViewById(R.id.btn_runningapp);
        btnStorage = (Button) findViewById(R.id.btn_storage);
    }

    private void turnWifi() {
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiEnabled = wifi.isWifiEnabled();
        if (wifiEnabled) {
            swWifi.setChecked(true);
        }
        swWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Turn on Wifi
                    wifi.setWifiEnabled(true);
                    wifiEnabled = true;

                } else {
                    // Turn off Wifi
                    wifi.setWifiEnabled(false);
                    wifiEnabled = false;
                }

                // Check status after change
                if (wifiEnabled) {
                    Toast.makeText(MainActivity.this, "Wifi ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Wifi OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getWifiInfo() {
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        hashMapWifiInfo = new HashMap<>();
        if (wifiInfo != null) {
            hashMapWifiInfo.put("BSSID", wifiInfo.getBSSID());
            hashMapWifiInfo.put("MacAddress", getWifiMacAddress());
            hashMapWifiInfo.put("SSID", wifiInfo.getSSID());
            hashMapWifiInfo.put("Frequency", String.valueOf(wifiInfo.getFrequency()));
            hashMapWifiInfo.put("HiddenSSID", String.valueOf(wifiInfo.getHiddenSSID()));
            hashMapWifiInfo.put("IpAddress", Formatter.formatIpAddress(wifiInfo.getIpAddress()));
            hashMapWifiInfo.put("LinkSpeed", String.valueOf(wifiInfo.getLinkSpeed()));
            hashMapWifiInfo.put("NetworkId", String.valueOf(wifiInfo.getNetworkId()));
            hashMapWifiInfo.put("Rssi", String.valueOf(wifiInfo.getRssi()));
            hashMapWifiInfo.put("SupplicantState", String.valueOf(wifiInfo.getSupplicantState()));
            hashMapWifiInfo.put("Security", getWifiSecurityType());
        }
        Log.i("--------- wifi info", hashMapWifiInfo.toString());
        String a = getWifiSecurityType();

    }

    private String getWifiSecurityType() {
        String SecurityType = "";
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.startScan();
        List<ScanResult> networkList = wifi.getScanResults();
        WifiInfo wi = wifi.getConnectionInfo();
        String currentSSID = wi.getSSID();

        if (networkList != null) {
            for (ScanResult network : networkList) {
                //check if current connected SSID
                if (currentSSID.equals("\"" + network.SSID + "\"")) {
                    //get capabilities of current connection
                    String Capabilities = network.capabilities;
                    Log.d(">>>>>>>>>>>>>>>>>>>>", network.SSID + " capabilities : " + Capabilities);

                    if (Capabilities.contains("WPA")) {
                        SecurityType += "WPA ";
                    }
                    if (Capabilities.contains("WPA2")) {
                        SecurityType += "WPA2 ";
                    }
                    if (Capabilities.contains("PSK")) {
                        SecurityType += "PSK ";
                    }
                    if (Capabilities.contains("WEP")) {
                        SecurityType += "WEP ";
                    }
                }
            }
        }
        return SecurityType;
    }

    private static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac == null) {
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) {
        }
        return "";
    }

    private void turnCellular() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Log.i("-------------------", String.valueOf(telephonyManager.getDataState()));
        if (telephonyManager.getDataState() == 2) { // 2 is DATA_CONNECTED
            cellcularEnabled = true;
        }
        if (cellcularEnabled) {
            sw3g.setChecked(true);
        } else {
            sw3g.setChecked(false);
        }
        switch (telephonyManager.getDataState()) {
            case TelephonyManager.DATA_CONNECTED:
                sw3g.setChecked(true);
                break;
            case TelephonyManager.DATA_DISCONNECTED:
                sw3g.setChecked(false);
                break;
        }

        sw3g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        setMobileDataEnabledMethod = telephonyManager.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    if (null != setMobileDataEnabledMethod) {
                        try {
                            setMobileDataEnabledMethod.invoke(telephonyManager, true);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.getCause().printStackTrace();
                        }
                    }
                } else {

                }
                // Check status after change
                if (telephonyManager.getDataState() == 2) {
                    Toast.makeText(MainActivity.this, "3G ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "3G OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void turnBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter.isEnabled()) {
            swBluetooth.setChecked(true);
        } else {
            swBluetooth.setChecked(false);
        }
        swBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Turn on Bluetooth
                    bluetoothAdapter.enable();
                } else {
                    // Turn off Bluetooth
                    bluetoothAdapter.disable();
                }

                // Check status after change
                if (bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainActivity.this, "Bluetooth ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void turnGps() {

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gpsEnabled) {
            swGps.setChecked(true);
        } else {
            swGps.setChecked(false);
        }
        swGps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Turn on GPS
                    /*Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                    intent.putExtra("enabled", true);
                    sendBroadcast(intent);*/
                } else {
                    // Turn off GPS
                    /*Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                    intent.putExtra("enabled", false);
                    sendBroadcast(intent);*/
                }

                // Check status after change
                if (true) {
                    Toast.makeText(MainActivity.this, "GPS ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "GPS OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void rotate() {
        int rotation = ((WindowManager) MainActivity.this.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        // Defaut sensor is disabled
        if (rotation == 0) {
            swRotate.setChecked(false);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        } else {
            swRotate.setChecked(true);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        swRotate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                }
            }
        });
    }

    private void turnAudio() {
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int audioStatus = audioManager.getRingerMode();
        if (audioStatus == 1) {
            swSilent.setChecked(true);
        }
        swSilent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    audioManager.setRingerMode(1);
                } else {
                    audioManager.setRingerMode(2);
                }
            }
        });
    }

    private void adjustBrightness() {
        // Instantiate seekbar object
        brightbar = (SeekBar) findViewById(R.id.sb_brightness);

        //Get the content resolver
        cResolver = getContentResolver();

        //Get the current window
        window = getWindow();

        //Set the seekbar range between 0 and 255
        brightbar.setMax(255);
        //Set the seek bar progress to 1
        brightbar.setKeyProgressIncrement(1);

        try {
            //Get the current system brightness
            brightness = System.getInt(cResolver, System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }

        //Set the progress of the seek bar based on the system's brightness
        brightbar.setProgress(brightness);

        //Register OnSeekBarChangeListener, so it can actually change values
        brightbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (Settings.System.canWrite(getApplicationContext())) {
                    //Set the system brightness using the brightness variable value
                    System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
                    //Get the current window attributes
                    WindowManager.LayoutParams layoutpars = window.getAttributes();
                    //Set the brightness of this window
                    layoutpars.screenBrightness = brightness / (float) 255;
                    //Apply attribute changes to this window
                    window.setAttributes(layoutpars);
                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //Nothing handled here
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Set the minimal brightness level
                //if seek bar is 20 or any value below
                if (progress <= 20) {
                    //Set the brightness to 20
                    brightness = 20;
                } else //brightness is greater than 20
                {
                    //Set brightness variable based on the progress bar
                    brightness = progress;
                }
                //Calculate the brightness percentage
                float perc = (brightness / (float) 255) * 100;
            }
        });
    }

    private void changeFontSize() {
        final int defaulTextSize = 10;
        final float defaultFontSize = 1.0f;
        float currentFontSize = 1.0f;
        try {
            currentFontSize = System.getFloat(getContentResolver(), Settings.System.FONT_SCALE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        fonsize.setMax(10);
        fonsize.setKeyProgressIncrement(2);

        fonsize.setProgress((int) currentFontSize);

        tvChangeSize.setTextSize(defaulTextSize * fonsize.getProgress());

        fonsize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    Settings.System.putFloat(getBaseContext().getContentResolver(),
                            Settings.System.FONT_SCALE, defaultFontSize);
                    tvChangeSize.setTextSize(defaulTextSize);
                    Toast.makeText(getApplicationContext(), "Please reboot", Toast.LENGTH_SHORT).show();
                } else {
                    Settings.System.putFloat(getBaseContext().getContentResolver(),
                            Settings.System.FONT_SCALE, defaultFontSize * progress);
                    tvChangeSize.setTextSize(defaulTextSize * progress);
                    Toast.makeText(getApplicationContext(), "Please reboot", Toast.LENGTH_SHORT).show();
                    /*PowerManager pm = (PowerManager) this.getApplicationContext().getSystemService(Context.POWER_SERVICE);
                    pm.reboot(null);*/
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}