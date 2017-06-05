package com.mitrakreasindo.pos.main.stock.product.service;

import com.mitrakreasindo.pos.main.stock.category.model.Category;
import com.mitrakreasindo.pos.main.stock.product.model.Product;

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
