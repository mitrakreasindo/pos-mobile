package com.mitrakreasindo.pos.model;

import java.util.Date;

/**
 * Created by lisa on 22/06/17.
 */

public class StockDiary
{
  private String id;
  private Date datenew;
  private int reason;
  private double units;
  private double price;
  private String appuser;
  private String siteguid;
  private Boolean sflag;
  private AttributesetInstace attributesetinstanceId;
  private Location location;
  private Product product;

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

  public int getReason()
  {
    return reason;
  }

  public void setReason(int reason)
  {
    this.reason = reason;
  }

  public double getUnits()
  {
    return units;
  }

  public void setUnits(double units)
  {
    this.units = units;
  }

  public double getPrice()
  {
    return price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public String getAppuser()
  {
    return appuser;
  }

  public void setAppuser(String appuser)
  {
    this.appuser = appuser;
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

  public AttributesetInstace getAttributesetinstanceId()
  {
    return attributesetinstanceId;
  }

  public void setAttributesetinstanceId(AttributesetInstace attributesetinstanceId)
  {
    this.attributesetinstanceId = attributesetinstanceId;
  }

  public Location getLocation()
  {
    return location;
  }

  public void setLocation(Location location)
  {
    this.location = location;
  }

  public Product getProduct()
  {
    return product;
  }

  public void setProduct(Product product)
  {
    this.product = product;
  }
}
