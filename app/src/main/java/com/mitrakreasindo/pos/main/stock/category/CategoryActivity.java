package com.mitrakreasindo.pos.main.stock.category;

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
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.controller.CategoryListAdapter;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.service.CategoryService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_category)
  RecyclerView listCategory;
  @BindView(R.id.main_content)
  LinearLayout mainContent;
  @BindView(R.id.edit_text_filter)
  EditText editTextFilter;

  private CategoryService categoryService;
  private CategoryListAdapter categoryListAdapter;
  private Category category;

  private TableCategoryHelper tableCategoryHelper;

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

    categoryService = ClientService.createService().create(CategoryService.class);

    categoryListAdapter = new CategoryListAdapter(this, new ArrayList<Category>());

    listCategory.setHasFixedSize(true);
    listCategory.setAdapter(categoryListAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listCategory.setLayoutManager(layoutManager);

    tableCategoryHelper = new TableCategoryHelper(this);

    editTextFilter.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        categoryListAdapter.clear();
        categoryListAdapter.addCategory(tableCategoryHelper.getData(editTextFilter.getText().toString()));
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
    categoryListAdapter.clear();
    categoryListAdapter.addCategory(tableCategoryHelper.getData());

//    getCategories();
  }

  @Override
  protected void onStart()
  {
    super.onStart();
    categoryListAdapter.clear();
    categoryListAdapter.addCategory(tableCategoryHelper.getData());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_list_menu, menu);
    MenuItem menuInsert = menu.findItem(R.id.action_add);
    if (PermissionUtil.getInactive(this, "stock_category_action").contains(MenuIds.rp_stk_category_action_insert))
    {
      menuInsert.setVisible(false);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_add)
    {
      startActivity(new Intent(this, CategoryFormActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }

//  private void getCategories(String kodeMerchant)
//  {
//    final Call<List<Category>> category = categoryService.getCategoryAll(kodeMerchant);
//    category.enqueue(new Callback<List<Category>>()
//    {
//      @Override
//      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
//      {
//        List<Category> categoryList = response.body();
//        Log.d("DATA :: ", categoryList.toString());
//        categoryListAdapter.clear();
//        categoryListAdapter.addCategory(categoryList);
//      }
//
//      @Override
//      public void onFailure(Call<List<Category>> call, Throwable t)
//      {
//
//      }
//    });
//
//  }
}
