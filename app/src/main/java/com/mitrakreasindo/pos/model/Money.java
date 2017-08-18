package com.mitrakreasindo.pos.model;

import java.io.Serializable;

/**
 * Created by lisa on 16/08/17.
 */

public class Money implements Serializable
{

  private Double day, week, month, year;

  public Double getDay()
  {
    return day;
  }

  public void setDay(Double day)
  {
    this.day = day;
  }

  public Double getWeek()
  {
    return week;
  }

  public void setWeek(Double week)
  {
    this.week = week;
  }

  public Double getMonth()
  {
    return month;
  }

  public void setMonth(Double month)
  {
    this.month = month;
  }

  public Double getYear()
  {
    return year;
  }

  public void setYear(Double year)
  {
    this.year = year;
  }
}
