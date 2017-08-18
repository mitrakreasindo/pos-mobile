package com.mitrakreasindo.pos.main.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.adapter.SubProductAdapter;
import com.mitrakreasindo.pos.model.SubProductReport;
import com.mitrakreasindo.pos.model.SubReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lisa on 09/08/17.
 */

public class SubReportActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_sub_product)
  RecyclerView listSubProduct;
  @BindView(R.id.item_sub_report_total_transaction)
  TextView itemSubReportTotalTransaction;
  @BindView(R.id.item_sub_report_sales)
  TextView itemSubReportSales;
  @BindView(R.id.item_sub_report_date)
  TextView itemSubReportDate;

  private SubProductAdapter subProductAdapter;
  private List<SubProductReport> subProductReportList = new ArrayList<SubProductReport>();
  private Bundle bundle;
  private SubReport subReport;
  private boolean twoPane = false;
  private DefaultHelper defaultHelper = new DefaultHelper();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sub_report);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    bundle = getIntent().getExtras();
    if (bundle != null)
    {

      subReport = (SubReport) bundle.getSerializable("listProduct");
      twoPane = bundle.getBoolean("twoPane");

      Log.d("NEXT", String.valueOf(subReport.getTotalTransaction()));

    }

    itemSubReportTotalTransaction.setText("Rp." + defaultHelper.decimalFormat(subReport.getTotalTransaction()));
    itemSubReportDate.setText(defaultHelper.dateOnlyFormat(subReport.getDate()));
    itemSubReportSales.setText(subReport.getPeopleName());

    subProductAdapter = new SubProductAdapter(this, subReport.getSubProductReports());
    subProductAdapter.twoPane = twoPane;

    listSubProduct.setAdapter(subProductAdapter);
    listSubProduct.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listSubProduct.setLayoutManager(layoutManager);
    listSubProduct.setItemAnimator(new DefaultItemAnimator());

  }
}
