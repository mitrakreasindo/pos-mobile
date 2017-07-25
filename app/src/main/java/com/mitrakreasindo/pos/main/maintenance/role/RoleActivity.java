package com.mitrakreasindo.pos.main.maintenance.role;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.controller.RoleAdapter;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.RoleService;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
  @BindView(R.id.edit_filter)
  EditText txtFilter;
  @BindView(R.id.button_filter)
  Button btnClearFilter;

  private RoleAdapter roleAdapter;
  private RoleService roleService;
  private Role role;
  private String kodeMerchant;
  private TableRoleHelper tableRoleHelper;

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

    btnClearFilter.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        txtFilter.setText("");
      }
    });

    kodeMerchant = SharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    roleService = ClientService.createService().create(RoleService.class);

    tableRoleHelper = new TableRoleHelper(this);
    roleAdapter = new RoleAdapter(this, new ArrayList<Role>());

    listRole.setHasFixedSize(true);
    listRole.setAdapter(roleAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listRole.setLayoutManager(layoutManager);


    roleAdapter.clear();
    roleAdapter.addRole(tableRoleHelper.getData());

    txtFilter.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        roleAdapter.clear();
        roleAdapter.addRole(tableRoleHelper.getDataByName(txtFilter.getText().toString()));
      }
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      {
      }
      @Override
      public void afterTextChanged(Editable s)
      {
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_list_menu, menu);
    MenuItem menuInsert = menu.findItem(R.id.action_add);
    if (PermissionUtil.getInactive(this, "maintenance_role_action").contains(MenuIds.rp_mtc_rl_action_insert))
    {
      menuInsert.setVisible(false);
    }

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_add)
    {
      startActivity(new Intent(this, RolePermissionActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    Toast.makeText(this, "i'am resuming this activity", Toast.LENGTH_LONG).show();

    roleAdapter.clear();
    roleAdapter.addRole(tableRoleHelper.getData());
    roleAdapter.setPermission();
//    getRole(kodeMerchant);
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

  public List<String> getInactive()
  {
    List<String> list = new ArrayList<>();
    TableRoleHelper tableRoleHelper = new TableRoleHelper(this);
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());
    if (permission != null)
    {
      list = XMLHelper.XMLReader(this, "role_action", permission);
    }
    return list;
  }

}
