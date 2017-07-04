package com.mitrakreasindo.pos.model;

import java.util.Collection;

/**
 * Created by lisa on 22/06/17.
 */

public class Tax
{

  private Collection<TaxLine> taxLineCollection;
  private String id;
  private String name;
  private double rate;
  private boolean ratecascade;
  private int rateorder;
  private String siteguid;
  private Boolean sflag;
  private TaxCategory category;
  private TaxCusCategory custcategory;
  private Collection<Tax> taxesCollection;
  private Tax parentid;
  private Collection<TicketLine> ticketlinesCollection;

  public Collection<TaxLine> getTaxLineCollection()
  {
    return taxLineCollection;
  }

  public void setTaxLineCollection(Collection<TaxLine> taxLineCollection)
  {
    this.taxLineCollection = taxLineCollection;
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

  public double getRate()
  {
    return rate;
  }

  public void setRate(double rate)
  {
    this.rate = rate;
  }

  public boolean isRatecascade()
  {
    return ratecascade;
  }

  public void setRatecascade(boolean ratecascade)
  {
    this.ratecascade = ratecascade;
  }

  public int getRateorder()
  {
    return rateorder;
  }

  public void setRateorder(int rateorder)
  {
    this.rateorder = rateorder;
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

  public TaxCategory getCategory()
  {
    return category;
  }

  public void setCategory(TaxCategory category)
  {
    this.category = category;
  }

  public TaxCusCategory getCustcategory()
  {
    return custcategory;
  }

  public void setCustcategory(TaxCusCategory custcategory)
  {
    this.custcategory = custcategory;
  }

  public Collection<Tax> getTaxesCollection()
  {
    return taxesCollection;
  }

  public void setTaxesCollection(Collection<Tax> taxesCollection)
  {
    this.taxesCollection = taxesCollection;
  }

  public Tax getParentid()
  {
    return parentid;
  }

  public void setParentid(Tax parentid)
  {
    this.parentid = parentid;
  }

  public Collection<TicketLine> getTicketlinesCollection()
  {
    return ticketlinesCollection;
  }

  public void setTicketlinesCollection(Collection<TicketLine> ticketlinesCollection)
  {
    this.ticketlinesCollection = ticketlinesCollection;
  }
}
