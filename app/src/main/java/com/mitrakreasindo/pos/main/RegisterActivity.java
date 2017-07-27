package com.mitrakreasindo.pos.main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.PasswordValidator;
import com.mitrakreasindo.pos.model.Merchant;
import com.mitrakreasindo.pos.model.MerchantCategories;
import com.mitrakreasindo.pos.model.MerchantRegistration;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.service.MerchantService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class RegisterActivity extends AppCompatActivity
{
  @BindView(R.id.spinner_business_category)
  Spinner spinnerBusinessCategory;
  @BindView(R.id.spinner_business_subcategory)
  Spinner spinnerBusinessSubcategory;
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
  @BindView(R.id.edittext_business_address)
  EditText edittextBusinessAddress;
  @BindView(R.id.edittext_business_city)
  EditText edittextBusinessCity;
  @BindView(R.id.edittext_business_state)
  EditText edittextBusinessState;
  @BindView(R.id.edittext_business_zip)
  EditText edittextBusinessZip;
  @BindView(R.id.spinner_owner_sex)
  Spinner spinnerOwnerSex;
  @BindView(R.id.edittext_business_phone)
  EditText edittextBusinessPhone;
  @BindView(R.id.edittext_owner_phone)
  EditText edittextOwnerPhone;
  @BindView(R.id.register_progress)
  ProgressBar registerProgress;
  @BindView(R.id.layout_business_shortname_phone)
  LinearLayout layoutBusinessShortnamePhone;
  @BindView(R.id.layout_business_type_category)
  LinearLayout layoutBusinessTypeCategory;
  @BindView(R.id.layout_business_city_state_zip)
  LinearLayout layoutBusinessCityStateZip;
  @BindView(R.id.textview_owner_info)
  TextView textviewOwnerInfo;
  @BindView(R.id.layout_owner_sex_birth_date)
  LinearLayout layoutOwnerSexBirthDate;
  @BindView(R.id.textview_business_register_term)
  TextView textviewBusinessRegisterTerm;
  @BindView(R.id.button_create_acc)
  Button buttonCreateAcc;

  private int mYear, mMonth, mDay;
  private String businessname, shortname, ownerfullname, owneremail;
  private String visibility;
  private View focusView = null;
  private PasswordValidator passwordValidator;
  private String[] arrayBusinessCategory;
  private String[] arrayBusinessSubCategory;
  private List<Integer> merchantCategoryId;
  private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    ButterKnife.bind(this);

    EventBus.getDefault().register(this);
    getMerchantCategories(EventCode.EVENT_BUSINESS_CATEGORY_GET);

    progressDialog = new ProgressDialog(this);

    spinnerBusinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
      {
        getMerchantSubCategories(parentView.getItemAtPosition(position).toString(),
          EventCode.EVENT_BUSINESS_SUBCATEGORY_GET);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView)
      {
      }
    });
  }

  @Override
  public void onResume()
  {
    super.onResume();
  }

  @Override
  protected void onStop()
  {
    super.onStop();
    EventBus.getDefault().unregister(this);
    registerProgress.setVisibility(View.GONE);
    buttonCreateAcc.setEnabled(true);
  }

  @Override
  public void onBackPressed()
  {
    new AlertDialog.Builder(this)
      .setIcon(android.R.drawable.ic_dialog_alert)
      .setTitle("Cancel Registration")
      .setMessage("Are you sure back to login screen?")
      .setPositiveButton
        (
          "Yes", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              finish();
            }
          }
        )
      .setNegativeButton("No", null)
      .show();
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
      progressDialog.setMessage("Please wait...");
      progressDialog.show();
      buttonCreateAcc.setEnabled(false);
      postMerchantRegistration(prepareRegistrationData());
    }
    else
      focusView.requestFocus();
  }

  private MerchantRegistration prepareRegistrationData()
  {
    Merchant merchant = new Merchant();
    People people = new People();
    MerchantRegistration merchantRegistration = new MerchantRegistration();
    try
    {
      MerchantCategories merchantCategories = new MerchantCategories();
      merchantCategories.setId(merchantCategoryId.get(spinnerBusinessSubcategory.getSelectedItemPosition()));

      merchant.setName(edittextBusinessName.getText().toString());
      merchant.setCode(edittextBusinessShortname.getText().toString());
      merchant.setCategory(merchantCategories);
      merchant.setPhone(edittextBusinessPhone.getText().toString());
      merchant.setAddress(edittextBusinessAddress.getText().toString() + ", " +
        edittextBusinessCity.getText().toString() + ", " +
        edittextBusinessState.getText().toString() + ", " +
        edittextBusinessZip.getText().toString());
      merchant.setSflag(true);

      people.setId(UUID.randomUUID().toString());
      people.setFullname(edittextOwnerFullName.getText().toString());
      people.setGender(spinnerOwnerSex.getSelectedItem().toString());
      people.setBirthdate(df.parse(edittextOwnerBirthDate.getText().toString()));
      people.setEmail(edittextOwnerEmail.getText().toString());
      people.setPhoneNumber(edittextOwnerPhone.getText().toString());
      people.setVisible(true);

      merchantRegistration.setMerchant(merchant);
      merchantRegistration.setPeople(people);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return merchantRegistration;
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
    else if (!isEmailValid(owneremail))
    {
      edittextOwnerEmail.setError(getString(R.string.error_valid_owneremail));
      focusView = edittextOwnerEmail;
      return false;
    }
    else
      return true;
  }

  private void postMerchantRegistration(final MerchantRegistration merchantRegistration)
  {
    MerchantService merchantService = ClientService.createService().create(MerchantService.class);
    Call<HashMap<Integer, String>> call = merchantService.postMerchantRegistration(merchantRegistration);
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
            progressDialog.dismiss();
            buttonCreateAcc.setEnabled(true);
            finish();
          }
        }
        Toast.makeText(RegisterActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        progressDialog.dismiss();
        buttonCreateAcc.setEnabled(true);
        responseCode = -1;
        responseMessage = "Cannot register. :( There is something wrong.";
        Toast.makeText(RegisterActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  private void getMerchantCategories(final int id)
  {
    MerchantService merchantService = ClientService.createService().create(MerchantService.class);
    Call<List<MerchantCategories>> call = merchantService.getMerchantCategories();
    call.enqueue(new Callback<List<MerchantCategories>>()
    {
      @Override
      public void onResponse(Call<List<MerchantCategories>> call, Response<List<MerchantCategories>> response)
      {
        List<MerchantCategories> data = response.body();
        List<String> category = new ArrayList<>();

        for (int i = 0; i < data.size(); i++)
        {
          category.add(data.get(i).getName());
        }
        arrayBusinessCategory = new String[category.size()];
        arrayBusinessCategory = category.toArray(arrayBusinessCategory);

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<List<MerchantCategories>> call, Throwable t)
      {
        arrayBusinessCategory = null;
        Toast.makeText(RegisterActivity.this, "Get category failed", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void getMerchantSubCategories(String categoryName, final int id)
  {
    final MerchantService merchantService = ClientService.createService().create(MerchantService.class);
    Call<List<MerchantCategories>> call = merchantService.getMerchantSubCategories(categoryName);
    call.enqueue(new Callback<List<MerchantCategories>>()
    {
      @Override
      public void onResponse(Call<List<MerchantCategories>> call, Response<List<MerchantCategories>> response)
      {
        List<MerchantCategories> data = response.body();
        List<String> subcategory = new ArrayList<>();
        merchantCategoryId = new ArrayList<>();

        for (int i = 0; i < data.size(); i++)
        {
          subcategory.add(data.get(i).getSubcategory());
          merchantCategoryId.add(data.get(i).getId());
        }
        arrayBusinessSubCategory = new String[subcategory.size()];
        arrayBusinessSubCategory = subcategory.toArray(arrayBusinessSubCategory);

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<List<MerchantCategories>> call, Throwable t)
      {
        arrayBusinessSubCategory = null;
        Toast.makeText(RegisterActivity.this, "Get sub category failed", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(Event event)
  {
    switch (event.getId())
    {
      case EventCode.EVENT_BUSINESS_CATEGORY_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          ArrayAdapter<String> adapterBusinessCategory = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, arrayBusinessCategory);
          adapterBusinessCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinnerBusinessCategory.setAdapter(adapterBusinessCategory);
        }
        break;
      case EventCode.EVENT_BUSINESS_SUBCATEGORY_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          ArrayAdapter<String> adapterBusinessSubCategory = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, arrayBusinessSubCategory);
          adapterBusinessSubCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinnerBusinessSubcategory.setAdapter(adapterBusinessSubCategory);
        }
        break;
    }
  }
}
