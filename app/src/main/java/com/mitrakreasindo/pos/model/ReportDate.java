package com.mitrakreasindo.pos.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisa on 29/08/17.
 */

public class ReportDate<T> implements Serializable
{

  private String merchantName;
  private String merchantAddress;
  private String merchantNpwp;
  private List<ReportSubDate> subReportDate;
  private double totalTax;
  private double totalTransaction;

  public String getMerchantName()
  {
    return merchantName;
  }

  public void setMerchantName(String merchantName)
  {
    this.merchantName = merchantName;
  }

  public String getMerchantAddress()
  {
    return merchantAddress;
  }

  public void setMerchantAddress(String merchantAddress)
  {
    this.merchantAddress = merchantAddress;
  }

  public String getMerchantNpwp()
  {
    return merchantNpwp;
  }

  public void setMerchantNpwp(String merchantNpwp)
  {
    this.merchantNpwp = merchantNpwp;
  }

  public List<ReportSubDate> getSubReportDate()
  {
    return subReportDate;
  }

  public void setSubReportDate(List<ReportSubDate> subReportDate)
  {
    this.subReportDate = subReportDate;
  }

  public double getTotalTax()
  {
    return totalTax;
  }

  public void setTotalTax(double totalTax)
  {
    this.totalTax = totalTax;
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
