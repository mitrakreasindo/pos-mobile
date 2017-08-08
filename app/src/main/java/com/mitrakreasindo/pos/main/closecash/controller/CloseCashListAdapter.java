package com.mitrakreasindo.pos.main.closecash.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Viewclosedcash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendric on 2017-08-08.
 */

public class CloseCashListAdapter extends RecyclerView.Adapter<CloseCashListAdapter.ViewHolder>
{
  private List<Viewclosedcash> viewclosedcashes = new ArrayList<>();
  private Context context;
  private LayoutInflater inflater;

  public CloseCashListAdapter(Context context, List<Viewclosedcash> viewclosedcashes)
  {
    this.context = context;
    this.viewclosedcashes = viewclosedcashes;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public CloseCashListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_closecash_transaction, parent, false);

    return new CloseCashListAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final CloseCashListAdapter.ViewHolder holder, final int position)
  {
    final Viewclosedcash viewclosedcash = viewclosedcashes.get(position);

    holder.txtTransactionID.setText(viewclosedcash.getReceiptId());
    holder.txtCash.setText(String.valueOf(viewclosedcash.getTotalCashPayment()));
    holder.txtUnitsSold.setText(String.valueOf(viewclosedcash.getTotalSoldUnits()));
    holder.txtPayment.setText(String.valueOf(viewclosedcash.getTotalAllPayment()));
  }

  public void addCloseCashTransaction(Viewclosedcash viewclosedcash)
  {
    viewclosedcashes.add(viewclosedcash);
    notifyDataSetChanged();
  }

  public void clear()
  {
    viewclosedcashes.clear();
    notifyDataSetChanged();
  }

  public void addCloseCashTransaction(List<Viewclosedcash> viewclosedcashes)
  {
    this.viewclosedcashes.addAll(viewclosedcashes);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return viewclosedcashes.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView txtTransactionID, txtUnitsSold, txtPayment, txtCash;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtTransactionID = (TextView) itemView.findViewById(R.id.txt_fill_transaction_id);
      txtUnitsSold = (TextView) itemView.findViewById(R.id.txt_fill_units_sold);
      txtPayment = (TextView) itemView.findViewById(R.id.txt_fill_payment);
      txtCash = (TextView) itemView.findViewById(R.id.txt_fill_cash);
    }
  }
}
