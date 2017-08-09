package com.mitrakreasindo.pos.common;

import com.mitrakreasindo.pos.model.SubProductReport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lisa on 09/08/17.
 */

public class DefaultHelper
{

  public String dateFormat(Date date)
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

  public String dateFormat(long date)
  {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = formater.format(date);

    return dateString;
  }

  public String decimalFormat(double d)
  {
    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    return decimalFormat.format(d);
  }

//  public List<SubProductReport> productReportList(List<SubProductReport> list)
//  {
//    return list;
//  }

}
