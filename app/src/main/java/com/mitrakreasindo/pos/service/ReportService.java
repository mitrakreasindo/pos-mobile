package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Report;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by lisa on 07/08/17.
 */

public interface ReportService
{

  @GET("reports/{kodeMerchant}/multi")
  Call<Report> getReportAll(@Path("kodeMerchant") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

  @GET("reports/{kodeMerchant}/multi/download/pdf")
  @Streaming
  Call<ResponseBody> downloadPDF(@Path("kodeMerchant") String kodeMerchant, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

}
