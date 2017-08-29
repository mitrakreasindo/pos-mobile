package com.mitrakreasindo.pos.main.report.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.ReportActivity;
import com.mitrakreasindo.pos.main.report.SubReportActivity;
import com.mitrakreasindo.pos.main.report.fragment.DetailReportFragment;
import com.mitrakreasindo.pos.model.ReportSalesSubItem;
import com.mitrakreasindo.pos.model.ReportSubCategorySub;
import com.mitrakreasindo.pos.model.ReportSubCategorySubItem;
import com.mitrakreasindo.pos.model.ReportSubDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 29/08/17.
 */

public class ReportSubCategoryAdapter extends RecyclerView.Adapter<ReportSubCategoryAdapter.ViewHolder>
{

  private List<ReportSubDate> reportSubDates = new ArrayList<ReportSubDate>();
  private Context context;
  private LayoutInflater inflater;
  private ReportSubCategorySub reportSubCategorySub;
  private DefaultHelper defaultHelper = new DefaultHelper();
  private SubProductAdapter subProductAdapter;
  private List<ReportSalesSubItem> productReportList = new ArrayList<>();
  public boolean twoPane = false;

  public ReportSubCategoryAdapter(Context context, List<ReportSubDate> reportSubDates)
  {
    this.context = context;
    this.reportSubDates = reportSubDates;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ReportSubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_subreport, parent, false);

    return new ReportSubCategoryAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ReportSubCategoryAdapter.ViewHolder holder, int position)
  {
    final ReportSubDate reportSubDate = reportSubDates.get(position);
    holder.itemReportSalesName.setText(DefaultHelper.dateDayFormat(reportSubDate.getDate()));
    holder.itemReportDate.setText(DefaultHelper.dateOnlyFormat(reportSubDate.getDate()));
//    holder.itemReportTotalTransaction.setText("Rp. " + defaultHelper.decimalFormat(reportSubDate.getTotalTransaction()));
    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if (twoPane)
        {
          Bundle bundle = new Bundle();
          bundle.putSerializable("subReport", reportSubDate);
          bundle.putBoolean("twoPane", true);
          DetailReportFragment detailReportFragment = new DetailReportFragment();
          detailReportFragment.setArguments(bundle);
          ((ReportActivity)context).getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.detail_report, detailReportFragment).commit();
        }
        else
        {
          List<ReportSubDate> list = new ArrayList<>();
          list.addAll(reportSubDates);
          Intent intent = new Intent(context, SubReportActivity.class);
          intent.putExtra("listCategory", reportSubDate);
          intent.putExtra("twoPane", false);
          context.startActivity(intent);
        }
      }
    });
  }



  public List<ReportSalesSubItem> listSubProduct()
  {
    return productReportList;
  }

  public void clear()
  {
    reportSubDates.clear();
    notifyDataSetChanged();
  }

  public void addSubReports(List<ReportSubDate> reportSubDates)
  {
    this.reportSubDates.addAll(reportSubDates);
    notifyDataSetChanged();

  }

  @Override
  public int getItemCount()
  {
    return reportSubDates.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView itemReportSalesName, itemReportDate, itemReportTotalTransaction;
    public ViewHolder(View itemView)
    {
      super(itemView);
      itemReportSalesName = (TextView) itemView.findViewById(R.id.item_report_sales_name);
      itemReportDate = (TextView) itemView.findViewById(R.id.item_report_date);
      itemReportTotalTransaction = (TextView) itemView.findViewById(R.id.item_report_total_transaction);
    }
  }

}
