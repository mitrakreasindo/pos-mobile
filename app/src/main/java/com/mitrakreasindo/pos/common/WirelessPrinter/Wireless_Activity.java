package com.mitrakreasindo.pos.common.WirelessPrinter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Wireless_Activity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
  /******************************************************************************************************/
  // Debugging
  private static final String TAG = "Wireless_Activity";
  private static final boolean DEBUG = true;
  public static int x = 0;
  public static int y = 0;
  public static int z = 0;
  /******************************************************************************************************/
  // Message types sent from the BluetoothService Handler
  public static final int MESSAGE_STATE_CHANGE = 1;
  public static final int MESSAGE_READ = 2;
  public static final int MESSAGE_WRITE = 3;
  public static final int MESSAGE_DEVICE_NAME = 4;
  public static final int MESSAGE_TOAST = 5;
  public static final int MESSAGE_CONNECTION_LOST = 6;
  public static final int MESSAGE_UNABLE_CONNECT = 7;
  public static String KEY_NAMA = "";
  
  /*******************************************************************************************************/
  // Key names received from the BluetoothService Handler
  public static final String DEVICE_NAME = "device_name";
  public static final String TOAST = "toast";
  
  // Intent request codes
  private static final int REQUEST_CONNECT_DEVICE = 1;
  private static final int REQUEST_ENABLE_BT = 2;
  private static final int REQUEST_CHOSE_BMP = 3;
  @BindView(R.id.progress)
  ProgressBar progress;
  @BindView(R.id.lnrSwitch)
  LinearLayout lnrSwitch;
  @BindView(R.id.textView2)
  TextView textView2;
  @BindView(R.id.lnrPaired)
  LinearLayout lnrPaired;
  @BindView(R.id.lnrRefresh)
  LinearLayout lnrRefresh;
  @BindView(R.id.lnrTexInfo)
  LinearLayout lnrTexInfo;
  private ArrayAdapter<String> mNewDevicesArrayAdapter;
  private ArrayAdapter<String> mPairedDevicesArrayAdapter;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.btn_switch)
  Switch btnSwitch;
  @BindView(R.id.title_paired_devices)
  TextView titlePairedDevices;
  @BindView(R.id.paired_devices)
  ListView pairedDevices;
  @BindView(R.id.title_new_devices)
  TextView titleNewDevices;
  @BindView(R.id.new_devices)
  ListView newDevices;
  @BindView(R.id.LnrBluetooth)
  LinearLayout LnrBluetooth;
  @BindView(R.id.txInfo)
  TextView txInfo;
  @BindView(R.id.btn_refresh)
  ImageButton btnRefresh;
  /*********************************************************************************/
  private ProgressDialog progressDialog;
  
  /******************************************************************************************************/
  // Name of the connected device
  private String mConnectedDeviceName = null;
  // Local Bluetooth adapter
  private BluetoothAdapter mBluetoothAdapter = null;
  // Member object for the services
  public static BluetoothService mService;
  static BluetoothDevice prev_device = null;
  public static GlobalVariable globalVariable = new GlobalVariable();
  
  /******************************************************************************************************/
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bluetooth);
    ButterKnife.bind(this);
    progressDialog = new ProgressDialog(this);
    setSupportActionBar(toolbar);
    btnSwitch.setOnCheckedChangeListener(this);
    getSupportActionBar().setTitle("Bluetooth Printer");
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });
    btnRefresh.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        doDiscovery();
      }
    });
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter == null)
    {
      Toast.makeText(this, getText(R.string.bluetooth_not_available),
        Toast.LENGTH_LONG).show();
      finish();
    }
    if (mService != null)
    {
      Log.d("mServie: ", String.valueOf(mService));
      if (mService.getState() == BluetoothService.STATE_CONNECTED)
      {
        lnrPaired.setVisibility(View.GONE);
        lnrRefresh.setVisibility(View.GONE);
        LnrBluetooth.setVisibility(View.GONE);
        lnrSwitch.setVisibility(View.VISIBLE);
        btnSwitch.setChecked(true);
        btnSwitch.setText(R.string.title_connected_to);
        lnrTexInfo.setVisibility(View.VISIBLE);
        String deviceName = globalVariable.getDeviceName();
        txInfo.setText(getText(R.string.title_connected_to) + " " + deviceName);
        Toast.makeText(this, getText(R.string.bluetooth_connected),
          Toast.LENGTH_SHORT).show();
        // Start the Bluetooth services
        return;
      }
      else if (mService.getState() != BluetoothService.STATE_CONNECTED)
      {
        KeyListenerInit();
        return;
      }
    }
    else
    {
      Log.d("mServie: ", String.valueOf(mService));
      KeyListenerInit();
    }
    
  }
  
  @Override
  public void onStart()
  {
    super.onStart();
    mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
    mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
    
    // Find and set up the ListView for paired devices
    ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
    pairedListView.setAdapter(mPairedDevicesArrayAdapter);
    pairedListView.setOnItemClickListener(mDeviceClickListener);
    
    // Find and set up the ListView for newly discovered devices
    ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
    newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
    newDevicesListView.setOnItemClickListener(mDeviceClickListener);
    
    // Register for broadcasts when a device is discovered
    IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    this.registerReceiver(mReceiver2, filter2);
    
    // Register for broadcasts when discovery has finished
    filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    this.registerReceiver(mReceiver2, filter2);
    if (!mBluetoothAdapter.isEnabled())
    {
      Intent enableIntent = new Intent(
        BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }
    else
    {
      Recovery();
    }
  }
  
  @Override
  public synchronized void onResume()
  {
    super.onResume();
    if (DEBUG)
      Log.e(TAG, "- ON RESUME -");
    if (mService != null)
    {
      if (mService.getState() == BluetoothService.STATE_NONE)
      {
        // Start the Bluetooth services
        mService.start();
      }
    }
    if (mBluetoothAdapter.isEnabled())
    {
      Recovery();
    }
  }
  
  @Override
  public synchronized void onPause()
  {
    super.onPause();
    if (DEBUG)
      Log.e(TAG, "- ON PAUSE -");
  }
  
  @Override
  public void onStop()
  {
    super.onStop();
    if (DEBUG)
      Log.e(TAG, "-- ON STOP --");
  }
  
  @Override
  public void onDestroy()
  {
    super.onDestroy();
    // Stop the Bluetooth services
    if (DEBUG)
      Log.e(TAG, "--- ON DESTROY ---");
    if(mBluetoothAdapter!=null)
    {
      if (mBluetoothAdapter != null) {
        mBluetoothAdapter.cancelDiscovery();
      }
      this.unregisterReceiver(mReceiver2);
    }
    
  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    if (DEBUG)
      Log.d(TAG, "onActivityResult " + resultCode);
    switch (requestCode)
    {
      case REQUEST_ENABLE_BT:
      {
        // When the request to enable Bluetooth returns
        if (resultCode == Activity.RESULT_OK)
        {
          SwitchOff();
          
        }
        else
        {
          Toast.makeText(this, this.getString(R.string.bt_not_enabled_leaving),
            Toast.LENGTH_SHORT).show();
          finish();
        }
        break;
      }
    }
  }
  
  private void KeyListenerInit()
  {
    mService = new BluetoothService(this, mHandler);
  }
  
  @SuppressLint("HandlerLeak")
  private final Handler mHandler = new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      switch (msg.what)
      {
        case MESSAGE_STATE_CHANGE:
          if (DEBUG)
            Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
          switch (msg.arg1)
          {
            case BluetoothService.STATE_CONNECTED:
              break;
            case BluetoothService.STATE_CONNECTING:
              progressDialog.setMessage(getApplicationContext().getString(R.string.connecting_message));
              progressDialog.setCancelable(false);
              progressDialog.show();
              break;
            case BluetoothService.STATE_LISTEN:
            
            case BluetoothService.STATE_NONE:
              
              break;
          }
          break;
        case MESSAGE_WRITE:
          
          break;
        case MESSAGE_READ:
          
          break;
        case MESSAGE_DEVICE_NAME:
          // save the connected device's name
          mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
          globalVariable.setDeviceName(mConnectedDeviceName);
          Toast.makeText(getApplicationContext(), getText(R.string.title_connected_to) + " " + mConnectedDeviceName,
            Toast.LENGTH_SHORT).show();
          progressDialog.dismiss();
          finish();
          break;
        case MESSAGE_TOAST:
          Toast.makeText(getApplicationContext(),
            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
            .show();
          progressDialog.dismiss();
          break;
        case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
          Toast.makeText(getApplicationContext(), getText(R.string.Connection_lost),
            Toast.LENGTH_SHORT).show();
          break;
        case MESSAGE_UNABLE_CONNECT:     //无法连接设备
          progressDialog.dismiss();
          Toast.makeText(getApplicationContext(), getText(R.string.unable_connect),
            Toast.LENGTH_SHORT).show();
          break;
      }
    }
  };
  
  public static void SendDataString(Context context, String data)
  {
    
    if (mService.getState() != BluetoothService.STATE_CONNECTED)
    {
      Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT)
        .show();
      return;
    }
    if (data.length() > 0)
    {
      try
      {
        mService.write(data.getBytes("GBK"));
      }
      catch (UnsupportedEncodingException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  
  
  public static void SendDataByte(Context context, byte[] data)
  {
    if (mService.getState() != BluetoothService.STATE_CONNECTED)
    {
      Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT)
        .show();
      return;
    }
    mService.write(data);
  }
  
  private void doDiscovery()
  {
    mNewDevicesArrayAdapter.clear();
    mNewDevicesArrayAdapter.notifyDataSetChanged();
    // If we're already discovering, stop it
    if (mBluetoothAdapter.isDiscovering())
    {
      mBluetoothAdapter.cancelDiscovery();
      mBluetoothAdapter.startDiscovery();
    }
    else
    {
      mBluetoothAdapter.startDiscovery();
    }
      
  }
  
  private final BroadcastReceiver mReceiver2 = new BroadcastReceiver()
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      String action = intent.getAction();
      progress.setVisibility(View.VISIBLE);
      // When discovery finds a device
      if (BluetoothDevice.ACTION_FOUND.equals(action))
      {
        // Get the BluetoothDevice object from the Intent
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        
        // If it's already paired, skip it, because it's been listed already
        if (device.getBondState() != BluetoothDevice.BOND_BONDED)
        {
            mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            mNewDevicesArrayAdapter.notifyDataSetChanged();
        }
        // When discovery is finished, change the Activity title
      }
      else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
      {
        progress.setVisibility(View.INVISIBLE);
        if (mNewDevicesArrayAdapter.getCount() == 0)
        {
          mNewDevicesArrayAdapter.clear();
          String noDevices = getResources().getText(R.string.none_found).toString();
          mNewDevicesArrayAdapter.add(noDevices);
          progress.setVisibility(View.INVISIBLE);
        }
      }
    }
  };
  
  private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener()
  {
    @Override
    public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
    {
      // Cancel discovery because it's costly and we're about to connect
      mBluetoothAdapter.cancelDiscovery();
      
      // Get the device MAC address, which is the last 17 chars in the View
      String info = ((TextView) v).getText().toString();
      String noDevices = getResources().getText(R.string.none_paired).toString();
      String noNewDevice = getResources().getText(R.string.none_found).toString();
      Log.i("tag", info);
      
      if (!info.equals(noDevices) && !info.equals(noNewDevice))
      {
        String address = info.substring(info.length() - 17);
        // Create the result Intent and include the MAC address
        if (BluetoothAdapter.checkBluetoothAddress(address))
        {
          BluetoothDevice device = mBluetoothAdapter
            .getRemoteDevice(address);
          // Attempt to connect to the device
          mService.start();
          mService.connect(device);
            /*Intent intent = new Intent(this, BluetoothService.class);
            bindService(intent,mConnection,Context.BIND_AUTO_CREATE);*/
        }
      }
    }
  };
  
  public void Recovery()
  {
    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    // If there are paired devices, add each one to the ArrayAdapter
    if (pairedDevices.size() > 0)
    {
      mPairedDevicesArrayAdapter.clear();
      for (BluetoothDevice device : pairedDevices)
      {
        mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
      }
    }
    else
    {
      String noDevices = getResources().getText(R.string.none_paired).toString();
      mPairedDevicesArrayAdapter.add(noDevices);
      mPairedDevicesArrayAdapter.notifyDataSetChanged();
    }
    doDiscovery();
  }
  
  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
  {
    if (btnSwitch.isChecked())
    {
      return;
    }
    else
    {
      if (!mBluetoothAdapter.isEnabled())
      {
        Toast.makeText(getApplicationContext(), getText(R.string.Connection_lost),
          Toast.LENGTH_SHORT).show();
        Intent enableIntent = new Intent(
          BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
      }
      else
      {
        if (mService.getState() == BluetoothService.STATE_CONNECTED)
        {
          AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
          builder1.setMessage(R.string.Qs_bluetooth);
          builder1.setCancelable(false);
          builder1.setPositiveButton(
            R.string.yes,
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int id)
              {
                mService.stop();
                SwitchOff();
              }
            });
  
          builder1.setNegativeButton(
            R.string.no,
            new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int id)
              {
                switchListener();
                btnSwitch.setChecked(true);
                dialog.cancel();
              }
            });
  
          AlertDialog alert11 = builder1.create();
          alert11.show();
        }
      }
    }
  }
  
  public void switchListener()
  {
    btnSwitch.setOnCheckedChangeListener(this);
  }
  
  public void SwitchOff()
  {
    KeyListenerInit();
    lnrTexInfo.setVisibility(View.GONE);
    lnrSwitch.setVisibility(View.GONE);
    lnrPaired.setVisibility(View.VISIBLE);
    lnrRefresh.setVisibility(View.VISIBLE);
    LnrBluetooth.setVisibility(View.VISIBLE);
    doDiscovery();
  }
}