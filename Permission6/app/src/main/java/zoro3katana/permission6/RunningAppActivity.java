package zoro3katana.permission6;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class RunningAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_app);


        Log.i("xxxxxxxxxxxxxxx", "CODENAME: " + Build.VERSION.CODENAME);
        Log.i("xxxxxxxxxxxxxxx", "INCREMENTAL: " + Build.VERSION.INCREMENTAL);
        Log.i("xxxxxxxxxxxxxxx", "RELEASE: " + Build.VERSION.RELEASE);
        Log.i("xxxxxxxxxxxxxxx", "SECURITY_PATCH: " + Build.VERSION.SECURITY_PATCH);
        Log.i("xxxxxxxxxxxxxxx", "SDK: " + Build.VERSION.SDK);



/*        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRecentTasks(1, ActivityManager.RECENT_WITH_EXCLUDED);
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)(i.next());
            try {
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(
                        info.processName, PackageManager.GET_META_DATA));
                Log.w("LABEL", c.toString());
            } catch (Exception e) {
                // Name Not FOund Exception
            }
        }*/
    }
}
