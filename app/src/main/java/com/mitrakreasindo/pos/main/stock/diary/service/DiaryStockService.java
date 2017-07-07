package com.mitrakreasindo.pos.main.stock.diary.service;

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

  @GET("chromis.stockdiary/{kodeMerchant}/")
  Call<List<StockDiary>> getStockDiaryAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("chromis.stockdiary/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postStockDiary(@Path("kodeMerchant") String kodeMerchant, @Body StockDiary StockDiary);

  @PUT("chromis.stockdiary/{kodeMerchant}/{id}")
  Call<List<StockDiary>> updateStockDiary(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body StockDiary StockDiary);

  @DELETE("chromis.stockdiary/public/{id}")
  Call<List<StockDiary>> deleteStockDiary(@Path("id") String id);

}
