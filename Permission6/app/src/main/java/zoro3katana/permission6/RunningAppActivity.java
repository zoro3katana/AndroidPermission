package zoro3katana.permission6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RunningAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_app);
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
