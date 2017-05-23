package com.mitrakreasindo.pos.main.maintenance.role;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.adapter.RoleAdapter;
import com.mitrakreasindo.pos.model.Roles;
import com.mitrakreasindo.pos.main.maintenance.role.service.RoleService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by error on 17/05/17.
 */

public class RoleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_role)
    RecyclerView listRole;

    private RoleAdapter roleAdapter;
    private RoleService roleService;
    private Roles roles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_role);
        ButterKnife.bind(this);

//        roleAdapter = new RoleAdapter()

        roleService = ClientService.createService().create(RoleService.class);
        roleAdapter = new RoleAdapter(this, new ArrayList<Roles>());

        listRole.setHasFixedSize(true);
        listRole.setAdapter(roleAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listRole.setLayoutManager(layoutManager);

        Log.d("Data :", String.valueOf(roleAdapter));

        getRole();

    }

    private void getRole() {

        final Call<List<Roles>> role = roleService.getRoleAll();
        role.enqueue(new Callback<List<Roles>>() {
            @Override
            public void onResponse(Call<List<Roles>> call, Response<List<Roles>> response) {
                List<Roles> rolesList = response.body();
                roleAdapter.clear();
                roleAdapter.addRole(rolesList);
            }

            @Override
            public void onFailure(Call<List<Roles>> call, Throwable t) {

            }
        });
    }
}
