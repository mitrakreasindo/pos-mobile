package com.mitrakreasindo.pos.main.stock.category.service;

import com.mitrakreasindo.pos.main.stock.category.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hendric on 2017-05-29.
 */

public interface CategoryService
{
  @GET("chromis.categories/public")
  Call<List<Category>> getCategoryAll();

}
