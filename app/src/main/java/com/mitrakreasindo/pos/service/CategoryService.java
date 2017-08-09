package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;

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
 * Created by hendric on 2017-05-29.
 */

public interface CategoryService
{
  @GET("categories/{kodeMerchant}/")
  Call<List<Category>> getCategoryAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("categories/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postCategory(@Path("kodeMerchant") String kodeMerchant, @Body Category category);

  @PUT("categories/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updateCategory(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body Category category);

  @DELETE("categories/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> deleteCategory(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);

}
