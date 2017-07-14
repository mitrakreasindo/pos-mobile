package com.mitrakreasindo.pos.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.mitrakreasindo.pos.main.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hendric on 2017-05-24.
 */

public class SharedPreferenceEditor
{
  private static String filename;
  private static String value;

  public static boolean SavePreferences(Context context, String key, String value)
  {
    try
    {
      filename = context.getResources().getString(R.string.preference_file_key);
      SharedPreferences sharedPreferences = context.getSharedPreferences(filename, MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString(key, value);
      editor.commit();
      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
  }

  public static String LoadPreferences(Context context, String key, String defaultValue)
  {
    try
    {
      filename = context.getResources().getString(R.string.preference_file_key);
      SharedPreferences sharedPreferences = context.getSharedPreferences(filename , MODE_PRIVATE);
      value = sharedPreferences.getString(key, defaultValue);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return value;
  }
}
