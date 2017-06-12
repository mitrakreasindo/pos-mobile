package com.mitrakreasindo.pos.main.maintenance.role;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.adapter.RoleAdapter;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.RoleService;

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

public class RoleActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_role)
  RecyclerView listRole;

  private RoleAdapter roleAdapter;
  private RoleService roleService;
  private Role role;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_role);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "");

    roleService = ClientService.createService().create(RoleService.class);
    roleAdapter = new RoleAdapter(this, new ArrayList<Role>());

    listRole.setHasFixedSize(true);
    listRole.setAdapter(roleAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listRole.setLayoutManager(layoutManager);

    getRole(kodeMerchant);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_list_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_add)
    {
      startActivity(new Intent(this, RoleFormActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    Toast.makeText(this, "i'am resuming this activity", Toast.LENGTH_LONG).show();
    getRole(kodeMerchant);
  }

  private void getRole(String kodeMerchant)
  {

    final Call<List<Role>> role = roleService.getRoleAll(kodeMerchant);
    role.enqueue(new Callback<List<Role>>()
    {
      @Override
      public void onResponse(Call<List<Role>> call, Response<List<Role>> response)
      {
        List<Role> rolesList = response.body();
        roleAdapter.clear();
        roleAdapter.addRole(rolesList);
      }

      @Override
      public void onFailure(Call<List<Role>> call, Throwable t)
      {

      }
    });
  }

}
