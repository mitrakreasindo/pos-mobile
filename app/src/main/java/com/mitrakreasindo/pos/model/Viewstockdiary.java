package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 28/07/17.
 */

public class Viewstockdiary
{

  private String id;
  private String datenew;
  private int reason;
  private String location;
  private String product;
  private String attributesetinstanceId;
  private Double units;
  private Double price;
  private String appuser;
  private String siteguid;
  private Boolean sflag;
  private String productName;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDatenew()
  {
    return datenew;
  }

  public void setDatenew(String datenew)
  {
    this.datenew = datenew;
  }

  public Integer getReason()
  {
    return reason;
  }

  public void setReason(Integer reason)
  {
    this.reason = reason;
  }

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

  public Double getUnits()
  {
    return units;
  }

  public void setUnits(Double units)
  {
    this.units = units;
  }

  public Double getPrice()
  {
    return price;
  }

  public void setPrice(Double price)
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

  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String productName)
  {
    this.productName = productName;
  }
}
