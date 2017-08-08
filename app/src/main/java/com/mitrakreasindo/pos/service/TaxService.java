package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Tax;

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
 * Created by hendric on 2017-07-07.
 */

public interface TaxService
{
  @GET("taxes/{kodeMerchant}/")
  Call<List<Tax>> getTaxAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("taxes/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postTax(@Path("kodeMerchant") String kodeMerchant, @Body Tax tax);

  @PUT("taxes/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updateTax(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body Tax tax);

  @DELETE("taxes/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> deleteTax(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);
}
