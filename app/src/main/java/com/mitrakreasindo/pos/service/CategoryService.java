package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-05-29.
 */

public interface CategoryService
{
  @GET("categories/")
  Call<List<Category>> getCategoryAll(@Header("merchantCode") String kodeMerchant);

  @POST("categories/")
  Call<HashMap<Integer, String>> postCategory(@Header("merchantCode") String kodeMerchant, @Body Category category);

  @PUT("categories/{id}")
  Call<HashMap<Integer, String>> updateCategory(@Header("merchantCode") String kodeMerchant, @Path("id") String id, @Body Category category);

  @DELETE("categories/{id}")
  Call<HashMap<Integer, String>> deleteCategory(@Header("merchantCode") String kodeMerchant, @Path("id") String id);

}
