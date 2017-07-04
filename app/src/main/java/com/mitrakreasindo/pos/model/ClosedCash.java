package com.mitrakreasindo.pos.model;

import java.util.Collection;
import java.util.Date;

/**
 * Created by lisa on 22/06/17.
 */

public class ClosedCash
{

  private String money;
  private String host;
  private int hostsequence;
  private Date datestart;
  private Date dateend;
  private int nosales;
  private String siteguid;
  private Boolean sflag;
  private Collection<Receipt> receiptsCollection;

  public String getMoney()
  {
    return money;
  }

  public void setMoney(String money)
  {
    this.money = money;
  }

  public String getHost()
  {
    return host;
  }

  public void setHost(String host)
  {
    this.host = host;
  }

  public int getHostsequence()
  {
    return hostsequence;
  }

  public void setHostsequence(int hostsequence)
  {
    this.hostsequence = hostsequence;
  }

  public Date getDatestart()
  {
    return datestart;
  }

  public void setDatestart(Date datestart)
  {
    this.datestart = datestart;
  }

  public Date getDateend()
  {
    return dateend;
  }

  public void setDateend(Date dateend)
  {
    this.dateend = dateend;
  }

  public int getNosales()
  {
    return nosales;
  }

  public void setNosales(int nosales)
  {
    this.nosales = nosales;
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

  public Collection<Receipt> getReceiptsCollection()
  {
    return receiptsCollection;
  }

  public void setReceiptsCollection(Collection<Receipt> receiptsCollection)
  {
    this.receiptsCollection = receiptsCollection;
  }
}
