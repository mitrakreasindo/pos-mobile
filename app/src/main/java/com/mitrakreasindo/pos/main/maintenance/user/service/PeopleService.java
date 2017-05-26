package com.mitrakreasindo.pos.main.maintenance.user.service;

import com.mitrakreasindo.pos.main.maintenance.user.model.People;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by lisa on 24/05/17.
 */

public interface PeopleService
{

  @GET("chromis.people/public")
  Call<List<People>> getPeopleAll();

  @POST("chromis.people/public/")
  Call<List<People>> postPeople(@Body People people);
}
