package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hendric on 2017-05-29.
 */

public interface ProductService
{
  @GET("chromis.products/public")
  Call<List<Product>> getProductAll();
}
