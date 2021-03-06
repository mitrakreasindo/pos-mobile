package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class Payment
{

  private byte[] returnmsg;
  private String id;
  private String payment;
  private double total;
  private String transid;
  private String notes;
  private double tendered;
  private String cardname;
  private String siteguid;
  private Boolean sflag;
  private Receipt receipt;

  public byte[] getReturnmsg()
  {
    return returnmsg;
  }

  public void setReturnmsg(byte[] returnmsg)
  {
    this.returnmsg = returnmsg;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getPayment()
  {
    return payment;
  }

  public void setPayment(String payment)
  {
    this.payment = payment;
  }

  public double getTotal()
  {
    return total;
  }

  public void setTotal(double total)
  {
    this.total = total;
  }

  public String getTransid()
  {
    return transid;
  }

  public void setTransid(String transid)
  {
    this.transid = transid;
  }

  public String getNotes()
  {
    return notes;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  }

  public double getTendered()
  {
    return tendered;
  }

  public void setTendered(double tendered)
  {
    this.tendered = tendered;
  }

  public String getCardname()
  {
    return cardname;
  }

  public void setCardname(String cardname)
  {
    this.cardname = cardname;
  }

  public String getSiteguid()
  {
    return siteguid;
  }

  public void setSiteguid(String siteguid)
  {
    this.siteguid = siteguid;
  }

  public Boolean getSflag()
  {
    return sflag;
  }

  public void setSflag(Boolean sflag)
  {
    this.sflag = sflag;
  }

  public Receipt getReceipt()
  {
    return receipt;
  }

  public void setReceipt(Receipt receipt)
  {
    this.receipt = receipt;
  }
}
