package com.mitrakreasindo.pos.main.stock.diary.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 22/06/17.
 */

public class DiarySelectProductAdapter extends RecyclerView.Adapter<DiarySelectProductAdapter.ViewHolder>
{
  private List<Product> products = new ArrayList<Product>();
  private Context context;
  private LayoutInflater inflater;

  public DiarySelectProductAdapter(Context context, List<Product> products)
  {
    this.context = context;
    this.products = products;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public DiarySelectProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adapter_product, parent, false);

    return new DiarySelectProductAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final DiarySelectProductAdapter.ViewHolder holder, final int position)
  {
    final Product product = products.get(position);

    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Intent intent = new Intent();
        intent.putExtra("barcode", product.getCode());
        intent.putExtra("name", product.getName());
        intent.putExtra("instock", product.getStockunits());
        intent.putExtra("buyprice", product.getPricebuy());
        intent.putExtra("sellprice", product.getPricesell());
        ((Activity) context).setResult(Activity.RESULT_OK, intent);
        ((Activity) context).onBackPressed();
      }
    });

    Glide.with(context)
      .asBitmap().load(product.getImage())
      .into(holder.itemProductImage).onLoadFailed(ContextCompat.getDrawable(context, R.drawable.box));

    if (product.getName().length() > 25)
    {
      holder.itemProductName.setText(String.valueOf(product.getName().substring(0, 25) + "..."));
    }
    else
    {
      holder.itemProductName.setText(product.getName());
    }

    holder.itemProductCode.setText(product.getCode());
    holder.itemProductPriceAndUnit.setText("Rp. "
      + DefaultHelper.decimalFormat(product.getPricesell())
      + " | " + DefaultHelper.decimalFormat(product.getStockunits())
      + " items");

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

    private ImageView itemProductImage;
    private TextView itemProductName, itemProductCode, itemProductPriceAndUnit;

    public ViewHolder(View itemView)
    {
      super(itemView);

      itemProductImage = (ImageView) itemView.findViewById(R.id.item_product_image);
      itemProductName = (TextView) itemView.findViewById(R.id.item_product_name);
      itemProductCode = (TextView) itemView.findViewById(R.id.item_product_code);
      itemProductPriceAndUnit = (TextView) itemView.findViewById(R.id.item_product_price_and_unit);

    }

  }
}
