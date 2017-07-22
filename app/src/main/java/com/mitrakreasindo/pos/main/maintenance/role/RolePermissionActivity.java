package com.mitrakreasindo.pos.main.maintenance.role;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Permission;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.RoleService;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by miftakhul on 7/18/17.
 */

public class RolePermissionActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.edit_role)
  EditText editRole;
  @BindView(R.id.rp_sales)
  CheckBox rpSales;
  @BindView(R.id.rp_edit_sales)
  CheckBox rpEditSales;
  @BindView(R.id.rp_customer_payment)
  CheckBox rpCustomerPayment;
  @BindView(R.id.rp_payment)
  CheckBox rpPayment;
  @BindView(R.id.rp_close_cash)
  CheckBox rpCloseCash;
  @BindView(R.id.rp_btn_menu_sales)
  CheckBox rpBtnMenuSales;
  @BindView(R.id.rp_btn_menu_data)
  CheckBox rpBtnMenuData;
  @BindView(R.id.rp_btn_menu_receive)
  CheckBox rpBtnMenuReceive;
  @BindView(R.id.rp_btn_menu_setting)
  CheckBox rpBtnMenuSetting;
  @BindView(R.id.rp_btn_menu_report)
  CheckBox rpBtnMenuReport;
  @BindView(R.id.rp_btn_menu_export)
  CheckBox rpBtnMenuExport;
  @BindView(R.id.rp_customer)
  CheckBox rpCustomer;
  @BindView(R.id.rp_stock)
  CheckBox rpStock;
  @BindView(R.id.rp_stk_more)
  Button rpStkMore;
  @BindView(R.id.rp_stk_more_layout)
  LinearLayout rpStkMoreLayout;
  @BindView(R.id.rp_stk_mnt_products)
  CheckBox rpStkMntProducts;
  @BindView(R.id.rp_stk_mnt_product_location)
  CheckBox rpStkMntProductLocation;
  @BindView(R.id.rp_stk_mnt_auxiliary_products)
  CheckBox rpStkMntAuxiliaryProducts;
  @BindView(R.id.rp_stk_mnt_categories)
  CheckBox rpStkMntCategories;
  @BindView(R.id.rp_stk_mnt_product_attributes)
  CheckBox rpStkMntProductAttributes;
  @BindView(R.id.rp_stk_mnt_attributes_values)
  CheckBox rpStkMntAttributesValues;
  @BindView(R.id.rp_stk_mnt_product_attribute_sets)
  CheckBox rpStkMntProductAttributeSets;
  @BindView(R.id.rp_stk_mnt_product_attribute_use)
  CheckBox rpStkMntProductAttributeUse;
  @BindView(R.id.rp_stk_mnt_stock_diary)
  CheckBox rpStkMntStockDiary;
  @BindView(R.id.rp_stk_mnt_stock_movement)
  CheckBox rpStkMntStockMovement;
  @BindView(R.id.rp_stk_mnt_promotions)
  CheckBox rpStkMntPromotions;
  @BindView(R.id.rp_stk_mnt_recipes)
  CheckBox rpStkMntRecipes;
  @BindView(R.id.rp_stk_report_labels_barcode)
  CheckBox rpStkReportLabelsBarcode;
  @BindView(R.id.rp_stk_report_inventory)
  CheckBox rpStkReportInventory;
  @BindView(R.id.rp_stk_report_current_directory)
  CheckBox rpStkReportCurrentDirectory;
  @BindView(R.id.rp_stk_report_inventory_broken)
  CheckBox rpStkReportInventoryBroken;
  @BindView(R.id.rp_stk_report_inventory_difference)
  CheckBox rpStkReportInventoryDifference;
  @BindView(R.id.rp_stk_report_inventory_difference_detail)
  CheckBox rpStkReportInventoryDifferenceDetail;
  @BindView(R.id.rp_stk_report_inventory_list_detaile)
  CheckBox rpStkReportInventoryListDetaile;
  @BindView(R.id.rp_stk_report_inventory_reorder)
  CheckBox rpStkReportInventoryReorder;
  @BindView(R.id.rp_stk_report_product_catalogue_list)
  CheckBox rpStkReportProductCatalogueList;
  @BindView(R.id.rp_stk_report_products)
  CheckBox rpStkReportProducts;
  @BindView(R.id.rp_stk_report_product_labels)
  CheckBox rpStkReportProductLabels;
  @BindView(R.id.rp_stk_report_catalogue)
  CheckBox rpStkReportCatalogue;
  @BindView(R.id.rp_stk_report_labels_shelf_egde_labels)
  CheckBox rpStkReportLabelsShelfEgdeLabels;
  @BindView(R.id.rp_stk_layout)
  LinearLayout rpStkLayout;
  @BindView(R.id.rp_administration_sales)
  CheckBox rpAdministrationSales;
  @BindView(R.id.rp_maintenance)
  CheckBox rpMaintenance;
  @BindView(R.id.rp_mtc_more)
  Button rpMtcMore;
  @BindView(R.id.rp_mtc_more_layout)
  LinearLayout rpMtcMoreLayout;
  @BindView(R.id.rp_mtc_users)
  CheckBox rpMtcUsers;
  @BindView(R.id.rp_mtc_roles)
  CheckBox rpMtcRoles;
  @BindView(R.id.rp_mtc_taxes)
  CheckBox rpMtcTaxes;
  @BindView(R.id.rp_mtc_tax_categories)
  CheckBox rpMtcTaxCategories;
  @BindView(R.id.rp_mtc_consumer_tax_categories)
  CheckBox rpMtcConsumerTaxCategories;
  @BindView(R.id.rp_mtc_resources)
  CheckBox rpMtcResources;
  @BindView(R.id.rp_mtc_locations)
  CheckBox rpMtcLocations;
  @BindView(R.id.rp_mtc_floors)
  CheckBox rpMtcFloors;
  @BindView(R.id.rp_mtc_tables)
  CheckBox rpMtcTables;
  @BindView(R.id.rp_mtc_reset_pickup_counter)
  CheckBox rpMtcResetPickupCounter;
  @BindView(R.id.rp_mtc_release_table_locks)
  CheckBox rpMtcReleaseTableLocks;
  @BindView(R.id.rp_mtc_report_user)
  CheckBox rpMtcReportUser;
  @BindView(R.id.rp_mtc_sales_by_user)
  CheckBox rpMtcSalesByUser;
  @BindView(R.id.rp_mtc_layout)
  LinearLayout rpMtcLayout;
  @BindView(R.id.rp_presence_management)
  CheckBox rpPresenceManagement;
  @BindView(R.id.rp_tools)
  CheckBox rpTools;
  @BindView(R.id.rp_change_password)
  CheckBox rpChangePassword;
  @BindView(R.id.rp_configuration)
  CheckBox rpConfiguration;
  @BindView(R.id.rp_printers)
  CheckBox rpPrinters;
  @BindView(R.id.rp_check_in_out)
  CheckBox rpCheckInOut;
  @BindView(R.id.rp_logout)
  CheckBox rpLogout;
  private Role role;
  private Permission permission;
  private RoleService roleService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  private Serializer serializer;
  private HashMap<Integer, String> listMenuId;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_permission);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });

    roleService = ClientService.createService().create(RoleService.class);

    sharedPreferenceEditor = new SharedPreferenceEditor();
    serializer = new Persister();
    listMenuId = new HashMap<>();

    listMenuId.putAll(MenuIds.listStock());


    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    final Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      String name = bundle.getString("name");
      String roleId = bundle.getString("id");
      byte[] bytePermission = bundle.getByteArray("permission");

      if (bytePermission != null) {
        try {
          Log.d("permission byte", "exist");
          String permissionXml = new String(bytePermission);
          Log.d("permission xml", permissionXml);
          permission = serializer.read(Permission.class, permissionXml);

          StringWriter newxml = new StringWriter();
          serializer.write(permission, newxml);
          Log.d("new permission xml", newxml.toString());

        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        Log.d("permission byte", "not exist");
      }

      editRole.setText(name);
    }
    setupDefaultPermission(permission);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.default_form_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    final Bundle bundle = getIntent().getExtras();

    Log.d("xml permission ", generateXmlPermission());

    if (id == R.id.action_confirm) {
      if (bundle != null) {
        updateRole();
      } else {
        postRole();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  @BindViews({R.id.rp_sales, R.id.rp_edit_sales, R.id.rp_customer_payment, R.id.rp_payment,
          R.id.rp_close_cash, R.id.rp_customer, R.id.rp_stock, R.id.rp_administration_sales,
          R.id.rp_maintenance, R.id.rp_presence_management, R.id.rp_tools, R.id.rp_change_password,
          R.id.rp_configuration, R.id.rp_printers, R.id.rp_check_in_out, R.id.rp_logout,
          R.id.rp_btn_menu_sales, R.id.rp_btn_menu_data, R.id.rp_btn_menu_receive,
          R.id.rp_btn_menu_setting, R.id.rp_btn_menu_report, R.id.rp_btn_menu_export,
          R.id.rp_mtc_users, R.id.rp_mtc_roles, R.id.rp_mtc_taxes, R.id.rp_mtc_tax_categories,
          R.id.rp_mtc_consumer_tax_categories, R.id.rp_mtc_resources, R.id.rp_mtc_locations,
          R.id.rp_mtc_floors, R.id.rp_mtc_tables, R.id.rp_mtc_reset_pickup_counter,
          R.id.rp_mtc_release_table_locks, R.id.rp_mtc_report_user, R.id.rp_mtc_sales_by_user,
          R.id.rp_stk_mnt_products, R.id.rp_stk_mnt_product_location,
          R.id.rp_stk_mnt_auxiliary_products, R.id.rp_stk_mnt_categories,
          R.id.rp_stk_mnt_product_attributes, R.id.rp_stk_mnt_attributes_values,
          R.id.rp_stk_mnt_product_attribute_sets, R.id.rp_stk_mnt_product_attribute_use,
          R.id.rp_stk_mnt_stock_diary, R.id.rp_stk_mnt_stock_movement, R.id.rp_stk_mnt_promotions,
          R.id.rp_stk_mnt_recipes, R.id.rp_stk_report_labels_barcode, R.id.rp_stk_report_inventory,
          R.id.rp_stk_report_current_directory, R.id.rp_stk_report_inventory_broken,
          R.id.rp_stk_report_inventory_difference, R.id.rp_stk_report_inventory_difference_detail,
          R.id.rp_stk_report_inventory_list_detaile, R.id.rp_stk_report_inventory_reorder,
          R.id.rp_stk_report_product_catalogue_list, R.id.rp_stk_report_products,
          R.id.rp_stk_report_product_labels, R.id.rp_stk_report_catalogue,
          R.id.rp_stk_report_labels_shelf_egde_labels
  })
  List<CheckBox> checkBoxMenus;

  @OnCheckedChanged({R.id.rp_sales, R.id.rp_edit_sales, R.id.rp_customer_payment, R.id.rp_payment,
          R.id.rp_close_cash, R.id.rp_customer, R.id.rp_stock, R.id.rp_administration_sales,
          R.id.rp_maintenance, R.id.rp_presence_management, R.id.rp_tools, R.id.rp_change_password,
          R.id.rp_configuration, R.id.rp_printers, R.id.rp_check_in_out, R.id.rp_logout,
          R.id.rp_btn_menu_sales, R.id.rp_btn_menu_data, R.id.rp_btn_menu_receive,
          R.id.rp_btn_menu_setting, R.id.rp_btn_menu_report, R.id.rp_btn_menu_export,
          R.id.rp_mtc_users, R.id.rp_mtc_roles, R.id.rp_mtc_taxes, R.id.rp_mtc_tax_categories,
          R.id.rp_mtc_consumer_tax_categories, R.id.rp_mtc_resources, R.id.rp_mtc_locations,
          R.id.rp_mtc_floors, R.id.rp_mtc_tables, R.id.rp_mtc_reset_pickup_counter,
          R.id.rp_mtc_release_table_locks, R.id.rp_mtc_report_user, R.id.rp_mtc_sales_by_user,
          R.id.rp_stk_mnt_products, R.id.rp_stk_mnt_product_location,
          R.id.rp_stk_mnt_auxiliary_products, R.id.rp_stk_mnt_categories,
          R.id.rp_stk_mnt_product_attributes, R.id.rp_stk_mnt_attributes_values,
          R.id.rp_stk_mnt_product_attribute_sets, R.id.rp_stk_mnt_product_attribute_use,
          R.id.rp_stk_mnt_stock_diary, R.id.rp_stk_mnt_stock_movement, R.id.rp_stk_mnt_promotions,
          R.id.rp_stk_mnt_recipes, R.id.rp_stk_report_labels_barcode, R.id.rp_stk_report_inventory,
          R.id.rp_stk_report_current_directory, R.id.rp_stk_report_inventory_broken,
          R.id.rp_stk_report_inventory_difference, R.id.rp_stk_report_inventory_difference_detail,
          R.id.rp_stk_report_inventory_list_detaile, R.id.rp_stk_report_inventory_reorder,
          R.id.rp_stk_report_product_catalogue_list, R.id.rp_stk_report_products,
          R.id.rp_stk_report_product_labels, R.id.rp_stk_report_catalogue,
          R.id.rp_stk_report_labels_shelf_egde_labels})
  void onChecked(CheckBox checkBox, boolean checked) {
    checkPermission(checkBox.getId(), checked);
  }

  @BindViews({R.id.rp_mtc_more, R.id.rp_stk_more})
  List<Button> moreButtons;

  @OnClick({R.id.rp_mtc_more, R.id.rp_stk_more})
  public void onMoreClick(Button button) {
    switch (button.getId()) {
      case R.id.rp_mtc_more:
        setVisibility(rpMtcLayout);
        break;
      case R.id.rp_stk_more:
        setVisibility(rpStkLayout);
        break;
    }

  }

  private void setupDefaultPermission(Permission permission) {
    if (permission == null) {
      this.permission = new Permission();
      this.permission.navigation.addAll(MenuIds.listNavigatoin().values());
      this.permission.main.addAll(MenuIds.listMain().values());
      this.permission.maintenace.addAll(MenuIds.listMaintenance().values());
      this.permission.stock.addAll(MenuIds.listStock().values());
    } else {
      List<String> inActivePermission = new ArrayList<>();
      inActivePermission.addAll(this.permission.navigation);
      inActivePermission.addAll(this.permission.main);
      inActivePermission.addAll(this.permission.maintenace);
      inActivePermission.addAll(this.permission.stock);

      // set default permission active
      setCheckAll(CHECK);

      for (String inactive : inActivePermission) {
        // set inactive permisssion
        setCheck(inactive, false);
      }

    }

  }

  private void setCheck(String inactive, boolean isCheck) {
    setInactive(inactive, isCheck);
  }

  private void checkPermission(int checkBoxId, boolean checked) {
    if (MenuIds.listNavigatoin().containsKey(checkBoxId)) {
      setPermission(permission.navigation, checked, MenuIds.listNavigatoin().get(checkBoxId));
    } else if (MenuIds.listMain().containsKey(checkBoxId)) {
      setPermission(permission.main, checked, MenuIds.listMain().get(checkBoxId));
    } else if (MenuIds.listMaintenance().containsKey(checkBoxId)) {
      setPermission(permission.maintenace, checked, MenuIds.listMaintenance().get(checkBoxId));
    } else if (MenuIds.listStock().containsKey(checkBoxId)) {
      setPermission(permission.stock, checked, MenuIds.listStock().get(checkBoxId));
    }

    setSubMenu(checkBoxId, checked);
  }

  private void setSubMenu(int checkBoxId, boolean checked) {
    switch (checkBoxId) {
      case R.id.rp_maintenance:
        setVisibility(rpMtcMoreLayout, rpMtcLayout, checked);
        break;
      case R.id.rp_stock:
        setVisibility(rpStkMoreLayout, rpStkLayout, checked);
        break;
    }
  }


  private void setInactive(String inactive, boolean isCheck) {
    Integer key = null;
    for (Map.Entry<Integer, String> entry : MenuIds.listAllMenu().entrySet()) {
      if (entry.getValue().equals(inactive)) {
        key = entry.getKey();
      }
    }

    if (key != null) {
      for (CheckBox c : checkBoxMenus) {
        if (c.getId() == key) {
          c.setChecked(isCheck);
        }
      }
    }

  }

  private void setCheckAll(ButterKnife.Action<CheckBox> isCheck) {
    ButterKnife.apply(checkBoxMenus, isCheck);
  }

  private void setVisibility(View headLayout, View contentLayout, boolean isCheck) {
    setVisibility(headLayout, isCheck);
    if (!isCheck) {
      setVisibility(contentLayout, isCheck);
    }
  }

  private void setVisibility(View view) {
    if (view.getVisibility() == View.GONE) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }

  private void setVisibility(View view, boolean isCheck) {
    if (isCheck) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }

  private void setPermission(List<String> inactiveList, boolean checked, String targetId) {
    if (checked) {
      if (inactiveList.contains(targetId)) {
        inactiveList.remove(targetId);
      }
      Log.d(getClass().getSimpleName(), "checked");
    } else {
      if (!inactiveList.contains(targetId)) {
        inactiveList.add(targetId);
      }
      Log.d(getClass().getSimpleName(), "not checked");
    }
  }

  private String generateXmlPermission() {
    StringWriter xml = new StringWriter();

    try {
      serializer.write(permission, xml);
      return xml.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  static final ButterKnife.Action<CheckBox> CHECK = new ButterKnife.Action<CheckBox>() {
    @Override
    public void apply(@NonNull CheckBox checkBox, int index) {
      checkBox.setChecked(true);
    }
  };

  static final ButterKnife.Action<CheckBox> UNCHECK = new ButterKnife.Action<CheckBox>() {
    @Override
    public void apply(@NonNull CheckBox checkBox, int index) {
      checkBox.setChecked(false);
    }
  };

  private void postRole() {
    Bundle bundle = getIntent().getExtras();

    final ProgressDialog progressDialog = new ProgressDialog(RolePermissionActivity.this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    Log.d(getClass().getSimpleName(), "Post Role !!!");
    Log.d(getClass().getSimpleName(), editRole.getText().toString());

    role = new Role();
    role.setId(UUID.randomUUID().toString());
    role.setName(editRole.getText().toString());
    role.setRightslevel(3);
    role.setPermissions(generateXmlPermission().getBytes());
    role.setSiteguid(RestVariable.SITE_GUID);
    role.setSflag(true);

    Call<HashMap<Integer, String>> call = roleService.postRole(kodeMerchant, role);
    call.enqueue(new Callback<HashMap<Integer, String>>() {

      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response) {
        Log.d(getClass().getSimpleName(), "Success Post Role !!!");

        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet()) {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0) {
            TableRoleHelper roleHelper = new TableRoleHelper(RolePermissionActivity.this);
            roleHelper.open();
            roleHelper.insert(role);
            roleHelper.close();

            progressDialog.dismiss();
            onBackPressed();
          }
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t) {
        progressDialog.dismiss();
        onBackPressed();
      }
    });

  }

  private void updateRole() {
    final ProgressDialog progressDialog = new ProgressDialog(RolePermissionActivity.this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();

    Bundle bundle = getIntent().getExtras();
    String id = bundle.getString("id");

    role = new Role();
    role.setId(id);
    role.setName(editRole.getText().toString());
    role.setRightslevel(3);
    role.setPermissions(generateXmlPermission().getBytes());
    role.setSiteguid(RestVariable.SITE_GUID);
    role.setSflag(true);

    Call<HashMap<Integer, String>> call = roleService.updateRole(kodeMerchant, id, role);
    call.enqueue(new Callback<HashMap<Integer, String>>() {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response) {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet()) {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0) {
            TableRoleHelper roleHelper = new TableRoleHelper(RolePermissionActivity.this);
            roleHelper.open();
            roleHelper.update(role);
            roleHelper.close();

            progressDialog.dismiss();
            onBackPressed();
          }
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t) {
        progressDialog.dismiss();
        onBackPressed();
      }
    });
  }


}
