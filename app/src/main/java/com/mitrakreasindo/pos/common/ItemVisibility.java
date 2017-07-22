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

    for(int i=0; i<menuitem.size(); i++)
    {
      MenuItem mi = menu.findItem(menuitem.get(i));
      if (mi == null) continue;
      mi.setVisible(false);
    }
  }

  public static void hideButton (View view, List<String> list)
  {
    List<Integer> button = new ArrayList<>();

    for (int i = 0; i < list.size() ; i++)
    {
      button.add(getResId(list.get(i), R.id.class));
    }
//    if (resource == R.layout.fragment_maintenance)
//    {
//      int btn_maintenance[] =
//        {
//          R.id.btn_tax_categories,
//          R.id.btn_consumer_tax_categories,
//          R.id.btn_resources,
//          R.id.btn_locations,
//          R.id.btn_floors,
//          R.id.btn_tables,
//          R.id.btn_reset_pickup_counter,
//          R.id.btn_release_table_locks
//        };
//
//      for (int i = 0; i < btn_maintenance.length ; i++)
//      {
//        button.add(btn_maintenance[i]);
//      }
//    }
//    else if (resource == R.layout.fragment_stock)
//    {
//      int btn_stock[] =
//        {
//          R.id.btn_stok_mnt_product_location,
//          R.id.btn_stok_mnt_auxiliary_products,
//          R.id.btn_stok_mnt_product_attributes,
//          R.id.btn_stok_mnt_attributes_values,
//          R.id.btn_stok_mnt_product_attribute_sets,
//          R.id.btn_stok_mnt_product_attribute_use,
//          R.id.btn_stok_mnt_stock_movement,
//          R.id.btn_stok_mnt_promotions,
//          R.id.btn_stok_mnt_recipes
//        };
//
//      for (int i = 0; i < btn_stock.length ; i++)
//      {
//        button.add(btn_stock[i]);
//      }
//    }

    Log.d("item visibilite", "button size "+button.size());
    for(int i=0; i<button.size(); i++)
    {
      Button btn = (Button) view.findViewById(button.get(i));
      btn.setVisibility(View.GONE);
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
