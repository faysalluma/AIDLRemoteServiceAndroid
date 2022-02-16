package com.example.aidlclient;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidlservice.IAdd;
import com.example.aidlservice.IRemoteProductService;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private EditText number1;
    private EditText number2;
    private Button btnAdd;
    private Button btnGetList;
    private TextView total;

    private String serverAppUri = "com.example.aidlservice";
    private IAdd additionService;
    private String Tag = MainActivity2.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialse connection with server
        initConnection();

        // Initialise Views
        number1 = (EditText) findViewById(R.id.num1);
        number2 = (EditText) findViewById(R.id.num2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGetList = (Button) findViewById(R.id.btnNonPremitive);
        total = (TextView) findViewById(R.id.total);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {


                if (appInstalledOrNot(serverAppUri)) {

                    if (number1.length() > 0 && number2.length() > 0 && additionService != null) {
                        try {
                            total.setText("Result: " + additionService
                                    .addNumbers(Integer.parseInt(number1.getText().toString()),
                                            Integer.parseInt(number2.getText().toString())));

                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(Tag, "Connection cannot be establish");
                        }
                    }

                } else {
                    Toast.makeText(MainActivity2.this, "Server App not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                try {
                    String result="";
                    List<String> lists = additionService.getStringList();
                    for (String item : lists){
                        result+=item.toString()+" , ";
                    }
                    total.setText(result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //student = IStudent.Stub.asInterface(iBinder);
            additionService = IAdd.Stub.asInterface(iBinder);
            Log.d(Tag, "Service Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            additionService=null;
            Log.d(Tag, "Service Disconnected");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (additionService == null) {
            initConnection();
        }
    }

    private void initConnection() {
        Intent intent = new Intent();
        intent.setAction("addservice");
        intent.setPackage(serverAppUri);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            PackageInfo a= pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            String b =a.toString();
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


}