package com.mitrakreasindo.pos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by hendric on 2017-05-29.
 */

public class Product implements Serializable
{
  private String id;
  private String reference;
  private String code;
  private String codetype;
  private String name;
  private Double pricebuy;
  private Double pricesell;
  private Category category;
  private TaxCategory taxcat;
  private String attributesetId;
  private Double stockcost;
  private Double stockvolume;
  private Boolean iscom;
  private Boolean isscale;
  private Boolean iskitchen;
  private Boolean printkb;
  private Boolean sendstatus;
  private Boolean isservice;
  private String display;
  private byte[] attributes;
  private Boolean isvprice;
  private Boolean isverpatrib;
  private String texttip;
  private Boolean warranty;
  private byte[] image;
  private Double stockunits;
  private String alias;
  private Boolean alwaysavailable;
  private String discounted;
  private Boolean candiscount;
  private Boolean iscatalog;
  private Integer catorder;
  private Boolean ispack;
  private Double packquantity;
  private String packproduct;
  private String promotionid;
  private Boolean allproducts;
  private Boolean managestock;
  private String siteguid;
  private Boolean sflag;
  
  public Product()
  {

  }
//  protected Product(Parcel in)
//  {
//    id = in.readString();
//    reference = in.readString();
//    code = in.readString();
//    codetype = in.readString();
//    name = in.readString();
//    attributesetId = in.readString();
//    display = in.readString();
//    attributes = in.createByteArray();
//    texttip = in.readString();
//    image = in.createByteArray();
//    alias = in.readString();
//    discounted = in.readString();
//    packproduct = in.readString();
//    promotionid = in.readString();
//    siteguid = in.readString();
//  }
//
//  public static final Creator<Product> CREATOR = new Creator<Product>()
//  {
//    @Override
//    public Product createFromParcel(Parcel in)
//    {
//      return new Product(in);
//    }
//
//    @Override
//    public Product[] newArray(int size)
//    {
//      return new Product[size];
//    }
//  };
//
  public String getId()
  {
    if (id == null)
    {
      return "null";
    }
    else
    {
      return id;
    }
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getReference()
  {
    return reference;
  }

  public void setReference(String reference)
  {
    this.reference = reference;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getCodetype()
  {
    return codetype;
  }

  public void setCodetype(String codetype)
  {
    this.codetype = codetype;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Double getPricebuy()
  {
    return pricebuy;
  }

  public void setPricebuy(Double pricebuy)
  {
    this.pricebuy = pricebuy;
  }

  public Double getPricesell()
  {
    return pricesell;
  }

  public void setPricesell(Double pricesell)
  {
    this.pricesell = pricesell;
  }

  public Double getStockcost()
  {
    if (stockcost == null)
    {
      return 0.0;
    }
    else
    {
      return stockcost;
    }
  }

  public void setStockcost(Double stockcost)
  {
    this.stockcost = stockcost;
  }

  public Double getStockvolume()
  {
    if (stockvolume == null)
    {
      return 0.0;
    }
    else
    {
      return stockvolume;
    }
  }

  public void setStockvolume(Double stockvolume)
  {
    this.stockvolume = stockvolume;
  }

  public Boolean getIscom()
  {
    return iscom;
  }

  public void setIscom(Boolean iscom)
  {
    this.iscom = iscom;
  }

  public Boolean getIsscale()
  {
    return isscale;
  }

  public void setIsscale(Boolean isscale)
  {
    this.isscale = isscale;
  }

  public Boolean getIskitchen()
  {
    return iskitchen;
  }

  public void setIskitchen(Boolean iskitchen)
  {
    this.iskitchen = iskitchen;
  }

  public Boolean isPrintkb()
  {
    return printkb;
  }

  public void setPrintkb(Boolean printkb)
  {
    this.printkb = printkb;
  }

  public Boolean isSendstatus()
  {
    return sendstatus;
  }

  public void setSendstatus(Boolean sendstatus)
  {
    this.sendstatus = sendstatus;
  }

  public Boolean getIsservice()
  {
    return isservice;
  }

  public void setIsservice(Boolean isservice)
  {
    this.isservice = isservice;
  }

  public String getDisplay()
  {
    return display;
  }

  public void setDisplay(String display)
  {
    this.display = display;
  }

  public byte[] getAttributes()
  {
    return attributes;
  }

  public void setAttributes(byte[] attributes)
  {
    this.attributes = attributes;
  }

  public Boolean getIsvprice()
  {
    return isvprice;
  }

  public void setIsvprice(Boolean isvprice)
  {
    this.isvprice = isvprice;
  }

  public Boolean getIsverpatrib()
  {
    return isverpatrib;
  }

  public void setIsverpatrib(Boolean isverpatrib)
  {
    this.isverpatrib = isverpatrib;
  }

  public String getTexttip()
  {
    return texttip;
  }

  public void setTexttip(String texttip)
  {
    this.texttip = texttip;
  }

  public Boolean isWarranty()
  {
    return warranty;
  }

  public void setWarranty(Boolean warranty)
  {
    this.warranty = warranty;
  }

  public byte[] getImage()
  {
    return image;
  }

  public void setImage(byte[] image)
  {
    this.image = image;
  }

  public Double getStockunits()
  {
    return stockunits;
  }

  public void setStockunits(Double stockunits)
  {
    this.stockunits = stockunits;
  }

  public String getAlias()
  {
    return alias;
  }

  public void setAlias(String alias)
  {
    this.alias = alias;
  }

  public Boolean isAlwaysavailable()
  {
    return alwaysavailable;
  }

  public void setAlwaysavailable(Boolean alwaysavailable)
  {
    this.alwaysavailable = alwaysavailable;
  }

  public String getDiscounted()
  {
    return discounted;
  }

  public void setDiscounted(String discounted)
  {
    this.discounted = discounted;
  }

  public Boolean isCandiscount()
  {
    return candiscount;
  }

  public void setCandiscount(Boolean candiscount)
  {
    this.candiscount = candiscount;
  }

  public Boolean getIscatalog()
  {
    return iscatalog;
  }

  public void setIscatalog(Boolean iscatalog)
  {
    this.iscatalog = iscatalog;
  }

  public Integer getCatorder()
  {
    return catorder;
  }

  public void setCatorder(Integer catorder)
  {
    this.catorder = catorder;
  }

  public Boolean getIspack()
  {
    return ispack;
  }

  public void setIspack(Boolean ispack)
  {
    this.ispack = ispack;
  }

  public Double getPackquantity()
  {
    return packquantity;
  }

  public void setPackquantity(Double packquantity)
  {
    this.packquantity = packquantity;
  }

  public Boolean getAllproducts()
  {
    return allproducts;
  }

  public void setAllproducts(Boolean allproducts)
  {
    this.allproducts = allproducts;
  }

  public Boolean getManagestock()
  {
    return managestock;
  }

  public void setManagestock(Boolean managestock)
  {
    this.managestock = managestock;
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

  public String getAttributesetId()
  {
    return attributesetId;
  }

  public void setAttributesetId(String attributesetId)
  {
    this.attributesetId = attributesetId;
  }

  public Category getCategory()
  {
    return category;
  }

  public void setCategory(Category category)
  {
    this.category = category;
  }

  public String getPackproduct()
  {
    return packproduct;
  }

  public void setPackproduct(String packproduct)
  {
    this.packproduct = packproduct;
  }

  public String getPromotionid()
  {
    return promotionid;
  }

  public void setPromotionid(String promotionid)
  {
    this.promotionid = promotionid;
  }

  public TaxCategory getTaxcat()
  {
    return taxcat;
  }

  public void setTaxcat(TaxCategory taxcat)
  {
    this.taxcat = taxcat;
  }

//  @Override
//  public String toString()
//  {
//    return name;
//  }
//
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
//    dest.writeString(id);
//    dest.writeString(reference);
//    dest.writeString(code);
//    dest.writeString(codetype);
//    dest.writeString(name);
//    dest.writeString(attributesetId);
//    dest.writeString(display);
//    dest.writeByteArray(attributes);
//    dest.writeString(texttip);
//    dest.writeByteArray(image);
//    dest.writeString(alias);
//    dest.writeString(discounted);
//    dest.writeString(packproduct);
//    dest.writeString(promotionid);
//    dest.writeString(siteguid);
//  }

  @Override
  public String toString(){

    return name;
  }
}
