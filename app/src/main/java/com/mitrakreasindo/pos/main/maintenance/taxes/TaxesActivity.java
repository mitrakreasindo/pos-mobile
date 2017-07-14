package com.mitrakreasindo.pos.main.maintenance.taxes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableTaxesHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.taxes.controller.TaxesListAdapter;
import com.mitrakreasindo.pos.main.maintenance.taxes.service.TaxService;
import com.mitrakreasindo.pos.model.Tax;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaxesActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_category)
  RecyclerView listCategory;
  @BindView(R.id.main_content)
  LinearLayout mainContent;
  @BindView(R.id.edit_text_filter)
  EditText editTextFilter;

  private Tax tax;
  private TaxService taxService;

  private TaxesListAdapter taxesListAdapter;

  private TableTaxesHelper tableTaxesHelper;

  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    sharedPreferenceEditor = new SharedPreferenceEditor();

    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    taxService = ClientService.createService().create(TaxService.class);

    taxesListAdapter = new TaxesListAdapter(this, new ArrayList<Tax>());

    listCategory.setHasFixedSize(true);
    listCategory.setAdapter(taxesListAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listCategory.setLayoutManager(layoutManager);

    tableTaxesHelper = new TableTaxesHelper(this);

    taxesListAdapter.clear();
    taxesListAdapter.addTax(tableTaxesHelper.getData());

    editTextFilter.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        taxesListAdapter.clear();
        taxesListAdapter.addTax(tableTaxesHelper.getData(editTextFilter.getText().toString()));
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      {

      }

      @Override
      public void afterTextChanged(Editable s)
      {

      }
    });

  }

  @Override
  protected void onStart()
  {
    super.onStart();
    taxesListAdapter.clear();
    taxesListAdapter.addTax(tableTaxesHelper.getData());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_list_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_add)
    {
      startActivity(new Intent(this, TaxesFormActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }

//  private void getTaxes(String kodeMerchant)
//  {
//    final Call<List<Tax>> tax = taxService.getTaxAll(kodeMerchant);
//    tax.enqueue(new Callback<List<Tax>>()
//    {
//      @Override
//      public void onResponse(Call<List<Tax>> call, Response<List<Tax>> response)
//      {
//        List<Tax> taxList = response.body();
//        taxesListAdapter.clear();
//        taxesListAdapter.addTax(taxList);
//      }
//
//      @Override
//      public void onFailure(Call<List<Tax>> call, Throwable t)
//      {
//
//      }
//    });
//
//  }
}
