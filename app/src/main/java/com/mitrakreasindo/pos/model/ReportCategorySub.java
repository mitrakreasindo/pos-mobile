package com.mitrakreasindo.pos.model;

import java.util.Date;
import java.util.List;

/**
 * Created by lisa on 25/08/17.
 */

public class ReportCategorySub
{

  private List<ReportCategorySubItem> subItems;
  private Date date;
  private double totaltax;
  private double totalTransaction;

  public List<ReportCategorySubItem> getSubItems()
  {
    return subItems;
  }

  public void setSubItems(List<ReportCategorySubItem> subItems)
  {
    this.subItems = subItems;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
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
}
