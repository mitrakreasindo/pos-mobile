package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 28/07/17.
 */

public class Viewtaxlines
{

  private String id;
  private String receipt;
  private String taxid;
  private Double base;
  private Double amount;
  private String siteguid;
  private Boolean sflag;
  private String taxName;

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

  public String getTaxid()
  {
    return taxid;
  }

  public void setTaxid(String taxid)
  {
    this.taxid = taxid;
  }

  public Double getBase()
  {
    return base;
  }

  public void setBase(Double base)
  {
    this.base = base;
  }

  public Double getAmount()
  {
    return amount;
  }

  public void setAmount(Double amount)
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

  public String getTaxName()
  {
    return taxName;
  }

  public void setTaxName(String taxName)
  {
    this.taxName = taxName;
  }
}
