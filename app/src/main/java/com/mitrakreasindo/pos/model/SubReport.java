package com.mitrakreasindo.pos.model;

import java.util.List;

/**
 * Created by lisa on 07/08/17.
 */

public class SubReport
{

  private List<SubProductReport> subProductReports;
  private String peopleName;
  private double totaltax;
  private double totalTransaction;

  public List<SubProductReport> getSubProductReports()
  {
    return subProductReports;
  }

  public void setSubProductReports(List<SubProductReport> subProductReports)
  {
    this.subProductReports = subProductReports;
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
}
