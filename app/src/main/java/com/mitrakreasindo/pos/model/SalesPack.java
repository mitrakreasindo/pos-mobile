package com.mitrakreasindo.pos.model;

import java.util.List;

/**
 * Created by lisa on 28/07/17.
 */

public class SalesPack
{

  private Viewsales sales;
  private Viewreceipts receipts;
  private List<Viewsalesitems> salesItems;
  private List<Viewpayments> payments;
  private List<Viewstockdiary> stockdiary;
  private List<Viewtaxlines> taxlines;

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

  public List<Viewsalesitems> getSalesItems()
  {
    return salesItems;
  }

  public void setSalesItems(List<Viewsalesitems> salesItems)
  {
    this.salesItems = salesItems;
  }

  public List<Viewpayments> getPayments()
  {
    return payments;
  }

  public void setPayments(List<Viewpayments> payments)
  {
    this.payments = payments;
  }

  public List<Viewstockdiary> getStockdiary()
  {
    return stockdiary;
  }

  public void setStockdiary(List<Viewstockdiary> stockdiary)
  {
    this.stockdiary = stockdiary;
  }

  public List<Viewtaxlines> getTaxlines()
  {
    return taxlines;
  }

  public void setTaxlines(List<Viewtaxlines> taxlines)
  {
    this.taxlines = taxlines;
  }
}
