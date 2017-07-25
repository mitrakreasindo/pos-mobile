package com.mitrakreasindo.pos.model;

import java.util.Collection;
import java.util.Date;

/**
 * Created by lisa on 22/06/17.
 */

public class Customer
{

  private byte[] image;
  private String id;
  private String searchkey;
  private String taxid;
  private String name;
  private String card;
  private double maxdebt;
  private String address;
  private String address2;
  private String postal;
  private String city;
  private String region;
  private String country;
  private String firstname;
  private String lastname;
  private String email;
  private String phone;
  private String phone2;
  private String fax;
  private String notes;
  private boolean visible;
  private Date curdate;
  private Double curdebt;
  private Double discount;
  private Date dob;
  private String siteguid;
  private Boolean sflag;
  private TaxCusCategory taxcategory;

  public byte[] getImage()
  {
    return image;
  }

  public void setImage(byte[] image)
  {
    this.image = image;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getSearchkey()
  {
    return searchkey;
  }

  public void setSearchkey(String searchkey)
  {
    this.searchkey = searchkey;
  }

  public String getTaxid()
  {
    return taxid;
  }

  public void setTaxid(String taxid)
  {
    this.taxid = taxid;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getCard()
  {
    return card;
  }

  public void setCard(String card)
  {
    this.card = card;
  }

  public double getMaxdebt()
  {
    return maxdebt;
  }

  public void setMaxdebt(double maxdebt)
  {
    this.maxdebt = maxdebt;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getAddress2()
  {
    return address2;
  }

  public void setAddress2(String address2)
  {
    this.address2 = address2;
  }

  public String getPostal()
  {
    return postal;
  }

  public void setPostal(String postal)
  {
    this.postal = postal;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public String getRegion()
  {
    return region;
  }

  public void setRegion(String region)
  {
    this.region = region;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String country)
  {
    this.country = country;
  }

  public String getFirstname()
  {
    return firstname;
  }

  public void setFirstname(String firstname)
  {
    this.firstname = firstname;
  }

  public String getLastname()
  {
    return lastname;
  }

  public void setLastname(String lastname)
  {
    this.lastname = lastname;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getPhone2()
  {
    return phone2;
  }

  public void setPhone2(String phone2)
  {
    this.phone2 = phone2;
  }

  public String getFax()
  {
    return fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public String getNotes()
  {
    return notes;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  }

  public boolean isVisible()
  {
    return visible;
  }

  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }

  public Date getCurdate()
  {
    return curdate;
  }

  public void setCurdate(Date curdate)
  {
    this.curdate = curdate;
  }

  public Double getCurdebt()
  {
    return curdebt;
  }

  public void setCurdebt(Double curdebt)
  {
    this.curdebt = curdebt;
  }

  public Double getDiscount()
  {
    return discount;
  }

  public void setDiscount(Double discount)
  {
    this.discount = discount;
  }

  public Date getDob()
  {
    return dob;
  }

  public void setDob(Date dob)
  {
    this.dob = dob;
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


  public TaxCusCategory getTaxcategory()
  {
    return taxcategory;
  }

  public void setTaxcategory(TaxCusCategory taxcategory)
  {
    this.taxcategory = taxcategory;
  }
}
