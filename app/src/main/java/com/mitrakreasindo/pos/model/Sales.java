package com.mitrakreasindo.pos.model;

import java.util.Collection;

/**
 * Created by lisa on 22/06/17.
 */

public class Sales
{

  private String id;
  private int tickettype;
  private int ticketid;
  private int status;
  private String siteguid;
  private Boolean sflag;
  private Customer customer;
  private People person;
  private Receipt receipts;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public int getTickettype()
  {
    return tickettype;
  }

  public void setTickettype(int tickettype)
  {
    this.tickettype = tickettype;
  }

  public int getTicketid()
  {
    return ticketid;
  }

  public void setTicketid(int ticketid)
  {
    this.ticketid = ticketid;
  }

  public int getStatus()
  {
    return status;
  }

  public void setStatus(int status)
  {
    this.status = status;
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

  public Customer getCustomer()
  {
    return customer;
  }

  public void setCustomer(Customer customer)
  {
    this.customer = customer;
  }

  public People getPerson()
  {
    return person;
  }

  public void setPerson(People person)
  {
    this.person = person;
  }

  public Receipt getReceipts()
  {
    return receipts;
  }

  public void setReceipts(Receipt receipts)
  {
    this.receipts = receipts;
  }
}
