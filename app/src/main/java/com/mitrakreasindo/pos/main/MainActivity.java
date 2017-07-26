package com.mitrakreasindo.pos.main;

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

import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableTaxesHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.fragment.MainFragment;
import com.mitrakreasindo.pos.main.fragment.MaintenanceFragment;
import com.mitrakreasindo.pos.main.fragment.SalesFragment;
import com.mitrakreasindo.pos.main.fragment.StockFragment;
import com.mitrakreasindo.pos.main.maintenance.role.RoleActivity;
import com.mitrakreasindo.pos.main.maintenance.taxes.TaxesActivity;
import com.mitrakreasindo.pos.main.maintenance.user.UserActivity;
import com.mitrakreasindo.pos.main.stock.category.CategoryActivity;
import com.mitrakreasindo.pos.main.stock.diary.activity.DiaryFormActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductFormActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener
{
  private String valueUser;
  private int mPrevSelectedId;
  private String companyCode;

  private NavigationView navigationView;

  TablePeopleHelper tablePeopleHelper;
  TableRoleHelper tableRoleHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    EventBus.getDefault().register(this);

    tablePeopleHelper = new TablePeopleHelper(this);
    tableRoleHelper = new TableRoleHelper(this);
    TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(this);
    TableProductHelper tableProductHelper = new TableProductHelper(this);
    TableTaxesHelper tableTaxesHelper = new TableTaxesHelper(this);

//    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View view)
//      {
//        startActivity(new Intent(MainActivity.this, RoleActivity.class));
////                Logout();
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//      }
//    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    valueUser = getIntent().getExtras().getString("USERNAME");
    IDs.setLoginUser(valueUser);
    Toast.makeText(this, "Welcome " + valueUser, Toast.LENGTH_SHORT).show();
    Toast.makeText(this, "Current Schema: " +
      getIntent().getExtras().getString("COMPANY"), Toast.LENGTH_SHORT).show();

    View headerLayout = navigationView.getHeaderView(0);
    TextView textViewUser = (TextView) headerLayout.findViewById(R.id.textViewUser);
    textViewUser.setText(valueUser);

    companyCode = SharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    MainFragment mainFragment = new MainFragment();
    getSupportFragmentManager().beginTransaction()
      .replace(R.id.main_content, mainFragment, "MAIN_FRAGMENT").commit();
    getSupportFragmentManager().executePendingTransactions();

//    tablePeopleHelper.downloadDataAlternate(companyCode, EventCode.EVENT_PEOPLE_GET);
//    tableCategoryHelper.downloadData(companyCode);
    tableProductHelper.downloadDataAlternate(companyCode);
    tableTaxesHelper.downloadData(companyCode);

//    setupNavigation();
  }


  @Override
  protected void onStop()
  {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  private Fragment getCurrentFragment()
  {
    FragmentManager fragmentManager = getSupportFragmentManager();
    String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
    Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
    return currentFragment;
  }

//  @Override
//  public void onBackPressed()
//  {
//    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//    if (drawer.isDrawerOpen(GravityCompat.START))
//    {
//      drawer.closeDrawer(GravityCompat.START);
//    }
//    else
//    {
//      Fragment mainFragment = getFragmentManager().findFragmentByTag("MAIN_FRAGMENT");
//      if (mainFragment == null)
//      {
//        new AlertDialog.Builder(this)
//          .setIcon(android.R.drawable.ic_dialog_alert)
//          .setTitle("Logout from POS++")
//          .setMessage("Are you sure you want to logout?")
//          .setPositiveButton
//            (
//              "Yes", new DialogInterface.OnClickListener()
//              {
//                @Override
//                public void onClick(DialogInterface dialog, int which)
//                {
//                  finish();
//                }
//              }
//            )
//          .setNegativeButton("No", null)
//          .show();
//      }
//      else
//      {
//        super.onBackPressed();
//        Toast.makeText(this, "TEST CLOSE", Toast.LENGTH_LONG).show();
//      }
//    }
//  }

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
//        getMenuInflater().inflate(R.menu.main, menu);
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
      Toast.makeText(this, "Goodbye " + valueUser + ". See you next time :)", Toast.LENGTH_SHORT).show();
      finish();
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
    }
    else if (id == R.id.nd_maintenance)
    {
      Toast.makeText(this, "Maintenance", Toast.LENGTH_LONG).show();
      getSupportActionBar().setTitle("Maintenance");
      MaintenanceFragment maintenanceFragment = new MaintenanceFragment();
      getSupportFragmentManager().beginTransaction()
        .replace(R.id.main_content, maintenanceFragment, "MAINTENANCE_FRAGMENT")
        .addToBackStack("MAINTENANCE_FRAGMENT").commit();
      getSupportFragmentManager().executePendingTransactions();
    }
    else if (id == R.id.nd_stock)
    {
      Toast.makeText(this, "Stock", Toast.LENGTH_LONG).show();
      getSupportActionBar().setTitle("Stock");
      StockFragment stockFragment = new StockFragment();
      getSupportFragmentManager().beginTransaction()
        .replace(R.id.main_content, stockFragment, "STOCK_FRAGMENT")
        .addToBackStack("STOCK_FRAGMENT").commit();
      getSupportFragmentManager().executePendingTransactions();
    }
    else if (id == R.id.nd_sales)
    {
      Toast.makeText(this, "Sales", Toast.LENGTH_LONG).show();
      getSupportActionBar().setTitle("Sales");
      SalesFragment salesFragment = new SalesFragment();
      getSupportFragmentManager().beginTransaction()
        .replace(R.id.main_content, salesFragment, "SALES_FRAGMENT")
        .addToBackStack("SALES_FRAGMENT").commit();
      getSupportFragmentManager().executePendingTransactions();
    }
    else if (id == R.id.nd_customer_payment)
    {
      Toast.makeText(this, "Product", Toast.LENGTH_LONG).show();
      startActivity(new Intent(this, ProductFormActivity.class));
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void Logout()
  {
    Toast.makeText(this, "Goodbye " + valueUser + ". See you next time :)", Toast.LENGTH_SHORT).show();
    finish();
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
    }
    else
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
        }
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
    {
      final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
      confirmationDialog.setTitle(R.string.logout_question);
      confirmationDialog.setMessage(R.string.logout_question_message);
      confirmationDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
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
    else
    {
      super.onBackPressed();
    }

  }


}
