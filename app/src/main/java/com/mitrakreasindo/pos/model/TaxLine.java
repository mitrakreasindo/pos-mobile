package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class TaxLine
{

  private String id;
  private double base;
  private double amount;
  private String siteguid;
  private Boolean sflag;
  private Receipt receipt;
  private Tax taxid;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public double getBase()
  {
    return base;
  }

  public void setBase(double base)
  {
    this.base = base;
  }

  public double getAmount()
  {
    return amount;
  }

  public void setAmount(double amount)
  {
    this.amount = amount;
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

  public Tax getTaxid()
  {
    return taxid;
  }

  public void setTaxid(Tax taxid)
  {
    this.taxid = taxid;
  }
}
