package com.mitrakreasindo.pos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lisa on 22/06/17.
 */

public class SalesItem implements Serializable
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
  
//  public SalesItem()
//  {
//
//  }
//
//  protected SalesItem(Parcel in)
//  {
//    id = in.readInt();
//    line = in.readInt();
//    units = in.readDouble();
//    price = in.readDouble();
//    attributes = in.createByteArray();
//    siteguid = in.readString();
//    product = in.readParcelable(Product.class.getClassLoader());
//  }

//  public static final Creator<SalesItem> CREATOR = new Creator<SalesItem>()
//  {
//    @Override
//    public SalesItem createFromParcel(Parcel in)
//    {
//      return new SalesItem(in);
//    }
//
//    @Override
//    public SalesItem[] newArray(int size)
//    {
//      return new SalesItem[size];
//    }
//  };
  
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
  
//  @Override
//  public int describeContents()
//  {
//    return 0;
//  }
//
//  @Override
//  public void writeToParcel(Parcel dest, int flags)
//  {
//
//    dest.writeInt(id);
//    dest.writeInt(line);
//    dest.writeDouble(units);
//    dest.writeDouble(price);
//    dest.writeByteArray(attributes);
//    dest.writeString(siteguid);
//    dest.writeParcelable(product, flags);
//  }
}
