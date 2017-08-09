package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 28/07/17.
 */

public class ViewSale
{

  private String id;
  private int salestype;
  private int salesnum;
  private String person;
  private String customer;
  private int status;
  private String siteguid;
  private Boolean sflag;
  private String customerName;
  private String personName;
  private String datenew;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public Integer getSalestype()
  {
    return salestype;
  }

  public void setSalestype(Integer salestype)
  {
    this.salestype = salestype;
  }

  public Integer getSalesnum()
  {
    return salesnum;
  }

  public void setSalesnum(Integer salesnum)
  {
    this.salesnum = salesnum;
  }

  public String getPerson()
  {
    return person;
  }

  public void setPerson(String person)
  {
    this.person = person;
  }

  public String getCustomer()
  {
    return customer;
  }

  public void setCustomer(String customer)
  {
    this.customer = customer;
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

  public String getCustomerName()
  {
    return customerName;
  }

  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }

  public String getPersonName()
  {
    return personName;
  }

  public void setPersonName(String personName)
  {
    this.personName = personName;
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
