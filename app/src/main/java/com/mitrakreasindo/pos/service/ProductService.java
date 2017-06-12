package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-05-29.
 */

public interface ProductService
{
  @GET("chromis.products/{kodeMerchant}/")
  Call<List<Product>> getProductAll(@Path("kodeMerchant") String kodeMerchant);
}
