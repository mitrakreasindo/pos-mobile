package com.mitrakreasindo.pos.common.Wireless.Adapter;

import java.util.ArrayList;
import java.util.List;


public class WirelessAdapter
{
  private String name;
  private int qty;
  private double prize;
  private double tot;

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

  public double getPrize()
  {
    return prize;
  }

  public void setPrize(double prize)
  {
    this.prize = prize;
  }

  public double getTot()
  {
    return tot;
  }

  public void setTot(double tot)
  {
    this.tot = tot;
  }


  public static List<WirelessAdapter> getAllSales(){
    List<WirelessAdapter> queues = new ArrayList<>();
  
    WirelessAdapter queue = new WirelessAdapter();
    queue.setName("RINSOOOOOOOOOO");
    queue.setQty(1);
    queue.setPrize(200);
    queue.setTot(10000);
    queues.add(queue);
  
    WirelessAdapter queue2 = new WirelessAdapter();
    queue2.setName("ROTI");
    queue2.setQty(1000);
    queue2.setPrize(3000);
    queue2.setTot(50000);
    queues.add(queue2);
  
    return queues;
  }
}
