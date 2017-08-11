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
    
    String cashier= "";
    if(IDs.getLoginUserFullname().length()<=15)
    {
      cashier = IDs.getLoginUserFullname();
    }
    else
    {
      cashier = IDs.getLoginUserFullname().substring(0, 1);
    }
    
    String strHeader = IDs.getLoginCompanyName().toUpperCase()+"\n" + IDs.getLoginCompanyAddress() +"\n" +
      "PHONE: " + IDs.getLoginCompanyPhone() +"\n\n";
    PrintDataObject printDataObject1 = new PrintDataObject(strHeader,8,true, PrintDataObject.ALIGN.CENTER);
    printDataObject1.setIsLittleSize(true);
    printDataObject1.setBold(true);
    list.add(printDataObject1);
    String separator = "------------------------------------------------";
    String tgl = String.format("%-23s",date);
    String kasir = String.format(context.getString(R.string.cashier)+" "+ cashier);
    String strDC = tgl+kasir +"\n" + separator ;
    PrintDataObject printDataObject3 = new PrintDataObject(strDC);
    printDataObject3.setIsLittleSize(true);
    printDataObject3.setBold(true);
    list.add(printDataObject3);
    
    for(int i=0;i<salesItemList.size();i++)
    {
      SalesItem tiket = salesItemList.get(i);
      String name = tiket.getProduct().getName();
      int Qty = (int) tiket.getUnits();
      int price =  tiket.getProduct().getPricesell().intValue();
      int Total = price * Qty;
  
      String ProductName;
      int Quantity;
      String Harga;
      String TotalHarga;
  
      if(name.length()<=20)
      {
        ProductName = name;
      }
      else
      {
        ProductName = name.substring(0, 20);
      }
      
      if((decimalFormat.format(price)).length()<=10)
      {
        Harga = decimalFormat.format(price).replace('.',',');
      }
      else
      {
        Harga = Integer.toString(price);
      }
  
      if(decimalFormat.format(Total).length()<=10)
      {
        TotalHarga = decimalFormat.format(Total).replace('.',',');
      }
      else
      {
        TotalHarga = Integer.toString(Total);
      }
      String nama = String.format("%-21s", ProductName);
      String quantity = String.format("%4s", Qty).replace(' ', ' ');
      String prise = String.format("%10s", Harga).replace(' ', ' ');
      String total = String.format("%10s", TotalHarga).replace(' ', ' ');
      String str = String.format(nama + " " + quantity + " " + prise + " " + total);
  
      PrintDataObject printDataObject = new PrintDataObject(str);
      printDataObject.setIsLittleSize(true);
      printDataObject.setBold(true);
      list.add(printDataObject);
    }
    String Total = String.format("%-33s",context.getString(R.string.text_total));
    String Total2 = String.format("%15s",decimalFormat.format(grandTotal).replace('.',',')).replace(' ',' ');
    
    String payment = String.format("%-33s",context.getString(R.string.text_tunai));
    String payment2 = String.format("%15s",decimalFormat.format(Cash).replace('.',',')).replace(' ',' ');
    String Change = String.format("%-33s",context.getString(R.string.text_kembali));
    String footer = context.getString(R.string.text_ucapan);
    String Change2 = String.format("%15s",decimalFormat.format(Cash-grandTotal).replace('.',',')).replace(' ',' ');
    String str2 = separator + Total + Total2 +"\n" + payment + payment2 +"\n" + Change + Change2 +"\n";
    PrintDataObject printDataObject2 = new PrintDataObject(str2);
    printDataObject2.setIsLittleSize(true);
    printDataObject2.setBold(true);
    list.add(printDataObject2);
  
    PrintDataObject printDataObject4 = new PrintDataObject(footer +"\n\n\n\n",8,false, PrintDataObject.ALIGN.CENTER);
    printDataObject4.setIsLittleSize(true);
    printDataObject4.setBold(true);
    list.add(printDataObject4);
    
  }
}
