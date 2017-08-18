package com.mitrakreasindo.pos.main;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableTaxesHelper;
import com.mitrakreasindo.pos.common.Wireless.Wireless_Activity;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.closecash.CloseCashActivity;
import com.mitrakreasindo.pos.main.fragment.MainFragment;
import com.mitrakreasindo.pos.main.fragment.MaintenanceFragment;
import com.mitrakreasindo.pos.main.fragment.StockFragment;
import com.mitrakreasindo.pos.main.maintenance.role.RoleActivity;
import com.mitrakreasindo.pos.main.maintenance.taxes.TaxesActivity;
import com.mitrakreasindo.pos.main.maintenance.user.UserActivity;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.main.stock.category.CategoryActivity;
import com.mitrakreasindo.pos.main.stock.diary.activity.DiaryFormActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Money;
import com.mitrakreasindo.pos.service.DashboardService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener
{
  private String valueUser, valueFullname, valueCompanyName, valueCompanyAddress, valueCompanyPhone;
  private String companyCode, valueCloseCashID;

  private NavigationView navigationView;
  private BluetoothAdapter mBluetoothAdapter = null;
  private BluetoothServerSocket mmServerSocket;
  private TablePeopleHelper tablePeopleHelper;
  private TableRoleHelper tableRoleHelper;
  private ProgressDialog progressDialog;
  private DashboardService dashboardService;
  private MainFragment mainFragment = new MainFragment();
//  private PendingTransactionService service;

  TableCategoryHelper tableCategoryHelper;
  TableProductHelper tableProductHelper;
  TableTaxesHelper tableTaxesHelper;
  TableSalesItemHelper tableSalesItemHelper;

  public Money revenue;
  public Money cost;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    dashboardService = ClientService.createService().create(DashboardService.class);

    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(this.getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

//    service = ClientService.createService().create(PendingTransactionService.class);

    EventBus.getDefault().register(this);

    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        tablePeopleHelper = new TablePeopleHelper(MainActivity.this);
        tableRoleHelper = new TableRoleHelper(MainActivity.this);
        tableCategoryHelper = new TableCategoryHelper(MainActivity.this);
        tableProductHelper = new TableProductHelper(MainActivity.this);
        tableTaxesHelper = new TableTaxesHelper(MainActivity.this);
        tableSalesItemHelper = new TableSalesItemHelper(MainActivity.this);

        tableTaxesHelper.downloadData(companyCode);
        tableCategoryHelper.downloadData(companyCode);
        tableProductHelper.downloadDataAlternate(companyCode);
        tablePeopleHelper.downloadDataAlternate(companyCode, EventCode.EVENT_PEOPLE_GET);

//        progressDialog.post(new Runnable() {
//          public void run() {
//            mImageView.setImageBitmap(bitmap);
//          }
//        });
      }
    }).start();

//    tablePeopleHelper = new TablePeopleHelper(this);
//    tableRoleHelper = new TableRoleHelper(this);
//    tableCategoryHelper = new TableCategoryHelper(this);
//    tableProductHelper = new TableProductHelper(this);
//    tableTaxesHelper = new TableTaxesHelper(this);
//    tableSalesItemHelper = new TableSalesItemHelper(this);


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    valueUser = getIntent().getExtras().getString("USERNAME");
    valueFullname = getIntent().getExtras().getString("FULLNAME");
    valueCompanyName = getIntent().getExtras().getString("COMPANYNAME");
    valueCompanyAddress = getIntent().getExtras().getString("COMPANYADDRESS");
    valueCompanyPhone = getIntent().getExtras().getString("COMPANYPHONE");
    valueCloseCashID = getIntent().getExtras().getString("CLOSECASH_ID");

    IDs.setLoginUser(valueUser);
    IDs.setLoginCompanyName(valueCompanyName);
    IDs.setLoginCompanyAddress(valueCompanyAddress);
    IDs.setLoginCompanyPhone(valueCompanyPhone);
    IDs.setLoginCloseCashID(valueCloseCashID);
    IDs.setLoginUserFullname(valueFullname);

    View headerLayout = navigationView.getHeaderView(0);
    TextView textViewUser = (TextView) headerLayout.findViewById(R.id.textViewUser);
    textViewUser.setText(valueFullname);
    TextView textViewCompany = (TextView) headerLayout.findViewById(R.id.textViewUserDesc);
    textViewCompany.setText(valueCompanyName);

    companyCode = SharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    getRevenue(companyCode, EventCode.EVENT_MONEY_GET);

    getCost(companyCode, EventCode.EVENT_MONEY_GET);

