package com.mitrakreasindo.pos.model;

import java.util.Collection;
import java.util.Date;

/**
 * Created by lisa on 22/06/17.
 */

public class Receipt
{

  private byte[] attributes;
  private Collection<TaxLine> taxLineCollection;
  private String id;
  private Date datenew;
  private String person;
  private String siteguid;
  private Boolean sflag;
  private Ticket ticket;
  private Collection<Payment> paymentCollection;
  private ClosedCash money;

}
