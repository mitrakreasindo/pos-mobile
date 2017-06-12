package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Role;

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

  @GET("chromis.roles/{kodeMerchant}/")
  Call<List<Role>> getRoleAll(@Path("kodeMerchant") String kodeMerchant);

  @POST("chromis.roles/{kodeMerchant}/")
  Call<List<Role>> postRole(@Path("kodeMerchant") String kodeMerchant, @Body Role role);

  @DELETE("chromis.roles/{kodeMerchant}/{id}")
  Call<List<Role>> deleteRole(@Path("kodeMerchant") String kodeMerchant, @Path("id") String id);

  @PUT("chromis.roles/{kodeMerchant}/")
  Call<List<Role>> updateRole(@Path("kodeMerchant") String kodeMerchant, @Body Role role);

}
