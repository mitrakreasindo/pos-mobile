package com.mitrakreasindo.pos.model;

import java.util.Collection;

/**
 * Created by lisa on 22/06/17.
 */

public class AttributesetInstance
{

  private String id;
  private String description;
  private String siteguid;
  private Boolean sflag;
  private Attributeset attributesetId;
  private Collection<SalesItem> ticketLinesCollection;
  private Collection<StockDiary> stockDiaryCollection;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
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

  public Attributeset getAttributesetId()
  {
    return attributesetId;
  }

  public void setAttributesetId(Attributeset attributesetId)
  {
    this.attributesetId = attributesetId;
  }

  public Collection<SalesItem> getTicketLinesCollection()
  {
    return ticketLinesCollection;
  }

  public void setTicketLinesCollection(Collection<SalesItem> ticketLinesCollection)
  {
    this.ticketLinesCollection = ticketLinesCollection;
  }

  public Collection<StockDiary> getStockDiaryCollection()
  {
    return stockDiaryCollection;
  }

  public void setStockDiaryCollection(Collection<StockDiary> stockDiaryCollection)
  {
    this.stockDiaryCollection = stockDiaryCollection;
  }
}