//    Log.d("REVENUE", revenue.getMonth().toString());

    MainFragment mainFragment = new MainFragment();
    getSupportFragmentManager().beginTransaction()
      .replace(R.id.main_content, mainFragment, "MAIN_FRAGMENT").commit();
    getSupportFragmentManager().executePendingTransactions();

//    tableTaxesHelper.downloadData(companyCode);
//    tableCategoryHelper.downloadData(companyCode);
//    tableProductHelper.downloadDataAlternate(companyCode);
//    tablePeopleHelper.downloadDataAlternate(companyCode, EventCode.EVENT_PEOPLE_GET);
  }

  @Override
  protected void onDestroy()
  {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }

  private Fragment getCurrentFragment()
  {
    FragmentManager fragmentManager = getSupportFragmentManager();
    String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
    Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
    return currentFragment;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.dashboard_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

//      @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.wireless, menu);
//        return true;
//    }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_logout)
    {
      Logout();
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item)
  {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nd_logout)
    {
      Logout();
    } else if (id == R.id.nd_maintenance)
    {
      getSupportActionBar().setTitle("Maintenance");
      MaintenanceFragment maintenanceFragment = new MaintenanceFragment();
      getSupportFragmentManager().beginTransaction()
        .replace(R.id.main_content, maintenanceFragment, "MAINTENANCE_FRAGMENT")
        .addToBackStack("MAINTENANCE_FRAGMENT").commit();
      getSupportFragmentManager().executePendingTransactions();
    } else if (id == R.id.nd_stock)
    {
      getSupportActionBar().setTitle("Stock");
      StockFragment stockFragment = new StockFragment();
      getSupportFragmentManager().beginTransaction()
        .replace(R.id.main_content, stockFragment, "STOCK_FRAGMENT")
        .addToBackStack("STOCK_FRAGMENT").commit();
      getSupportFragmentManager().executePendingTransactions();
    } else if (id == R.id.nd_sales)
    {
//      getSupportActionBar().setTitle("Sales");
//      SalesFragment salesFragment = new SalesFragment();
//      getSupportFragmentManager().beginTransaction()
//        .replace(R.id.main_content, salesFragment, "SALES_FRAGMENT")
//        .addToBackStack("SALES_FRAGMENT").commit();
//      getSupportFragmentManager().executePendingTransactions();
      startActivity(new Intent(this, SalesActivity.class));
    } else if (id == R.id.nd_printers)
    {
      startActivity(new Intent(this, Wireless_Activity.class));
    } else if (id == R.id.nd_close_cash)
    {
      if (IDs.getLoginCloseCashID() != null)
        startActivity(new Intent(this, CloseCashActivity.class));
      else
        Toast.makeText(MainActivity.this, R.string.error_close_cash, Toast.LENGTH_SHORT).show();
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void Logout()
  {
    final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
    confirmationDialog.setTitle(R.string.logout_question);
    confirmationDialog.setMessage(R.string.logout_question_message);
    confirmationDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        Toast.makeText(MainActivity.this, getString(R.string.logout_message, valueFullname),
          Toast.LENGTH_SHORT).show();
        finish();
      }
    });

    confirmationDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        dialog.dismiss();
      }
    });
    confirmationDialog.show();
  }

  private void setupNavigation()
  {
    TableRoleHelper tableRoleHelper = new TableRoleHelper(this);
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());
    List<String> navigationList = XMLHelper.XMLReader(this, "navigation", permission);
    ItemVisibility.hideItemNavigation(navigationView, navigationList);

    Log.d(getClass().getSimpleName(), "id login user " + IDs.getLoginUser());
    if (permission != null)
    {
      Log.d(getClass().getSimpleName(), "permission not null " + permission);
    } else
    {
      Log.d(getClass().getSimpleName(), "permission null");
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(Event event)
  {
    switch (event.getId())
    {
      case EventCode.EVENT_PEOPLE_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          tableRoleHelper.downloadData(companyCode, EventCode.EVENT_ROLE_GET);
        }
        break;
      case EventCode.EVENT_ROLE_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          setupNavigation();
//          if (tablePeopleHelper.getRoleID(IDs.getLoginUser()).equals("2"))
//          {
//            downloadUnpaidPendingTransaction(companyCode , EventCode.EVENT_PENDING_TRANSACTION_GET);
//          }
          progressDialog.dismiss();
          Toast.makeText(MainActivity.this, getString(R.string.login_message, valueFullname), Toast.LENGTH_SHORT).show();
        }
        break;
//      case EventCode.EVENT_PENDING_TRANSACTION_GET:
//        if (event.getStatus() == Event.COMPLETE)
//        {
//          mainFragment.pendingTransactionListAdapter.clear();
//          Log.d("COMPLETE GET UNPAID", "clear done");
//          mainFragment.pendingTransactionListAdapter.addPendingTransaction(mainFragment.viewPendingTransactions);
//          Log.d("COMPLETE GET UNPAID", "add done");
//        }
//        break;
    }
  }

  public void openUserActivity(View view)
  {
    startActivity(new Intent(this, UserActivity.class));
  }

  public void openRolesActivity(View view)
  {
    startActivity(new Intent(this, RoleActivity.class));
  }

  public void openTaxesActivity(View view)
  {
    startActivity(new Intent(this, TaxesActivity.class));
  }

  public void openProductsActivity(View view)
  {
    startActivity(new Intent(this, ProductActivity.class));
  }

  public void openCategoriesActivity(View view)
  {
    startActivity(new Intent(this, CategoryActivity.class));
  }

  public void openStockDiaryActivity(View view)
  {
    startActivity(new Intent(this, DiaryFormActivity.class));
  }

  @Override
  public void onBackPressed()
  {
    Fragment fragment = getSupportFragmentManager().findFragmentByTag("MAIN_FRAGMENT");
    if (fragment.isVisible())
      Logout();
    else
      super.onBackPressed();
  }

