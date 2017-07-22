package com.mitrakreasindo.pos.model;

/**
 * Created by hendric on 2017-07-22.
 */

public class MerchantCategories
{
  private Integer id;
  private String name;
  private String subcategory;
  private boolean sflag;

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getSubcategory()
  {
    return subcategory;
  }

  public void setSubcategory(String subcategory)
  {
    this.subcategory = subcategory;
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
