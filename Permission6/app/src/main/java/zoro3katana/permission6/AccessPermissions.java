package zoro3katana.permission6;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompatApi23.requestPermissions;
import static android.support.v4.app.ActivityCompatApi23.shouldShowRequestPermissionRationale;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by nttrung on 4/20/2017.
 */

public class AccessPermissions {
    /**
     * Request permissions from android 6.0 or highter
     *
     * @param activity current activity when require permission
     * @return none
     */
    public static void checkAndRequestPermissions(Activity activity) {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,

                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,

                Manifest.permission.MODIFY_PHONE_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,

                Manifest.permission.WRITE_SETTINGS,

                Manifest.permission.KILL_BACKGROUND_PROCESSES
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            }
        }
    }

    public static void initPermission(Activity activity, String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(activity, permission)) {
                    Toast.makeText(activity, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(activity, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{permission}, 1);

            }
        }
    }

}
