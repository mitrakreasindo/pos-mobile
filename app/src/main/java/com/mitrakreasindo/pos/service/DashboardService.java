package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Money;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lisa on 16/08/17.
 */

public interface DashboardService
{

  @GET("monies/{merchantCode}/revenue")
  Call<Money> getRevenue(@Path("merchantCode") String kodeMerchant);

  @GET("monies/{merchantCode}/cost")
  Call<Money> getCost(@Path("merchantCode") String kodeMerchant);

}
