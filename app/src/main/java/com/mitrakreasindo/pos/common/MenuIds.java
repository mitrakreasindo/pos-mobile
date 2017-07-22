package com.mitrakreasindo.pos.common;

import com.mitrakreasindo.pos.main.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by miftakhul on 7/21/17.
 */

public class MenuIds {

  public static HashMap<Integer, String> listNavigatoin()
  {
    HashMap<Integer, String> list = new HashMap<>();
    list.put(R.id.rp_sales, "nd_sales");
    list.put(R.id.rp_edit_sales, "nd_edit_sales");
    list.put(R.id.rp_customer_payment, "nd_customer_payment");
    list.put(R.id.rp_payment, "nd_payment");
    list.put(R.id.rp_close_cash, "nd_close_cash");
    list.put(R.id.rp_customer, "nd_customer");
    list.put(R.id.rp_stock, "nd_stock");
    list.put(R.id.rp_administration_sales, "nd_administration_sales");
    list.put(R.id.rp_maintenance, "nd_maintenance");
    list.put(R.id.rp_presence_management, "nd_presence_management");
    list.put(R.id.rp_tools, "nd_tools");
    list.put(R.id.rp_change_password, "nd_change_password");
    list.put(R.id.rp_configuration, "nd_configuration");
    list.put(R.id.rp_printers, "nd_printers");
    list.put(R.id.rp_check_in_out, "nd_check_in_out");
    list.put(R.id.rp_logout, "nd_logout");
    return list;
  }

  public static HashMap<Integer, String> listMain()
  {
    HashMap<Integer, String> list = new HashMap<>();
    list.put(R.id.rp_btn_menu_sales, "btn_menu_sales");
    list.put(R.id.rp_btn_menu_data, "btn_menu_data");
    list.put(R.id.rp_btn_menu_receive, "btn_menu_receive");
    list.put(R.id.rp_btn_menu_setting, "btn_menu_setting");
    list.put(R.id.rp_btn_menu_report, "btn_menu_report");
    list.put(R.id.rp_btn_menu_export, "btn_menu_export");
    return list;
  }

  public static HashMap<Integer, String> listMaintenance()
  {
    HashMap<Integer, String> list = new HashMap<>();
    list.put(R.id.rp_mtc_users, "btn_users");
    list.put(R.id.rp_mtc_roles, "btn_roles");
    list.put(R.id.rp_mtc_taxes, "btn_taxes");
    list.put(R.id.rp_mtc_tax_categories, "btn_tax_categories");
    list.put(R.id.rp_mtc_consumer_tax_categories, "btn_consumer_tax_categories");
    list.put(R.id.rp_mtc_resources, "btn_resources");
    list.put(R.id.rp_mtc_locations, "btn_locations");
    list.put(R.id.rp_mtc_floors, "btn_floors");
    list.put(R.id.rp_mtc_tables, "btn_tables");
    list.put(R.id.rp_mtc_reset_pickup_counter, "btn_reset_pickup_counter");
    list.put(R.id.rp_mtc_release_table_locks, "btn_release_table_locks");
    list.put(R.id.rp_mtc_report_user, "btn_report_user");
    list.put(R.id.rp_mtc_sales_by_user, "btn_sales_by_user");
    return list;
  }

  public static HashMap<Integer, String> listStock()
  {
    HashMap<Integer, String> list = new HashMap<>();
    list.put(R.id.rp_stk_mnt_products, "btn_stok_mnt_products");
    list.put(R.id.rp_stk_mnt_product_location, "btn_stok_mnt_product_location");
    list.put(R.id.rp_stk_mnt_auxiliary_products, "btn_stok_mnt_auxiliary_products");
    list.put(R.id.rp_stk_mnt_categories, "btn_stok_mnt_categories");
    list.put(R.id.rp_stk_mnt_product_attributes, "btn_stok_mnt_product_attributes");
    list.put(R.id.rp_stk_mnt_attributes_values, "btn_stok_mnt_attributes_values");
    list.put(R.id.rp_stk_mnt_product_attribute_sets, "btn_stok_mnt_product_attribute_sets");
    list.put(R.id.rp_stk_mnt_product_attribute_use, "btn_stok_mnt_product_attribute_use");
    list.put(R.id.rp_stk_mnt_stock_diary, "btn_stok_mnt_stock_diary");
    list.put(R.id.rp_stk_mnt_stock_movement, "btn_stok_mnt_stock_movement");
    list.put(R.id.rp_stk_mnt_promotions, "btn_stok_mnt_promotions");
    list.put(R.id.rp_stk_mnt_recipes, "btn_stok_mnt_recipes");
    list.put(R.id.rp_stk_report_labels_barcode, "btn_stok_report_labels_barcode");
    list.put(R.id.rp_stk_report_inventory, "btn_stok_report_inventory");
    list.put(R.id.rp_stk_report_current_directory, "btn_stok_report_current_directory");
    list.put(R.id.rp_stk_report_inventory_broken, "btn_stok_report_inventory_broken");
    list.put(R.id.rp_stk_report_inventory_difference, "btn_stok_report_inventory_difference");
    list.put(R.id.rp_stk_report_inventory_difference_detail, "btn_stok_report_inventory_difference_detail");
    list.put(R.id.rp_stk_report_inventory_list_detaile, "btn_stok_report_inventory_list_detaile");
    list.put(R.id.rp_stk_report_inventory_reorder, "btn_stok_report_inventory_reorder");
    list.put(R.id.rp_stk_report_product_catalogue_list, "btn_stok_report_product_catalogue_list");
    list.put(R.id.rp_stk_report_products, "btn_stok_report_products");
    list.put(R.id.rp_stk_report_product_labels, "btn_stok_report_product_labels");
    list.put(R.id.rp_stk_report_catalogue, "btn_stok_report_catalogue");
    list.put(R.id.rp_stk_report_labels_shelf_egde_labels, "btn_stok_report_labels_shelf_egde_labels");
    return list;
  }

  public static HashMap<Integer, String> listAllMenu()
  {
    HashMap<Integer, String> list = new HashMap<>();
    list.putAll(listNavigatoin());
    list.putAll(listMain());
    list.putAll(listMaintenance());
    list.putAll(listStock());
    return list;
  }

}

