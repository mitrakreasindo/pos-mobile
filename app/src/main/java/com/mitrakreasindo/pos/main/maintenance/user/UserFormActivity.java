package com.mitrakreasindo.pos.main.maintenance.user;

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
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.PeopleService;
import com.mitrakreasindo.pos.service.RoleService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
  @BindView(R.id.edittext_password)
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

  private RoleService roleService;
  private Role role;
  private People people;
  private PeopleService peopleService;
  private ArrayAdapter<Role> rolesArrayAdapter;
  private List<Role> data;
  private int RESULT_LOAD_IMG;
  private Bundle bundle;
  private String kodeMerchant, peopleId;
  private boolean visibility;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_form);
    ButterKnife.bind(this);

    roleService = ClientService.createService().create(RoleService.class);
    peopleService = ClientService.createService().create(PeopleService.class);

    kodeMerchant = SharedPreferenceEditor.LoadPreferences(this, "");

    radiobuttonVisible.setChecked(true);

    TableRoleHelper tableRoleHelper = new TableRoleHelper(this);
    data = tableRoleHelper.getData();
    rolesArrayAdapter = new ArrayAdapter<>(UserFormActivity.this, android.R.layout.simple_spinner_item, data);
    spinnerRole.setAdapter(rolesArrayAdapter);

    bundle = getIntent().getExtras();
    if (bundle != null)
    {
      peopleId = bundle.getString("id");
      String name = bundle.getString("name");
      String password = bundle.getString("password");
      String roleId = bundle.getString("role");

      Log.d("ROLE", roleId);
      Log.d("DATA NAME : ", name);

      edittextName.setText(name);
      edittextPass.setText(password);

      int spinnerPosition = 0;

      if (!roleId.equals(null))
      {
        int i = 0;
        while (i < data.size())
        {
          if (data.get(i).getId().equals(roleId))
          {
            spinnerPosition = i;
            break;
          }
          i++;
        }

        Log.d("ROLE_ID", String.valueOf(spinnerPosition));
        spinnerRole.setSelection(spinnerPosition);
      }

    }

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
    if (radiobuttonVisible.isChecked())
    {
      visibility = true;
    }
    else if (radiobuttonInvisible.isChecked())
    {
      visibility = false;
    }

    role = new Role();
    role.setId(data.get(spinnerRole.getSelectedItemPosition()).getId());

    people = new People();
    people.setId(UUID.randomUUID().toString());
    people.setName(edittextName.getText().toString());
    people.setApppassword(edittextPass.getText().toString());
    people.setCard(null);
    people.setVisible(visibility);
    people.setImage(null);
    people.setSiteguid(null);
    people.setSflag(true);
    people.setEmail(null);
    people.setRole(role);

    if (bundle != null)
    {
      updatePeople(people);
    }
    else
    {
      postPeople(people);
    }
  }

  public void Cancel(View view)
  {
    finish();
  }

  private void postPeople(final People people)
  {
    Call<HashMap<Integer, String>> call = peopleService.postPeople(kodeMerchant, people);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          if (responseCode == 0)
          {
            TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(UserFormActivity.this);
            tablePeopleHelper.open();
            tablePeopleHelper.insert(people);
            tablePeopleHelper.close();
          }
          Toast.makeText(UserFormActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = "Cannot create new user. :( There is something wrong.";
        Toast.makeText(UserFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  private void updatePeople(final People people)
  {
    Call<HashMap<Integer, String>> call = peopleService.updatePeople(kodeMerchant, peopleId, people);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          if (responseCode == 0)
          {
            TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(UserFormActivity.this);
            tablePeopleHelper.open();
            tablePeopleHelper.insert(people);
            tablePeopleHelper.close();
          }
          Toast.makeText(UserFormActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = "Cannot update user. :( There is something wrong.";
        Toast.makeText(UserFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }
}