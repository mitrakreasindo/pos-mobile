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
  @GET("stockcurrents/{kodeMerchant}/")
  Call<List<StockDiary>> getstockcurrentsAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("stockcurrents/{kodeMerchant}/")
  Call<HashMap<Integer, String>> poststockcurrents(@Path("kodeMerchant") String kodeMerchant, @Body StockDiary stockDiary);

  @PUT("stockcurrents/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updatestockcurrents(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body StockDiary stockDiary);

  @DELETE("stockcurrents/public/{id}")
  Call<HashMap<Integer, String>> deletestockcurrents(@Path("id") String id);
}
