package com.mitrakreasindo.pos.main.maintenance.taxes.service;

import com.mitrakreasindo.pos.model.Tax;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by lisa on 06/07/17.
 */

public interface TaxService
{

  @GET("chromis.taxes/{kodeMerchant}/")
  Call<List<Tax>> getTaxAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("chromis.taxes/{kodeMerchant}/")
  Call<List<Tax>> postTax(@Path("kodeMerchant") String kodeMerchant, @Body Tax tax);

  @PUT("chromis.taxes/{kodeMerchant}/{id}")
  Call<List<Tax>> updateTax(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body Tax tax);

  @DELETE("chromis.taxes/public/{id}")
  Call<List<Tax>> deleteTax(@Path("id") String id);
  
}
