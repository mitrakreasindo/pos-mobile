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
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.model.Ticket;
import com.mitrakreasindo.pos.model.TicketLine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 17/07/17.
 */

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder>
{

  private List<TicketLine> ticketLines = new ArrayList<>();
  private Context context;
  private LayoutInflater layoutInflater;
  private double number;

  public SalesListAdapter(Context context, List<TicketLine> ticketLines)
  {
    this.ticketLines = ticketLines;
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
  public void onBindViewHolder(final SalesListAdapter.ViewHolder holder, int position)
  {

    final TicketLine ticketLine = ticketLines.get(position);
    holder.txtName.setText(ticketLine.getProduct().getName());
    holder.txtQty.setText(String.valueOf((int) ticketLine.getUnits()));
    holder.txtPrice.setText(String.valueOf(ticketLine.getProduct().getPricesell()));

    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    holder.txtSubTotal.setText(decimalFormat.format(
      Double.parseDouble(holder.txtPrice.getText().toString()) *
        Double.parseDouble(holder.txtQty.getText().toString())));
//    holder.txtSubTotal.setText(String.valueOf(0));
//    number = Integer.valueOf(holder.txtQty.getText().toString());
//    String qty = holder.txtQty.getText().toString().replaceAll(",", "");
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
          DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
          holder.txtSubTotal.setText(decimalFormat.format(
            Double.parseDouble(holder.txtPrice.getText().toString()) *
              Double.parseDouble(holder.txtQty.getText().toString())));
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

  }

  @Override
  public int getItemCount()
  {
    return ticketLines.size();
  }

  public int grandTotal()
  {
    notifyDataSetChanged();
    int totalPrice = 0;
    for (int i = 0; i < ticketLines.size(); i++)
    {
      totalPrice += ticketLines.get(i).getProduct().getPricesell();
    }
    Log.e("TICKET SIZE", String.valueOf(ticketLines.size()));
    notifyDataSetChanged();
    return totalPrice;
  }

  public void addTicketLine(List<TicketLine> ticketLines)
  {
    this.ticketLines.addAll(ticketLines);
    notifyDataSetChanged();
  }

  public void addTicketLine(TicketLine ticketLine)
  {

    Log.d("SIZE", String.valueOf(ticketLines.size()));
    if (ticketLines.size() == 0)
    {
      ticketLines.add(ticketLine);
      notifyDataSetChanged();
      Log.e("SIZE AFTER ADD", String.valueOf(ticketLines.size()));
    }
    else
    {
      Log.e("SIZE", String.valueOf(ticketLines.size()));
      Log.e(getClass().getSimpleName(), "tickets not null");

      boolean isAdd = false;
      for (int x = 0; x < ticketLines.size(); x++)
      {
        Log.e("SIZE", "ticke x " + x + " prodcut " + ticketLines.get(x).getProduct().getName());
        Log.e(getClass().getSimpleName(), "ticketline product id " + ticketLines.get(x).getProduct().getId());
        Log.e(getClass().getSimpleName(), "product id " + ticketLine.getProduct().getId());

        if (ticketLines.get(x).getProduct().getId().equals(ticketLine.getProduct().getId()))
        {
          double q = ticketLines.get(x).getUnits() + 1;
          Log.e(getClass().getSimpleName(), "new q " + q);
          ticketLine.setUnits(q);

          ticketLines.set(x, ticketLine);
          removeTicketLine(ticketLine);
          Log.e("DATA PRODUCT sama", ticketLine.getProduct().toString());
          notifyDataSetChanged();
        }
        else
        {
          isAdd = true;
        }

      }

      if (isAdd = true)
      {
        ticketLines.add(ticketLine);
        Log.e("DATA PRODUCT not sama", ticketLine.getProduct().toString());
        notifyDataSetChanged();
      }

    }

    notifyDataSetChanged();
  }

  public void removeTicketLine(TicketLine ticketLine)
  {
    ticketLines.remove(ticketLine);
    notifyDataSetChanged();
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





}
