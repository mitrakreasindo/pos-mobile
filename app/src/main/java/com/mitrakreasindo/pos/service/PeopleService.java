package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.People;

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
 * Created by lisa on 24/05/17.
 */

public interface PeopleService
{
  @GET("peoples/")
  Call<List<People>> getPeopleAll(@Header("merchantCode") String kodeMerchant);

  @GET("peoples/{id}")
  Call<List<People>> getPeople(@Header("merchantCode") String kodeMerchant, @Path("id") String id);

  @POST("peoples/")
  Call<HashMap<Integer, String>> postPeople(@Header("merchantCode") String kodeMerchant, @Body People people);

  @PUT("peoples/{id}")
  Call<HashMap<Integer, String>> updatePeople(@Header("merchantCode") String kodeMerchant, @Path("id") String id, @Body People people);

  @DELETE("peoples/{id}")
  Call<HashMap<Integer, String>> deletePeople(@Header("merchantCode") String kodeMerchant, @Path("id") String id);
}
