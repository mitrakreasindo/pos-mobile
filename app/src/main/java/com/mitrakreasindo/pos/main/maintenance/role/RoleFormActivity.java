package com.mitrakreasindo.pos.main.maintenance.role;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.RestVariable;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.service.RoleService;
import com.mitrakreasindo.pos.model.Roles;

import java.lang.reflect.Array;
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

  private Roles role;
  private RoleService roleService;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_role_form);
    ButterKnife.bind(this);

    roleService = ClientService.createService().create(RoleService.class);

    submitRole.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        postRole();
      }
    });
  }

  private void postRole()
  {

    final ProgressDialog progressDialog = new ProgressDialog(RoleFormActivity.this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    Log.d(getClass().getSimpleName(), "Post Role !!!");
    Log.d(getClass().getSimpleName(), roleField.getText().toString());

    String example = "Convert Java String";
    byte[] bytes = example.getBytes();

    role = new Roles();
    role.setName(roleField.getText().toString());
    role.setRightslevel(3);
    role.setPermissions(bytes);
    role.setSiteguid(RestVariable.SITE_GUID);
    role.setSflag(true);

    Call<List<Roles>> call = roleService.postRole(role);
    call.enqueue(new Callback<List<Roles>>() {
      @Override
      public void onResponse(Call<List<Roles>> call, Response<List<Roles>> response) {
        Log.d(getClass().getSimpleName(), "Success Post Role !!!");
        onBackPressed();
      }

      @Override
      public void onFailure(Call<List<Roles>> call, Throwable t) {
//        progressDialog.dismiss();
//        Toast.makeText(RoleFormActivity.this, "Failure Post Role", Toast.LENGTH_LONG);
      }
    });

    Intent intent = new Intent(RoleFormActivity.this, RoleActivity.class);
    startActivity(intent);

    finish();

  }

}
