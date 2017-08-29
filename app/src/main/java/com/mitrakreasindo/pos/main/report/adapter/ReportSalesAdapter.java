package com.mitrakreasindo.pos.main.report.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.ReportActivity;
import com.mitrakreasindo.pos.main.report.SubReportActivity;
import com.mitrakreasindo.pos.main.report.fragment.DetailReportFragment;
import com.mitrakreasindo.pos.model.ReportSalesSubItem;
import com.mitrakreasindo.pos.model.ReportSalesSub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 05/08/17.
 */

public class ReportSalesAdapter extends RecyclerView.Adapter<ReportSalesAdapter.ViewHolder>
{

  private List<ReportSalesSub> subReports = new ArrayList<ReportSalesSub>();
  private Context context;
  private LayoutInflater inflater;
  private ReportSalesSub subReport;
  private DefaultHelper defaultHelper = new DefaultHelper();
  private SubProductAdapter subProductAdapter;
  private List<ReportSalesSubItem> productReportList = new ArrayList<>();
  public boolean twoPane = false;

  public ReportSalesAdapter(Context context, List<ReportSalesSub> subReports)
  {
    this.context = context;
    this.subReports = subReports;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ReportSalesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_subreport, parent, false);

    return new ReportSalesAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ReportSalesAdapter.ViewHolder holder, int position)
  {
    final ReportSalesSub subReport = subReports.get(position);
    Log.d("SUBREPORT", subReport.getPeopleName());
    holder.itemReportSalesName.setText(subReport.getPeopleName());
    holder.itemReportDate.setText(defaultHelper.dateOnlyFormat(subReport.getDate()));
    holder.itemReportTotalTransaction.setText("Rp. " + defaultHelper.decimalFormat(subReport.getTotalTransaction()));
    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if (twoPane)
        {
          Bundle bundle = new Bundle();
          bundle.putSerializable("subReport", subReport);
          bundle.putBoolean("twoPane", true);
          DetailReportFragment detailReportFragment = new DetailReportFragment();
          detailReportFragment.setArguments(bundle);
          ((ReportActivity)context).getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.detail_report, detailReportFragment).commit();
        }
        else
        {
          List<ReportSalesSubItem> list = new ArrayList<ReportSalesSubItem>();
          list.addAll(subReport.getSubItems());
          Intent intent = new Intent(context, SubReportActivity.class);
          intent.putExtra("listProduct", subReport);
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
    subReports.clear();
    notifyDataSetChanged();
  }

  public void addSubReports(List<ReportSalesSub> subReports)
  {
    this.subReports.addAll(subReports);
    notifyDataSetChanged();

  }

  @Override
  public int getItemCount()
  {
    return subReports.size();
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
