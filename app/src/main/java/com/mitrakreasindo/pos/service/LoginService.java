package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by hendric on 2017-05-24.
 */

public interface LoginService
{
  @Headers("Content-type: application/json")
  @POST("chromis.people/doLogin")
  Call<HashMap<Integer,String>> postLogin(@Body Login login);
}
