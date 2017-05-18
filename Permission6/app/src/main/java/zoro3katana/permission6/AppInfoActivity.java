package zoro3katana.permission6;

import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> appsList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        ListView listView = (ListView) findViewById(R.id.activity_app_info_lv_showall);
        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(getApplicationContext(), R.layout.app_info_list_row, appsList);
        listView.setAdapter(appInfoAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(AppInfoActivity.this);
                dialog.setCancelable(true);
                dialog.setTitle("Forced stop");
                dialog.setMessage("Do you want forced it to stop!");
                dialog.setPositiveButton("FORCED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*try {
                            checkAndRequestPermissions(AppInfoActivity.this);
                            Process suProcess = Runtime.getRuntime().exec("su");
                            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

                            os.writeBytes("adb shell" + "\n");

                            os.flush();

                            os.writeBytes("am force-stop com.xxxxxx" + "\n");

                            os.flush();
                        } catch (Exception e) {
                            e.getCause().printStackTrace();
                        }*/
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
