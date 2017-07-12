package com.mitrakreasindo.pos.main.maintenance.taxes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableTaxesHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.taxes.controller.TaxesListAdapter;
import com.mitrakreasindo.pos.main.maintenance.taxes.service.TaxService;
import com.mitrakreasindo.pos.main.stock.category.CategoryFormActivity;
import com.mitrakreasindo.pos.model.Tax;
import com.mitrakreasindo.pos.model.TaxCategory;
import com.mitrakreasindo.pos.model.TaxCusCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaxesFormActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.main_content)
  CoordinatorLayout mainContent;
  @BindView(R.id.tax_field)
  EditText taxField;
  @BindView(R.id.rate_field)
  EditText rateField;

  private Tax tax;
  private TaxService taxService;

  private TaxesListAdapter taxesListAdapter;

  private Bundle bundle;

  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tax_form);
    ButterKnife.bind(this);

    taxService = ClientService.createService().create(TaxService.class);

    taxesListAdapter = new TaxesListAdapter(this, new ArrayList<Tax>());

    sharedPreferenceEditor = new SharedPreferenceEditor();

    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "");

    bundle = getIntent().getExtras();

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    if (bundle != null)
    {
      String name = bundle.getString("name");
      String rate = bundle.getString("rate");
      taxField.setText(name);
      rateField.setText(rate);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_form_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();
    if (id == R.id.action_confirm)
    {
      if (bundle != null)
      {
        updateCategory();
        Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
      }
      else
      {
        postCategory();
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
      }

    }
    return super.onOptionsItemSelected(item);
  }

  private void postCategory()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();

    TaxCategory taxCategory = new TaxCategory();
    taxCategory.setId(UUID.randomUUID().toString());

    TaxCusCategory taxCusCategory = new TaxCusCategory();
    taxCusCategory.setId(null);

    Tax tax1 = new Tax();
    tax1.setId(null);

    final Tax tax = new Tax();
    tax.setId(UUID.randomUUID().toString());
    tax.setName(taxField.getText().toString());
    tax.setRate(Double.valueOf(rateField.getText().toString()));
    tax.setCategory(taxCategory);
    tax.setCustcategory(taxCusCategory);
    tax.setParentid(tax1);

    Call<HashMap<Integer, String>> call = taxService.postTax(kodeMerchant, tax);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {

      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {

        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0)
          {
            TableTaxesHelper tableTaxesHelper = new TableTaxesHelper(TaxesFormActivity.this);
            tableTaxesHelper.open();
            tableTaxesHelper.insert(tax);
            tableTaxesHelper.close();

            taxesListAdapter.addTax(tax);
            taxesListAdapter.notifyDataSetChanged();
          }
          Toast.makeText(TaxesFormActivity.this, "Succesfull add taxes", Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
      }
    });

  }

  private void updateCategory()
  {

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();

    String id = bundle.getString("id");
    String categoryId = bundle.getString("category");

    TaxCategory taxCategory = new TaxCategory();
    taxCategory.setId(categoryId);

    TaxCusCategory taxCusCategory = new TaxCusCategory();
    taxCusCategory.setId(null);

    Tax tax1 = new Tax();
    tax1.setId(null);

    final Tax tax = new Tax();
    tax.setId(id);
    tax.setName(taxField.getText().toString());
    tax.setRate(Double.valueOf(rateField.getText().toString()));
    tax.setCategory(taxCategory);
    tax.setCustcategory(taxCusCategory);
    tax.setParentid(tax1);

    Call<HashMap<Integer, String>> call = taxService.updateTax(kodeMerchant, id, tax);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {

      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {

        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0)
          {
            TableTaxesHelper tableTaxesHelper = new TableTaxesHelper(TaxesFormActivity.this);
            tableTaxesHelper.open();
            tableTaxesHelper.update(tax);
            tableTaxesHelper.close();

            taxesListAdapter.addTax(tax);
            taxesListAdapter.notifyDataSetChanged();

          }
          Toast.makeText(TaxesFormActivity.this, "Succesfull update tax", Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
      }
    });

  }
}