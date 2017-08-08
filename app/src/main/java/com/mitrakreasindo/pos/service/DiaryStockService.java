package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.StockDiary;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by lisa on 06/07/17.
 */

public interface DiaryStockService
{
  @GET("stockdiary/{kodeMerchant}/")
  Call<List<StockDiary>> getStockDiaryAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("stockdiary/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postStockDiary(@Path("kodeMerchant") String kodeMerchant, @Body StockDiary stockDiary);

  @PUT("stockdiary/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updateStockDiary(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body StockDiary StockDiary);

  @DELETE("stockdiary/public/{id}")
  Call<HashMap<Integer, String>> deleteStockDiary(@Path("id") String id);
}
