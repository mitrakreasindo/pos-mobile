package com.mitrakreasindo.pos.main.stock.product.controller;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;
import com.mitrakreasindo.pos.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendric on 2017-05-29.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>
{
  private List<Product> products = new ArrayList<Product>();
  private Context context;
  private LayoutInflater inflater;
  private ProductActivity productActivity;
  public List<Product> list_product = new ArrayList<>();
  int counter = 0;

  public ProductListAdapter(Context context, List<Product> products)
  {
    this.context = context;
    this.products = products;
    productActivity = (ProductActivity) context;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_product, parent, false);

    return new ProductListAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final ProductListAdapter.ViewHolder holder, final int position)
  {

//    if (counter == 0){ holder.checkBox.setVisibility(View.GONE); }
    holder.productItem.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if (!productActivity.is_action_mode)
        {
//          holder.checkBox.setChecked(true);
          notifyDataSetChanged();
        }
        else
        {
          holder.checkBox.toggle();
          prepareSelection(holder, position);
          Toast.makeText(context, "IF TRUE", Toast.LENGTH_LONG).show();
          notifyDataSetChanged();
        }
      }
    });


    holder.productItem.setOnLongClickListener(new View.OnLongClickListener()
    {
      @Override
      public boolean onLongClick(View v)
      {
        productActivity.is_action_mode = true;
        holder.checkBox.toggle();
        prepareSelection(holder, position);
        productActivity.txtActionToolbar.setVisibility(View.VISIBLE);
        notifyDataSetChanged();
        return true;
      }
    });

    final Product product = products.get(position);
    holder.txtCodeProduct.setText(product.getCode());
    holder.txtSellPrice.setText("IDR " + Double.toString(product.getPricesell()));
    holder.txtNameProduct.setText(product.getName());
    holder.txtBuyPrice.setText(Double.toString(product.getPricebuy()));

    if (!productActivity.is_action_mode)
    {
      holder.checkBox.setVisibility(View.GONE);
      holder.checkBox.setChecked(false);
    }
    else
    {
      holder.checkBox.setVisibility(View.VISIBLE);
    }

  }

  public void addProduct(Product product)
  {
    products.add(product);
    notifyDataSetChanged();
  }

  public void clear()
  {
    products.clear();
    notifyDataSetChanged();
  }

  public void addProduct(List<Product> products)
  {
    this.products.addAll(products);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return products.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView txtCodeProduct, txtNameProduct, txtBuyPrice,txtSellPrice;
    private CheckBox checkBox;
    private LinearLayout productItem;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtCodeProduct = (TextView) itemView.findViewById(R.id.txt_code_product);
      txtNameProduct = (TextView) itemView.findViewById(R.id.txt_name_product);
      checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
      productItem = (LinearLayout) itemView.findViewById(R.id.item_product);
      txtBuyPrice = (TextView) itemView.findViewById(R.id.txt_buy_price_product);
      txtSellPrice = (TextView) itemView.findViewById(R.id.txt_sell_price);
    }


  }


  public void prepareSelection(final ProductListAdapter.ViewHolder holder, int position){

    if (holder.checkBox.isChecked()){
      list_product.add(products.get(position));
      counter = counter + 1;
      updateCounter(counter);
    }
    else
    {
      list_product.remove(products.get(position));
      counter = counter - 1;
      updateCounter(counter);
    }
    notifyDataSetChanged();
  }

  public void updateCounter(int counter){



    if (counter == 0){
      productActivity.txtActionToolbar.setText("0 item selected");
      productActivity.is_action_mode = false;
//      holder.checkBox.setChecked(false);
      productActivity.txtActionToolbar.setVisibility(View.GONE);
      productActivity.toolbar.getMenu().clear();
      productActivity.toolbar.inflateMenu(R.menu.default_list_menu);
      notifyDataSetChanged();
    }
    else
    {
      productActivity.txtActionToolbar.setText(counter + " item selected");
      productActivity.toolbar.getMenu().clear();
      productActivity.toolbar.inflateMenu(R.menu.menu_action_mode);

    }
    notifyDataSetChanged();
  }

  public void deleteMultipleProduct()
  {
    productActivity.is_action_mode = false;
    productActivity.txtActionToolbar.setVisibility(View.GONE);
    productActivity.toolbar.getMenu().clear();
    productActivity.toolbar.inflateMenu(R.menu.default_list_menu);
//    list_product.clear();
    counter = 0;
    list_product.clear();
    notifyDataSetChanged();

  }


//
//  private void getCategories()
//  {
//    final Call<List<Category>> category = categoryService.getCategoryAll();
//    category.enqueue(new Callback<List<Category>>()
//    {
//      @Override
//      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
//      {
//        List<Category> categoryList = response.body();
//        clear();
//        addCategory(categoryList);
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