package com.example.bluetoothdemo01;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button searchBtn;
    TextView statusTextView;
    ArrayList<String>deviceDetails;
    ArrayAdapter<String>myAdapter;


    BluetoothAdapter bluetoothAdapter;



    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
            {
                statusTextView.setText("Finsihed");
            searchBtn.setEnabled(true);

            }
            else if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String address = device.getAddress();
                String rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE)+"";

               String det = "Name:"+name+" Address:"+address+" Rssi:"+rssi;
               deviceDetails.add(det);
               myAdapter.notifyDataSetChanged();

            }



        }
    };

    public void searchDevices(View view){

        deviceDetails.clear();
        statusTextView.setText("Searching ...");
        searchBtn.setEnabled(false);
        bluetoothAdapter.startDiscovery();





    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        statusTextView = findViewById(R.id.statusTextVIew);
        searchBtn = findViewById(R.id.searchBtn);
        deviceDetails = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,deviceDetails);


        listView.setAdapter(myAdapter);



        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(broadcastReceiver,intentFilter);









    }
}
