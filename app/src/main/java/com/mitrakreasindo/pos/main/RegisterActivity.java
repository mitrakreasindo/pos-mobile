package com.mitrakreasindo.pos.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.PasswordValidator;
import com.mitrakreasindo.pos.model.Merchant;
import com.mitrakreasindo.pos.service.MerchantService;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
{
  @BindView(R.id.spinner_business_type)
  Spinner spinnerBusinessType;
  @BindView(R.id.spinner_business_category)
  Spinner spinnerBusinessCategory;
  @BindView(R.id.edittext_owner_birth_date)
  TextView edittextOwnerBirthDate;
  @BindView(R.id.edittext_business_name)
  EditText edittextBusinessName;
  @BindView(R.id.edittext_business_shortname)
  EditText edittextBusinessShortname;
  @BindView(R.id.edittext_owner_full_name)
  EditText edittextOwnerFullName;
  @BindView(R.id.edittext_owner_email)
  EditText edittextOwnerEmail;

  private int mYear, mMonth, mDay;
  private String businessname, shortname, ownerfullname, owneremail;
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

    this.arraySpinnerBusinessType = new String[]{"Category", "1", "2", "3", "4", "5"};
    ArrayAdapter<String> adapterBusinessType = new ArrayAdapter<>(this,
      android.R.layout.simple_spinner_item, arraySpinnerBusinessType);
    adapterBusinessType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerBusinessType.setAdapter(adapterBusinessType);

    this.arraySpinnerBusinessCategory = new String[]{"Sub Category", "1", "2", "3", "4", "5"};
    ArrayAdapter<String> adapterBusinessCategory = new ArrayAdapter<>(this,
      android.R.layout.simple_spinner_item, arraySpinnerBusinessCategory);
    adapterBusinessCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerBusinessCategory.setAdapter(adapterBusinessCategory);
  }

  @Override
  public void onResume()
  {
    super.onResume();
    findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
  }

  public void Cancel(View view)
  {
    finish();
  }

  public void RegisterOwnerPickDate(View view)
  {
    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonth = c.get(Calendar.MONTH);
    mDay = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener()
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
        edittextOwnerBirthDate.setText(year + "-" + month + "-" + day);
      }
    }, mYear, mMonth, mDay);

    datePickerDialog.show();
  }

  public void CreateAccount(View view)
  {
    if (attemptRegister())
    {
//      if (rdbVisible.isChecked())
//        visibility = "true";
//      else
//        visibility = "false";
//
//      findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
//      new HttpRequestTask().execute();
//      findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
    else
      focusView.requestFocus();
  }

  private boolean isEmailValid(String email)
  {
    return email.contains("@");
  }

  private boolean attemptRegister()
  {
    passwordValidator = new PasswordValidator();

    edittextBusinessName.setError(null);
    edittextBusinessShortname.setError(null);
    edittextOwnerFullName.setError(null);
    edittextOwnerEmail.setError(null);

    businessname = edittextBusinessName.getText().toString();
    shortname = edittextBusinessShortname.getText().toString();
    ownerfullname = edittextOwnerFullName.getText().toString();
    owneremail = edittextOwnerEmail.getText().toString();

    if (TextUtils.isEmpty(businessname))
    {
      edittextBusinessName.setError(getString(R.string.error_empty_businessname));
      focusView = edittextBusinessName;
      return false;
    }
    else if (TextUtils.isEmpty(shortname))
    {
      edittextBusinessShortname.setError(getString(R.string.error_empty_businessshortname));
      focusView = edittextBusinessShortname;
      return false;
    }
    else if (TextUtils.isEmpty(ownerfullname))
    {
      edittextOwnerFullName.setError(getString(R.string.error_empty_ownerfullname));
      focusView = edittextOwnerFullName;
      return false;
    }
    else if (TextUtils.isEmpty(owneremail))
    {
      edittextOwnerEmail.setError(getString(R.string.owner_email));
      focusView = edittextOwnerEmail;
      return false;
    }
    else if (isEmailValid(owneremail))
    {
      edittextOwnerEmail.setError(getString(R.string.error_valid_owneremail));
      focusView = edittextOwnerEmail;
      return false;
    }
    else
      return true;
  }

  private void postMerchant(final Merchant merchant)
  {
    MerchantService merchantService = ClientService.createService().create(MerchantService.class);
    Call<HashMap<Integer, String>> call = merchantService.postMerchant(merchant);
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
            finish();
          }
        }
        Toast.makeText(RegisterActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = "Cannot register. :( There is something wrong.";
        Toast.makeText(RegisterActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }
}
