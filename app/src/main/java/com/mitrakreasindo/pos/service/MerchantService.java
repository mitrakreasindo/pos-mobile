package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.MerchantCategories;
import com.mitrakreasindo.pos.model.MerchantRegistration;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-07-22.
 */

public interface MerchantService
{
  @POST("merchants/public")
  Call<HashMap<Integer,String>> postMerchantRegistration(@Body MerchantRegistration merchantRegistration);

  @GET("merchantcategories/public/getname/")
  Call<List<MerchantCategories>> getMerchantCategories();

  @GET("merchantcategories/public/getsubname/{name}/")
  Call<List<MerchantCategories>> getMerchantSubCategories(@Path("name") String categoryName);

  @GET("merchantcategories/public/")
  Call<List<MerchantCategories>> getAllMerchantCategories();
}
