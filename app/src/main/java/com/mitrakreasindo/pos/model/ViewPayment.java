package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 28/07/17.
 */

public class ViewPayment
{
  private String id;
  private String receipt;
  private String payment;
  private Double total;
  private String transid;
  private String notes;
  private Double tendered;
  private String cardname;
  private byte[] returnmsg;
  private String siteguid;
  private Boolean sflag;
  private String datenew;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getReceipt()
  {
    return receipt;
  }

  public void setReceipt(String receipt)
  {
    this.receipt = receipt;
  }

  public String getPayment()
  {
    return payment;
  }

  public void setPayment(String payment)
  {
    this.payment = payment;
  }

  public Double getTotal()
  {
    return total;
  }

  public void setTotal(Double total)
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

  public Double getTendered()
  {
    return tendered;
  }

  public void setTendered(Double tendered)
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

  public byte[] getReturnmsg()
  {
    return returnmsg;
  }

  public void setReturnmsg(byte[] returnmsg)
  {
    this.returnmsg = returnmsg;
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

  public String getDatenew()
  {
    return datenew;
  }

  public void setDatenew(String datenew)
  {
    this.datenew = datenew;
  }
}
