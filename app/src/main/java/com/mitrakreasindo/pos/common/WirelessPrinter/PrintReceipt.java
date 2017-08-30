package com.mitrakreasindo.pos.common.WirelessPrinter;

import android.annotation.SuppressLint;
import android.content.Context;
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
  public static void printReceipt(Context context, List<SalesItem> ticketLinelist, double grandTotal,double Cash,String consumerName)
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
  
        String cashier,consumer = "";
        if (IDs.getLoginUserFullname().length() <= 23)
        {
          cashier = IDs.getLoginUserFullname();
        }
        else
        {
          cashier = IDs.getLoginUserFullname().substring(0, 23);
        }
  
        if (consumerName.length() <= 23)
        {
          consumer = consumerName;
        }
        else
        {
          consumer = consumerName.substring(0, 23);
        }
        if ((int) Cash == 0)
        {
          try
          {
            
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
            String tgl = String.format("%-23s", date);
            String kasir = String.format("%-16s",context.getString(R.string.cashier));
            String customer = String.format("%-16s",context.getString(R.string.consumer));
            String strDC = tgl + "\n" + kasir + ": " + cashier + "\n" + customer +": " + consumer + "\n" + "------------------------------------------\n";
            SendDataByte(context,(strDC).getBytes("GBK"));
            
            Command.ESC_Align[2] = 0x01;
            SendDataByte(context, Command.ESC_Align);
            SendDataByte(context, String.format(context.getString(R.string.text_retur)+"\n\n").getBytes("GBK"));
            
            for (int i = 0; i < ticketLinelist.size(); i++)
            {
              SalesItem tiket = ticketLinelist.get(i);
              String name = tiket.getProduct().getName();
              long Qty = (long) tiket.getUnits();
              long price = tiket.getProduct().getPricesell().longValue();
              long Total = price * Qty;
      
              String ProductName;
              if (name.length() <= 42)
              {
                ProductName = name;
              }
              else
              {
                ProductName = name.substring(0, 42);
              }
              String Harga = decimalFormat.format(price).replace('.', ',');
              String TotalHarga = decimalFormat.format(Total).replace('.', ',');
              
              String nama = String.format("%-42s", ProductName);
              String quantity = String.format("%-3s",(String.format(String.valueOf(Qty))).replace(' ', ' '));
              String prise = String.format("%16s", Harga).replace(' ', ' ');
              String total = String.format("%17s",(String.format(""+TotalHarga)).replace(' ', ' '));
              String sales = String.format(nama + "\n" + quantity +" x " + prise + " = " + total);
  
              Command.ESC_Align[2] = 0x00;
              SendDataByte(context, Command.ESC_Align);
              SendDataString(context,sales);
              
            }
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataString(context, "------------------------------------------\n");
  
            String Total = String.format("%-15s", context.getString(R.string.text_total));
            String Total2 = String.format("%27s", decimalFormat.format(grandTotal).replace('.', ',')).replace(' ', ' ');
            
            String str2 = Total + Total2 + "\n\n";
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataString(context, String.format(str2));
            
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
            String tgl = String.format("%-23s", date);
            String kasir = String.format("%-16s",context.getString(R.string.cashier));
            String customer = String.format("%-16s",context.getString(R.string.consumer));
            String strDC = tgl + "\n" + kasir + ": " + cashier + "\n" + customer +": " + consumer + "\n" + "------------------------------------------\n";
            SendDataByte(context,(strDC).getBytes("GBK"));
            
            for (int i = 0; i < ticketLinelist.size(); i++)
            {
              SalesItem tiket = ticketLinelist.get(i);
              String name = tiket.getProduct().getName();
              long Qty = (long) tiket.getUnits();
              long price = tiket.getProduct().getPricesell().longValue();
              long Total = price * Qty;
  
              String ProductName;
              if (name.length() <= 42)
              {
                ProductName = name;
              }
              else
              {
                ProductName = name.substring(0, 42);
              }
              String Harga = decimalFormat.format(price).replace('.', ',');
              String TotalHarga = decimalFormat.format(Total).replace('.', ',');
  
              String nama = String.format("%-42s", ProductName);
              String quantity = String.format("%-3s",(String.format(String.valueOf(Qty))).replace(' ', ' '));
              String prise = String.format("%16s", Harga).replace(' ', ' ');
              String total = String.format("%17s",(String.format(""+TotalHarga)).replace(' ', ' '));
              String sales = String.format(nama + "\n" + quantity +" x " + prise + " = " + total);
  
              Command.ESC_Align[2] = 0x00;
              SendDataByte(context, Command.ESC_Align);
              SendDataString(context,sales);
            }
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataString(context, "------------------------------------------\n");
            
            String Total = String.format("%-15s", context.getString(R.string.text_total));
            String Total2 = String.format("%27s", decimalFormat.format(grandTotal).replace('.', ',')).replace(' ', ' ');
  
            String payment = String.format("%-15s", context.getString(R.string.text_tunai));
            String payment2 = String.format("%27s", decimalFormat.format(Cash).replace('.', ',')).replace(' ', ' ');
            
            String Change = String.format("%-15s", context.getString(R.string.text_kembali));
            String Change2 = String.format("%27s", decimalFormat.format(Cash - grandTotal).replace('.', ',')).replace(' ', ' ');
            String str2 = Total + Total2 + "\n" + payment + payment2 + "\n" + Change + Change2 + "\n\n";
            
            Command.ESC_Align[2] = 0x00;
            SendDataByte(context, Command.ESC_Align);
            SendDataString(context, String.format(str2));
            
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
