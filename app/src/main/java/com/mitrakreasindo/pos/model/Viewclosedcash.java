package com.mitrakreasindo.pos.model;

import java.util.Date;

/**
 * Created by hendric on 2017-08-07.
 */

public class Viewclosedcash
{
  private String receiptId;
  private String money;
  private Double totalAllPayment;
  private Double totalCashPayment;
  private Double totalSoldUnits;
  private Date dateStart;
  private String host;

  public String getReceiptId()
  {
    return receiptId;
  }

  public void setReceiptId(String receiptId)
  {
    this.receiptId = receiptId;
  }

  public String getMoney()
  {
    return money;
  }

  public void setMoney(String money)
  {
    this.money = money;
  }

  public Double getTotalAllPayment()
  {
    return totalAllPayment;
  }

  public void setTotalAllPayment(Double totalAllPayment)
  {
    this.totalAllPayment = totalAllPayment;
  }

  public Double getTotalCashPayment()
  {
    return totalCashPayment;
  }

  public void setTotalCashPayment(Double totalCashPayment)
  {
    this.totalCashPayment = totalCashPayment;
  }

  public Double getTotalSoldUnits()
  {
    return totalSoldUnits;
  }

  public void setTotalSoldUnits(Double totalSoldUnits)
  {
    this.totalSoldUnits = totalSoldUnits;
  }

  public Date getDateStart()
  {
    return dateStart;
  }

  public void setDateStart(Date dateStart)
  {
    this.dateStart = dateStart;
  }

  public String getHost()
  {
    return host;
  }

  public void setHost(String host)
  {
    this.host = host;
  }
}
