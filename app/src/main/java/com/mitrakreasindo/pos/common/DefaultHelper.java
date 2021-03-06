package com.mitrakreasindo.pos.common;

import java.text.DecimalFormat;
import java.text.ParseException;
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

  public static String dateDayFormat(long date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("EEEE");
    String dateString = formater.format(date);

    return dateString;
  }

  public static String dateDayFormat(Date date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("EEEE");
    String dateString = formater.format(date);

    return dateString;
  }


  public static String dateOnlyFormat(long date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formater.format(date);

    return dateString;
  }

  public static String dateOnlyFormat(Date date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formater.format(date);

    return dateString;
  }

  public static Date stringFormatToDate(String str) throws ParseException
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    Date stringDate = formater.parse(str);

    return stringDate;
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

  public static String decimalFormatToPercentage(double d)
  {
    DecimalFormat decimalFormat = new DecimalFormat(" #,##0.00 '%'");
    return decimalFormat.format(d);
  }
//  public List<SubProductReport> productReportList(List<SubProductReport> list)
//  {
//    return list;
//  }

}
