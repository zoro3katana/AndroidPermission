package zoro3katana.permission6;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SoftwareInfoActivity extends AppCompatActivity {
    TextView txtvCodeName, txtvIncremental, txtvRelease, txtvSecurityPatch, txtvSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_info);
        mapping();
        setValue();
    }

    private void setValue() {
        txtvCodeName.setText(Build.VERSION.CODENAME);
        txtvIncremental.setText(Build.VERSION.INCREMENTAL);
        txtvRelease.setText(Build.VERSION.RELEASE);
        txtvSecurityPatch.setText(Build.VERSION.SECURITY_PATCH);
        txtvSdk.setText(Integer.toString(Build.VERSION.SDK_INT));
    }

    private void mapping() {
        txtvCodeName = (TextView) findViewById(R.id.txtv_software_code_name);
        txtvIncremental = (TextView) findViewById(R.id.txtv_software_incremental);
        txtvRelease = (TextView) findViewById(R.id.txtv_software_release);
        txtvSecurityPatch = (TextView) findViewById(R.id.txtv_software_security_patch);
        txtvSdk = (TextView) findViewById(R.id.txtv_software_sdk);
    }
}
