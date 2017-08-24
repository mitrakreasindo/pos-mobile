package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.StockDiary;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by lisa on 06/07/17.
 */

public interface DiaryStockService
{
  @GET("stocks/diary/")
  Call<List<StockDiary>> getstockcurrentsAll(@Header("merchantCode") String kodeMerchant);

  @POST("stocks/diary/")
  Call<HashMap<Integer, String>> poststockcurrents(@Header("merchantCode") String kodeMerchant, @Body StockDiary stockDiary);

  @PUT("stocks/diary/{id}")
  Call<HashMap<Integer, String>> updatestockcurrents(@Header("merchantCode") String kodeMerchant, @Path("id") String id, @Body StockDiary stockDiary);

  @DELETE("stocks/diary/{id}")
  Call<HashMap<Integer, String>> deletestockcurrents(@Path("id") String id, @Header("merchantCode") String kodeMerchant);
}
