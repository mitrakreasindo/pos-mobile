package com.mitrakreasindo.pos.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.ReportActivity;
import com.mitrakreasindo.pos.main.report.adapter.SubProductAdapter;
import com.mitrakreasindo.pos.main.report.adapter.SubReportListAdapter;
import com.mitrakreasindo.pos.model.ReportSelection;
import com.mitrakreasindo.pos.model.SubProductReport;
import com.mitrakreasindo.pos.model.SubReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 23/08/17.
 */

public class ListReportByCategoryAdapter extends RecyclerView.Adapter<ListReportByCategoryAdapter.ViewHolder>
{

  private List<ReportSelection> reportSelections = new ArrayList<ReportSelection>();
  private Context context;
  private LayoutInflater inflater;

  public ListReportByCategoryAdapter(Context context, List<ReportSelection> reportSelections)
  {
    this.context = context;
    this.reportSelections = reportSelections;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ListReportByCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_select_report, parent, false);

    return new ListReportByCategoryAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ListReportByCategoryAdapter.ViewHolder holder, int position)
  {
    ReportSelection reportSelection = reportSelections.get(position);
    holder.txtSampleSrc.setImageDrawable(ContextCompat.getDrawable(context, reportSelection.getSrc()));
    holder.txtSampleTitle.setText(reportSelection.getTitle());
//    holder.txtSampleDescription.setText(reportSelection.getDescription());
    holder.reportCircleLayout.setDrawingCacheBackgroundColor(ContextCompat.getColor(context, reportSelection.getColor()));

    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        context.startActivity(new Intent(context, ReportActivity.class));
      }
    });
  }

  @Override
  public int getItemCount()
  {
    return reportSelections.size();
  }

  public void addReportSelection(ReportSelection reportSelection)
  {
    reportSelections.add(reportSelection);
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private ImageView txtSampleSrc;
    private TextView txtSampleTitle, txtSampleDescription;
    private FrameLayout reportCircleLayout;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtSampleSrc = (ImageView) itemView.findViewById(R.id.txtSampleSrc);
      txtSampleTitle = (TextView) itemView.findViewById(R.id.txtSampleTitle);
//      txtSampleDescription = (TextView) itemView.findViewById(R.id.txtSampleDescription);
      reportCircleLayout = (FrameLayout) itemView.findViewById(R.id.report_circle_layout);
    }
  }
}
