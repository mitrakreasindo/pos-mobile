package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class SalesItem
{

  private byte[] attributes;
  protected TicketLinePK ticketlinesPK;
  private double units;
  private double price;
  private Double refundqty;
  private String siteguid;
  private Boolean sflag;
  private AttributesetInstace attributesetinstanceId;
  private Product product;
  private Tax taxid;
  private Sales sales;

  public byte[] getAttributes()
  {
    return attributes;
  }

  public void setAttributes(byte[] attributes)
  {
    this.attributes = attributes;
  }

  public TicketLinePK getTicketlinesPK()
  {
    return ticketlinesPK;
  }

  public void setTicketlinesPK(TicketLinePK ticketlinesPK)
  {
    this.ticketlinesPK = ticketlinesPK;
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

  public AttributesetInstace getAttributesetinstanceId()
  {
    return attributesetinstanceId;
  }

  public void setAttributesetinstanceId(AttributesetInstace attributesetinstanceId)
  {
    this.attributesetinstanceId = attributesetinstanceId;
  }

  public Product getProduct()
  {
    return product;
  }

  public void setProduct(Product product)
  {
    this.product = product;
  }

  public Tax getTaxid()
  {
    return taxid;
  }

  public void setTaxid(Tax taxid)
  {
    this.taxid = taxid;
  }

  public Sales getSales()
  {
    return sales;
  }

  public void setSales(Sales sales)
  {
    this.sales = sales;
  }
}
