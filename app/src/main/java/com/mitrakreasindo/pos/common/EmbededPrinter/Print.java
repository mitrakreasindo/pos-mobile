package com.mitrakreasindo.pos.common.EmbededPrinter;

import android.content.Context;

import com.centerm.smartpos.aidl.printer.PrintDataObject;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.SalesItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by MK-fepriyadi on 8/10/2017.
 */

public class Print
{
  
  public static void IsiStruk(Context context, List<SalesItem> salesItemList,
                              List<PrintDataObject> list, double grandTotal, Double Cash,String consumerName)
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String tanggal = formatter.format(curDate);
    String date = tanggal;
    String separator = "--------------------------------";
    String cashier,consumer = "";
    
    if (IDs.getLoginUserFullname().length() <= 14)
    {
      cashier = IDs.getLoginUserFullname();
    }
    else
    {
      cashier = IDs.getLoginUserFullname().substring(0, 14);
    }
    if (consumerName.length() <= 14)
    {
      consumer = consumerName;
    }
    else
    {
      consumer = consumerName.substring(0, 14);
    }
    
    if (Cash == 0)
    {
      String strHeader = IDs.getLoginCompanyName().toUpperCase() + "\n" + IDs.getLoginCompanyAddress() + "\n" +
        (context.getString(R.string.phone)) + IDs.getLoginCompanyPhone() + "\n" + separator;
      PrintDataObject printDataObject1 = new PrintDataObject(strHeader, 8, true, PrintDataObject.ALIGN.CENTER);
      printDataObject1.setIsLittleSize(false);
      printDataObject1.setBold(false);
      list.add(printDataObject1);
  
      String tgl = String.format("%-23s", date);
      String kasir = String.format("%-16s",context.getString(R.string.cashier));
      String customer = String.format("%-16s",context.getString(R.string.consumer));
      String strDC = tgl + "\n" + kasir + ": " + cashier + "\n" + customer +": " + consumer + "\n" + separator;
      PrintDataObject printDataObject3 = new PrintDataObject(strDC);
      printDataObject3.setIsLittleSize(false);
      printDataObject3.setBold(false);
      list.add(printDataObject3);
  
      PrintDataObject printTitle = new PrintDataObject(context.getString(R.string.text_retur)+"\n");
      printTitle.setBold(true);
      printTitle.setAlign(PrintDataObject.ALIGN.CENTER);
      list.add((printTitle));
      
      for (int i = 0; i < salesItemList.size(); i++)
      {
        SalesItem tiket = salesItemList.get(i);
        String name = tiket.getProduct().getName();
        long Qty = (long) tiket.getUnits();
        long price = tiket.getProduct().getPricesell().intValue();
        long Total = price * Qty;
  
        String ProductName;
        String Harga;
        String TotalHarga;
  
        if (name.length() <= 32)
        {
          ProductName = name;
        }
        else
        {
          ProductName = name.substring(0, 32);
        }
  
        if ((decimalFormat.format(price)).length() <= 12)
        {
          Harga = decimalFormat.format(price).replace('.', ',');
        }
        else
        {
          Harga = Long.toString(price);
        }
  
        if (decimalFormat.format(Total).length() <= 12)
        {
          TotalHarga = decimalFormat.format(Total).replace('.', ',');
        }
        else
        {
          TotalHarga = Long.toString(Total);
        }
                
        String nama = String.format("%-32s", ProductName).replace(' ', ' ');
        String quantity = String.format("%-3s",(String.format(""+Qty)).replace(' ', ' '));
        String prise = String.format("%11s", Harga).replace(' ', ' ');
        String total = String.format("%12s",(String.format(""+TotalHarga)).replace(' ', ' '));
        String str = String.format(nama + "\n" + quantity +" x " + prise + " = " + total);
  
        PrintDataObject printDataObject = new PrintDataObject(str);
        printDataObject.setIsLittleSize(false);
        printDataObject.setBold(false);
        list.add(printDataObject);
      }
      String GrandTotal = decimalFormat.format(grandTotal).replace('.', ',');
      String Total = String.format("%-17s", context.getString(R.string.text_total));
      String Total2 = String.format("%15s", String.format(""+GrandTotal).replace("-","")).replace(' ', ' ');
      String footer = context.getString(R.string.text_ucapan);
      String str2 = separator + "\n" + Total + Total2 + "\n";
      PrintDataObject printDataObject2 = new PrintDataObject(str2);
      printDataObject2.setIsLittleSize(false);
      printDataObject2.setBold(false);
      list.add(printDataObject2);
  
      PrintDataObject printDataObject4 = new PrintDataObject(footer + "\n\n\n\n", 8, false, PrintDataObject.ALIGN.CENTER);
      printDataObject4.setIsLittleSize(false);
      printDataObject4.setBold(false);
      list.add(printDataObject4);
    }
    else
    {
      String strHeader = IDs.getLoginCompanyName().toUpperCase() + "\n" + IDs.getLoginCompanyAddress() + "\n" +
        (context.getString(R.string.phone)) + IDs.getLoginCompanyPhone() + "\n" + separator;
      PrintDataObject printDataObject1 = new PrintDataObject(strHeader, 8, true, PrintDataObject.ALIGN.CENTER);
      printDataObject1.setIsLittleSize(false);
      printDataObject1.setBold(false);
      list.add(printDataObject1);
  
      String tgl = String.format("%-23s", date);
      String kasir = String.format("%-16s",context.getString(R.string.cashier));
      String customer = String.format("%-16s",context.getString(R.string.consumer));
      String strDC = tgl + "\n" + kasir + ": " + cashier + "\n" + customer +": " + consumer + "\n" + separator;
      PrintDataObject printDataObject3 = new PrintDataObject(strDC);
      printDataObject3.setIsLittleSize(false);
      printDataObject3.setBold(false);
      list.add(printDataObject3);
  
      for (int i = 0; i < salesItemList.size(); i++)
      {
        SalesItem tiket = salesItemList.get(i);
        String name = tiket.getProduct().getName();
        long Qty = (long) tiket.getUnits();
        long price = tiket.getProduct().getPricesell().intValue();
        long Total = price * Qty;
    
        String ProductName;
        int Quantity;
        String Harga;
        String TotalHarga;
    
        if (name.length() <= 32)
        {
          ProductName = name;
        }
        else
        {
          ProductName = name.substring(0, 32);
        }
    
        if ((decimalFormat.format(price)).length() <= 12)
        {
          Harga = decimalFormat.format(price).replace('.', ',');
        }
        else
        {
          Harga = Long.toString(price);
        }
    
        if (decimalFormat.format(Total).length() <= 12)
        {
          TotalHarga = decimalFormat.format(Total).replace('.', ',');
        }
        else
        {
          TotalHarga = Long.toString(Total);
        }
        String nama = String.format("%-32s", ProductName).replace(' ', ' ');
        String quantity = String.format("%-3s", Qty).replace(' ', ' ');
        String prise = String.format("%11s", Harga).replace(' ', ' ');
        String total = String.format("%12s", TotalHarga).replace(' ', ' ');
        String str = String.format(nama + "\n" + quantity + " x " + prise + " = " + total);
    
        PrintDataObject printDataObject = new PrintDataObject(str);
        printDataObject.setIsLittleSize(false);
        printDataObject.setFontSize(-1);
        printDataObject.getFontSize();
        printDataObject.setBold(false);
        list.add(printDataObject);
      }
      String Total = String.format("%-17s", context.getString(R.string.text_total));
      String Total2 = String.format("%15s", decimalFormat.format(grandTotal).replace('.', ',')).replace(' ', ' ');
  
      String payment = String.format("%-17s", context.getString(R.string.text_tunai));
      String payment2 = String.format("%15s", decimalFormat.format(Cash).replace('.', ',')).replace(' ', ' ');
      String Change = String.format("%-17s", context.getString(R.string.text_kembali));
      String footer = context.getString(R.string.text_ucapan);
      String Change2 = String.format("%15s", decimalFormat.format(Cash - grandTotal).replace('.', ',')).replace(' ', ' ');
      String str2 = separator + "\n" + Total + Total2 + "\n" + payment + payment2 + "\n" + Change + Change2 + "\n";
      PrintDataObject printDataObject2 = new PrintDataObject(str2);
      printDataObject2.setIsLittleSize(false);
      printDataObject2.setBold(false);
      list.add(printDataObject2);
  
      PrintDataObject printDataObject4 = new PrintDataObject(footer + "\n\n\n\n", 8, false, PrintDataObject.ALIGN.CENTER);
      printDataObject4.setIsLittleSize(false);
      printDataObject4.setBold(false);
      list.add(printDataObject4);
  
    }
  }
}
