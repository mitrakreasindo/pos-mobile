package com.mitrakreasindo.pos.model;

import java.util.Collection;
import java.util.List;

/**
 * Created by lisa on 22/06/17.
 */

public class Location
{

  private String id;
  private String name;
  private String address;
  private String siteguid;
  private Boolean sflag;
  private Collection<StockDiary> stockdiaryCollection;

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

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
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

  public Collection<StockDiary> getStockdiaryCollection()
  {
    return stockdiaryCollection;
  }

  public void setStockdiaryCollection(Collection<StockDiary> stockdiaryCollection)
  {
    this.stockdiaryCollection = stockdiaryCollection;
  }
}
