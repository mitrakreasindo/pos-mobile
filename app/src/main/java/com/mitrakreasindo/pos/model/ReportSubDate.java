package com.mitrakreasindo.pos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lisa on 29/08/17.
 */

public class ReportSubDate<T> implements Serializable
{

  private Date date;
  private List<T> subReport;

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public List<T> getSubReport()
  {
    return subReport;
  }

  public void setSubReport(List<T> subReport)
  {
    this.subReport = subReport;
  }
}
