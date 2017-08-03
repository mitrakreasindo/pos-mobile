package com.mitrakreasindo.pos.main.sales.payment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
      .inflate(R.layout.adapter_sales_item_list, parent, false);
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
    holder.txtPrice.setText(decimalFormat.format(salesItem.getProduct().getPricesell()));
//    holder.txtSubTotal.setText(decimalFormat.format(
//      Double.parseDouble(holder.txtPrice.getText().toString()) *
//        Double.parseDouble(holder.txtQty.getText().toString())));
//    holder.txtQty.addTextChangedListener(new TextWatcher()
//    {
//      @Override
//      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
//      {
//      }
//
//      @Override
//      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
//      {
//        if (charSequence.length() > 0 && holder.txtQty.getText().toString().length() > 0)
//        {
//          DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
//          holder.txtSubTotal.setText(decimalFormat.format(
//            Double.parseDouble(holder.txtPrice.getText().toString()) *
//              Double.parseDouble(holder.txtQty.getText().toString())));
//
//          salesItem.setUnits(Double.parseDouble(charSequence.toString()));
//        }
//        else
//        {
//          holder.txtQty.setText("0");
//        }
//      }
//
//      @Override
//      public void afterTextChanged(Editable editable)
//      {
//      }
//
//    });

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
      txtQty = (EditText) itemView.findViewById(R.id.sales_text_qty);
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
