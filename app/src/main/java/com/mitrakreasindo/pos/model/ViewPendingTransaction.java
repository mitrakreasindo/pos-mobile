package com.mitrakreasindo.pos.model;

/**
 * Created by hendric on 2017-08-14.
 */

public class ViewPendingTransaction
{
  private String customer;
  private String host;
  private String receiptId;
  private Double receiptTotal;
  private String transactionTime;

  public String getCustomer()
  {
    return customer;
  }

  public void setCustomer(String customer)
  {
    this.customer = customer;
  }

  public String getHost()
  {
    return host;
  }

  public void setHost(String host)
  {
    this.host = host;
  }

  public String getReceiptId()
  {
    return receiptId;
  }

  public void setReceiptId(String receiptId)
  {
    this.receiptId = receiptId;
  }

  public Double getReceiptTotal()
  {
    return receiptTotal;
  }

  public void setReceiptTotal(Double receiptTotal)
  {
    this.receiptTotal = receiptTotal;
  }

  public String getTransactionTime()
  {
    return transactionTime;
  }

  public void setTransactionTime(String transactionTime)
  {
    this.transactionTime = transactionTime;
  }
}
