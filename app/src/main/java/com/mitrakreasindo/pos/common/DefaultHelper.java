package com.mitrakreasindo.pos.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lisa on 09/08/17.
 */

public class DefaultHelper
{

  public static String dateFormat(Date date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = formater.format(date);

    return dateString;
  }

  public String dateOnlyFormat(long date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formater.format(date);

    return dateString;
  }

  public String dateOnlyFormat(Date date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formater.format(date);

    return dateString;
  }

  public static String dateFormat(long date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = formater.format(date);

    return dateString;
  }

  public static String decimalFormat(double d)
  {
    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    return decimalFormat.format(d);
  }

  public String decimalFormatToPercentage(double d)
  {
    DecimalFormat decimalFormat = new DecimalFormat(" #,##0.00 '%'");
    return decimalFormat.format(d);
  }
//  public List<SubProductReport> productReportList(List<SubProductReport> list)
//  {
//    return list;
//  }

}
