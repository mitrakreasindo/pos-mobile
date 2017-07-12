package com.mitrakreasindo.pos.model;

import java.util.Collection;

/**
 * Created by lisa on 22/06/17.
 */

public class TaxCusCategory
{

  private String id;
  private String name;
  private String siteguid;
  private Boolean sflag;
//  private Collection<Tax> taxesCollection;
//  private Collection<Customer> customersCollection;

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

//  public Collection<Tax> getTaxesCollection()
//  {
//    return taxesCollection;
//  }
//
//  public void setTaxesCollection(Collection<Tax> taxesCollection)
//  {
//    this.taxesCollection = taxesCollection;
//  }
//
//  public Collection<Customer> getCustomersCollection()
//  {
//    return customersCollection;
//  }
//
//  public void setCustomersCollection(Collection<Customer> customersCollection)
//  {
//    this.customersCollection = customersCollection;
//  }
}
