package com.mitrakreasindo.pos.main.maintenance.role.service;

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

  @GET("chromis.roles/public/")
  Call<List<Role>> getRoleAll();

  @POST("chromis.roles/public/")
  Call<List<Role>> postRole(@Body Role role);

  @DELETE("chromis.roles/public/{id}")
  Call<List<Role>> deleteRole(@Path("id") String id);

  @PUT("chromis.roles/public/")
  Call<List<Role>> updateRole(@Body Role role);

}
