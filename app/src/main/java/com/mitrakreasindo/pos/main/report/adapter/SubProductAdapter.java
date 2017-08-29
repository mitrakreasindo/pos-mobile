package com.mitrakreasindo.pos.main.report.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.fragment.DetailReportFragment;
import com.mitrakreasindo.pos.model.ReportSalesSubItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 09/08/17.
 */

public class SubProductAdapter extends RecyclerView.Adapter<SubProductAdapter.ViewHolder>
{

  private List<ReportSalesSubItem> subProductReports = new ArrayList<ReportSalesSubItem>();
  private Context context;
  private LayoutInflater inflater;
  private ReportSalesSubItem subProductReport;
  public boolean twoPane = false;
  private DefaultHelper defaultHelper = new DefaultHelper();

  public SubProductAdapter(Context context, List<ReportSalesSubItem> subProductReports)
  {
    this.context = context;
    this.subProductReports = subProductReports;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public SubProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_sub_product, parent, false);

    return new SubProductAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(SubProductAdapter.ViewHolder holder, int position)
  {
    DetailReportFragment detailReportFragment = new DetailReportFragment();
    subProductReport = subProductReports.get(position);
//
//    if (detailReportFragment != null)
//      Log.d("FRAGNOTNULL", "NOTNULL");
//    else
//      Log.d("FRAGNULL", "NULL");
    Log.d("TWOPANE", String.valueOf(twoPane));
    if (subProductReport.getProductName().length() > 20  && !twoPane)
    {
      holder.itemSubProductName
        .setText(String.valueOf(subProductReport.getProductName().substring(0,20) + "..."));
    }
    else
    {
      holder.itemSubProductName
        .setText(subProductReport.getProductName());
    }

    holder.itemSubProductItemAndDate.setText(defaultHelper.decimalFormat(subProductReport.getQty()) + " items");
    holder.itemProductTotal.setText("Rp. " + defaultHelper.decimalFormat(subProductReport.getTotal()));
  }


  public void clear()
  {
    subProductReports.clear();
    notifyDataSetChanged();
  }

  public void addSubReports(List<ReportSalesSubItem> subProductReports)
  {
    this.subProductReports.addAll(subProductReports);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return subProductReports.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView itemSubProductName, itemSubProductItemAndDate, itemProductTotal;
    public ViewHolder(View itemView)
    {
      super(itemView);
      itemSubProductName = (TextView) itemView.findViewById(R.id.item_sub_product_name);
      itemSubProductItemAndDate = (TextView) itemView.findViewById(R.id.item_sub_product_item_and_date);
      itemProductTotal = (TextView) itemView.findViewById(R.id.item_sub_product_total);
    }
  }

}
