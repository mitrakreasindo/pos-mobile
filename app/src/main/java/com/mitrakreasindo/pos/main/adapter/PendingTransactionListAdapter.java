package com.mitrakreasindo.pos.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.payment.PaymentActivity;
import com.mitrakreasindo.pos.model.ViewPendingTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hendric on 2017-08-14.
 */

public class PendingTransactionListAdapter extends RecyclerView.Adapter<PendingTransactionListAdapter.ViewHolder>
{
  private List<ViewPendingTransaction> pendingTransactions = new ArrayList<>();
  private Context context;
  private LayoutInflater layoutInflater;

  public PendingTransactionListAdapter(Context context, List<ViewPendingTransaction> pendingTransactions)
  {
    this.context = context;
    this.pendingTransactions = pendingTransactions;
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_queue_list, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position)
  {
    final ViewPendingTransaction viewPendingTransactions = pendingTransactions.get(position);

    holder.itemLayout.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra("sales_id", viewPendingTransactions.getReceiptId());
        context.startActivity(intent);
      }
    });

    holder.txtNo.setText(String.valueOf(position+1));
    holder.txtCustomer.setText(viewPendingTransactions.getCustomer());
    holder.txtTransactionTime.setText(viewPendingTransactions.getTransactionTime());
    holder.txtTotal.setText(DefaultHelper.decimalFormat(viewPendingTransactions.getReceiptTotal()));
    holder.txtHost.setText(viewPendingTransactions.getHost());
  }

  public void clear()
  {
    pendingTransactions.clear();
    notifyDataSetChanged();
  }

  public void addPendingTransaction(List<ViewPendingTransaction> pendingTransactions)
  {
    this.pendingTransactions.addAll(pendingTransactions);
    notifyDataSetChanged();
  }

  public void addPendingTransaction(ViewPendingTransaction[] pendingTransactions)
  {
    this.pendingTransactions.addAll(new ArrayList<>(Arrays.asList(pendingTransactions)));
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return pendingTransactions.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView txtNo, txtCustomer, txtTransactionTime, txtTotal, txtHost;
    private LinearLayout itemLayout;

    public ViewHolder(View itemView)
    {
      super(itemView);
      itemLayout = (LinearLayout) itemView.findViewById(R.id.queue_layout);
      txtNo = (TextView) itemView.findViewById(R.id.unpaid_no);
      txtCustomer = (TextView) itemView.findViewById(R.id.unpaid_customer);
      txtTransactionTime = (TextView) itemView.findViewById(R.id.unpaid_transaction_time);
      txtTotal = (TextView) itemView.findViewById(R.id.unpaid_total);
      txtHost = (TextView) itemView.findViewById(R.id.unpaid_host);
    }
  }
}
