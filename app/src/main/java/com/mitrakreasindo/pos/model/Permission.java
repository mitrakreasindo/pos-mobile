package com.mitrakreasindo.pos.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miftakhul on 7/18/17.
 */

public class Permission
{
  @ElementList(entry = "inactive", name = "navigation", required = false)
  public List<String> navigation = new ArrayList<>();

  @ElementList(entry = "inactive", name = "main", required = false)
  public List<String> main = new ArrayList<>();

  @ElementList(entry = "inactive", name = "stock", required = false)
  public List<String> stock = new ArrayList<>();

  @ElementList(entry = "inactive", name = "stock_product_action", required = false)
  public List<String> stockProductAction = new ArrayList<>();

  @ElementList(entry = "inactive", name = "stock_category_action", required = false)
  public List<String> stockCategoryAction = new ArrayList<>();

  @ElementList(entry = "inactive", name = "maintenance", required = false)
  public List<String> maintenance = new ArrayList<>();

  @ElementList(entry = "inactive", name = "maintenance_user_action", required = false)
  public List<String> maintenanceUserAction = new ArrayList<>();

  @ElementList(entry = "inactive", name = "maintenance_role_action", required = false)
  public List<String> maintenanceRoleAction = new ArrayList<>();

  @ElementList(entry = "inactive", name = "maintenance_tax_action", required = false)
  public List<String> maintenanceTaxAction = new ArrayList<>();

}