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
  @GET("chromis.categories/{kodeMerchant}/")
  Call<List<Category>> getCategoryAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("chromis.categories/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postCategory(@Path("kodeMerchant") String kodeMerchant, @Body Category category);

  @PUT("chromis.categories/{kodeMerchant}/{id}")
  Call<List<Category>> updateCategory(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body Category category);

  @DELETE("chromis.categories/public/{id}")
  Call<List<Category>> deleteCategory(@Path("id") String id);

}
