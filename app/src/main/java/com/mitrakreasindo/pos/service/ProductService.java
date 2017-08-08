package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Product;

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

public interface ProductService
{

  @POST("products/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postProduct(@Path("kodeMerchant") String kodeMerchant, @Body Product product);

  @PUT("products/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updateProduct(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body Product product);

  @GET("products/{kodeMerchant}/")
  Call<List<Product>> getProductAll(@Path("kodeMerchant") String kodeMerchant);

  @GET("products/{kodeMerchant}/{id}")
  Call<List<Product>> getProductById(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);

  @DELETE("products/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> deleteProduct(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);
}
