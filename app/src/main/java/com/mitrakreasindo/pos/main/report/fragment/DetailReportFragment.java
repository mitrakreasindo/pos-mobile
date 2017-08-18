package com.mitrakreasindo.pos.main.report.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.adapter.SubProductAdapter;
import com.mitrakreasindo.pos.model.SubReport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lisa on 15/08/17.
 */

public class DetailReportFragment extends Fragment
{

  @BindView(R.id.item_sub_report_total_transaction)
  TextView itemSubReportTotalTransaction;
  @BindView(R.id.item_sub_report_sales)
  TextView itemSubReportSales;
  @BindView(R.id.item_sub_report_date)
  TextView itemSubReportDate;
  @BindView(R.id.list_sub_product)
  RecyclerView listSubProduct;
  Unbinder unbinder;
  private SubProductAdapter subProductAdapter;
  private Bundle bundle;
  private SubReport subReport;
  private DefaultHelper defaultHelper = new DefaultHelper();
  private boolean twoPane = false;

  public DetailReportFragment()
  {

  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.item_detail_report, container, false);
    unbinder = ButterKnife.bind(this, view);

    bundle = getArguments();
    if (bundle != null)
    {
      subReport = (SubReport) bundle.getSerializable("subReport");
      twoPane = bundle.getBoolean("twoPane");
    }

    itemSubReportTotalTransaction.setText("Rp." + defaultHelper.decimalFormat(subReport.getTotalTransaction()));
    itemSubReportDate.setText(defaultHelper.dateOnlyFormat(subReport.getDate()));
    itemSubReportSales.setText(subReport.getPeopleName());

    subProductAdapter = new SubProductAdapter(getContext(), subReport.getSubProductReports());

    subProductAdapter.twoPane = twoPane;

    listSubProduct.setAdapter(subProductAdapter);
    listSubProduct.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    listSubProduct.setLayoutManager(layoutManager);
    listSubProduct.setItemAnimator(new DefaultItemAnimator());

    return view;
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }
}
