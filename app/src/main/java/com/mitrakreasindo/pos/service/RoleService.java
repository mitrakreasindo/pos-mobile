package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.Role;

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
 * Created by error on 19/05/17.
 */

public interface RoleService
{

  @GET("roles/")
  Call<List<Role>> getRoleAll(@Header("merchantCode") String kodeMerchant);

  @POST("roles/")
  Call<HashMap<Integer, String>> postRole(@Header("merchantCode") String kodeMerchant, @Body Role role);

  @DELETE("roles/{id}")
  Call<HashMap<Integer, String>> deleteRole(@Header("merchantCode") String kodeMerchant, @Path("id") String id);

  @PUT("roles/{id}")
  Call<HashMap<Integer, String>> updateRole(@Header("merchantCode") String kodeMerchant, @Path("id") String id, @Body Role role);

}
