package com.mitrakreasindo.pos.common.job;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.UserFormActivity;
import com.mitrakreasindo.pos.main.stock.category.CategoryFormActivity;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.service.CategoryService;
import com.mitrakreasindo.pos.service.PeopleService;
import com.mitrakreasindo.pos.service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 10/08/17.
 */

public class PostCategoryJob extends Job
{

  private CategoryService categoryService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;
  private Category category;
  private String status;

  public static final int PRIORITY = 1;

  public PostCategoryJob(String status, Category category)
  {
    super(new Params(PRIORITY));
    this.category = category;
    this.status = status;
  }

  @Override
  public void onAdded()
  {

  }

  @Override
  public void onRun() throws Throwable
  {

    categoryService = ClientService.createService().create(CategoryService.class);
    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(getApplicationContext(), "Company Code", "");

    category.setId(UUID.randomUUID().toString());
    category.setName(category.getName());
    category.setTexttip(category.getTexttip());
    category.setCatshowname(category.isCatshowname());
    category.setImage(category.getImage());
    category.setParentid(category.getParentid());
    category.setColour(category.getColour());
    category.setCatorder(category.getCatorder());
    category.setSiteguid(category.getSiteguid());
    category.setSflag(category.isSflag());

    Call<HashMap<Integer, String>> call = categoryService.postCategory(kodeMerchant, category);
    try
    {
      call.execute();
    } catch (IOException e)
    {
      e.printStackTrace();
    }

  }

  @Override
  protected void onCancel(int cancelReason, @Nullable Throwable throwable)
  {

  }

  @Override
  protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount)
  {
    return null;
  }

  private void postCategory()
  {
    final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
    progressDialog.setMessage(getApplicationContext().getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

    Category parentCategory = new Category();
    parentCategory.setId(null);

    final Category category = new Category();
    category.setId(UUID.randomUUID().toString());
    category.setName(category.getName());
    category.setTexttip("");
    category.setCatshowname(true);
    category.setImage(null);
    category.setParentid(parentCategory);
    category.setColour("");
    category.setCatorder(11);
    category.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    category.setSflag(true);

    Call<HashMap<Integer, String>> call = categoryService.postCategory(kodeMerchant, category);
    try
    {
      call.execute();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
//    call.enqueue(new Callback<HashMap<Integer, String>>()
//    {
//      private int responseCode;
//      private String responseMessage;
//
//      @Override
//      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
//      {
//        final HashMap<Integer, String> data = response.body();
//        for (int resultKey : data.keySet())
//        {
//          responseCode = resultKey;
//          responseMessage = data.get(resultKey);
//
//          Log.e("RESPONSE ", responseMessage);
//          if (responseCode == 0)
//          {
//            TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(CategoryFormActivity.this);
//            tableCategoryHelper.open();
//            tableCategoryHelper.insert(category);
//            tableCategoryHelper.close();
//
//          }
//        }
//      }
//
//      @Override
//      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
//      {
//        responseCode = -1;
//        responseMessage = getString(R.string.error_webservice);
//        Toast.makeText(CategoryFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
//      }
//    });
  }

}
