package com.mitrakreasindo.pos.common;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hendric on 2017-06-19.
 */

public class TransactionID
{
  public static String generateTransID()
  {
    return Build.SERIAL + "-" + new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(new Date());
  }
}
