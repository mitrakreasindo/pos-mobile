package com.mitrakreasindo.pos.main.stock.category.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.model.Category;
import com.mitrakreasindo.pos.main.stock.category.service.CategoryService;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;

import java.util.ArrayList;
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


  public CategoryListAdapter(Context context, List<Category> categories)
  {
    this.context = context;
    this.categories = categories;
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

    final Category category = categories.get(position);
    holder.txtCategory.setText(category.getName());

    //On Click
    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
//        Intent intent = new Intent(context, UserDetailActivity.class);
//        intent.putExtra("name", category.getName());
//        context.startActivity(intent);
      }
    });

    //On Long Click
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
    {
      @Override
      public boolean onLongClick(final View v)
      {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Options");
        builder.setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
            switch (which)
            {
              case 0:
                Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, UserFormActivity.class);
//                intent.putExtra("id", category.getName());
//                intent.putExtra("name", category.getApppassword());
//                context.startActivity(intent);
                break;

              case 1:
                Toast.makeText(context, "User Deleted", Toast.LENGTH_LONG).show();
                categoryService = ClientService.createService().create(CategoryService.class);
//                Call<List<Category>> call = categoryService.deleteCategory(category.getId());
//                call.enqueue(new Callback<List<Category>>()
//                {
//                  @Override
//                  public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
//                  {
//
//                  }
//
//                  @Override
//                  public void onFailure(Call<List<Category>> call, Throwable t)
//                  {
//
//                  }
//
//
//                });
//
//                getCategories();
                Toast.makeText(context, "Category deleted!", Toast.LENGTH_LONG).show();
                break;
            }
          }
        });
        builder.show();
        return false;
      }
    });
  }

  public void addCategory(Category category)
  {
    categories.add(category);
    notifyDataSetChanged();
  }

  public void clear(){
    categories.clear();
    notifyDataSetChanged();
  }

  public void addCategory(List<Category> categories)
  {
    this.categories.addAll(categories);
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

  private void getCategories()
  {
    final Call<List<Category>> category = categoryService.getCategoryAll();
    category.enqueue(new Callback<List<Category>>()
    {
      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
      {
        List<Category> categoryList = response.body();
        clear();
        addCategory(categoryList);
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t)
      {

      }
    });

  }
}
