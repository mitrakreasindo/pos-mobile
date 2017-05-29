package com.mitrakreasindo.pos.main.stock.product.model;

/**
 * Created by hendric on 2017-05-29.
 */

public class Product
{
  private String id;
  private String reference;
  private String code;
  private String codetype;
  private String name;
  private double pricebuy;
  private double pricesell;
  private String category;
  private String taxcat;
  private String attributeset;
  private double stockcost;
  private double stockvolume;
  private boolean iscom;
  private boolean isscale;
  private boolean iskitchen;
  private boolean printkb;
  private boolean sendstatus;
  private boolean isservice;
  private String display;
  private byte[] attributes;
  private boolean isvprice;
  private boolean isverpatrib;
  private String texttip;
  private boolean warranty;
  private byte[] image;
  private double stockunits;
  private String alias;
  private boolean alwaysavailable;
  private String discounted;
  private boolean candiscount;
  private boolean iscatalog;
  private int catorder;
  private boolean ispack;
  private double packquantity;
  private String packproduct;
  private String promotionid;
  private boolean allproducts;
  private boolean managestock;
  private String siteguid;
  private boolean sflag;

  public String getId()
  {
    return id;
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

  public double getPricebuy()
  {
    return pricebuy;
  }

  public void setPricebuy(double pricebuy)
  {
    this.pricebuy = pricebuy;
  }

  public double getPricesell()
  {
    return pricesell;
  }

  public void setPricesell(double pricesell)
  {
    this.pricesell = pricesell;
  }

  public String getCategory()
  {
    return category;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  public String getTaxcat()
  {
    return taxcat;
  }

  public void setTaxcat(String taxcat)
  {
    this.taxcat = taxcat;
  }

  public String getAttributeset()
  {
    return attributeset;
  }

  public void setAttributeset(String attributeset)
  {
    this.attributeset = attributeset;
  }

  public double getStockcost()
  {
    return stockcost;
  }

  public void setStockcost(double stockcost)
  {
    this.stockcost = stockcost;
  }

  public double getStockvolume()
  {
    return stockvolume;
  }

  public void setStockvolume(double stockvolume)
  {
    this.stockvolume = stockvolume;
  }

  public boolean iscom()
  {
    return iscom;
  }

  public void setIscom(boolean iscom)
  {
    this.iscom = iscom;
  }

  public boolean isscale()
  {
    return isscale;
  }

  public void setIsscale(boolean isscale)
  {
    this.isscale = isscale;
  }

  public boolean iskitchen()
  {
    return iskitchen;
  }

  public void setIskitchen(boolean iskitchen)
  {
    this.iskitchen = iskitchen;
  }

  public boolean isPrintkb()
  {
    return printkb;
  }

  public void setPrintkb(boolean printkb)
  {
    this.printkb = printkb;
  }

  public boolean isSendstatus()
  {
    return sendstatus;
  }

  public void setSendstatus(boolean sendstatus)
  {
    this.sendstatus = sendstatus;
  }

  public boolean isservice()
  {
    return isservice;
  }

  public void setIsservice(boolean isservice)
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

  public boolean isvprice()
  {
    return isvprice;
  }

  public void setIsvprice(boolean isvprice)
  {
    this.isvprice = isvprice;
  }

  public boolean isverpatrib()
  {
    return isverpatrib;
  }

  public void setIsverpatrib(boolean isverpatrib)
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

  public boolean isWarranty()
  {
    return warranty;
  }

  public void setWarranty(boolean warranty)
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

  public double getStockunits()
  {
    return stockunits;
  }

  public void setStockunits(double stockunits)
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

  public boolean isAlwaysavailable()
  {
    return alwaysavailable;
  }

  public void setAlwaysavailable(boolean alwaysavailable)
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

  public boolean isCandiscount()
  {
    return candiscount;
  }

  public void setCandiscount(boolean candiscount)
  {
    this.candiscount = candiscount;
  }

  public boolean iscatalog()
  {
    return iscatalog;
  }

  public void setIscatalog(boolean iscatalog)
  {
    this.iscatalog = iscatalog;
  }

  public int getCatorder()
  {
    return catorder;
  }

  public void setCatorder(int catorder)
  {
    this.catorder = catorder;
  }

  public boolean ispack()
  {
    return ispack;
  }

  public void setIspack(boolean ispack)
  {
    this.ispack = ispack;
  }

  public double getPackquantity()
  {
    return packquantity;
  }

  public void setPackquantity(double packquantity)
  {
    this.packquantity = packquantity;
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

  public boolean isAllproducts()
  {
    return allproducts;
  }

  public void setAllproducts(boolean allproducts)
  {
    this.allproducts = allproducts;
  }

  public boolean isManagestock()
  {
    return managestock;
  }

  public void setManagestock(boolean managestock)
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

  public boolean isSflag()
  {
    return sflag;
  }

  public void setSflag(boolean sflag)
  {
    this.sflag = sflag;
  }
}
