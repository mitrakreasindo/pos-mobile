package com.mitrakreasindo.pos.model;

import java.util.Collection;
import java.util.List;

/**
 * Created by lisa on 22/06/17.
 */

public class Attributeset
{
  private String id;
  private String name;
  private String siteguid;
  private Boolean sflag;
  private Collection<Product> productsCollection;

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
