package com.mitrakreasindo.pos.common;

import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mitrakreasindo.pos.main.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendric on 2017-06-21.
 */

public class ItemVisibility
{
  public static void hideItemNavigation (NavigationView navigationView, List<String> list)
  {
    Menu menu = navigationView.getMenu();
    List<Integer> menuitem = new ArrayList<>();

    for (int i = 0; i < list.size() ; i++)
    {
      menuitem.add(getResId(list.get(i), R.id.class));
    }
    Log.d("item visibility", "Nav menuitem size "+menuitem.size());
    for(int i=0; i<menuitem.size(); i++)
    {
      MenuItem mi = menu.findItem(menuitem.get(i));
      if (mi != null)
      {
        mi.setVisible(false);
      }
    }
  }

  public static void hideButton (View view, List<String> list)
  {
    List<Integer> button = new ArrayList<>();

    for (int i = 0; i < list.size() ; i++)
    {
      button.add(getResId(list.get(i), R.id.class));
    }
    Log.d("item visibility", "View button size "+button.size());
    for(int i=0; i<button.size(); i++)
    {
      Button btn = (Button) view.findViewById(button.get(i));
      if (btn != null)
      {
        btn.setVisibility(View.GONE);
      }
    }
  }

  public static int getResId(String resName, Class<?> c)
  {
    try
    {
      Field idField = c.getDeclaredField(resName);
      return idField.getInt(idField);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return -1;
    }
  }
}
