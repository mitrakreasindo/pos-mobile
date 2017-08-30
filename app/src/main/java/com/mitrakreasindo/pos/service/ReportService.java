package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Report;
import com.mitrakreasindo.pos.model.ReportCategory;
import com.mitrakreasindo.pos.model.ReportCategorySub;
import com.mitrakreasindo.pos.model.ReportDate;
import com.mitrakreasindo.pos.model.ReportSalesSub;
import com.mitrakreasindo.pos.model.ReportSubCategorySub;
import com.mitrakreasindo.pos.model.ReportSubDate;

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
  Call<Report<ReportSalesSub>> getReportAll(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/category/")
  Call<Report<ReportCategorySub>> getReportCategoryAll(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/subcategory/")
  Call<ReportDate<ReportSubDate>> getReportSubCategoryAll(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/sales/download/pdf")
  @Streaming
  Call<ResponseBody> downloadReportSales(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/category/download/pdf")
  @Streaming
  Call<ResponseBody> downloadReportCategories(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/subcategory/download/pdf")
  @Streaming
  Call<ResponseBody> downloadReportSubCategories(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);
//  @GET("reports/sales/")
//  Call<SalesReport> getReportAll(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);
//
//  @GET("reports/sales/download/pdf")
//  @Streaming
//  Call<ResponseBody> downloadPDF(@Header("merchantCode") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

}
