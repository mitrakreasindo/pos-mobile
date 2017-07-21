package com.mitrakreasindo.pos.main.maintenance.role;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.RoleService;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoleFormActivity extends AppCompatActivity
{

  @BindView(R.id.role_field)
  EditText roleField;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.main_content)
  CoordinatorLayout mainContent;

  private Role role;
  private RoleService roleService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_role_form);
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

    roleService = ClientService.createService().create(RoleService.class);

    sharedPreferenceEditor = new SharedPreferenceEditor();

    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    final Bundle bundle = getIntent().getExtras();

    if (bundle != null)
    {
      String name = bundle.getString("name");
      String roleId = bundle.getString("id");

      roleField.setText(name);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_form_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {

    int id = item.getItemId();

    final Bundle bundle = getIntent().getExtras();

    if (id == R.id.action_confirm)
    {
      if (bundle != null)
      {
        updateRole();
      }
      else
      {
        postRole();
      }
    }
    return super.onOptionsItemSelected(item);
  }

  private void postRole()
  {
    Bundle bundle = getIntent().getExtras();

    final ProgressDialog progressDialog = new ProgressDialog(RoleFormActivity.this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    Log.d(getClass().getSimpleName(), "Post Role !!!");
    Log.d(getClass().getSimpleName(), roleField.getText().toString());

    String example = "Convert Java String";
    byte[] bytes = example.getBytes();

    role = new Role();
    role.setName(roleField.getText().toString());
    role.setRightslevel(3);
    role.setPermissions(bytes);
    role.setSiteguid(RestVariable.SITE_GUID);
    role.setSflag(true);

    Call<HashMap<Integer, String>> call = roleService.postRole(kodeMerchant, role);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        Log.d(getClass().getSimpleName(), "Success Post Role !!!");
        onBackPressed();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
      }
    });
    onBackPressed();
  }

  private void updateRole()
  {

    String example = "Convert Java String";
    byte[] bytes = example.getBytes();

    Bundle bundle = getIntent().getExtras();
    String id = bundle.getString("id");

    role = new Role();
    role.setId(id);
    role.setName(roleField.getText().toString());
    role.setRightslevel(3);
    role.setPermissions(bytes);
    role.setSiteguid(RestVariable.SITE_GUID);
    role.setSflag(true);

    Call<HashMap<Integer, String>> call = roleService.updateRole(kodeMerchant, role);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {

      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {

      }
    });
    onBackPressed();
  }
}
