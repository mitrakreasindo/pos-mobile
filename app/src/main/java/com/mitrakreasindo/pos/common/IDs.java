package com.mitrakreasindo.pos.common;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hendric on 2017-06-19.
 */

public class IDs
{
  private static String loginUser, loginUsername,
    loginCompanyName, loginCompanyAddress, loginCompanyPhone,
    loginCloseCashID;

  public static String getLoginUser()
  {
    return loginUser;
  }

  public static void setLoginUser(String loginUser)
  {
    IDs.loginUser = loginUser;
  }

  public static String getLoginUsername()
  {
    return loginUsername;
  }

  public static void setLoginUsername(String loginUsername)
  {
    IDs.loginUsername = loginUsername;
  }

  public static String getLoginCompanyName()
  {
    return loginCompanyName;
  }

  public static void setLoginCompanyName(String loginCompanyName)
  {
    IDs.loginCompanyName = loginCompanyName;
  }



  public static String getLoginCompanyAddress()
  {
    return loginCompanyAddress;
  }

  public static void setLoginCompanyAddress(String loginCompanyAddress)
  {
    IDs.loginCompanyAddress = loginCompanyAddress;
  }

  public static String getLoginCompanyPhone()
  {
    return loginCompanyPhone;
  }

  public static void setLoginCompanyPhone(String loginCompanyPhone)
  {
    IDs.loginCompanyPhone = loginCompanyPhone;
  }

  public static String getLoginCloseCashID()
  {
    return loginCloseCashID;
  }

  public static void setLoginCloseCashID(String loginCloseCashID)
  {
    IDs.loginCloseCashID = loginCloseCashID;
  }

  public static String generateTransactionID()
  {
    return Build.SERIAL + "-" + new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date());
  }
}
