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
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.controller.CategoryListAdapter;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
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

  private Category category;
  private CategoryService categoryService;

  private CategoryListAdapter categoryListAdapter;

  private Bundle bundle;

  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_form);
    ButterKnife.bind(this);

    categoryService = ClientService.createService().create(CategoryService.class);

    categoryListAdapter = new CategoryListAdapter(this, new ArrayList<Category>());

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

    String example = "Convert Java String";
    byte[] bytes = example.getBytes();

    category = new Category();
    category.setId(UUID.randomUUID().toString());
    category.setName(categoryField.getText().toString());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);
    category.setParentid(null);
    category.setColour("");
    category.setCatorder(11);
    category.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    category.setSflag(true);

    Call<List<Category>> call = categoryService.postCategory(kodeMerchant, category);
    call.enqueue(new Callback<List<Category>>()
    {
      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
      {
        Log.d(getClass().getSimpleName(), "Success Post Category !!!");
        categoryListAdapter.addCategory(category);
        categoryListAdapter.notifyDataSetChanged();
        onBackPressed();
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t)
      {
      }
    });
    onBackPressed();

  }

  private void updateCategory()
  {

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    Log.d(getClass().getSimpleName(), "Post Role !!!");
    Log.d(getClass().getSimpleName(), categoryField.getText().toString());

    String example = "Convert Java String";
    byte[] bytes = example.getBytes();
    String categoryId = bundle.getString("id");

    category = new Category();
    category.setId(categoryId);
    category.setName(categoryField.getText().toString());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);
    category.setParentid(null);
    category.setColour("");
    category.setCatorder(11);
    category.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    category.setSflag(true);

    Call<List<Category>> call = categoryService.updateCategory(kodeMerchant, category.getId(), category);
    call.enqueue(new Callback<List<Category>>()
    {
      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
      {
        Log.d(getClass().getSimpleName(), "Success Update Category !!!");
        onBackPressed();
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t)
      {
      }
    });

    onBackPressed();

  }
}