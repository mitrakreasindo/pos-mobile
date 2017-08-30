package com.mitrakreasindo.pos.main.report;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.common.DownloadService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.adapter.ReportCategoryAdapter;
import com.mitrakreasindo.pos.main.report.adapter.ReportSalesAdapter;
import com.mitrakreasindo.pos.main.report.adapter.ReportSubCategoryAdapter;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;
import com.mitrakreasindo.pos.model.Download;
import com.mitrakreasindo.pos.model.Report;
import com.mitrakreasindo.pos.model.ReportCategorySub;
import com.mitrakreasindo.pos.model.ReportDate;
import com.mitrakreasindo.pos.model.ReportSalesSub;
import com.mitrakreasindo.pos.model.ReportSubDate;
import com.mitrakreasindo.pos.service.ReportService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
  @BindView(R.id.filter_report_from_date)
  TextView filterReportFromDate;
  @BindView(R.id.filter_report_to_date)
  TextView filterReportToDate;

  private ReportSalesAdapter reportListAdapter;
  private ReportCategoryAdapter reportCategoryAdapter;
  private ReportSubCategoryAdapter reportSubCategoryAdapter;

  private ReportService reportService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  private DefaultHelper defaultHelper = new DefaultHelper();
  private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

  public static final String MESSAGE_PROGRESS = "message_progress";
  private static final int PERMISSION_REQUEST_CODE = 1;

  private Report data;

  private Bundle bundle;

  String TAG;

  private int totalFileSize;
  private NotificationCompat.Builder notificationBuilder;
  private NotificationManager notificationManager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_report);
    ButterKnife.bind(this);

    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationBuilder = new NotificationCompat.Builder(this)
      .setSmallIcon(R.drawable.ic_file_download_black_24dp)
      .setContentTitle("Download")
      .setContentText("Downloading File")
      .setAutoCancel(true);
    notificationManager.notify(0, notificationBuilder.build());

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

            switch (TAG)
            {
              case "SALES":
                getReportSales();
                break;

              case "CATEGORY":
                getReportCategories();
                break;
              case "SUBCATEGORY":
                getReportSubCategories();
                break;
            }
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
            switch (TAG)
            {
              case "SALES":
                getReportSales();
                break;

              case "CATEGORY":
                getReportCategories();
                break;
              case "SUBCATEGORY":
                getReportSubCategories();
                break;
            }
          }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
      }
    });

    bundle = getIntent().getExtras();
    if (bundle != null)
    {
      TAG = bundle.getString("tag");
      switch (TAG)
      {
        case "SALES":
          reportListAdapter = new ReportSalesAdapter(this, new ArrayList<ReportSalesSub>());
          if (findViewById(R.id.detail_report) != null)
          {
            reportListAdapter.twoPane = true;
          }
          listReport.setAdapter(reportListAdapter);
          getReportSales();
          break;

        case "CATEGORY":
          reportCategoryAdapter = new ReportCategoryAdapter(this, new ArrayList<ReportCategorySub>());
          if (findViewById(R.id.detail_report) != null)
          {
            reportCategoryAdapter.twoPane = true;
          }
          listReport.setAdapter(reportCategoryAdapter);
          getReportCategories();
          break;
        case "SUBCATEGORY":
          reportSubCategoryAdapter = new ReportSubCategoryAdapter(this, new ArrayList<ReportSubDate>());
          if (findViewById(R.id.detail_report) != null)
          {
            reportSubCategoryAdapter.twoPane = true;
          }
          listReport.setAdapter(reportSubCategoryAdapter);
          getReportSubCategories();
          break;
      }

    }

    listReport.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listReport.setLayoutManager(layoutManager);
    listReport.setItemAnimator(new DefaultItemAnimator());


    registerReceiver();

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

      if (checkPermission())
      {
        switch (TAG)
        {
          case "SALES":
            downloadSalesReport();
            break;

          case "CATEGORY":
            downloadCategoryReport();
            break;
          case "SUBCATEGORY":
            downloadSubCategoryReport();
            break;
        }
      } else
      {
        requestPermission();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  public void getReportSales()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<Report<ReportSalesSub>> reportCall = reportService
      .getReportAll(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

    reportCall.enqueue(new Callback<Report<ReportSalesSub>>()
    {
      @Override
      public void onResponse(Call<Report<ReportSalesSub>> call, Response<Report<ReportSalesSub>> response)
      {
        Report data = response.body();
        if (data != null)
        {
          reportListAdapter.clear();
          reportListAdapter.addSubReports(data.getSubReports());
        }
      }

      @Override
      public void onFailure(Call<Report<ReportSalesSub>> call, Throwable t)
      {
      }

    });

    Log.d("ESTES", "TEST");
    progressDialog.dismiss();
  }

  public void getReportCategories()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<Report<ReportCategorySub>> reportCall = reportService
      .getReportCategoryAll(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

    reportCall.enqueue(new Callback<Report<ReportCategorySub>>()
    {
      @Override
      public void onResponse(Call<Report<ReportCategorySub>> call, Response<Report<ReportCategorySub>> response)
      {
        Report data = response.body();
        if (data != null)
        {
          reportCategoryAdapter.clear();
          reportCategoryAdapter.addSubReports(data.getSubReports());
        }
      }

      @Override
      public void onFailure(Call<Report<ReportCategorySub>> call, Throwable t)
      {
        Log.d("FAILUREREPORT", "FAILURE");
      }

    });

    Log.d("ESTES", "TEST");
    progressDialog.dismiss();
  }

  public void getReportSubCategories()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<ReportDate<ReportSubDate>> reportCall = reportService
      .getReportSubCategoryAll(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

    reportCall.enqueue(new Callback<ReportDate<ReportSubDate>>()
    {
      @Override
      public void onResponse(Call<ReportDate<ReportSubDate>> call, Response<ReportDate<ReportSubDate>> response)
      {
        ReportDate<ReportSubDate> data = response.body();
//        List<ReportSubCategorySub> reportSubCategorySubs = data.getSubReportDate().;
        Log.d("GET0", data.getSubReportDate().get(0).getDate().toString());
        if (data != null)
        {
          reportSubCategoryAdapter.clear();
          reportSubCategoryAdapter.addSubReports(data.getSubReportDate());
        }
      }

      @Override
      public void onFailure(Call<ReportDate<ReportSubDate>> call, Throwable t)
      {
        Log.d("FAILUREREPORT", t.getMessage());
      }

    });

    Log.d("ESTES", "TEST");
    progressDialog.dismiss();
  }

  public void downloadSalesReport()
  {

    String title = "REPORT SALES - "
      + filterReportFromDate.getText().toString()
      + " - "
      + filterReportToDate.getText().toString();

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<ResponseBody> downloadReportCall = reportService
      .downloadReportSales(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

    try
    {
      downloadFile(downloadReportCall.execute().body(), title);
    } catch (IOException e)
    {
      e.printStackTrace();
    }

//    downloadReportCall.enqueue(new Callback<ResponseBody>()
//    {
//      @Override
//      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
//      {
//
//        writeResponseBodyToDisk(response.body());
//        Log.d("FILESIZE", String.valueOf(response.body().contentLength()));
//        Toast.makeText(ReportActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
//      }
//
//      @Override
//      public void onFailure(Call<ResponseBody> call, Throwable t)
//      {
//        Toast.makeText(ReportActivity.this, "FAILED", Toast.LENGTH_LONG).show();
//      }
//    });

    progressDialog.dismiss();
  }

  public void downloadCategoryReport()
  {

    String title = "REPORT CATEGORY - "
      + filterReportFromDate.getText().toString()
      + " - "
      + filterReportToDate.getText().toString();

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<ResponseBody> downloadReportCall = reportService
      .downloadReportCategories(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

    try
    {
      downloadFile(downloadReportCall.execute().body(), title);
    } catch (IOException e)
    {
      e.printStackTrace();
    }

    progressDialog.dismiss();
  }

  public void downloadSubCategoryReport()
  {
    String title = "REPORT SUB CATEGORY - "
      + filterReportFromDate.getText().toString()
      + " - "
      + filterReportToDate.getText().toString();

    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage(getString(R.string.prepare_data));
    progressDialog.show();

    final Call<ResponseBody> downloadReportCall = reportService
      .downloadReportSubCategories(kodeMerchant, filterReportFromDate.getText().toString() + " 00:00:00",
        filterReportToDate.getText().toString() + " 00:00:00");

    try
    {
      downloadFile(downloadReportCall.execute().body(), title);
    } catch (IOException e)
    {
      e.printStackTrace();
    }

    progressDialog.dismiss();
  }



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

  private void downloadFile(ResponseBody body, String title) throws IOException {

    int count;
    byte data[] = new byte[1024 * 4];
    long fileSize = body.contentLength();
    InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
    File outputFile = new File(Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), title + ".pdf");
    OutputStream output = new FileOutputStream(outputFile);
    long total = 0;
    long startTime = System.currentTimeMillis();
    int timeCount = 1;
    while ((count = bis.read(data)) != -1) {

      total += count;
      totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
      double current = Math.round(total / (Math.pow(1024, 2)));

      int progress = (int) ((total * 100) / fileSize);

      long currentTime = System.currentTimeMillis() - startTime;

      Download download = new Download();
      download.setTotalFileSize(totalFileSize);

      if (currentTime > 1000 * timeCount) {

        download.setCurrentFileSize((int) current);
        download.setProgress(progress);
        sendNotification(download);
        timeCount++;
      }

      output.write(data, 0, count);
    }
    onDownloadComplete(title);
    output.flush();
    output.close();
    bis.close();

  }

  private void sendNotification(Download download){

    notificationBuilder.setProgress(100,download.getProgress(),false);
    notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
    notificationManager.notify(0, notificationBuilder.build());
  }

  private void onDownloadComplete(String title){

    Intent intent = new Intent();
    intent.setAction(android.content.Intent.ACTION_VIEW);
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), title + ".pdf");
    intent.setDataAndType(Uri.fromFile(file), "application/pdf");

    PendingIntent resultPendingIntent =
      PendingIntent.getActivity(
        this,
        0,
        intent,
        0
      );
    Download download = new Download();
    download.setProgress(100);

    notificationManager.cancel(0);
    notificationBuilder.setProgress(0,0,false);
    notificationBuilder.setContentText("File Downloaded");
    notificationBuilder.setContentIntent(resultPendingIntent);
    notificationManager.notify(0, notificationBuilder.build());

  }

}
