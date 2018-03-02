package com.example.localboundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MTAG";
    private BoundService service;
    private boolean isBound = false;
    private Button button;
    private TextView textView;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.LocalBinder localBinder = (BoundService.LocalBinder) iBinder;
            service = localBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.time);

        Intent intent  = new Intent(this,BoundService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "onCreate: Binded To Service");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                getTime();
                break;
            default:
                break;
        }
    }

    public void getTime() {
        textView.setText(service.getCurrentTime());
    }
}











