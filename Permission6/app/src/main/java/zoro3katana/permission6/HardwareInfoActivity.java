package zoro3katana.permission6;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HardwareInfoActivity extends AppCompatActivity {
    TextView txtvBoard, txtvBootloader, txtvBrand, txtvDeviced, txtvDisplay,
            txtvFingerPrint, txtvRadioVersion, txtvHardware, txtvHost, txtvId,
            txtvManufacture, txtvModel, txtvProduct, txtvSerial, txtvTags,
            txtvType, txtvUser, txtvCpuAbi, txtvCpuAbi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_info);
        mapping();
        setValue();
    }

    private void mapping() {
        txtvBoard = (TextView) findViewById(R.id.txtv_hardware_board);
        txtvBootloader = (TextView) findViewById(R.id.txtv_hardware_bootloader);
        txtvBrand = (TextView) findViewById(R.id.txtv_hardware_brand);
        txtvDeviced = (TextView) findViewById(R.id.txtv_hardware_device);
        txtvDisplay = (TextView) findViewById(R.id.txtv_hardware_display);
        txtvFingerPrint = (TextView) findViewById(R.id.txtv_hardware_fingerprint);
        txtvRadioVersion = (TextView) findViewById(R.id.txtv_hardware_radio_version);
        txtvHardware = (TextView) findViewById(R.id.txtv_hardware_hardware);
        txtvHost = (TextView) findViewById(R.id.txtv_hardware_host);
        txtvId = (TextView) findViewById(R.id.txtv_hardware_id);
        txtvManufacture = (TextView) findViewById(R.id.txtv_hardware_manufacture);
        txtvModel = (TextView) findViewById(R.id.txtv_hardware_model);
        txtvProduct = (TextView) findViewById(R.id.txtv_hardware_product);
        txtvSerial = (TextView) findViewById(R.id.txtv_hardware_serial);
        txtvTags = (TextView) findViewById(R.id.txtv_hardware_tags);
        txtvType = (TextView) findViewById(R.id.txtv_hardware_type);
        txtvUser = (TextView) findViewById(R.id.txtv_hardware_user);
        txtvCpuAbi = (TextView) findViewById(R.id.txtv_hardware_cpu_abi);
        txtvCpuAbi2 = (TextView) findViewById(R.id.txtv_hardware_cpu_abi2);
    }

    private void setValue() {
        txtvBoard.setText(Build.BOARD);
        txtvBootloader.setText(Build.BOOTLOADER);
        txtvBrand.setText(Build.BRAND);
        txtvDeviced.setText(Build.DEVICE);
        txtvDisplay.setText(Build.DISPLAY);

        txtvFingerPrint.setText(Build.FINGERPRINT);
        txtvRadioVersion.setText(Build.getRadioVersion());
        txtvHardware.setText(Build.HARDWARE);
        txtvHost.setText(Build.HOST);
        txtvId.setText(Build.ID);

        txtvManufacture.setText(Build.MANUFACTURER);
        txtvModel.setText(Build.MODEL);
        txtvProduct.setText(Build.PRODUCT);
        txtvSerial.setText(Build.SERIAL);
        txtvTags.setText(Build.TAGS);

        txtvType.setText(Build.TYPE);
        txtvUser.setText(Build.USER);
        txtvCpuAbi.setText(Build.CPU_ABI);
        txtvCpuAbi2.setText(Build.CPU_ABI2);
    }
}
