package zoro3katana.permission6;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;

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
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            }
        }
    }

    public static void initPermission(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                //Register permission
                requestPermissions(activity, new String[]{permission}, 1);

            }
        }
    }
}
