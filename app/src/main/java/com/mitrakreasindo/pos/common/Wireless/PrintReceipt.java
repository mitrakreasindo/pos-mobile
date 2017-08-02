package com.mitrakreasindo.pos.common.Wireless;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.SalesItem;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mitrakreasindo.pos.common.Wireless.Wireless_Activity.SendDataByte;
import static com.mitrakreasindo.pos.common.Wireless.Wireless_Activity.SendDataString;
import static com.mitrakreasindo.pos.common.Wireless.Wireless_Activity.mService;

/**
 * Created by MK-fepriyadi on 7/31/2017.
 */

public class PrintReceipt
{
  @SuppressLint("SimpleDateFormat")
  public static void printReceipt(Context context, List<SalesItem> ticketLinelist)
  {
    if (mService == null) {
      Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT)
        .show();
      return;
    }
    else
    {
      if (mService.getState()!= BluetoothService.STATE_CONNECTED)
      {
        Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT)
          .show();
        return;
      }
      else if (ticketLinelist.size()==0)
      {
        Toast.makeText(context, "Sales is Empty", Toast.LENGTH_SHORT)
          .show();
        return;
      }
      else if (mService.getState() == BluetoothService.STATE_CONNECTED) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String date = str;
  
        try
        {
          SendDataByte(context, Command.ESC_Init);
          SendDataByte(context, Command.ESC_Three);
          Command.ESC_ExclamationMark[2] = 0x01;
          SendDataByte(context, Command.ESC_ExclamationMark);
    
      /*Command.ESC_Align[2] = 0x01;
      SendDataByte(context,Command.ESC_Align);
      SendDataByte(context,"PT. MITRA KREASINDO\n".getBytes("GBK"));
    
      Command.ESC_Align[2] = 0x01;
      SendDataByte(context,Command.ESC_Align);
      SendDataByte(context,"Jl. Balikpapan No.31 Petojo Gambir, Jakarta Pusat 10160\nPHONE: +622129607000\n\n".getBytes("GBK"));
      Command.ESC_Align[2] = 0x00;
      SendDataByte(context,Command.ESC_Align);
      SendDataString(context,(date) + "\n==========================================\n");*/
    
          for(int i=0;i<ticketLinelist.size();i++)
          {
            SalesItem tiket = ticketLinelist.get(i);
            Log.d("Nama product: ", (tiket.getProduct().getName()));
            
            String name = tiket.getProduct().getName();
            int Qty = (int) tiket.getUnits();
            Log.d("Jlh product: ", Double.toString(tiket.getUnits()));
            int Prize =  (int) tiket.getPrice();
            int Total = Prize * Qty;
            
            String ProductName;
            int Quantity;
            int Harga;
            int TotalHarga;
  
            if(name.length()<=14)
            {
              ProductName = name;
            }
            else
            {
              ProductName = name.substring(0, 14);
            }
            /*if(Integer.toString(Qty).length()<=4)
            {
              Quantity = Qty;
            }
            else
            {
              Quantity = Integer.parseInt(Integer.toString(Qty).substring(0,4));
            }
            
            if(decimalFormat.format(Prize).length()<=10)
            {
              Harga = Prize;
            }
            else
            {
              Harga = Integer.parseInt(Integer.toString(Prize).substring(0,10));
            }
  
            if(decimalFormat.format(Total).length()<=10)
            {
              TotalHarga = Harga;
            }
            else
            {
              TotalHarga = Integer.parseInt(Integer.toString(Total).substring(0,9));
            }*/
            
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
      
            Command.ESC_Relative[2] = 1;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context,String.format(ProductName, "").getBytes("GBK"));
      
            Command.ESC_Relative[2] = (byte) 140;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context,String.format("%4s" , decimalFormat.format(Qty)).replace(' ',' ').getBytes("GBK"));
      
            Command.ESC_Relative[2] = (byte) 190;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context,String.format("%10s" ,decimalFormat.format(Prize)).replace(' ',' ') .getBytes("GBK"));
      
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x25;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context,String.format("%11s", decimalFormat.format(Total) + "\n").replace(' ',' ').getBytes("GBK"));
          }
      Command.ESC_Align[2] = 0x00;
      SendDataByte(context, Command.ESC_Align);
      SendDataString(context,"------------------------------------------\n");
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(context, Command.ESC_Relative);
    
      Command.ESC_Relative[2] = 0;
      SendDataByte(context, Command.ESC_Relative);
      SendDataByte(context,String.format("TOTAL   :","").getBytes("GBK"));
    
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x25;
      Command.ESC_Relative[3] = 0x01;
      SendDataByte(context, Command.ESC_Relative);
      SendDataByte(context,String.format("%11s",decimalFormat.format(90000000)+"\n").replace(' ',' ').getBytes("GBK"));
    
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(context, Command.ESC_Relative);
    
      Command.ESC_Relative[2] = 0;
      SendDataByte(context, Command.ESC_Relative);
      SendDataByte(context,String.format("TUNAI   :","").getBytes("GBK"));
    
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x25;
      Command.ESC_Relative[3] = 0x01;
      SendDataByte(context, Command.ESC_Relative);
      SendDataByte(context,String.format("%11s",decimalFormat.format(900000)+"\n").replace(' ',' ').getBytes("GBK"));
    
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[3] = 0x00;
      SendDataByte(context, Command.ESC_Relative);
    
      Command.ESC_Relative[2] = 1;
      SendDataByte(context, Command.ESC_Relative);
      SendDataByte(context,String.format("KEMBALI :","").getBytes("GBK"));
    
      Command.ESC_Relative[2] = 0x79;
      Command.ESC_Relative[2] = 0x25;
      Command.ESC_Relative[3] = 0x01;
      SendDataByte(context, Command.ESC_Relative);
      SendDataByte(context,String.format("%11s",decimalFormat.format(9000000)+"\n").replace(' ',' ').getBytes("GBK"));
    
      Command.ESC_Align[2] = 0x01;
      SendDataByte(context, Command.ESC_Align);
      Command.GS_ExclamationMark[2] = 0x00;
      SendDataByte(context, Command.GS_ExclamationMark);
      SendDataByte(context,"\nTERIMA KASIH DAN SELAMAT BELANJA KEMBALI\n\n".getBytes("GBK"));
        } catch (UnsupportedEncodingException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
  
      }
      
    }
    
  }
}
