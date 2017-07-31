package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 28/07/17.
 */

public class Viewsalesitems
{

  private int id;
  private String salesId;
  private int line;
  private String product;
  private String attributesetinstanceId;
  private Double units;
  private Double price;
  private String taxid;
  private byte[] attributes;
  private Double refundqty;
  private String siteguid;
  private Boolean sflag;
  private String productName;
  private String taxName;
  private Double rate;

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getSalesId()
  {
    return salesId;
  }

  public void setSalesId(String salesId)
  {
    this.salesId = salesId;
  }

  public Integer getLine()
  {
    return line;
  }

  public void setLine(Integer line)
  {
    this.line = line;
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

  public String getTaxid()
  {
    return taxid;
  }

  public void setTaxid(String taxid)
  {
    this.taxid = taxid;
  }

  public byte[] getAttributes()
  {
    return attributes;
  }

  public void setAttributes(byte[] attributes)
  {
    this.attributes = attributes;
  }

  public Double getRefundqty()
  {
    return refundqty;
  }

  public void setRefundqty(Double refundqty)
  {
    this.refundqty = refundqty;
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

  public String getTaxName()
  {
    return taxName;
  }

  public void setTaxName(String taxName)
  {
    this.taxName = taxName;
  }

  public Double getRate()
  {
    return rate;
  }

  public void setRate(Double rate)
  {
    this.rate = rate;
  }
}
