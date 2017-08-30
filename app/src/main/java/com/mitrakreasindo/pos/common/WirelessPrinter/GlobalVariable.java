package com.mitrakreasindo.pos.common.WirelessPrinter;

import android.app.Application;

/**
 * Created by MK-fepriyadi on 8/21/2017.
 */

public class GlobalVariable extends Application
{
  String deviceName = "";
  
  public String getDeviceName(){
    return deviceName;
  }
  public void setDeviceName(String p){
    deviceName = p;
  }
  
}
