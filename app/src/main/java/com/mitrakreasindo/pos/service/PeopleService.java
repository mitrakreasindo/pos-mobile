package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.People;

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
 * Created by lisa on 24/05/17.
 */

public interface PeopleService
{
  @GET("peoples/{kodeMerchant}/")
  Call<List<People>> getPeopleAll(@Path("kodeMerchant") String kodeMerchant);

  @GET("peoples/{kodeMerchant}/{id}")
  Call<List<People>> getPeople(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);

  @POST("peoples/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postPeople(@Path("kodeMerchant") String kodeMerchant, @Body People people);

  @PUT("peoples/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updatePeople(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body People people);

  @DELETE("peoples/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> deletePeople(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);
}
