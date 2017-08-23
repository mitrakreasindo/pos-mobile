package com.mitrakreasindo.pos.common.WirelessPrinter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.SalesItem;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mitrakreasindo.pos.common.WirelessPrinter.Wireless_Activity.SendDataByte;
import static com.mitrakreasindo.pos.common.WirelessPrinter.Wireless_Activity.SendDataString;
import static com.mitrakreasindo.pos.common.WirelessPrinter.Wireless_Activity.mService;
/**
  * Created by MK-fepriyadi on 7/31/2017.*/

 


public class PrintReceipt
{
  @SuppressLint("SimpleDateFormat")
  public static void printReceipt(Context context, List<SalesItem> ticketLinelist, double grandTotal,double Cash)
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
        Toast.makeText(context, R.string.SalesEmpty, Toast.LENGTH_SHORT)
          .show();
        return;
      }
      else if (mService.getState() == BluetoothService.STATE_CONNECTED)
      {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String date = str;
  
        if ((int) Cash == 0)
        {
          try
          {
            String cashier = "";
            if (IDs.getLoginUserFullname().length() <= 11)
            {
              cashier = IDs.getLoginUserFullname();
            }
            else
            {
              cashier = IDs.getLoginUserFullname().substring(0, 11);
            }
            SendDataByte(context, Command.ESC_Init);
            SendDataByte(context, Command.ESC_Three);
            Command.ESC_ExclamationMark[2] = 0x01;
            SendDataByte(context, Command.ESC_ExclamationMark);
    
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format(IDs.getLoginCompanyName().toUpperCase() + "\n").getBytes("GBK"));
    
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format(IDs.getLoginCompanyAddress() + "\n" + (context.getString(R.string.phone)) + IDs.getLoginCompanyPhone() + "\n\n").getBytes("GBK"));
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format((date) + " " + (context.getString(R.string.cashier)) + " " + cashier + "\n==========================================\n").getBytes("GBK"));
            for (int i = 0; i < ticketLinelist.size(); i++)
            {
              SalesItem tiket = ticketLinelist.get(i);
              Log.d("Nama product: ", (tiket.getProduct().getName()));
      
              String name = tiket.getProduct().getName();
              int Qty = (int) tiket.getUnits();
              Log.d("Jlh product: ", Integer.toString(Qty));
              int price = tiket.getProduct().getPricesell().intValue();
              Log.d("printReceipt: ", decimalFormat.format(price));
              int Total = price * Qty;
      
              String ProductName;
              int Quantity;
              String Harga;
              String TotalHarga;
      
              if (name.length() <= 14)
              {
                ProductName = name;
              }
              else
              {
                ProductName = name.substring(0, 14);
              }
              if (Integer.toString(Qty).length() <= 4)
              {
                Quantity = Qty;
              }
              else
              {
                Quantity = Integer.parseInt(Integer.toString(Qty).substring(0, 4));
              }
      
      
              if ((decimalFormat.format(price)).length() <= 10)
              {
                Harga = decimalFormat.format(price).replace('.', ',');
              }
              else
              {
                Harga = Integer.toString(price);
              }
      
              if (decimalFormat.format(Total).length() <= 10)
              {
                TotalHarga = decimalFormat.format(Total).replace('.', ',');
              }
              else
              {
                TotalHarga = Integer.toString(Total);
              }
      
              Command.ESC_Relative[2] = 0x79;
              Command.ESC_Relative[3] = 0x00;
              SendDataByte(context, Command.ESC_Relative);
      
              Command.ESC_Relative[2] = 1;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format(ProductName, "").getBytes("GBK"));
      
              Command.ESC_Relative[2] = (byte) 140;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format("%4s", Qty).replace(' ', ' ').getBytes("GBK"));
      
              Command.ESC_Relative[2] = (byte) 190;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format("%10s", Harga).replace(' ', ' ').getBytes("GBK"));
      
              Command.ESC_Relative[2] = 0x79;
              Command.ESC_Relative[2] = 0x79;
              Command.ESC_Relative[2] = 0x25;
              Command.ESC_Relative[3] = 0x01;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format("%11s", TotalHarga + "\n").replace(' ', ' ').getBytes("GBK"));
            }
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataString(context, "------------------------------------------\n");
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
    
            Command.ESC_Relative[2] = 0;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, String.format(context.getString(R.string.text_total)));
    
            Command.ESC_Relative[2] = (byte) 100;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, ":");
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x00;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context, String.format("%15s", decimalFormat.format(grandTotal).replace('.', ',') + "\n").replace(' ', ' ').getBytes("GBK"));
     /*
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
    
            Command.ESC_Relative[2] = 1;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, (context.getString(R.string.text_tunai)));
    
            Command.ESC_Relative[2] = (byte) 100;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, ":");
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x00;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context, String.format("%15s", decimalFormat.format(Cash).replace('.', ',') + "\n").replace(' ', ' ').getBytes("GBK"));
   
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
    
            Command.ESC_Relative[2] = 1;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, (context.getString(R.string.text_kembali)));
    
            Command.ESC_Relative[2] = (byte) 100;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, ":");
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x00;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context, String.format("%16s", decimalFormat.format(Cash - grandTotal).replace('.', ',') + "\n\n").replace(' ', ' ').getBytes("GBK"));
    */
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(context, Command.GS_ExclamationMark);
            SendDataString(context, (context.getString(R.string.text_ucapan)) + "\n\n");
            SendDataByte(context, PrinterCommand.POS_Set_PrtAndFeedPaper(55));
            SendDataByte(context, Command.GS_V_m_n);
          }
          catch (UnsupportedEncodingException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        else
        {
          try
          {
            String cashier = "";
            if (IDs.getLoginUserFullname().length() <= 11)
            {
              cashier = IDs.getLoginUserFullname();
            }
            else
            {
              cashier = IDs.getLoginUserFullname().substring(0, 11);
            }
            SendDataByte(context, Command.ESC_Init);
            SendDataByte(context, Command.ESC_Three);
            Command.ESC_ExclamationMark[2] = 0x01;
            SendDataByte(context, Command.ESC_ExclamationMark);
    
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format(IDs.getLoginCompanyName().toUpperCase() + "\n").getBytes("GBK"));
    
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format(IDs.getLoginCompanyAddress() + "\n" + (context.getString(R.string.phone)) + IDs.getLoginCompanyPhone() + "\n\n").getBytes("GBK"));
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format((date) + " " + (context.getString(R.string.cashier)) + " " + cashier + "\n==========================================\n").getBytes("GBK"));
            for (int i = 0; i < ticketLinelist.size(); i++)
            {
              SalesItem tiket = ticketLinelist.get(i);
              Log.d("Nama product: ", (tiket.getProduct().getName()));
      
              String name = tiket.getProduct().getName();
              int Qty = (int) tiket.getUnits();
              Log.d("Jlh product: ", Integer.toString(Qty));
              int price = tiket.getProduct().getPricesell().intValue();
              Log.d("printReceipt: ", decimalFormat.format(price));
              int Total = price * Qty;
      
              String ProductName;
              int Quantity;
              String Harga;
              String TotalHarga;
      
              if (name.length() <= 14)
              {
                ProductName = name;
              }
              else
              {
                ProductName = name.substring(0, 14);
              }
              if (Integer.toString(Qty).length() <= 4)
              {
                Quantity = Qty;
              }
              else
              {
                Quantity = Integer.parseInt(Integer.toString(Qty).substring(0, 4));
              }
      
      
              if ((decimalFormat.format(price)).length() <= 10)
              {
                Harga = decimalFormat.format(price).replace('.', ',');
              }
              else
              {
                Harga = Integer.toString(price);
              }
      
              if (decimalFormat.format(Total).length() <= 10)
              {
                TotalHarga = decimalFormat.format(Total).replace('.', ',');
              }
              else
              {
                TotalHarga = Integer.toString(Total);
              }
      
              Command.ESC_Relative[2] = 0x79;
              Command.ESC_Relative[3] = 0x00;
              SendDataByte(context, Command.ESC_Relative);
      
              Command.ESC_Relative[2] = 1;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format(ProductName, "").getBytes("GBK"));
      
              Command.ESC_Relative[2] = (byte) 140;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format("%4s", Qty).replace(' ', ' ').getBytes("GBK"));
      
              Command.ESC_Relative[2] = (byte) 190;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format("%10s", Harga).replace(' ', ' ').getBytes("GBK"));
      
              Command.ESC_Relative[2] = 0x79;
              Command.ESC_Relative[2] = 0x79;
              Command.ESC_Relative[2] = 0x25;
              Command.ESC_Relative[3] = 0x01;
              SendDataByte(context, Command.ESC_Relative);
              SendDataByte(context, String.format("%11s", TotalHarga + "\n").replace(' ', ' ').getBytes("GBK"));
            }
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataString(context, "------------------------------------------\n");
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
    
            Command.ESC_Relative[2] = 0;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, String.format(context.getString(R.string.text_total)));
    
            Command.ESC_Relative[2] = (byte) 100;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, ":");
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x00;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context, String.format("%15s", decimalFormat.format(grandTotal).replace('.', ',') + "\n").replace(' ', ' ').getBytes("GBK"));
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
    
            Command.ESC_Relative[2] = 1;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, (context.getString(R.string.text_tunai)));
    
            Command.ESC_Relative[2] = (byte) 100;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, ":");
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x00;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context, String.format("%15s", decimalFormat.format(Cash).replace('.', ',') + "\n").replace(' ', ' ').getBytes("GBK"));
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[3] = 0x00;
            SendDataByte(context, Command.ESC_Relative);
    
            Command.ESC_Relative[2] = 1;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, (context.getString(R.string.text_kembali)));
    
            Command.ESC_Relative[2] = (byte) 100;
            SendDataByte(context, Command.ESC_Relative);
            SendDataString(context, ":");
    
            Command.ESC_Relative[2] = 0x79;
            Command.ESC_Relative[2] = 0x00;
            Command.ESC_Relative[3] = 0x01;
            SendDataByte(context, Command.ESC_Relative);
            SendDataByte(context, String.format("%16s", decimalFormat.format(Cash - grandTotal).replace('.', ',') + "\n\n").replace(' ', ' ').getBytes("GBK"));
    
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            Command.GS_ExclamationMark[2] = 0x00;
            SendDataByte(context, Command.GS_ExclamationMark);
            SendDataString(context, (context.getString(R.string.text_ucapan)) + "\n\n");
            SendDataByte(context, PrinterCommand.POS_Set_PrtAndFeedPaper(55));
            SendDataByte(context, Command.GS_V_m_n);
          }
          catch (UnsupportedEncodingException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
  
        }
      }
    }
  }
  
  
}
