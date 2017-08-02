package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class Sales
{

  private String id;
  private int salesnum;
  private int salestype;
  private int status;
  private String siteguid;
  private Boolean sflag;
  private Customer customer;
  private People person;
  private Receipt receipt;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public int getStatus()
  {
    return status;
  }

  public void setStatus(int status)
  {
    this.status = status;
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

  public Customer getCustomer()
  {
    return customer;
  }

  public void setCustomer(Customer customer)
  {
    this.customer = customer;
  }

  public People getPerson()
  {
    return person;
  }

  public void setPerson(People person)
  {
    this.person = person;
  }

  public int getSalesnum()
  {
    return salesnum;
  }

  public void setSalesnum(int salesnum)
  {
    this.salesnum = salesnum;
  }

  public int getSalestype()
  {
    return salestype;
  }

  public void setSalestype(int salestype)
  {
    this.salestype = salestype;
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