//  public void downloadUnpaidPendingTransaction(final String kodeMerchant, final int id)
//  {
//    Log.d("GET API", "get unpaid trans");
//    Call<List<ViewPendingTransaction>> call = service.getAllPendingTransaction(kodeMerchant);
//    call.enqueue(new Callback<List<ViewPendingTransaction>>()
//    {
//      @Override
//      public void onResponse(Call<List<ViewPendingTransaction>> call, Response<List<ViewPendingTransaction>> response)
//      {
//        Log.d("COMPLETE GET API", "get unpaid trans done");
//        mainFragment.viewPendingTransactions = response.body();
//        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
//      }
//
//      @Override
//      public void onFailure(Call<List<ViewPendingTransaction>> call, Throwable t)
//      {
//        Log.d("COMPLETE GET API", "get unpaid trans failed");
//        Toast.makeText(MainActivity.this, getString(R.string.error_webservice),
//          Toast.LENGTH_LONG).show();
//      }
//    });
//  }

  public void getRevenue(String kodeMerchant, final int id)
  {
    final Call<Money> call = dashboardService.getRevenue(kodeMerchant);
    call.enqueue(new Callback<Money>()
    {
      @Override
      public void onResponse(Call<Money> call, Response<Money> response)
      {
        revenue = response.body();

        Log.d("REVENUE", revenue.getMonth().toString());

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));

      }

      @Override
      public void onFailure(Call<Money> call, Throwable t)
      {

      }

    });

  }

  public void getCost(String kodeMerchant, final int id)
  {
    final Call<Money> call = dashboardService.getCost(kodeMerchant);
    call.enqueue(new Callback<Money>()
    {
      @Override
      public void onResponse(Call<Money> call, Response<Money> response)
      {
        cost = response.body();

        Log.d("REVENUE", cost.getMonth().toString());

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));

      }

      @Override
      public void onFailure(Call<Money> call, Throwable t)
      {

      }

    });

  }

}
