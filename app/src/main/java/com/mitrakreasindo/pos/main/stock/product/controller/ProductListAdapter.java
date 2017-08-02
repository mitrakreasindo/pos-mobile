package com.mitrakreasindo.pos.main.stock.product.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductFormActivity;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-05-29.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>
{
  private List<Product> products = new ArrayList<Product>();
  private Context context;
  private LayoutInflater inflater;
  private ProductService productService;
  private ProductActivity productActivity;
  public List<Product> list_product = new ArrayList<>();
  private int counter = 0;
  private Product product;
  private List<String> inactive;

  public ProductListAdapter(Context context, List<Product> products)
  {
    this.context = context;
    this.products = products;
    inactive = PermissionUtil.getInactive(context, "stock_product_action");
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
    product = products.get(position);

    if (!inactive.contains(MenuIds.rp_stk_product_action_update)) {
      holder.productItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, ProductFormActivity.class);
          intent.putExtra("id", product.getId());
          intent.putExtra("barcode", product.getCode());
          intent.putExtra("name", product.getName());
          intent.putExtra("shortName", product.getAlias());
          intent.putExtra("category", product.getCategory().getId());
          intent.putExtra("buyPrice", product.getPricebuy().toString());
          intent.putExtra("sellPrice", product.getPricebuy().toString());
          intent.putExtra("stockCost", product.getStockcost().toString());
          intent.putExtra("stockVolume", product.getStockvolume().toString());
          intent.putExtra("image", product.getImage());
          context.startActivity(intent);
        }
      });
    }

    if (!inactive.contains(MenuIds.rp_stk_product_action_delete)) {
      holder.productItem.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View v) {
          final AlertDialog.Builder builder = new AlertDialog.Builder(context);
          builder.setTitle(R.string.adapter_options_dialog);
          builder.setItems(new String[]{context.getString(R.string.adapter_options_delete)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                case 0:
                  new AlertDialog.Builder(context)
                          .setTitle(context.getString(R.string.alert_dialog_delete_product))
                          .setMessage(context.getString(R.string.alert_dialog_delete_product_message))
                          .setIcon(android.R.drawable.ic_dialog_alert)
                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                              deleteProduct(SharedPreferenceEditor.LoadPreferences(context, "Company Code", ""), product.getId());
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
//    if (counter == 0){ holder.checkBox.setVisibility(View.GONE); }
//    holder.productItem.setOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View v)
//      {
//        if (!productActivity.is_action_mode)
//        {
////          holder.checkBox.setChecked(true);
//          notifyDataSetChanged();
//        }
//        else
//        {
//          holder.checkBox.toggle();
//          prepareSelection(holder, position);
//          Toast.makeText(context, "IF TRUE", Toast.LENGTH_LONG).show();
//          notifyDataSetChanged();
//        }
//      }
//    });


//    holder.productItem.setOnLongClickListener(new View.OnLongClickListener()
//    {
//      @Override
//      public boolean onLongClick(View v)
//      {
//        productActivity.is_action_mode = true;
//        holder.checkBox.setChecked(true);
//        prepareSelection(holder, position);
//        productActivity.txtActionToolbar.setVisibility(View.VISIBLE);
//        notifyDataSetChanged();
//        return true;
//      }
//    });

    holder.txtCodeProduct.setText(product.getCode());
    holder.txtSellPrice.setText("IDR " + Double.toString(product.getPricesell()));
    holder.txtNameProduct.setText(product.getName());
    holder.txtBuyPrice.setText(Double.toString(product.getPricebuy()));

//    if (!productActivity.is_action_mode)
//    {
//      holder.checkBox.setVisibility(View.GONE);
//      holder.checkBox.setChecked(false);
//    }
//    else
//    {
//      holder.checkBox.setVisibility(View.VISIBLE);
//    }
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

  public void removeProduct(Product product)
  {
    products.remove(product);
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

  private void deleteProduct(String kodeMerchant, final String id)
  {
    productService = ClientService.createService().create(ProductService.class);
    Call<HashMap<Integer, String>> call = productService.deleteProduct(kodeMerchant, id);
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
            TableProductHelper tableProductHelper = new TableProductHelper(context);
            tableProductHelper.open();
            tableProductHelper.delete(product.getId());
            tableProductHelper.close();
          }
          Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = context.getString(R.string.error_webservice);
        Toast.makeText(context, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
    removeProduct(product);
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