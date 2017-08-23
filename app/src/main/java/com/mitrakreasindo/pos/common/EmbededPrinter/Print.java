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
                              List<PrintDataObject> list, double grandTotal, Double Cash)
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss ");
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String tanggal = formatter.format(curDate);
    String date = tanggal;
    String separator = "--------------------------------";
    String cashier = "";
    if (Cash == 0)
    {
      if (IDs.getLoginUserFullname().length() <= 20)
      {
        cashier = IDs.getLoginUserFullname();
      }
      else
      {
        cashier = IDs.getLoginUserFullname().substring(0, 20);
      }
  
      String strHeader = IDs.getLoginCompanyName().toUpperCase() + "\n" + IDs.getLoginCompanyAddress() + "\n" +
        (context.getString(R.string.phone)) + IDs.getLoginCompanyPhone() + "\n" + separator;
      PrintDataObject printDataObject1 = new PrintDataObject(strHeader, 8, true, PrintDataObject.ALIGN.CENTER);
      printDataObject1.setIsLittleSize(false);
      printDataObject1.setBold(false);
      list.add(printDataObject1);
  
      String tgl = String.format("%-23s", date);
      String kasir = String.format(context.getString(R.string.cashier) + " " + cashier);
      String strDC = tgl + "\n" + kasir + "\n" + separator;
      PrintDataObject printDataObject3 = new PrintDataObject(strDC);
      printDataObject3.setIsLittleSize(false);
      printDataObject3.setBold(false);
      list.add(printDataObject3);
  
      for (int i = 0; i < salesItemList.size(); i++)
      {
        SalesItem tiket = salesItemList.get(i);
        String name = tiket.getProduct().getName();
        int Qty = (int) tiket.getUnits();
        int price = tiket.getProduct().getPricesell().intValue();
        int Total = price * Qty;
  
        String ProductName;
        int Quantity;
        String Harga;
        String TotalHarga;
  
        if (name.length() <= 30)
        {
          ProductName = name;
        }
        else
        {
          ProductName = name.substring(0, 30);
        }
  
        if ((decimalFormat.format(price)).length() <= 12)
        {
          Harga = decimalFormat.format(price).replace('.', ',');
        }
        else
        {
          Harga = Integer.toString(price);
        }
  
        if (decimalFormat.format(Total).length() <= 12)
        {
          TotalHarga = decimalFormat.format(Total).replace('.', ',');
        }
        else
        {
          TotalHarga = Integer.toString(Total);
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
      if (IDs.getLoginUserFullname().length() <= 20)
      {
        cashier = IDs.getLoginUserFullname();
      }
      else
      {
        cashier = IDs.getLoginUserFullname().substring(0, 20);
      }
  
      String strHeader = IDs.getLoginCompanyName().toUpperCase() + "\n" + IDs.getLoginCompanyAddress() + "\n" +
        (context.getString(R.string.phone)) + IDs.getLoginCompanyPhone() + "\n" + separator;
      PrintDataObject printDataObject1 = new PrintDataObject(strHeader, 8, true, PrintDataObject.ALIGN.CENTER);
      printDataObject1.setIsLittleSize(false);
      printDataObject1.setBold(false);
      list.add(printDataObject1);
  
      String tgl = String.format("%-23s", date);
      String kasir = String.format(context.getString(R.string.cashier) + " " + cashier);
      String strDC = tgl + "\n" + kasir + "\n" + separator;
      PrintDataObject printDataObject3 = new PrintDataObject(strDC);
      printDataObject3.setIsLittleSize(false);
      printDataObject3.setBold(false);
      list.add(printDataObject3);
  
      for (int i = 0; i < salesItemList.size(); i++)
      {
        SalesItem tiket = salesItemList.get(i);
        String name = tiket.getProduct().getName();
        int Qty = (int) tiket.getUnits();
        int price = tiket.getProduct().getPricesell().intValue();
        int Total = price * Qty;
    
        String ProductName;
        int Quantity;
        String Harga;
        String TotalHarga;
    
        if (name.length() <= 30)
        {
          ProductName = name;
        }
        else
        {
          ProductName = name.substring(0, 30);
        }
    
        if ((decimalFormat.format(price)).length() <= 12)
        {
          Harga = decimalFormat.format(price).replace('.', ',');
        }
        else
        {
          Harga = Integer.toString(price);
        }
    
        if (decimalFormat.format(Total).length() <= 12)
        {
          TotalHarga = decimalFormat.format(Total).replace('.', ',');
        }
        else
        {
          TotalHarga = Integer.toString(Total);
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
