package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 25/08/17.
 */

public class ReportCategorySubItem
{

  private String categoryId;
  private String categoryName;
  private double qty;
  private double price;
  private double subTotal;
  private double disc;
  private double tax;
  private double total;

  public String getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId(String categoryId)
  {
    this.categoryId = categoryId;
  }

  public String getCategoryName()
  {
    return categoryName;
  }

  public void setCategoryName(String categoryName)
  {
    this.categoryName = categoryName;
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
