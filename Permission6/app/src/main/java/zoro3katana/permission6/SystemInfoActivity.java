package zoro3katana.permission6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SystemInfoActivity extends AppCompatActivity implements View.OnClickListener {
    View viewInfoHardware, viewInfoSoftware, viewInfoBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info);
        mapping();
        viewInfoHardware.setOnClickListener(this);
        viewInfoSoftware.setOnClickListener(this);
        viewInfoBattery.setOnClickListener(this);

    }

    public void mapping() {
        viewInfoHardware = findViewById(R.id.info_hardware);
        viewInfoSoftware = findViewById(R.id.info_software);
        viewInfoBattery = findViewById(R.id.info_battery);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_hardware:
                Intent intentHardwareInfo = new Intent(SystemInfoActivity.this, HardwareInfoActivity.class);
                startActivity(intentHardwareInfo);
                break;
            case R.id.info_software:
                Intent intentSoftwareInfo = new Intent(SystemInfoActivity.this, SoftwareInfoActivity.class);
                startActivity(intentSoftwareInfo);
                break;
            case R.id.info_battery:
                Intent intentBatteryInfo = new Intent(SystemInfoActivity.this, BatteryInfoActivity.class);
                startActivity(intentBatteryInfo);
                break;

        }
    }
}
