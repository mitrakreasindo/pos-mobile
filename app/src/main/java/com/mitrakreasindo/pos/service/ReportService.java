package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Report;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by lisa on 07/08/17.
 */

public interface ReportService
{

  @GET("reports/sales/")
  Call<Report> getReportAll(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/sales/download/pdf")
  @Streaming
  Call<ResponseBody> downloadPDF(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

//  @GET("reports/sales/")
//  Call<SalesReport> getReportAll(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);
//
//  @GET("reports/sales/download/pdf")
//  @Streaming
//  Call<ResponseBody> downloadPDF(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

}
