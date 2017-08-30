package com.mitrakreasindo.pos.main.stock.category;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.controller.CategoryListAdapter;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
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
  @BindView(R.id.add_parent_category_switch)
  SwitchCompat addParentCategorySwitch;
  @BindView(R.id.parent_category_spinner)
  Spinner parentCategorySpinner;
  @BindView(R.id.parent_category_form)
  CardView parentCategoryForm;

  private CategoryService categoryService;
  private CategoryListAdapter categoryListAdapter;
  private Bundle bundle;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant, name, categoryId;
  private JobManager jobManager;

  private TableCategoryHelper tableCategoryHelper;
  private List<Category> dataCategory;
  private ArrayAdapter<Category> categoryArrayAdapter;
  private Category category = new Category();

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

    Log.d("SWITCH", String.valueOf(addParentCategorySwitch.getSplitTrack()));

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });


    tableCategoryHelper = new TableCategoryHelper(this);
    dataCategory = tableCategoryHelper.getData();
    categoryArrayAdapter = new ArrayAdapter<>(CategoryFormActivity.this, android.R.layout.simple_spinner_dropdown_item, dataCategory);
    parentCategorySpinner.setAdapter(categoryArrayAdapter);

    bundle = getIntent().getExtras();
    if (bundle != null)
    {
      Category c = (Category) bundle.getSerializable("category");
      categoryId = c.getId();
      name = c.getName();
      categoryField.setText(name);

      System.out.println(c.getParentid().getId());

      int spinnerPosition = 0;
      if (!c.getParentid().getId().equals(""))
      {
        addParentCategorySwitch.setChecked(true);
        parentCategoryForm.setVisibility(View.VISIBLE);
        int i = 0;
        System.out.println("KOSONG");
        while (i < dataCategory.size())
        {
          if (dataCategory.get(i).getId().equals(c.getParentid().getId()))
          {
            spinnerPosition = i;
            break;
          }
          i++;
        }
        Log.d("ROLE_ID", String.valueOf(spinnerPosition));
        parentCategorySpinner.setSelection(spinnerPosition);
      }
      else
      {
        addParentCategorySwitch.setChecked(false);
        parentCategoryForm.setVisibility(View.GONE);
        System.out.println("ADA");
      }

    }

    addParentCategorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
      {
        if (isChecked)
          parentCategoryForm.setVisibility(View.VISIBLE);
        else
          parentCategoryForm.setVisibility(View.GONE);

      }
    });
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
      } else
      {
        postCategory();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  private void postCategory()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

    Category parentCategory = new Category();
    parentCategory.setId(null);

    final Category category = new Category();
    category.setId(UUID.randomUUID().toString());
    category.setName(categoryField.getText().toString());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);
    Log.d("SWITCHSTATUS", addParentCategorySwitch.isChecked()+"");

    if (addParentCategorySwitch.isChecked())
      category.setParentid(dataCategory.get(parentCategorySpinner.getSelectedItemPosition()));
    else
      category.setParentid(null);

    category.setColour("");
    category.setCatorder(11);
    category.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    category.setSflag(true);

//    jobManager.addJobInBackground(new PostCategoryJob(FormMode.SAVE, category));

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

            finish();
          }
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        t.printStackTrace();
        Toast.makeText(CategoryFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }

    });

    progressDialog.dismiss();

  }

  private void updateCategory()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

    Category parentCategory = new Category();
    parentCategory.setId("663a30b3-fe7c-45e8-8061-9a98e9b23aae");

    final Category category = new Category();
    category.setId(categoryId);
    category.setName(categoryField.getText().toString());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);

    if (addParentCategorySwitch.isChecked())
      category.setParentid(dataCategory.get(parentCategorySpinner.getSelectedItemPosition()));
    else
      category.setParentid(null);

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

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0)
          {
            Log.e("CATEGORY ID ", category.getId());
            TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(CategoryFormActivity.this);
            tableCategoryHelper.open();
            tableCategoryHelper.update(category);
            tableCategoryHelper.close();

            categoryListAdapter.addCategory(category);
            categoryListAdapter.notifyDataSetChanged();

            progressDialog.dismiss();

            finish();
          }
          Toast.makeText(CategoryFormActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(CategoryFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });

  }
}