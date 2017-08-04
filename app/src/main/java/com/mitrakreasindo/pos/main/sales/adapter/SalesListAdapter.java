package com.mitrakreasindo.pos.main.sales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.SalesItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 17/07/17.
 */

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder>
{

  public List<SalesItem> salesItems = new ArrayList<>();
  private Context context;
  private LayoutInflater layoutInflater;
  private double number;
  DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

  public SalesListAdapter(Context context, List<SalesItem> salesItems)
  {
    this.salesItems = salesItems;
    this.context = context;
    this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }


  @Override
  public SalesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sales_item_list, parent, false);

    return new SalesListAdapter.ViewHolder(itemView);
  }


  @Override
  public void onBindViewHolder(final SalesListAdapter.ViewHolder holder, final int position)
  {

    final SalesItem salesItem = salesItems.get(position);
    holder.txtName.setText(salesItem.getProduct().getName());
    holder.txtQty.setText(String.valueOf((int) salesItem.getUnits()));
    holder.txtPrice.setText(decimalFormat.format(salesItem.getProduct().getPricesell()));
//    double price = salesItem.getProduct().getPricesell();
    holder.txtSubTotal.setText(decimalFormat.format(
      salesItem.getProduct().getPricesell() *
        Double.parseDouble(holder.txtQty.getText().toString())));
    holder.txtQty.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {
        if (charSequence.length() > 0 && holder.txtQty.getText().toString().length() > 0)
        {
          holder.txtSubTotal.setText(decimalFormat.format(
            salesItem.getProduct().getPricesell() *
              Double.parseDouble(holder.txtQty.getText().toString())));

          salesItem.setUnits(Double.parseDouble(charSequence.toString()));
        }
        else
        {
          holder.txtQty.setText("0");
        }
      }

      @Override
      public void afterTextChanged(Editable editable)
      {
      }

    });

    holder.btnSalesDeleteItem.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        removeSalesItem(salesItem);
      }
    });

  }

  @Override
  public int getItemCount()
  {
    return salesItems.size();
  }


  public void addSalesItem(List<SalesItem> salesItems)
  {
    this.salesItems.addAll(salesItems);
    notifyDataSetChanged();
  }

  public void addSalesItem(SalesItem salesItem)
  {

    Log.d("SIZE", String.valueOf(salesItems.size()));
    if (salesItems.size() == 0)
    {
      salesItems.add(salesItem);
      notifyDataSetChanged();
      Log.e("SIZE AFTER ADD", String.valueOf(salesItems.size()));
    }
    else
    {
      Log.e("SIZE", String.valueOf(salesItems.size()));
      Log.e(getClass().getSimpleName(), "tickets not null");

      boolean isAdd = false;
      for (int x = 0; x < salesItems.size(); x++)
      {
        Log.e("SIZE", "ticke x " + x + " prodcut " + salesItems.get(x).getProduct().getName());
        Log.e(getClass().getSimpleName(), "ticketline product id " + salesItems.get(x).getProduct().getId());
        Log.e(getClass().getSimpleName(), "product id " + salesItem.getProduct().getId());

        if (salesItems.get(x).getProduct().getId().equals(salesItem.getProduct().getId()))
        {
          double q = salesItems.get(x).getUnits() + 1;
          Log.e(getClass().getSimpleName(), "new q " + q);
          salesItem.setUnits(q);

          salesItems.set(x, salesItem);
          removeSalesItem(salesItem);
          Log.e("DATA PRODUCT sama", salesItem.getProduct().toString());
          notifyDataSetChanged();
        }
        else
        {
          isAdd = true;
        }

      }

      if (isAdd = true)
      {
        salesItems.add(salesItem);
        Log.e("DATA PRODUCT not sama", salesItem.getProduct().toString());
        notifyDataSetChanged();
      }

    }

    notifyDataSetChanged();
  }

  public void removeSalesItem(SalesItem salesItem)
  {
    salesItems.remove(salesItem);
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView txtName, txtPrice, txtSubTotal, txtTotal;
    private EditText txtQty;
    private ImageView btnSalesDeleteItem;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtName = (TextView) itemView.findViewById(R.id.sales_text_name);
      txtQty = (EditText) itemView.findViewById(R.id.sales_text_qty);
      txtPrice = (TextView) itemView.findViewById(R.id.sales_text_price);
      txtSubTotal = (TextView) itemView.findViewById(R.id.sales_text_subtotal);
      btnSalesDeleteItem = (ImageView) itemView.findViewById(R.id.sales_btn_delete_item);
    }
  }


  public double grandTotal()
  {
    notifyDataSetChanged();
    double totalPrice = 0;

    for (int i = 0; i < salesItems.size(); i++)
    {
      totalPrice += salesItems.get(i).getProduct().getPricesell() * salesItems.get(i).getUnits();
    }
    Log.e("TICKET SIZE", String.valueOf(salesItems.size()));
    return totalPrice;
  }

}
