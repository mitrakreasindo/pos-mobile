package com.mitrakreasindo.pos.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Collection;
import java.util.List;

/**
 * Created by lisa on 28/07/17.
 */

public class SalesPack
{

  private Viewsales sales;
  private Viewreceipts receipts;
  private Collection<Viewsalesitems> salesItems;
  private Collection<Viewpayments> payments;
  private Collection<Viewstockdiary> stockdiary;
  private Collection<Viewtaxlines> taxlines;

  public Viewsales getSales()
  {
    return sales;
  }

  public void setSales(Viewsales sales)
  {
    this.sales = sales;
  }

  public Viewreceipts getReceipts()
  {
    return receipts;
  }

  public void setReceipts(Viewreceipts receipts)
  {
    this.receipts = receipts;
  }

  public Collection<Viewsalesitems> getSalesItems()
  {
    return salesItems;
  }

  public void setSalesItems(Collection<Viewsalesitems> salesItems)
  {
    this.salesItems = salesItems;
  }

  public Collection<Viewpayments> getPayments()
  {
    return payments;
  }

  public void setPayments(Collection<Viewpayments> payments)
  {
    this.payments = payments;
  }

  public Collection<Viewstockdiary> getStockdiary()
  {
    return stockdiary;
  }

  public void setStockdiary(Collection<Viewstockdiary> stockdiary)
  {
    this.stockdiary = stockdiary;
  }

  public Collection<Viewtaxlines> getTaxlines()
  {
    return taxlines;
  }

  public void setTaxlines(Collection<Viewtaxlines> taxlines)
  {
    this.taxlines = taxlines;
  }
}
