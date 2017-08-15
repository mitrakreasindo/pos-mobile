package com.mitrakreasindo.pos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MK-fepriyadi on 7/25/2017.
 */

public class Print implements Serializable
{
  private static final long serialVersionUID = 1L;
  private List<SalesItem> ticketLines;
  
  public Print()
  {
    
  }
  
//  private Print(Parcel in)
//  {
//    ticketLines = new ArrayList<>();
//    in.readTypedList(ticketLines, SalesItem.CREATOR);
//  }
//
//  public static final Creator<Print> CREATOR = new Creator<Print>()
//  {
//    @Override
//    public Print createFromParcel(Parcel in)
//    {
//      return new Print(in);
//    }
//
//    @Override
//    public Print[] newArray(int size)
//    {
//      return new Print[size];
//    }
//  };
  
  public List<SalesItem> getTicketLines()
  {
    return this.ticketLines;
  }
  
  public void setTicketLines(List<SalesItem> ticketLines)
  {
    this.ticketLines = ticketLines;
  }
  
//  @Override
//  public int describeContents()
//  {
//    return 0;
//  }
//
//  @Override
//  public void writeToParcel(Parcel dest, int flags)
//  {
//      dest.writeTypedList(ticketLines);
//  }
}
