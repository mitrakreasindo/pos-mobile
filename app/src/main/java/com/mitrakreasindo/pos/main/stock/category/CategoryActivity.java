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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.controller.CategoryListAdapter;
import com.mitrakreasindo.pos.model.Category;

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
  @BindView(R.id.edit_filter)
  EditText editFilter;
  @BindView(R.id.button_filter)
  Button buttonFilter;

  private CategoryListAdapter categoryListAdapter;
  private TableCategoryHelper tableCategoryHelper;

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

    categoryListAdapter = new CategoryListAdapter(this, new ArrayList<Category>());

    listCategory.setHasFixedSize(true);
    listCategory.setAdapter(categoryListAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listCategory.setLayoutManager(layoutManager);

    tableCategoryHelper = new TableCategoryHelper(this);
    editFilter.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        categoryListAdapter.clear();
        categoryListAdapter.addCategory(tableCategoryHelper.getData(editFilter.getText().toString()));
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

    buttonFilter.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        editFilter.setText("");
      }
    });

    categoryListAdapter.clear();
    categoryListAdapter.addCategory(tableCategoryHelper.getData());
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
}
