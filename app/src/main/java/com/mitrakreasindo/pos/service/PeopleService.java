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
  @GET("chromis.people/{kodeMerchant}/")
  Call<List<People>> getPeopleAll(@Path("kodeMerchant") String kodeMerchant);

  @GET("chromis.people/{kodeMerchant}/{id}")
  Call<List<People>> getPeople(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);

  @POST("chromis.people/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postPeople(@Path("kodeMerchant") String kodeMerchant, @Body People people);

  @PUT("chromis.people/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updatePeople(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body People people);

  @DELETE("chromis.people/{kodeMerchant}/{id}")
  Call<List<People>> deletePeople(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);
}
