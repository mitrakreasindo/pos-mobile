package com.mitrakreasindo.pos.model;

/**
 * Created by hendric on 2017-05-29.
 */

public class Category
{
  private String id;
  private String name;
  private Category parentid;
  private String texttip;
  private boolean catshowname;
  private byte[] image;
  private String colour;
  private int catorder;
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

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Category getParentid()
  {
    return parentid;
  }

  public void setParentid(Category parentid)
  {
    this.parentid = parentid;
  }

  public String getTexttip()
  {
    return texttip;
  }

  public void setTexttip(String texttip)
  {
    this.texttip = texttip;
  }

  public boolean isCatshowname()
  {
    return catshowname;
  }

  public void setCatshowname(boolean catshowname)
  {
    this.catshowname = catshowname;
  }

  public byte[] getImage()
  {
    return image;
  }

  public void setImage(byte[] image)
  {
    this.image = image;
  }

  public String getColour()
  {
    return colour;
  }

  public void setColour(String colour)
  {
    this.colour = colour;
  }

  public int getCatorder()
  {
    return catorder;
  }

  public void setCatorder(int catorder)
  {
    this.catorder = catorder;
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

  @Override
  public String toString(){

    return name;
  }
}
