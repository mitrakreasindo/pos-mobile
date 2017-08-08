package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Role;

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
 * Created by error on 19/05/17.
 */

public interface RoleService
{

  @GET("roles/{kodeMerchant}/")
  Call<List<Role>> getRoleAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("roles/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postRole(@Path("kodeMerchant") String kodeMerchant, @Body Role role);

  @DELETE("roles/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> deleteRole(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);

  @PUT("roles/{kodeMerchant}/{id}")
  Call<HashMap<Integer, String>> updateRole(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id, @Body Role role);

}
