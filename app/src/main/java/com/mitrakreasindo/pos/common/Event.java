package com.mitrakreasindo.pos.common;

/**
 * Created by miftakhul on 7/22/17.
 */

public class Event {

  public static int COMPLATE = 200;
  public static int ERROR = 400;
  private int id;
  private int status;

  public Event(int id, int status)
  {
    this.id = id;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public int getStatus() {
    return status;
  }

}
