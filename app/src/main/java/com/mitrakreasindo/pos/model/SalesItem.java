package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class SalesItem
{
  private int id;
  private Sales salesId;
  private int line;
  private Product product;
  private AttributesetInstance attributesetinstanceId;
  private double units;
  private double price;
  private Tax taxid;
  private byte[] attributes;
  private Double refundqty;
  private String siteguid;
  private Boolean sflag;

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public Sales getSalesId()
  {
    return salesId;
  }

  public void setSalesId(Sales salesId)
  {
    this.salesId = salesId;
  }

  public int getLine()
  {
    return line;
  }

  public void setLine(int line)
  {
    this.line = line;
  }

  public Product getProduct()
  {
    return product;
  }

  public void setProduct(Product product)
  {
    this.product = product;
  }

  public AttributesetInstance getAttributesetinstanceId()
  {
    return attributesetinstanceId;
  }

  public void setAttributesetinstanceId(AttributesetInstance attributesetinstanceId)
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

  public double getPrice()
  {
    return price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public Tax getTaxid()
  {
    return taxid;
  }

  public void setTaxid(Tax taxid)
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
}
