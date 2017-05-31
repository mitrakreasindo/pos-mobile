package com.mitrakreasindo.pos.main.stock.category;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.controller.CategoryListAdapter;
import com.mitrakreasindo.pos.main.stock.category.model.Category;
import com.mitrakreasindo.pos.main.stock.category.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_category)
  RecyclerView listCategories;
  @BindView(R.id.main_content)
  ConstraintLayout mainContent;

  private CategoryListAdapter categoryListAdapter;
  private CategoryService categoryService;
  private SharedPreferenceEditor sharedPreferenceEditor = new SharedPreferenceEditor();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category);
    ButterKnife.bind(this);

    categoryService = ClientService.createService().create(CategoryService.class);

    toolbar.setTitle("Categories");
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    categoryListAdapter = new CategoryListAdapter(this, new ArrayList<Category>());
    listCategories.setAdapter(categoryListAdapter);
    listCategories.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    listCategories.setLayoutManager(layoutManager);
    listCategories.setItemAnimator(new DefaultItemAnimator());

    getCategory();
  }

  private void getCategory()
  {
    System.out.println(sharedPreferenceEditor.LoadPreferences(this,""));

    final Call<List<Category>> category = categoryService.getCategoryAll(sharedPreferenceEditor.LoadPreferences(this, ""));
    category.enqueue(new Callback<List<Category>>()
    {
      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
      {
        List<Category> categoryList = response.body();
//        if (categoryList == null)
//        {
//          Toast.makeText(CategoryActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
          categoryListAdapter.clear();
          categoryListAdapter.addCategory(categoryList);
//        }
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t)
      {

      }
    });
  }
}
