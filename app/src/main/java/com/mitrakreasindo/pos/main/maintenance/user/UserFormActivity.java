package com.mitrakreasindo.pos.main.maintenance.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.service.RoleService;
import com.mitrakreasindo.pos.main.maintenance.user.model.People;
import com.mitrakreasindo.pos.main.maintenance.user.service.PeopleService;
import com.mitrakreasindo.pos.model.Role;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 23/05/17.
 */


public class UserFormActivity extends AppCompatActivity
{


  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.textview_name)
  TextView textviewName;
  @BindView(R.id.edittext_name)
  EditText edittextName;
  @BindView(R.id.textview_pass)
  TextView textviewPass;
  @BindView(R.id.edittext_pass)
  EditText edittextPass;
  @BindView(R.id.textview_repass)
  TextView textviewRepass;
  @BindView(R.id.edittext_repass)
  EditText edittextRepass;
  @BindView(R.id.textview_role)
  TextView textviewRole;
  @BindView(R.id.spinner_role)
  Spinner spinnerRole;
  @BindView(R.id.textview_visibility)
  TextView textviewVisibility;
  @BindView(R.id.radiobutton_visible)
  RadioButton radiobuttonVisible;
  @BindView(R.id.radiobutton_invisible)
  RadioButton radiobuttonInvisible;
  @BindView(R.id.radiogroup_visibility)
  RadioGroup radiogroupVisibility;
  @BindView(R.id.textview_image)
  TextView textviewImage;
  @BindView(R.id.button_imageselect)
  Button buttonImageselect;
  @BindView(R.id.imageview)
  ImageView imageview;
  @BindView(R.id.button_confirm)
  Button buttonConfirm;
  @BindView(R.id.button_cancel)
  Button buttonCancel;
  private List<Role> roles = new ArrayList<>();

  private RoleService roleService;
  private Role role;
  private People people;
  private PeopleService peopleService;

  List<Role> data;


  int RESULT_LOAD_IMG;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_form);
    ButterKnife.bind(this);

    roleService = ClientService.createService().create(RoleService.class);
    peopleService = ClientService.createService().create(PeopleService.class);


    final Call<List<Role>> role = roleService.getRoleAll();
    role.enqueue(new Callback<List<Role>>()
    {
      @Override
      public void onResponse(Call<List<Role>> call, Response<List<Role>> response)
      {
        data = response.body();
        final ArrayAdapter<Role> rolesArrayAdapter = new ArrayAdapter<Role>(UserFormActivity.this, android.R.layout.simple_spinner_item, data);
        spinnerRole.setAdapter(rolesArrayAdapter);

        Role r = data.get(spinnerRole.getSelectedItemPosition());

        String roleId = r.getId();

      }

      @Override
      public void onFailure(Call<List<Role>> call, Throwable t)
      {

      }
    });


    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(R.string.action_register);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    String spinData = String.valueOf(spinnerRole.getSelectedItemId());


    Log.d("SPINNER VALUE : ", spinData);


//    getRole();


  }


  public void Select(View view)
  {
    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        photoPickerIntent.setType("image/*");
    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
  }

  @Override
  protected void onActivityResult(int reqCode, int resultCode, Intent data)
  {
    super.onActivityResult(reqCode, resultCode, data);

    if (resultCode == RESULT_OK)
    {
      try
      {
        final Uri imageUri = data.getData();
        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        imageview.setImageBitmap(selectedImage);
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
      }
    }
    else
      Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
  }

  public void ConfirmRegisterPeople(View view)
  {
    postPeople();
    finish();
  }

  public void Cancel(View view)
  {

        finish();
//    postPeople();
  }

//  private void getRole()
//  {
//
//    final Call<List<Role>> role = roleService.getRoleAll();
//    role.enqueue(new Callback<List<Role>>()
//    {
//      @Override
//      public void onResponse(Call<List<Role>> call, Response<List<Role>> response)
//      {
//        final List<Role> data = response.body();
//        final ArrayAdapter<Role> rolesArrayAdapter = new ArrayAdapter<Role>(UserFormActivity.this, android.R.layout.simple_spinner_item, data);
//        spinnerRole.setAdapter(rolesArrayAdapter);
//
//      }
//
//      @Override
//      public void onFailure(Call<List<Role>> call, Throwable t)
//      {
//
//      }
//    });
//  }

  private void postPeople()
  {

    final ProgressDialog progressDialog = new ProgressDialog(UserFormActivity.this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    Log.d(getClass().getSimpleName(), "Post Role !!!");



    role = new Role();
    role.setId(data.get(spinnerRole.getSelectedItemPosition()).getId());

    people = new People();
    people.setName(edittextName.getText().toString());
    people.setApppassword(edittextPass.getText().toString());
    people.setCard(null);
    people.setVisible(true);
    people.setImage(null);
    people.setSiteguid(null);
    people.setSflag(true);
    people.setEmail(null);
    people.setRole(role);

    Call<List<People>> call = peopleService.postPeople(people);
    call.enqueue(new Callback<List<People>>()
    {
      @Override
      public void onResponse(Call<List<People>> call, Response<List<People>> response)
      {
        Log.d(getClass().getSimpleName(), "Success Post Role !!!");
        onBackPressed();
      }

      @Override
      public void onFailure(Call<List<People>> call, Throwable t)
      {
      }
    });

    Intent intent = new Intent(UserFormActivity.this, UserActivity.class);
    startActivity(intent);

    finish();

  }

  private void updatePeople()
  {

    final ProgressDialog progressDialog = new ProgressDialog(UserFormActivity.this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    Log.d(getClass().getSimpleName(), "Post Role !!!");

    String roleValue = spinnerRole.getSelectedItem().toString();


//        String example = "Convert Java String";
//        byte[] bytes = example.getBytes();

    role = new Role();
    role.setId("1");




    people = new People();
    people.setName(edittextName.getText().toString());
    people.setApppassword(edittextPass.getText().toString());
    people.setCard(null);
//        people.setRole();
    people.setVisible(true);
    people.setImage(null);
    people.setSiteguid(null);
    people.setSflag(true);
    people.setEmail(null);
    people.setRole(role);
//        people.setRole(spinnerRole.getSelectedItem().toString());

    Call<List<People>> call = peopleService.postPeople(people);
    call.enqueue(new Callback<List<People>>()
    {
      @Override
      public void onResponse(Call<List<People>> call, Response<List<People>> response)
      {
        Log.d(getClass().getSimpleName(), "Success Post Role !!!");
        onBackPressed();
      }

      @Override
      public void onFailure(Call<List<People>> call, Throwable t)
      {
      }
    });

    Intent intent = new Intent(UserFormActivity.this, UserActivity.class);
    startActivity(intent);

    finish();

  }
}
