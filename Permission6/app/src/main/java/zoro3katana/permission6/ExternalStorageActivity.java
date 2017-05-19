package zoro3katana.permission6;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static zoro3katana.permission6.AccessPermissions.initPermission;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtWriteName, edtWriteContent, edtReadName;
    TextView txtvReadContent;
    Button btnWrite, btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);
        initPermission(ExternalStorageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mapping();
        btnWrite.setOnClickListener(this);
        btnRead.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_external_write:
                saveData();
                break;
            case R.id.btn_external_read:
                readData();
                break;
        }
    }

    public void saveData() {
        FileOutputStream fileOutputStream = null;
        File file;
        try {
            // hide keyboard after click button
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            file = new File(Environment.getExternalStoragePublicDirectory("Download"), edtWriteName.getText().toString());
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(edtWriteContent.getText().toString().getBytes());
            fileOutputStream.close();
            edtWriteName.setText("");
            edtWriteContent.setText("");
            Toast.makeText(ExternalStorageActivity.this, "Saved to Download folder", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        // hide keyboard after click button
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        BufferedReader bufferedReader = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory("Download"), edtReadName.getText().toString());
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            txtvReadContent.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            Toast.makeText(ExternalStorageActivity.this, "File not found!", Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapping() {
        edtWriteName = (EditText) findViewById(R.id.edt_external_write_name);
        edtWriteContent = (EditText) findViewById(R.id.edt_external_write_content);
        edtReadName = (EditText) findViewById(R.id.edt_external_read_name);
        txtvReadContent = (TextView) findViewById(R.id.txtv_external_read_content);
        btnWrite = (Button) findViewById(R.id.btn_external_write);
        btnRead = (Button) findViewById(R.id.btn_external_read);
    }

}
