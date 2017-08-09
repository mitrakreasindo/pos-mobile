package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.SalesPack;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lisa on 31/07/17.
 */

public interface SalesService
{

  @POST("sales/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postSales(@Path("kodeMerchant") String kodeMerchant, @Body SalesPack salesPack);

}
