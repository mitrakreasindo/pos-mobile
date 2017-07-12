package com.mitrakreasindo.pos.main.maintenance.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.mitrakreasindo.pos.common.PasswordValidator;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.adapter.UserListAdapter;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.PeopleService;

import java.io.InputStream;
import java.util.ArrayList;
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

  private int RESULT_TAKE_PHOTO = 0;
  private int RESULT_PICK_GALLERY = 1;

  private Role role;
  private People people;
  private PeopleService peopleService;
  private ArrayAdapter<Role> rolesArrayAdapter;
  private List<Role> data;
  private Bundle bundle;
  private String kodeMerchant, peopleId;
  private String name, pass, repass;
  private boolean visibility;
  private PasswordValidator passwordValidator;
  private View focusView = null;
  private UserListAdapter userListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_form);
    ButterKnife.bind(this);

    peopleService = ClientService.createService().create(PeopleService.class);

    kodeMerchant = SharedPreferenceEditor.LoadPreferences(this, "");

    radiobuttonVisible.setChecked(true);

    TableRoleHelper tableRoleHelper = new TableRoleHelper(this);
    data = tableRoleHelper.getData();
    rolesArrayAdapter = new ArrayAdapter<>(UserFormActivity.this, android.R.layout.simple_spinner_item, data);
    userListAdapter = new UserListAdapter(this, new ArrayList<People>());
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
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Options");
    builder.setItems(new String[]{"Take a Photo", "Pick from Gallery"}, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which){
          case 0:
            Toast.makeText(UserFormActivity.this, "Take a photo", Toast.LENGTH_LONG).show();
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, RESULT_TAKE_PHOTO);
            break;

          case 1:
            Toast.makeText(UserFormActivity.this, "Pick from Gallery", Toast.LENGTH_LONG).show();
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
              android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photoPickerIntent, RESULT_PICK_GALLERY);
            break;
        }
      }
    });
    builder.show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK)
    {
      try
      {
        final Uri imageUri = data.getData();

        switch (requestCode)
        {
          case 0:
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageview.setImageBitmap(bitmap);
            break;

          case 1:
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageview.setImageBitmap(selectedImage);
            break;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
      }
    }
    else
      Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
  }

  private boolean attemptRegister()
  {
    passwordValidator = new PasswordValidator();

    edittextName.setError(null);
    edittextPass.setError(null);
    edittextRepass.setError(null);

    name = edittextName.getText().toString();
    pass = edittextPass.getText().toString();
    repass = edittextRepass.getText().toString();

    if (TextUtils.isEmpty(name))
    {
      edittextName.setError(getString(R.string.error_empty_name));
      focusView = edittextName;
      return false;
    }
    else if (TextUtils.isEmpty(pass))
    {
      edittextPass.setError(getString(R.string.error_empty_pass));
      focusView = edittextPass;
      return false;
    }
    else if (!passwordValidator.validate(pass))
    {
      edittextPass.setError("- Password must at least 8 chars and max 10 chars \n" +
        "- Contains at least 1 digit (0-9)\n" +
        "- 1 lower case alphabet char (a-z)\n" +
        "- 1 upper case alphabet char (A-Z)\n" +
        "- 1 special char (~!@#$%^&*-+=,.?)\n" +
        "- No whitespace allowed");
      focusView = edittextPass;
      return false;
    }
    else if (TextUtils.isEmpty(repass))
    {
      edittextRepass.setError(getString(R.string.error_empty_repass));
      focusView = edittextRepass;
      return false;
    }
    else if (!pass.equals(repass))
    {
      edittextRepass.setError(getString(R.string.error_incorrect_reinput_password));
      focusView = edittextRepass;
      return false;
    }
    else
      return true;
  }

  private boolean attemptUpdate()
  {
    boolean flag = false;

    passwordValidator = new PasswordValidator();

    edittextName.setError(null);
    edittextPass.setError(null);
    edittextRepass.setError(null);

    name = edittextName.getText().toString();
    pass = edittextPass.getText().toString();
    repass = edittextRepass.getText().toString();

    if (TextUtils.isEmpty(name))
    {
      edittextName.setError(getString(R.string.error_empty_name));
      focusView = edittextName;
      flag = false;
    }
    else if (!TextUtils.isEmpty(pass))
    {
      if (!passwordValidator.validate(pass))
      {
        edittextPass.setError("- Password must at least 8 chars and max 10 chars \n" +
          "- Contains at least 1 digit (0-9)\n" +
          "- 1 lower case alphabet char (a-z)\n" +
          "- 1 upper case alphabet char (A-Z)\n" +
          "- 1 special char (~!@#$%^&*-+=,.?)\n" +
          "- No whitespace allowed");
        focusView = edittextPass;
        flag = false;
      }
      else if (TextUtils.isEmpty(repass))
      {
        edittextRepass.setError(getString(R.string.error_empty_repass));
        focusView = edittextRepass;
        flag = false;
      }
      else if (!pass.equals(repass))
      {
        edittextRepass.setError(getString(R.string.error_incorrect_reinput_password));
        focusView = edittextRepass;
        flag = false;
      }
      else
        flag = true;
    }
    else
      flag = true;
    return flag;
  }

  public People PrepareData ()
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
    if (bundle == null)
    {
      people.setId(UUID.randomUUID().toString());
    }
    else
    {
      people.setId(peopleId);
    }
    people.setName(edittextName.getText().toString());
    people.setApppassword(edittextPass.getText().toString());
    people.setCard(null);
    people.setVisible(visibility);
    people.setImage(null);
    people.setSiteguid(null);
    people.setSflag(true);
    people.setEmail(null);
    people.setRole(role);

    return people;
  }

  public void ConfirmRegisterPeople(View view)
  {
    if (bundle == null)
    {
      if (attemptRegister())
      {
        postPeople(PrepareData());
      }
    }
    else
    {
      if (attemptUpdate())
      {
        updatePeople(PrepareData());
      }
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
          Log.d("RESPONSE WEBSERVICE: ", String.valueOf(responseCode) + responseMessage);

          if (responseCode == 0)
          {
            TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(UserFormActivity.this);
            tablePeopleHelper.open();
            tablePeopleHelper.insert(people);
            tablePeopleHelper.close();

            userListAdapter.addUser(people);
            userListAdapter.notifyDataSetChanged();
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
          Log.d("RESPONSE WEBSERVICE: ", String.valueOf(responseCode) + responseMessage);

          if (responseCode == 0)
          {
            TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(UserFormActivity.this);
            tablePeopleHelper.open();
            tablePeopleHelper.update(people);
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