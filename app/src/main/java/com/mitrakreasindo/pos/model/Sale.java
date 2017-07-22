package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 19/07/17.
 */

public class Sale
{
  private String name;
  private int qty;
  private int price;
  private int subTotal;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getQty()
  {
    return qty;
  }

  public void setQty(int qty)
  {
    this.qty = qty;
  }

  public int getPrice()
  {
    return price;
  }

  public void setPrice(int price)
  {
    this.price = price;
  }

  public int getSubTotal()
  {
    return subTotal;
  }

  public void setSubTotal(int subTotal)
  {
    this.subTotal = subTotal;
  }

}
