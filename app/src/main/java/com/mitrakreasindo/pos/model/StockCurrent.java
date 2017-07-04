package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class StockCurrent
{

  private String location;
  private String product;
  private String attributesetinstanceId;
  private double units;
  private String siteguid;
  private boolean sflag;

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getProduct()
  {
    return product;
  }

  public void setProduct(String product)
  {
    this.product = product;
  }

  public String getAttributesetinstanceId()
  {
    return attributesetinstanceId;
  }

  public void setAttributesetinstanceId(String attributesetinstanceId)
  {
    this.attributesetinstanceId = attributesetinstanceId;
  }

  public double getUnits()
  {
    return units;
  }

  public void setUnits(double units)
  {
    this.units = units;
  }

  public String getSiteguid()
  {
    return siteguid;
  }

  public void setSiteguid(String siteguid)
  {
    this.siteguid = siteguid;
  }

  public boolean isSflag()
  {
    return sflag;
  }

  public void setSflag(boolean sflag)
  {
    this.sflag = sflag;
  }
}
