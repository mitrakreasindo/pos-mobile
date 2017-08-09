package com.mitrakreasindo.pos.model;

import java.io.Serializable;

/**
 * Created by lisa on 07/08/17.
 */

public class SubProductReport implements Serializable
{

  private String productId;
  private String productName;
  private double qty;
  private double price;
  private double subTotal;
  private double disc;
  private double tax;
  private double total;

  public String getProductId()
  {
    return productId;
  }

  public void setProductId(String productId)
  {
    this.productId = productId;
  }

  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String productName)
  {
    this.productName = productName;
  }

  public double getQty()
  {
    return qty;
  }

  public void setQty(double qty)
  {
    this.qty = qty;
  }

  public double getPrice()
  {
    return price;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public double getSubTotal()
  {
    return subTotal;
  }

  public void setSubTotal(double subTotal)
  {
    this.subTotal = subTotal;
  }

  public double getDisc()
  {
    return disc;
  }

  public void setDisc(double disc)
  {
    this.disc = disc;
  }

  public double getTax()
  {
    return tax;
  }

  public void setTax(double tax)
  {
    this.tax = tax;
  }

  public double getTotal()
  {
    return total;
  }

  public void setTotal(double total)
  {
    this.total = total;
  }
}
