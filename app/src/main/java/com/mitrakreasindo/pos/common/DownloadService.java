package com.mitrakreasindo.pos.common;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.MainActivity;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.report.ReportActivity;
import com.mitrakreasindo.pos.model.Download;
import com.mitrakreasindo.pos.service.ReportService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by lisa on 10/08/17.
 */

public class DownloadService extends IntentService
{

  public DownloadService() {
    super("Download Service");
  }

  private NotificationCompat.Builder notificationBuilder;
  private NotificationManager notificationManager;
  private int totalFileSize;
  private ReportService reportService;

  @Override
  protected void onHandleIntent(Intent intent) {

    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationBuilder = new NotificationCompat.Builder(this)
      .setSmallIcon(R.drawable.ic_file_download_black_24dp)
      .setContentTitle("Download")
      .setContentText("Downloading File")
      .setAutoCancel(true);
    notificationManager.notify(0, notificationBuilder.build());

    initDownload();

  }

  private void initDownload(){

    reportService = ClientService.createService().create(ReportService.class);

    Call<ResponseBody> request = reportService.downloadPDF("public", "2017-07-08 17:47:00.0", "2017-08-09 00:00:00.0");
    try {

      downloadFile(request.execute().body());

    } catch (IOException e) {

      e.printStackTrace();
      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

    }
  }

  private void downloadFile(ResponseBody body) throws IOException {

    int count;
    byte data[] = new byte[1024 * 4];
    long fileSize = body.contentLength();
    InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
    File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "file.pdf");
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
    onDownloadComplete();
    output.flush();
    output.close();
    bis.close();

  }

  private void sendNotification(Download download){

    sendIntent(download);
    notificationBuilder.setProgress(100,download.getProgress(),false);
    notificationBuilder.setContentText("Downloading file "+ download.getCurrentFileSize() +"/"+totalFileSize +" MB");
    notificationManager.notify(0, notificationBuilder.build());
  }

  private void sendIntent(Download download){

    Intent intent = new Intent(ReportActivity.MESSAGE_PROGRESS);
    intent.putExtra("download",download);
    LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
  }

  private void onDownloadComplete(){

    Download download = new Download();
    download.setProgress(100);
    sendIntent(download);

    notificationManager.cancel(0);
    notificationBuilder.setProgress(0,0,false);
    notificationBuilder.setContentText("File Downloaded");
    notificationManager.notify(0, notificationBuilder.build());

  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    notificationManager.cancel(0);
  }

}