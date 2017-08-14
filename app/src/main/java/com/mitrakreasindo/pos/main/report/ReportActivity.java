package com.mitrakreasindo.pos.main.report;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.common.DownloadService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.adapter.SubReportListAdapter;
import com.mitrakreasindo.pos.main.stock.diary.activity.DiaryFormActivity;
import com.mitrakreasindo.pos.model.Download;
import com.mitrakreasindo.pos.model.Report;
import com.mitrakreasindo.pos.model.SubReport;
import com.mitrakreasindo.pos.service.ReportService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
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
  @BindView(R.id.filter_report_from_date)
  TextView filterReportFromDate;
  @BindView(R.id.filter_report_to_date)
  TextView filterReportToDate;

  private SubReportListAdapter reportListAdapter;
  private ReportService reportService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  private DefaultHelper defaultHelper = new DefaultHelper();
  private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

  public static final String MESSAGE_PROGRESS = "message_progress";
  private static final int PERMISSION_REQUEST_CODE = 1;


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

    Calendar cal = GregorianCalendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DAY_OF_YEAR, -7);
    Date aWeekDateBefore = cal.getTime();

    filterReportFromDate.setText(defaultHelper.dateOnlyFormat(aWeekDateBefore));
    filterReportToDate.setText(defaultHelper.dateOnlyFormat(new Date()));

    filterReportFromDate.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond = c.get(Calendar.SECOND);


        DatePickerDialog datePickerDialog = new DatePickerDialog(ReportActivity.this, R.style.DefaultDatePicker, new DatePickerDialog.OnDateSetListener()
        {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
          {
            String month, day;

            if (String.valueOf(monthOfYear + 1).length() == 1)
            {
              month = "0" + String.valueOf(monthOfYear + 1);
            } else
            {
              month = String.valueOf(monthOfYear + 1);
            }

            if (String.valueOf(dayOfMonth).length() == 1)
            {
              day = "0" + String.valueOf(dayOfMonth);
            } else
            {
              day = String.valueOf(dayOfMonth);
            }

            filterReportFromDate.setText(year + "-" + month + "-" + day);
            getReport();
          }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
      }
    });

    filterReportToDate.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond = c.get(Calendar.SECOND);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(ReportActivity.this, R.style.DefaultDatePicker, new DatePickerDialog.OnDateSetListener()
        {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
          {
            String month, day;

            if (String.valueOf(monthOfYear + 1).length() == 1)
            {
              month = "0" + String.valueOf(monthOfYear + 1);
            } else
            {
              month = String.valueOf(monthOfYear + 1);
            }

            if (String.valueOf(dayOfMonth).length() == 1)
            {
              day = "0" + String.valueOf(dayOfMonth);
            } else
            {
              day = String.valueOf(dayOfMonth);
            }

            filterReportToDate.setText(year + "-" + month + "-" + day);
            getReport();
          }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
      }
    });

    reportListAdapter = new SubReportListAdapter(this, new ArrayList<SubReport>());
    listReport.setAdapter(reportListAdapter);
    listReport.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listReport.setLayoutManager(layoutManager);
    listReport.setItemAnimator(new DefaultItemAnimator());

    registerReceiver();

    getReport();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.report_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();
    if (id == R.id.action_download)
    {
//      downloadReport();
      if (checkPermission())
      {
        startDownload();
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
      } else
      {
        requestPermission();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  public void getReport()
  {

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<Report> reportCall = reportService
      .getReportAll(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

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

//  public void downloadReport()
//  {
//
//    final ProgressDialog progressDialog = new ProgressDialog(this);
//    progressDialog.setCancelable(false);
//    progressDialog.setMessage(getString(R.string.prepare_data));
//    progressDialog.show();
//
//    final Call<Report> downloadReportCall = reportService
//      .downloadPDF(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
//        filterReportToDate.getText().toString() + " 00:00:00");
//
//    downloadReportCall.enqueue(new Callback<Report>()
//    {
//      @Override
//      public void onResponse(Call<Report> call, Response<Report> response)
//      {
//
//      }
//
//      @Override
//      public void onFailure(Call<Report> call, Throwable t)
//      {
//
//      }
//    });
//
//    Log.d(getClass().getSimpleName(), "test download");
//    progressDialog.dismiss();
//  }

  private void startDownload()
  {

    Intent intent = new Intent(this, DownloadService.class);
    startService(intent);

  }

  private void registerReceiver()
  {

    LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(MESSAGE_PROGRESS);
    bManager.registerReceiver(broadcastReceiver, intentFilter);

  }

  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {

      if (intent.getAction().equals(MESSAGE_PROGRESS))
      {

        Download download = intent.getParcelableExtra("download");
//        mProgressBar.setProgress(download.getProgress());
//        if(download.getProgress() == 100){
//
//          mProgressText.setText("File Download Complete");
//
//        } else {
//
//          mProgressText.setText(String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
//
//        }
      }
    }
  };

  private boolean checkPermission()
  {
    int result = ContextCompat.checkSelfPermission(this,
      Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (result == PackageManager.PERMISSION_GRANTED)
    {

      return true;

    } else
    {

      return false;
    }
  }

  private void requestPermission()
  {

    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
  {
    switch (requestCode)
    {
      case PERMISSION_REQUEST_CODE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

          startDownload();
        } else
        {

//          Snackbar.make(findViewById(R.id.coordinatorLayout),"Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();
          Toast.makeText(this, "Permisson denied", Toast.LENGTH_LONG).show();
        }
        break;
    }
  }

}
