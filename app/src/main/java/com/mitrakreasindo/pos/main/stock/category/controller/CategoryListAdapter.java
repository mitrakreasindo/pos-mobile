package com.mitrakreasindo.pos.main.stock.category.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.CategoryFormActivity;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-05-29.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>
{
  private List<Category> categories = new ArrayList<Category>();
  private Context context;
  private LayoutInflater inflater;
  private CategoryService categoryService;
  private Category category;
  private List<String> inactive;

  public CategoryListAdapter(Context context, List<Category> categories)
  {
    this.context = context;
    this.categories = categories;
    inactive = PermissionUtil.getInactive(context, "stock_category_action");
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_category, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(CategoryListAdapter.ViewHolder holder, int position)
  {
    category = categories.get(position);
    holder.txtCategory.setText(category.getName());
    Log.e("ID CAT FROM ADAPTER", category.getId());

    if (!inactive.contains(MenuIds.rp_stk_category_action_update)) {
      //On Click
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(context, CategoryFormActivity.class);
          intent.putExtra("id", category.getId());
          intent.putExtra("name", category.getName());
          context.startActivity(intent);
        }
      });
    }

    if (!inactive.contains(MenuIds.rp_stk_category_action_delete))
    {
      //On Long Click
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View v) {
          final AlertDialog.Builder builder = new AlertDialog.Builder(context);
          builder.setTitle("Options");
          builder.setItems(new String[]{"Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                case 0:
                  new AlertDialog.Builder(context)
                          .setTitle(context.getString(R.string.alert_dialog_delete_category))
                          .setMessage(context.getString(R.string.alert_dialog_delete_category_message))
                          .setIcon(android.R.drawable.ic_dialog_alert)
                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                              deleteCategory(SharedPreferenceEditor.LoadPreferences(context, "Company Code", ""), category.getId());
                            }
                          })
                          .setNegativeButton(android.R.string.no, null).show();
                  break;
              }
            }
          });
          builder.show();
          return false;
        }
      });
    }
  }

  public void addCategory(Category category)
  {
    categories.add(category);
    notifyDataSetChanged();
  }

  public void clear()
  {
    categories.clear();
    notifyDataSetChanged();
  }

  public void addCategory(List<Category> categories)
  {
    this.categories.addAll(categories);
    notifyDataSetChanged();
  }

  public void removeCategory(Category category)
  {
    categories.remove(category);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return categories.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView txtCategory;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtCategory = (TextView) itemView.findViewById(R.id.txt_category);
    }
  }

  private void deleteCategory(String kodeMerchant, final String id)
  {
    categoryService = ClientService.createService().create(CategoryService.class);
    Call<HashMap<Integer, String>> call = categoryService.deleteCategory(kodeMerchant, id);
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
          Log.d("RESPONSE WEBSERVICE: ", String.valueOf(responseCode) + responseMessage);

          if (responseCode == 0)
          {
            TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(context);
            tableCategoryHelper.open();
            tableCategoryHelper.delete(id);
            tableCategoryHelper.close();
          }
          Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = "Cannot delete category. :( There is something wrong.";
        Toast.makeText(context, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
    removeCategory(category);
  }
}
