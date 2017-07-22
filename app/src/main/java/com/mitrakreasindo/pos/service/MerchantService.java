package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Merchant;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by hendric on 2017-07-22.
 */

public interface MerchantService
{
  @Headers("Content-type: application/json")
  @POST("chromis.merchants/public")
  Call<HashMap<Integer,String>> postMerchant(@Body Merchant merchant);
}
