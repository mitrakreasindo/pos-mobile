package com.mitrakreasindo.pos.model;

import java.util.Date;

/**
 * Created by lisa on 22/06/17.
 */

public class Receipt
{
  private byte[] attributes;
  private Sales sales;
  private String id;
  private Date datenew;
  private String person;
  private String siteguid;
  private Boolean sflag;
  private ClosedCash money;

  public byte[] getAttributes()
  {
    return attributes;
  }

  public void setAttributes(byte[] attributes)
  {
    this.attributes = attributes;
  }

  public Sales getSales()
  {
    return sales;
  }

  public void setSales(Sales sales)
  {
    this.sales = sales;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public Date getDatenew()
  {
    return datenew;
  }

  public void setDatenew(Date datenew)
  {
    this.datenew = datenew;
  }

  public String getPerson()
  {
    return person;
  }

  public void setPerson(String person)
  {
    this.person = person;
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

  public ClosedCash getMoney()
  {
    return money;
  }

  public void setMoney(ClosedCash money)
  {
    this.money = money;
  }
}
