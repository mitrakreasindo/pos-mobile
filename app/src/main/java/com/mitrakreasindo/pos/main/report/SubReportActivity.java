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
import com.mitrakreasindo.pos.main.report.adapter.SubReportCategoryAdapter;
import com.mitrakreasindo.pos.main.report.adapter.SubProductAdapter;
import com.mitrakreasindo.pos.model.ReportCategorySub;
import com.mitrakreasindo.pos.model.ReportSalesSub;
import com.mitrakreasindo.pos.model.ReportSalesSubItem;

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
  @BindView(R.id.item_text_sales_or_day)
  TextView itemTextSalesOrDay;

  private SubProductAdapter subProductAdapter;
  private SubReportCategoryAdapter reportSubCategoryAdapter;
  private List<ReportSalesSubItem> subProductReportList = new ArrayList<ReportSalesSubItem>();
  private Bundle bundle;
  private ReportSalesSub reportSalesSub;
  private ReportCategorySub reportCategorySub;
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
      twoPane = bundle.getBoolean("twoPane");
      if (bundle.getSerializable("listCategory") != null)
      {
        itemTextSalesOrDay.setText(getString(R.string.day));
        reportCategorySub = (ReportCategorySub) bundle.getSerializable("listCategory");
        Log.d("CATEGORY", "LIST");
        reportSubCategoryAdapter = new SubReportCategoryAdapter(this, reportCategorySub.getSubItems());
        reportSubCategoryAdapter.twoPane = twoPane;

        itemSubReportTotalTransaction.setText("Rp." + defaultHelper.decimalFormat(reportCategorySub.getTotalTransaction()));
        itemSubReportDate.setText(defaultHelper.dateOnlyFormat(reportCategorySub.getDate()));
        itemSubReportSales.setText(DefaultHelper.dateDayFormat(reportCategorySub.getDate()));
        listSubProduct.setAdapter(reportSubCategoryAdapter);

      } else if (bundle.getSerializable("listProduct") != null)
      {
        itemTextSalesOrDay.setText(getString(R.string.sales));
        reportSalesSub = (ReportSalesSub) bundle.getSerializable("listProduct");
        Log.d("SALES", "LIST");
        subProductAdapter = new SubProductAdapter(this, reportSalesSub.getSubItems());
        subProductAdapter.twoPane = twoPane;

        itemSubReportTotalTransaction.setText("Rp." + defaultHelper.decimalFormat(reportSalesSub.getTotalTransaction()));
        itemSubReportDate.setText(defaultHelper.dateOnlyFormat(reportSalesSub.getDate()));
        itemSubReportSales.setText(reportSalesSub.getPeopleName());
        listSubProduct.setAdapter(subProductAdapter);
      }

    }

//    if (reportCategorySub != null)
//    {
//
//    }
//    else if (reportSalesSub != null)
//    {
//
//    }


    listSubProduct.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listSubProduct.setLayoutManager(layoutManager);
    listSubProduct.setItemAnimator(new DefaultItemAnimator());

  }
}
