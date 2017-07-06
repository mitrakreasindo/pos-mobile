package com.mitrakreasindo.pos.common;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hendric on 2017-06-19.
 */

public class IDs
{
  private static String loginUser;

  public static String getLoginUser()
  {
    return loginUser;
  }

  public static void setLoginUser(String loginUser)
  {
    IDs.loginUser = loginUser;
  }

  public static String generateTransactionID()
  {
    return Build.SERIAL + "-" + new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date());
  }
}
