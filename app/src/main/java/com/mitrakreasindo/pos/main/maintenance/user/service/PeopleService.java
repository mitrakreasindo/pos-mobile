package com.mitrakreasindo.pos.main.maintenance.user.service;

import com.mitrakreasindo.pos.main.maintenance.user.model.People;

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

  @GET("chromis.people/public/")
  Call<List<People>> getPeopleAll();

  @POST("chromis.people/public/")
  Call<List<People>> postPeople(@Body People people);

  @DELETE("chromis.people/public/{id}")
  Call<List<People>> deletePeople(@Path("id") String id);

  @PUT("chromis.people/public/")
  Call<List<People>> updatePeople(@Body People people);
}
