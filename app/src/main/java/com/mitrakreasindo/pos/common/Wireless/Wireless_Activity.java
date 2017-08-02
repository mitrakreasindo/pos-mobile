package com.mitrakreasindo.pos.common.Wireless;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;
import com.mitrakreasindo.pos.model.Print;

import java.io.UnsupportedEncodingException;

public class Wireless_Activity extends Activity implements View.OnClickListener
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
/*********************************************************************************/
	private TextView mTitle;
	EditText editText;
	ImageView imageViewPicture;
	private Button sendButton = null;
	private Button testButton = null;
	private Button printbmpButton = null;
	private Button btnScanButton = null;
	private Button btnClose = null;
	private Button btn_BMP = null;
	private Button btn_ChoseCommand = null;
	private Button btn_prtsma = null;
	private Button btn_prttableButton = null;
	private Button btn_prtcodeButton = null;
	private Button btn_scqrcode = null;
	private Button btn_camer = null;
  private SalesListAdapter salesListAdapter;
  private Print print;

/******************************************************************************************************/
	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the services
	public static BluetoothService mService;
/***************************   指                 令****************************************************************/
/******************************************************************************************************/
	@Override
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  if (DEBUG)
    Log.e(TAG, "+++ ON CREATE +++");
    setContentView(R.layout.wireless);
    // Get local Bluetooth adapter
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    
    // If the adapter is null, then Bluetooth is not supported
  if (mBluetoothAdapter == null) {
    Toast.makeText(this, "Bluetooth is not available",
      Toast.LENGTH_LONG).show();
    finish();
  }
  
  if(mService!=null)
  {
    if (mService.getState() == BluetoothService.STATE_CONNECTED)
    {
      KeyListenerDis();
      btnScanButton.setEnabled(false);
      btnClose.setEnabled(true);
      
      btnClose.setOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View v)
        {
          mService.stop();
          KeyListenerInit();
          Toast.makeText(getApplicationContext(),"disconnected",Toast.LENGTH_SHORT).show();
        }
      });
      // Start the Bluetooth services
      Toast.makeText(this, "Bluetooth is Connected",
        Toast.LENGTH_SHORT).show();
      return;
    }
    else if (mService.getState() != BluetoothService.STATE_CONNECTED)
    {
      KeyListenerInit();
      return;
    }
  }
}
  
  @Override
  public void onStart()
  {
    super.onStart();
    if (DEBUG)
      Log.e(TAG, "- ON START -");
    if (!mBluetoothAdapter.isEnabled())
    {
      Intent enableIntent = new Intent(
        BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
      // Otherwise, setup the session
    }
    else
    {
      if (mService == null)
      {
        Toast.makeText(this, "mService Null",
          Toast.LENGTH_SHORT).show();
        KeyListenerInit();
      }
    }
  }
  @Override
  public synchronized void onResume() {
    super.onResume();
    if (DEBUG)
      Log.e(TAG, "- ON RESUME -");
    
  }
  
  @Override
  public synchronized void onPause() {
    super.onPause();
    if (DEBUG)
      Log.e(TAG, "- ON PAUSE -");
  }
  
  @Override
  public void onStop() {
    super.onStop();
    if (DEBUG)
      Log.e(TAG, "-- ON STOP --");
  }
  
  @Override
  public void onDestroy() {
    super.onDestroy();
    // Stop the Bluetooth services
      if (DEBUG)
        Log.e(TAG, "--- ON DESTROY ---");
    
  }
  
  /*****************************************************************************************************/
  private void KeyListenerDis() {
  
    btnScanButton = (Button)findViewById(R.id.button_scan);
    btnScanButton.setOnClickListener(this);
  
    btnClose = (Button)findViewById(R.id.btn_close);
    btnClose.setOnClickListener(this);
    
    /*mService = new BluetoothService(this, mHandler);*/
  }
  private void KeyListenerInit() {
    
    btnScanButton = (Button)findViewById(R.id.button_scan);
    btnScanButton.setOnClickListener(this);
    
    btnClose = (Button)findViewById(R.id.btn_close);
    btnClose.setOnClickListener(this);
    
    btnClose.setEnabled(false);
    mService = new BluetoothService(this, mHandler);
  }
  @Override
  public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {
      case R.id.button_scan:{
        Intent serverIntent = new Intent(Wireless_Activity.this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
        break;
      }
      case R.id.btn_close:{
        /*unbindService(mConnection);*/
        mService.stop();
        btnClose.setEnabled(false);
        btnScanButton.setEnabled(true);
        btnScanButton.setText(getText(R.string.connect));
        break;
      }
      /*case R.id.btn_prtsma:
      {
      *//*  Intent intent = getIntent();
        Print print = intent.getParcelableExtra("ticketLines");
        print.getTicketLines().size();
        Printer.printReceipt(this,print.getTicketLines());*//*
        *//*Print_Ex();*//*
        break;
      }*/
      default:
        break;
    }
  }
  /*****************************************************************************************************/
	/*
	 * SendDataString
	 */
  public static void SendDataString(Context context, String data) {
    
    if (mService.getState() != BluetoothService.STATE_CONNECTED) {
      Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT)
        .show();
      return;
    }
    if (data.length() > 0) {
      try {
        mService.write(data.getBytes("GBK"));
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  
  /*
   *SendDataByte
   */
  public static void SendDataByte(Context context, byte[] data) {
    if (mService.getState() != BluetoothService.STATE_CONNECTED) {
      Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT)
        .show();
      return;
    }
    mService.write(data);
  }
  
  /****************************************************************************************************/
  @SuppressLint("HandlerLeak")
  private final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MESSAGE_STATE_CHANGE:
          if (DEBUG)
            Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
          switch (msg.arg1) {
            case BluetoothService.STATE_CONNECTED:
					/*mTitle.setText(R.string.title_connected_to);
					mTitle.append(mConnectedDeviceName);*/
              btnScanButton.setText(getText(R.string.Connecting));
              btnScanButton.setEnabled(false);
              /*imageViewPicture.setEnabled(true);*/
              btnClose.setEnabled(true);
             /* btn_prtsma.setEnabled(true);*/
              break;
            case BluetoothService.STATE_CONNECTING:
				/*	mTitle.setText(R.string.title_connecting);*/
              break;
            case BluetoothService.STATE_LISTEN:
            case BluetoothService.STATE_NONE:
					/*mTitle.setText(R.string.title_not_connected);*/
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
          Toast.makeText(getApplicationContext(),
            "Connected to " + mConnectedDeviceName,
            Toast.LENGTH_SHORT).show();
          break;
        case MESSAGE_TOAST:
          Toast.makeText(getApplicationContext(),
            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
            .show();
          break;
        case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
          Toast.makeText(getApplicationContext(), "Device connection was lost",
            Toast.LENGTH_SHORT).show();
         /* editText.setEnabled(false);*/
          /*imageViewPicture.setEnabled(false);*/
          btnClose.setEnabled(false);
          /*btn_prtsma.setEnabled(false);*/
          break;
        case MESSAGE_UNABLE_CONNECT:     //无法连接设备
          Toast.makeText(getApplicationContext(), "Unable to connect device",
            Toast.LENGTH_SHORT).show();
          break;
      }
    }
  };
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (DEBUG)
      Log.d(TAG, "onActivityResult " + resultCode);
    switch (requestCode) {
      case REQUEST_CONNECT_DEVICE:{
        // When DeviceListActivity returns with a device to connect
        if (resultCode == Activity.RESULT_OK) {
          // Get the device MAC address
          String address = data.getExtras().getString(
            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
          // Get the BLuetoothDevice object
          if (BluetoothAdapter.checkBluetoothAddress(address)) {
            BluetoothDevice device = mBluetoothAdapter
              .getRemoteDevice(address);
            // Attempt to connect to the device
            mService.connect(device);
            /*Intent intent = new Intent(this, BluetoothService.class);
            bindService(intent,mConnection,Context.BIND_AUTO_CREATE);*/
          }
        }
        break;
      }
      case REQUEST_ENABLE_BT:{
        // When the request to enable Bluetooth returns
        if (resultCode == Activity.RESULT_OK) {
          // Bluetooth is now enabled, so set up a session
          btnClose.setEnabled(false);
        } else {
          // User did not enable Bluetooth or an error occured
          Log.d(TAG, "BT not enabled");
          Toast.makeText(this, R.string.bt_not_enabled_leaving,
            Toast.LENGTH_SHORT).show();
          finish();
        }
        break;
      }
    }
  }
  
  private ServiceConnection mConnection = new ServiceConnection()
  {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
      BluetoothService.Localservice localservice = (BluetoothService.Localservice)service;
      mService = localservice.getservice();
    }
  
    @Override
    public void onServiceDisconnected(ComponentName name)
    {
    
    }
  };
  /*@SuppressLint("SimpleDateFormat")
  private void Print_Ex()
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String str = formatter.format(curDate);
    String date = str;
    
    Intent intent = getIntent();
    Print print = intent.getParcelableExtra("ticketLines");
    print.getTicketLines().size();
    Log.d("Print_Ex: ", Integer.toString(print.getTicketLines().size()));
    try
    {
    SendDataByte(Command.ESC_Init);
    SendDataByte(Command.ESC_Three);
    Command.ESC_ExclamationMark[2] = 0x01;
    SendDataByte(Command.ESC_ExclamationMark);
      
    Command.ESC_Align[2] = 0x01;
    SendDataByte(Command.ESC_Align);
    SendDataByte("PT. MITRA KREASINDO\n".getBytes("GBK"));

    Command.ESC_Align[2] = 0x01;
    SendDataByte(Command.ESC_Align);
    SendDataByte("Jl. Balikpapan No.31 Petojo Gambir, Jakarta Pusat 10160\nPHONE: +622129607000\n\n".getBytes("GBK"));

    Command.ESC_Align[2] = 0x00;
    SendDataByte(Command.ESC_Align);
    SendDataString((date) + "\n==========================================\n");
   
    for(int i=0;i<print.getTicketLines().size();i++)
    {
      TicketLine tiket = print.getTicketLines().get(i);
      Log.d("Nama product: ", (tiket.getProduct().getName()));
      String name = tiket.getProduct().getName();
      int Qty = (int) tiket.getUnits();
      int Prize = (int) tiket.getPrice();
      int Total = Prize * Qty;
  
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(Command.ESC_Relative);

      Command.ESC_Relative[2] = 1;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format(name, "").getBytes("GBK"));
      
      Command.ESC_Relative[2] = (byte) 170;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("%1s" + Qty, "").getBytes("GBK"));

      Command.ESC_Relative[2] = (byte) 220;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("%1s" + Prize, "").getBytes("GBK"));

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x30;
      Command.ESC_Relative[3] = 0x01;
      Command.ESC_Relative[2] = (byte) 255;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("%s" + Total + "\n", "").getBytes("GBK"));
    }
      Command.ESC_Align[2] = 0x00;
      SendDataByte(Command.ESC_Align);
      SendDataString("------------------------------------------\n");

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(Command.ESC_Relative);

      Command.ESC_Relative[2] = 0;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("TOTAL   :","").getBytes("GBK"));

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x20;
      Command.ESC_Relative[3] = 0x01;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("%s"+"90000000\n","").getBytes("GBK"));

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(Command.ESC_Relative);

      Command.ESC_Relative[2] = 0;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("TUNAI   :","").getBytes("GBK"));

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x20;
      Command.ESC_Relative[3] = 0x01;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("%s"+"9000\n","").getBytes("GBK"));

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(Command.ESC_Relative);

      Command.ESC_Relative[2] = 1;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("KEMBALI :","").getBytes("GBK"));

      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x20;
      Command.ESC_Relative[3] = 0x01;
      SendDataByte(Command.ESC_Relative);
      SendDataByte(String.format("%s"+"9000000\n","").getBytes("GBK"));

      Command.ESC_Align[2] = 0x01;
      SendDataByte(Command.ESC_Align);
      Command.GS_ExclamationMark[2] = 0x00;
      SendDataByte(Command.GS_ExclamationMark);
      SendDataByte("\nTERIMA KASIH DAN SELAMAT BELANJA KEMBALI\n\n".getBytes("GBK"));
      
  } catch (UnsupportedEncodingException e)
  {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  }*/
  private void cekdatakirim()
  {
    Intent intent = getIntent();
    Print print = intent.getParcelableExtra("ticketLines");
    print.getTicketLines().size();
    Log.d("cekdatakirim: ", Integer.toString(print.getTicketLines().size()));
  }
  
}