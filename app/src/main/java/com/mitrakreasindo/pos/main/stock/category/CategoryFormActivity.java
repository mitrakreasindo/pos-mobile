package com.mitrakreasindo.pos.main.stock.category;

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
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.controller.CategoryListAdapter;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFormActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.main_content)
  CoordinatorLayout mainContent;
  @BindView(R.id.category_field)
  EditText categoryField;

//  private Category category;
  private CategoryService categoryService;

  private CategoryListAdapter categoryListAdapter;

  private Bundle bundle;

  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant, name, categoryId;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_form);
    ButterKnife.bind(this);

    categoryService = ClientService.createService().create(CategoryService.class);

    categoryListAdapter = new CategoryListAdapter(this, new ArrayList<Category>());

    sharedPreferenceEditor = new SharedPreferenceEditor();

    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");


    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    bundle = getIntent().getExtras();

    if (bundle != null)
    {
      categoryId = bundle.getString("id");
      name = bundle.getString("name");
      categoryField.setText(name);
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
    Log.d(getClass().getSimpleName(), "Post Role !!!");
    Log.d(getClass().getSimpleName(), categoryField.getText().toString());

    Category parentCategory = new Category();
    parentCategory.setId(null);

    final Category category = new Category();
    category.setId(UUID.randomUUID().toString());
    category.setName(categoryField.getText().toString());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);
    category.setParentid(parentCategory);
    category.setColour("");
    category.setCatorder(11);
    category.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    category.setSflag(true);


    Call<HashMap<Integer, String>> call = categoryService.postCategory(kodeMerchant, category);
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
            TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(CategoryFormActivity.this);
            tableCategoryHelper.open();
            tableCategoryHelper.insert(category);
            tableCategoryHelper.close();

            categoryListAdapter.addCategory(category);
            categoryListAdapter.notifyDataSetChanged();
          }
          Log.d(getClass().getSimpleName(), "Success Post Category !!!");
          Toast.makeText(CategoryFormActivity.this, "Succesfull add category", Toast.LENGTH_SHORT).show();
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

    Category parentCategory = new Category();
    parentCategory.setId(null);

    final Category category = new Category();
    category.setId(categoryId);
    category.setName(categoryField.getText().toString());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);
    category.setParentid(parentCategory);
    category.setColour("");
    category.setCatorder(11);
    category.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    category.setSflag(true);

    Call<HashMap<Integer, String>> call = categoryService.updateCategory(kodeMerchant, category.getId(), category);
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
          Log.e("RESPONSE CODE", String.valueOf(responseCode));
          Log.e("RESPONSE ", responseMessage);

          if (responseCode == 0)
          {
            Log.e("CATEGORY ID ", category.getId());
            TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(CategoryFormActivity.this);
            tableCategoryHelper.open();
            tableCategoryHelper.update(category);
            tableCategoryHelper.close();


          }
          Toast.makeText(CategoryFormActivity.this, "Succesfull update category", Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = "Cannot update category. :( There is something wrong.";
        Toast.makeText(CategoryFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });

  }
}