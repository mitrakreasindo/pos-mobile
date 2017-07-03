package com.mitrakreasindo.pos.common;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mitrakreasindo.pos.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendric on 2017-06-21.
 */

public class ItemVisibility
{
  public static void hideItemNavigation (NavigationView navigationView)
  {
    Menu menu = navigationView.getMenu();

    int menuitem[] =
      {
        R.id.nd_edit_sales,
        R.id.nd_customer_payment,
        R.id.nd_payment,
        R.id.nd_customer,
        R.id.nd_administration_sales,
        R.id.nd_presence_management,
        R.id.item_utilities,
        R.id.nd_tools,
        R.id.nd_change_password,
        R.id.nd_configuration,
        R.id.nd_printers,
        R.id.nd_check_in_out
      };

    for(int i=0; i<menuitem.length; i++)
    {
      MenuItem mi = menu.findItem(menuitem[i]);
      mi.setVisible(false);
    }
  }

  public static void hideButton (int resource, View view)
  {
    List<Integer> button = new ArrayList<>();

    if (resource == R.layout.fragment_maintenance)
    {
      int btn_maintenance[] =
        {
          R.id.btn_tax_categories,
          R.id.btn_consumer_tax_categories,
          R.id.btn_resources,
          R.id.btn_locations,
          R.id.btn_floors,
          R.id.btn_tables,
          R.id.btn_reset_pickup_counter,
          R.id.btn_release_table_locks
        };

      for (int i = 0; i < btn_maintenance.length ; i++)
      {
        button.add(btn_maintenance[i]);
      }
    }
    else if (resource == R.layout.fragment_stock)
    {
      int btn_stock[] =
        {
          R.id.btn_stok_mnt_product_location,
          R.id.btn_stok_mnt_auxiliary_products,
          R.id.btn_stok_mnt_product_attributes,
          R.id.btn_stok_mnt_attributes_values,
          R.id.btn_stok_mnt_product_attribute_sets,
          R.id.btn_stok_mnt_product_attribute_use,
          R.id.btn_stok_mnt_stock_movement,
          R.id.btn_stok_mnt_promotions,
          R.id.btn_stok_mnt_recipes
        };

      for (int i = 0; i < btn_stock.length ; i++)
      {
        button.add(btn_stock[i]);
      }
    }

    for(int i=0; i<button.size(); i++)
    {
      Button btn = (Button) view.findViewById(button.get(i));
      btn.setVisibility(View.GONE);
    }
  }
}
