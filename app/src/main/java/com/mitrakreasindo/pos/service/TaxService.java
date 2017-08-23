package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Tax;

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
 * Created by hendric on 2017-07-07.
 */

public interface TaxService
{
  @GET("taxes/")
  Call<List<Tax>> getTaxAll(@Header("merchantCode") String kodeMerchant);

  @POST("taxes/")
  Call<HashMap<Integer, String>> postTax(@Header("merchantCode") String kodeMerchant, @Body Tax tax);

  @PUT("taxes/{id}")
  Call<HashMap<Integer, String>> updateTax(@Header("merchantCode") String kodeMerchant, @Path("id") String id, @Body Tax tax);

  @DELETE("taxes/{id}")
  Call<HashMap<Integer, String>> deleteTax(@Header("merchantCode") String kodeMerchant, @Path("id") String id);
}
