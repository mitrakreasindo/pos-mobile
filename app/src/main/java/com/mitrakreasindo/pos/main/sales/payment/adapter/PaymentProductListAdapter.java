package com.mitrakreasindo.pos.main.sales.payment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.SalesItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 02/08/17.
 */

public class PaymentProductListAdapter extends RecyclerView.Adapter<PaymentProductListAdapter.ViewHolder>
{

  private List<SalesItem> salesItems = new ArrayList<>();
  private Context context;
  private LayoutInflater inflater;
//  private SalesItem salesItem;
  DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

  public PaymentProductListAdapter(Context context, List<SalesItem> salesItems)
  {
    this.salesItems = salesItems;
    this.context = context;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_payment_item_list, parent, false);
    return new ViewHolder(itemView);
  }
  public List<SalesItem> getAllTickets()
  {
    return salesItems;
  }
  @Override
  public void onBindViewHolder(final ViewHolder holder, int position)
  {
    final SalesItem salesItem = salesItems.get(position);

    holder.txtName.setText(salesItem.getProduct().getName());
    holder.txtQty.setText(String.valueOf((int) salesItem.getUnits()));
    Log.d("UNIT: ", Double.toString(salesItem.getUnits()));
    holder.txtPrice.setText(decimalFormat.format(salesItem.getProduct().getPricesell()));
    holder.txtSubTotal.setText(decimalFormat.format(
      salesItem.getProduct().getPricesell() *
        Double.parseDouble(holder.txtQty.getText().toString())));

  }

  @Override
  public int getItemCount()
  {
    return salesItems.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView txtName, txtQty, txtPrice, txtSubTotal;

    public ViewHolder(View itemView)
    {
      super(itemView);

      txtName = (TextView) itemView.findViewById(R.id.sales_text_name);
      txtQty = (TextView) itemView.findViewById(R.id.payment_text_qty);
      txtPrice = (TextView) itemView.findViewById(R.id.sales_text_price);
      txtSubTotal = (TextView) itemView.findViewById(R.id.sales_text_subtotal);

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
