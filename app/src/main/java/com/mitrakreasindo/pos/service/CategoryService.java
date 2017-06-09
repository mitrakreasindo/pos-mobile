package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Role;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-05-29.
 */

public interface CategoryService
{
  @GET("chromis.categories/public")
  Call<List<Category>> getCategoryAll();

  @POST("chromis.categories/public/")
  Call<List<Category>> postCategory(@Body Category category);

  @PUT("chromis.categories/public/{id}")
  Call<List<Category>> updateCategory(@Path("id") String id, @Body Category category);

}
