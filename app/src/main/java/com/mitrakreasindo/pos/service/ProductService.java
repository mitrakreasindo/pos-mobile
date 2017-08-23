package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Product;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-05-29.
 */

public interface ProductService
{

  @POST("products/")
  Call<HashMap<Integer, String>> postProduct(@Header("merchantCode") String kodeMerchant, @Body Product product);

  @PUT("products/{id}")
  Call<HashMap<Integer, String>> updateProduct(@Header("merchantCode") String kodeMerchant, @Path("id") String id, @Body Product product);

  @GET("products/")
  Call<List<Product>> getProductAll(@Header("merchantCode") String kodeMerchant);

  @GET("products/{id}")
  Call<List<Product>> getProductById(@Header("merchantCode") String kodeMerchant, @Path("id") String id);

  @DELETE("products/{id}")
  Call<HashMap<Integer, String>> deleteProduct(@Header("merchantCode") String kodeMerchant, @Path("id") String id);
}
