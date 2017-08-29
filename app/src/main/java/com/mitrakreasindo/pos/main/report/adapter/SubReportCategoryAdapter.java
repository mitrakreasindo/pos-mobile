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
import com.mitrakreasindo.pos.model.ReportCategorySubItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 29/08/17.
 */

public class SubReportCategoryAdapter extends RecyclerView.Adapter<SubReportCategoryAdapter.ViewHolder>
{

  private List<ReportCategorySubItem> reportCategorySubItems = new ArrayList<ReportCategorySubItem>();
  private Context context;
  private LayoutInflater inflater;
  private ReportCategorySubItem reportCategorySubItem;
  public boolean twoPane = false;
  private DefaultHelper defaultHelper = new DefaultHelper();

  public SubReportCategoryAdapter(Context context, List<ReportCategorySubItem> reportCategorySubItems)
  {
    this.context = context;
    this.reportCategorySubItems = reportCategorySubItems;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public SubReportCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_sub_product, parent, false);

    return new SubReportCategoryAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(SubReportCategoryAdapter.ViewHolder holder, int position)
  {
    DetailReportFragment detailReportFragment = new DetailReportFragment();
    reportCategorySubItem = reportCategorySubItems.get(position);

    Log.d("TWOPANE", String.valueOf(twoPane));
    if (reportCategorySubItem.getCategoryName().length() > 20  && !twoPane)
    {
      holder.itemSubProductName
        .setText(String.valueOf(reportCategorySubItem.getCategoryName().substring(0,20) + "..."));
    }
    else
    {
      holder.itemSubProductName
        .setText(reportCategorySubItem.getCategoryName());
    }

    holder.itemSubProductItemAndDate.setText(defaultHelper.decimalFormat(reportCategorySubItem.getQty()) + " items");
    holder.itemProductTotal.setText("Rp. " + defaultHelper.decimalFormat(reportCategorySubItem.getTotal()));
  }


  public void clear()
  {
    reportCategorySubItems.clear();
    notifyDataSetChanged();
  }

  public void addSubReports(List<ReportCategorySubItem> reportCategorySubItems)
  {
    this.reportCategorySubItems.addAll(reportCategorySubItems);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return reportCategorySubItems.size();
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
