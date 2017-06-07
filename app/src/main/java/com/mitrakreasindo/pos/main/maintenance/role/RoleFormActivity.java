package com.mitrakreasindo.pos.main.maintenance.role;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.service.RoleService;
import com.mitrakreasindo.pos.model.Role;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoleFormActivity extends AppCompatActivity
{

  @BindView(R.id.role_field)
  EditText roleField;
  @BindView(R.id.submit_role)
  Button submitRole;
  @BindView(R.id.log_id)
  TextView logId;

  private Role role;
  private RoleService roleService;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_role_form);
    ButterKnife.bind(this);

    roleService = ClientService.createService().create(RoleService.class);

    final Bundle bundle = getIntent().getExtras();

    if (bundle != null)
    {
      String name = bundle.getString("name");
      String id = bundle.getString("id");

      logId.setText(id);
      roleField.setText(name);
    }

    submitRole.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
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
    });
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

    Call<List<Role>> call = roleService.postRole(role);
    call.enqueue(new Callback<List<Role>>()
    {
      @Override
      public void onResponse(Call<List<Role>> call, Response<List<Role>> response)
      {
        Log.d(getClass().getSimpleName(), "Success Post Role !!!");
        onBackPressed();
      }

      @Override
      public void onFailure(Call<List<Role>> call, Throwable t)
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

    Call<List<Role>> call = roleService.updateRole(role);
    call.enqueue(new Callback<List<Role>>()
    {
      @Override
      public void onResponse(Call<List<Role>> call, Response<List<Role>> response)
      {

      }

      @Override
      public void onFailure(Call<List<Role>> call, Throwable t)
      {

      }
    });

    onBackPressed();

  }
}
