package com.mitrakreasindo.pos.model;

import java.util.Collection;

/**
 * Created by lisa on 22/06/17.
 */

public class Ticket
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
  private Collection<TicketLine> ticketlineCollection;

}
