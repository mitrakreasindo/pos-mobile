package com.mitrakreasindo.pos.main.maintenance.user;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.ImageHelper;
import com.mitrakreasindo.pos.common.PasswordValidator;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.controller.UserListAdapter;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.PeopleService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
  @BindView(R.id.textview_username)
  TextView textviewUsername;
  @BindView(R.id.edittext_username)
  EditText edittextUsername;
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
  @BindView(R.id.textview_image)
  TextView textviewImage;
  @BindView(R.id.button_imageselect)
  Button buttonImageselect;
  @BindView(R.id.imageview_image_select)
  ImageView imageviewImageSelect;
  @BindView(R.id.button_confirm)
  Button buttonConfirm;
  @BindView(R.id.button_cancel)
  Button buttonCancel;
  @BindView(R.id.edittext_birth_date)
  TextView edittextBirthDate;
  @BindView(R.id.edittext_fullname)
  EditText edittextFullname;
  @BindView(R.id.spinner_sex)
  Spinner spinnerSex;
  @BindView(R.id.edittext_phone)
  EditText edittextPhone;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.textview_fullname)
  TextView textviewFullname;
  @BindView(R.id.textview_birth_date)
  TextView textviewBirthDate;
  @BindView(R.id.textview_sex)
  TextView textviewSex;
  @BindView(R.id.layout_people_id)
  LinearLayout layoutPeopleId;
  @BindView(R.id.textview_phone)
  TextView textviewPhone;
  @BindView(R.id.textview_old_pass)
  TextView textviewOldPass;
  @BindView(R.id.edittext_old_password)
  EditText edittextOldPass;

  private int RESULT_TAKE_PHOTO = 0;
  private int RESULT_PICK_GALLERY = 1;

  private int mYear, mMonth, mDay;
  private Role role;
  private People people;
  private PeopleService peopleService;
  private ArrayAdapter<Role> rolesArrayAdapter;
  private List<Role> data;
  private String kodeMerchant, peopleId, oldPassword;
  private String name, pass, repass;
  private Bundle bundle;
  private boolean isNeedPassResetter;
  private View focusView;
  private UserListAdapter userListAdapter;
  private byte[] imageInByte;
  private Bitmap bitmap;
  private ByteArrayOutputStream baos;
  private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_form);
    ButterKnife.bind(this);

    peopleService = ClientService.createService().create(PeopleService.class);
    kodeMerchant = SharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    setSupportActionBar(toolbar);

    SetupCreateEditMode();

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });
  }

  private void SetupCreateEditMode()
  {
    bundle = getIntent().getExtras();

    // Create mode
    if (bundle == null)
    {
      textviewOldPass.setVisibility(View.GONE);
      edittextOldPass.setVisibility(View.GONE);

      Calendar c = Calendar.getInstance();
      edittextBirthDate.setText(df.format(c.getTime()));

      TableRoleHelper tableRoleHelper = new TableRoleHelper(this);
      data = tableRoleHelper.getData();
      rolesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
      userListAdapter = new UserListAdapter(this, new ArrayList<People>());
      rolesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerRole.setAdapter(rolesArrayAdapter);

      getSupportActionBar().setTitle(R.string.action_bar_userform_create);
    }
    // Edit mode
    else
    {
      textviewBirthDate.setVisibility(View.GONE);
      edittextBirthDate.setVisibility(View.GONE);
      textviewSex.setVisibility(View.GONE);
      spinnerSex.setVisibility(View.GONE);
      textviewRole.setVisibility(View.GONE);
      spinnerRole.setVisibility(View.GONE);

      if (bundle.getBoolean("isNeedPasswordResetter"))
      {
        textviewUsername.setVisibility(View.GONE);
        edittextUsername.setVisibility(View.GONE);
        textviewPhone.setVisibility(View.GONE);
        edittextPhone.setVisibility(View.GONE);
        textviewFullname.setVisibility(View.GONE);
        edittextFullname.setVisibility(View.GONE);
        textviewImage.setVisibility(View.GONE);
        buttonImageselect.setVisibility(View.GONE);
        imageviewImageSelect.setVisibility(View.GONE);

        getSupportActionBar().setTitle(R.string.action_bar_userform_edit_pass);
      }
      else
      {
        getSupportActionBar().setTitle(R.string.action_bar_userform_edit);
      }

      final People people = (People) bundle.getSerializable("people");
      isNeedPassResetter = people.isVisible();
      peopleId = people.getId();
      oldPassword = people.getOldPassword();
      byte[] image = people.getImage();

      edittextFullname.setText(people.getFullname());
      edittextPhone.setText(people.getPhoneNumber());
      edittextUsername.setText(people.getName());
      edittextPass.setText(people.getApppassword());

      if (image != null)
      {
        if (image.length != 0)
        {
          Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
          DisplayMetrics dm = new DisplayMetrics();
          getWindowManager().getDefaultDisplay().getMetrics(dm);
          imageviewImageSelect.setMinimumHeight(dm.heightPixels);
          imageviewImageSelect.setMinimumWidth(dm.widthPixels);
          imageviewImageSelect.setImageBitmap(bm);
          imageviewImageSelect.setVisibility(View.VISIBLE);
        }
        else
        {
          imageviewImageSelect.setVisibility(View.INVISIBLE);
        }
      }
      else
      {
        imageviewImageSelect.setVisibility(View.INVISIBLE);
      }

      Log.d("PEOPLE_ID", peopleId);
      Log.d("USERNAME", edittextUsername.getText().toString());
      Log.d("APPPASS", edittextPass.getText().toString());
    }
  }

  public void Select(View view)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.dialog_pick_image_title);
    builder.setItems(new String[]
      {
        getString(R.string.dialog_pick_image_camera),
        getString(R.string.dialog_pick_image_gallery)
      }, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        switch (which)
        {
          case 0:
            Toast.makeText(UserFormActivity.this, getString(R.string.dialog_pick_image_camera),
              Toast.LENGTH_LONG).show();
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, RESULT_TAKE_PHOTO);
            break;

          case 1:
            Toast.makeText(UserFormActivity.this, getString(R.string.dialog_pick_image_gallery),
              Toast.LENGTH_LONG).show();
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photoPickerIntent, RESULT_PICK_GALLERY);
            break;
        }
      }
    });
    builder.show();
  }

  public void PickDate(View view)
  {
    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonth = c.get(Calendar.MONTH);
    mDay = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(UserFormActivity.this, new DatePickerDialog.OnDateSetListener()
    {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
      {
        String month, day;

        if (String.valueOf(monthOfYear + 1).length() == 1)
        {
          month = "0" + String.valueOf(monthOfYear + 1);
        }
        else
        {
          month = String.valueOf(monthOfYear + 1);
        }

        if (String.valueOf(dayOfMonth).length() == 1)
        {
          day = "0" + String.valueOf(dayOfMonth);
        }
        else
        {
          day = String.valueOf(dayOfMonth);
        }
        edittextBirthDate.setText(year + "-" + month + "-" + day);
      }
    }, mYear, mMonth, mDay);

    datePickerDialog.show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
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
            Bitmap bitmap = (Bitmap) extras.get("PrepareData");
            imageviewImageSelect.setImageBitmap(bitmap);
            imageviewImageSelect.setVisibility(View.VISIBLE);
            break;

          case 1:
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageviewImageSelect.setImageBitmap(selectedImage);
            imageviewImageSelect.setVisibility(View.VISIBLE);
            break;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_LONG).show();
      }
    }
    else
    {
      imageviewImageSelect.setVisibility(View.INVISIBLE);
    }
  }

  private boolean attemptCreate()
  {
    edittextUsername.setError(null);
    edittextPass.setError(null);
    edittextRepass.setError(null);

    name = edittextUsername.getText().toString();
    pass = edittextPass.getText().toString();
    repass = edittextRepass.getText().toString();

    if (TextUtils.isEmpty(name))
    {
      edittextUsername.setError(getString(R.string.error_empty_name));
      focusView = edittextUsername;
      return false;
    }
    else if (TextUtils.isEmpty(pass))
    {
      edittextPass.setError(getString(R.string.error_empty_pass));
      focusView = edittextPass;
      return false;
    }
    else if (!PasswordValidator.validate(pass))
    {
      edittextPass.setError(getString(R.string.error_password_invalid));
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
    boolean flag;

    edittextUsername.setError(null);
    edittextPass.setError(null);
    edittextRepass.setError(null);

    name = edittextUsername.getText().toString();
    pass = edittextPass.getText().toString();
    repass = edittextRepass.getText().toString();

    if (TextUtils.isEmpty(name))
    {
      edittextUsername.setError(getString(R.string.error_empty_name));
      focusView = edittextUsername;
      flag = false;
    }
    else if (!TextUtils.isEmpty(pass))
    {
      Log.d("PASSWORD", pass);
      if (!PasswordValidator.validate(pass))
      {
        edittextPass.setError(getString(R.string.error_password_invalid));
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
    else if (TextUtils.isEmpty(pass))
    {
      if (isNeedPassResetter)
      {
        edittextPass.setError(getString(R.string.error_field_required));
        focusView = edittextPass;
        flag = false;
      }
      else
        flag = true;
    }
    else
      flag = true;
    return flag;
  }

  private People PrepareData()
  {
    try
    {
      people = new People();
      // Create mode
      if (bundle == null)
      {
        people.setId(UUID.randomUUID().toString());

        role = new Role();
        role.setId(data.get(spinnerRole.getSelectedItemPosition()).getId());
        people.setRole(role);

        people.setBirthdate(df.parse(edittextBirthDate.getText().toString()));
        people.setGender(spinnerSex.getSelectedItem().toString());

        // For self user created password, there's no need show resetter in the future
        people.setVisible(false);
      }
      // Edit mode
      else
      {
        people.setId(peopleId);
        if (isNeedPassResetter)
        {
          if (edittextPass.getText().equals("") && edittextRepass.getText().equals(""))
          {
            // Password not changed and still need resetter in the future login
            people.setVisible(true);
          }
          else
          {
            // Password will changed and no need resetter in the future login
            people.setOldPassword(edittextOldPass.getText().toString());
            people.setVisible(false);
          }
        }
        else
        {
          // Once password already changed, there's no need show resetter again in the future
          people.setVisible(false);
        }
      }

      people.setName(edittextUsername.getText().toString());
      people.setEmail(null);
      people.setFullname(edittextFullname.getText().toString());
      people.setPhoneNumber(edittextPhone.getText().toString());
      people.setApppassword(edittextPass.getText().toString());
      people.setCard(null);

      if (imageviewImageSelect.getVisibility() == View.VISIBLE)
      {
        bitmap = ((BitmapDrawable) imageviewImageSelect.getDrawable()).getBitmap();
        bitmap = ImageHelper.getResizedBitmap(bitmap, 150);
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        imageInByte = baos.toByteArray();
      }
      else
      {
        imageInByte = null;
      }
      people.setImage(imageInByte);
      people.setSiteguid(null);
      people.setSflag(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return people;
  }

  public void ConfirmRegisterPeople(View view)
  {
    if (bundle == null)
    {
      if (attemptCreate())
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
        responseMessage = getString(R.string.error_webservice);
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
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(UserFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }
}