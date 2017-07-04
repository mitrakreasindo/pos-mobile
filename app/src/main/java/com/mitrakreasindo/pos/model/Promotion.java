package com.mitrakreasindo.pos.model;

import java.util.Collection;

/**
 * Created by lisa on 22/06/17.
 */

public class Promotion
{

  private byte[] criteria;
  private byte[] script;
  private String id;
  private String name;
  private Boolean allproducts;
  private Boolean isenabled;
  private String siteguid;
  private Boolean sflag;
  private Collection<Product> productsCollection;

  public byte[] getCriteria()
  {
    return criteria;
  }

  public void setCriteria(byte[] criteria)
  {
    this.criteria = criteria;
  }

  public byte[] getScript()
  {
    return script;
  }

  public void setScript(byte[] script)
  {
    this.script = script;
  }

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

  public Boolean getAllproducts()
  {
    return allproducts;
  }

  public void setAllproducts(Boolean allproducts)
  {
    this.allproducts = allproducts;
  }

  public Boolean getIsenabled()
  {
    return isenabled;
  }

  public void setIsenabled(Boolean isenabled)
  {
    this.isenabled = isenabled;
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

  public Collection<Product> getProductsCollection()
  {
    return productsCollection;
  }

  public void setProductsCollection(Collection<Product> productsCollection)
  {
    this.productsCollection = productsCollection;
  }
}
