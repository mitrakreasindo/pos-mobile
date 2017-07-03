package com.mitrakreasindo.pos.main;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.main.fragment.MainFragment;
import com.mitrakreasindo.pos.main.fragment.MaintenanceFragment;
import com.mitrakreasindo.pos.main.fragment.SalesFragment;
import com.mitrakreasindo.pos.main.fragment.StockFragment;
import com.mitrakreasindo.pos.main.stock.product.ProductFormActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
  private String valueUser;
  private int mPrevSelectedId;
  private String companyCode;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

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

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    ItemVisibility.hideItemNavigation(navigationView);
    navigationView.setNavigationItemSelectedListener(this);

    valueUser = getIntent().getExtras().getString("USERNAME");
    Toast.makeText(this, "Welcome " + valueUser, Toast.LENGTH_SHORT).show();
    Toast.makeText(this, "Current Schema: " +
            getIntent().getExtras().getString("COMPANY"), Toast.LENGTH_SHORT).show();

    View headerLayout = navigationView.getHeaderView(0);
    TextView textViewUser = (TextView) headerLayout.findViewById(R.id.textViewUser);
    textViewUser.setText(valueUser);

    companyCode = SharedPreferenceEditor.LoadPreferences(this, "");

    MainFragment mainFragment = new MainFragment();
    getSupportFragmentManager().beginTransaction()
      .replace(R.id.main_content, mainFragment, "MAIN_FRAGMENT").commit();
    getSupportFragmentManager().executePendingTransactions();

    TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(this);
    tablePeopleHelper.downloadData(companyCode);
    TableRoleHelper tableRoleHelper = new TableRoleHelper(this);
    tableRoleHelper.downloadData(companyCode);
    TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(this);
    tableCategoryHelper.downloadData(companyCode);
    TableProductHelper tableProductHelper = new TableProductHelper(this);
    tableProductHelper.downloadDataAlternate(companyCode);
  }

  @Override
  public void onBackPressed()
  {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START))
    {
      drawer.closeDrawer(GravityCompat.START);
    }
    else
    {
      Fragment mainFragment = getFragmentManager().findFragmentByTag("MAIN_FRAGMENT");
      if (mainFragment != null && mainFragment.isVisible())
      {
        new AlertDialog.Builder(this)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .setTitle("Logout from POS++")
          .setMessage("Are you sure you want to logout?")
          .setPositiveButton
            (
              "Yes", new DialogInterface.OnClickListener()
              {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                  finish();
                }
              }
            )
          .setNegativeButton("No", null)
          .show();
      }
      else
      {
        super.onBackPressed();
      }
    }
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
}
