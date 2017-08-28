package com.mitrakreasindo.pos.model;

import java.util.List;

/**
 * Created by lisa on 25/08/17.
 */

public class ReportCategory
{

  private String merchantName;
  private String merchantAddress;
  private String merchantNpwp;
  private List<ReportCategorySub> subReports;
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

  public List<ReportCategorySub> getSubReports()
  {
    return subReports;
  }

  public void setSubReports(List<ReportCategorySub> subReports)
  {
    this.subReports = subReports;
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
