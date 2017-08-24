package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.MerchantCategories;
import com.mitrakreasindo.pos.model.MerchantRegistration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-07-22.
 */

public interface MerchantService
{
  @POST("merchants/")
  Call<HashMap<Integer,String>> postMerchantRegistration(@Header("merchantCode") String kodeMerchant, @Body MerchantRegistration merchantRegistration);

  @GET("merchants/categories/name")
  Call<List<MerchantCategories>> getMerchantCategories(@Header("merchantCode") String kodeMerchant);

  @GET("merchants/categories/subname/{name}")
  Call<List<MerchantCategories>> getMerchantSubCategories(@Header("merchantCode") String kodeMerchant, @Path("name") String categoryName);

  @GET("merchants/public/categories")
  Call<List<MerchantCategories>> getAllMerchantCategories();
}
