package com.mitrakreasindo.pos.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.PasswordValidator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity
{
  @BindView(R.id.spinner_business_type)
  Spinner spinnerBusinessType;
  @BindView(R.id.spinner_business_category)
  Spinner spinnerBusinessCategory;

  private int RESULT_TAKE_PHOTO = 0;
  private int RESULT_PICK_GALLERY = 1;

  private String name, pass, repass, role;
  private String visibility;
  private View focusView = null;
  private PasswordValidator passwordValidator;
  private String[] arraySpinnerBusinessType;
  private String[] arraySpinnerBusinessCategory;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    ButterKnife.bind(this);

    this.arraySpinnerBusinessType = new String[] { "Business Type", "1", "2", "3", "4", "5" };
    ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<>(this,
      android.R.layout.simple_spinner_dropdown_item, arraySpinnerBusinessType);
    spinnerBusinessType.setAdapter(adapterBusinessType);

    this.arraySpinnerBusinessCategory = new String[] { "Business Category", "1", "2", "3", "4", "5" };
    ArrayAdapter<String> adapterBusinessCategory = new ArrayAdapter<>(this,
      android.R.layout.simple_spinner_dropdown_item, arraySpinnerBusinessCategory);
    spinnerBusinessCategory.setAdapter(adapterBusinessCategory);
  }

  @Override
  public void onResume()
  {
    super.onResume();
    findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
//        rdbVisible.setChecked(true);
  }

  public void Cancel(View view)
  {
    finish();
  }

  public void Confirm(View view)
  {
//    if (attemptRegister())
//    {
//      if (rdbVisible.isChecked())
//        visibility = "true";
//      else
//        visibility = "false";
//
//      findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
//      new HttpRequestTask().execute();
//      findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//    }
//    else
//      focusView.requestFocus();
  }

  private boolean attemptRegister()
  {
//    passwordValidator = new PasswordValidator();
//
//    edtName.setError(null);
//    edtPass.setError(null);
//    edtRepass.setError(null);
//
//    name = edtName.getText().toString();
//    pass = edtPass.getText().toString();
//    repass = edtRepass.getText().toString();
//
//    if (TextUtils.isEmpty(name))
//    {
//      edtName.setError(getString(R.string.error_empty_name));
//      focusView = edtName;
//      return false;
//    }
//    else if (TextUtils.isEmpty(pass))
//    {
//      edtPass.setError(getString(R.string.error_empty_pass));
//      focusView = edtPass;
//      return false;
//    }
//    else if (!passwordValidator.validate(pass))
//    {
//      edtPass.setError("- Password must at least 8 chars and max 10 chars \n" +
//        "- Contains at least 1 digit (0-9)\n" +
//        "- 1 lower case alphabet char (a-z)\n" +
//        "- 1 upper case alphabet char (A-Z)\n" +
//        "- 1 special char (~!@#$%^&*-+=,.?)\n" +
//        "- No whitespace allowed");
//      focusView = edtPass;
//      return false;
//    }
//    else if (TextUtils.isEmpty(repass))
//    {
//      edtRepass.setError(getString(R.string.error_empty_repass));
//      focusView = edtRepass;
//      return false;
//    }
//    else if (!pass.equals(repass))
//    {
//      edtRepass.setError(getString(R.string.error_incorrect_reinput_password));
//      focusView = edtRepass;
//      return false;
//    }
//    else
      return true;
  }

  public void Select(View view)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Options");
    builder.setItems(new String[]{"Take a Photo", "Pick from Gallery"}, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        switch (which)
        {
          case 0:
            Toast.makeText(RegisterActivity.this, "Take a photo", Toast.LENGTH_LONG).show();
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, RESULT_TAKE_PHOTO);
            break;

          case 1:
            Toast.makeText(RegisterActivity.this, "Pick from Gallery", Toast.LENGTH_LONG).show();
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photoPickerIntent, RESULT_PICK_GALLERY);
            break;
        }
      }
    });
    builder.show();
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
            Bitmap bitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(bitmap);
            break;

          case 1:
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//            imageView.setImageBitmap(selectedImage);
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

  public class HttpRequestTask extends AsyncTask<Void, Void, String>
  {
    @Override
    protected String doInBackground(Void... params)
    {
      try
      {
        Map<String, String> vars = new HashMap<>();
        vars.put("name", name);
        vars.put("pass", pass);
        vars.put("card", "test_android");
        vars.put("role", role);
        vars.put("visible", visibility);

//                HttpHeaders headers = new HttpHeaders();HttpHeaders
//                headers.setContentType(MediaType.APPLICATION_JSON);

//                final String url = "http://192.168.1.113:8080/ManualServices/resources/PeopleServices/QueryString/" +
//                        "?name={name}&apppassword={pass}&card={card}&role={role}&visible={visible}";

        final String url = "http://192.168.1.113:8080/MKChromisServices/webresources/chromis.people/public/" +
          "?name={name}&apppassword={pass}&card={card}&role={role}&visible={visible}";
//                RestTemplate restTemplate = new RestTemplate();
//                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//                String register = restTemplate.getForObject(url, String.class, vars);

//                Register register = new Register();
//                register.setId("9");
//                register.setName(name);
//                register.setApppassword(pass);
//                register.setCard("testing");
//                register.setRole(role);
//                register.setVisible(visibility);
//                register.setImage(null);

//                HttpEntity<Register> entity = new HttpEntity<>(register, headers);
        System.out.println(role);
//                System.out.println(register);
//                return register;
      }
      catch (Exception e)
      {
        Log.e("RegisterActivity", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(String register)
    {
      Toast.makeText(RegisterActivity.this, "Done " + register, Toast.LENGTH_LONG);
      finish();
//            TextView registerText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            registerText.setText(register.getId());
//            greetingContentText.setText(register.getContent());
    }
  }
}
