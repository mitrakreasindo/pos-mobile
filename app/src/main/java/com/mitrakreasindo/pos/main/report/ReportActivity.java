package com.mitrakreasindo.pos.main.report;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.adapter.SubReportListAdapter;
import com.mitrakreasindo.pos.model.Report;
import com.mitrakreasindo.pos.model.SubReport;
import com.mitrakreasindo.pos.service.ReportService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 05/08/17.
 */

public class ReportActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_report)
  RecyclerView listReport;
  @BindView(R.id.main_content)
  LinearLayout mainContent;

  private SubReportListAdapter reportListAdapter;
  private ReportService reportService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_report);
    ButterKnife.bind(this);

    reportService = ClientService.createService().create(ReportService.class);
    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    reportListAdapter = new SubReportListAdapter(this, new ArrayList<SubReport>());
    listReport.setAdapter(reportListAdapter);
    listReport.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listReport.setLayoutManager(layoutManager);
    listReport.setItemAnimator(new DefaultItemAnimator());

    getReport();
  }


  public void getReport()
  {

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage("Preparing your data...");
    progressDialog.show();

    final Call<Report> reportCall = reportService.getReportAll(kodeMerchant);
    reportCall.enqueue(new Callback<Report>()
    {
      @Override
      public void onResponse(Call<Report> call, Response<Report> response)
      {
        Report data = response.body();
        reportListAdapter.clear();
        reportListAdapter.addSubReports(data.getSubReports());
      }

      @Override
      public void onFailure(Call<Report> call, Throwable t)
      {

      }


    });

    progressDialog.dismiss();
  }
}
