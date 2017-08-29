package com.mitrakreasindo.pos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lisa on 29/08/17.
 */

public class ReportSubCategorySub implements Serializable
{

  private List<ReportSubCategorySubItem> subItems;
  private String categoryName;
  private String subCategoryName;
  private Date date;
  private double totaltax;
  private double totalTransaction;

  public List<ReportSubCategorySubItem> getSubItems()
  {
    return subItems;
  }

  public void setSubItems(List<ReportSubCategorySubItem> subItems)
  {
    this.subItems = subItems;
  }

  public String getCategoryName()
  {
    return categoryName;
  }

  public void setCategoryName(String categoryName)
  {
    this.categoryName = categoryName;
  }

  public String getSubCategoryName()
  {
    return subCategoryName;
  }

  public void setSubCategoryName(String subCategoryName)
  {
    this.subCategoryName = subCategoryName;
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
