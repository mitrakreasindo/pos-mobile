package com.mitrakreasindo.pos.main.maintenance.role.service;

import com.mitrakreasindo.pos.model.Roles;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by error on 19/05/17.
 */

public interface RoleService {

    @GET("chromis.roles/")
    Call<List<Roles>> getRoleAll();
}
