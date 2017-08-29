package com.mitrakreasindo.pos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lisa on 07/08/17.
 */

public class ReportSalesSub implements Serializable
{

  private List<ReportSalesSubItem> subItems;
  private String peopleName;
  private double totaltax;
  private double totalTransaction;
  private Date date;

  public List<ReportSalesSubItem> getSubItems()
  {
    return subItems;
  }

  public void setSubItems(List<ReportSalesSubItem> subItems)
  {
    this.subItems = subItems;
  }

  public String getPeopleName()
  {
    return peopleName;
  }

  public void setPeopleName(String peopleName)
  {
    this.peopleName = peopleName;
  }

  public double getTotaltax()
  {
    return totaltax;
  }

  public void setTotaltax(double totaltax)
  {
    this.totaltax = totaltax;
  }

  public double getTotalTransaction()
  {
    return totalTransaction;
  }

  public void setTotalTransaction(double totalTransaction)
  {
    this.totalTransaction = totalTransaction;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public static List<ReportSalesSub> data()
  {
    List<ReportSalesSub> subReports = new ArrayList<>();

    ReportSalesSub subReport = new ReportSalesSub();
    subReport.setPeopleName("YArrooo");
    subReport.setDate(new Date());
    subReport.setTotaltax(150);
    subReport.setTotalTransaction(10000000);
    subReports.add(subReport);

    ReportSalesSub subReport2 = new ReportSalesSub();
    subReport2.setPeopleName("YArrooo");
    subReport2.setDate(new Date());
    subReport2.setTotaltax(150);
    subReport2.setTotalTransaction(10000000);
    subReports.add(subReport2);

    return subReports;
  }
}
