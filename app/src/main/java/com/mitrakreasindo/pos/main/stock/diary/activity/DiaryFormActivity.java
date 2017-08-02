package com.mitrakreasindo.pos.main.stock.diary.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.service.DiaryStockService;
import com.mitrakreasindo.pos.model.Location;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.StockDiary;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryFormActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.diary_product_barcode_field)
  TextView diaryProductBarcodeField;
  @BindView(R.id.btn_diary_select_product)
  Button btnDiarySelectProduct;
  @BindView(R.id.diary_date_field)
  TextView diaryDateField;
  @BindView(R.id.diary_product_buy_price_field)
  TextView diaryProductBuyPriceField;
  @BindView(R.id.diary_product_sell_price_field)
  TextView diaryProductSellPriceField;
  @BindView(R.id.diary_unit_field)
  EditText diaryUnitField;
  @BindView(R.id.diary_product_price_field)
  EditText diaryProductPriceField;
  @BindView(R.id.textView)
  TextView textView;
  @BindView(R.id.button)
  Button button;
  @BindView(R.id.diary_product_name_field)
  TextView diaryProductNameField;
  @BindView(R.id.diary_product_instock_field)
  TextView diaryProductInstockField;
  @BindView(R.id.diary_product_reason_spinner)
  Spinner diaryProductReasonSpinner;
  @BindView(R.id.diary_product_total)
  TextView diaryProductTotal;

  private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
  private Bundle bundle;
  private String barcode, name;
  private double inStock, buyPrice, sellPrice;
  private String kodeMerchant;
  private DiaryStockService diaryStockService;
  private StockDiary stockDiary;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private TableProductHelper tableProductHelper;
  private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_form);
    ButterKnife.bind(this);

    diaryStockService = ClientService.createService().create(DiaryStockService.class);
    sharedPreferenceEditor = new SharedPreferenceEditor();
    tableProductHelper = new TableProductHelper(this);

    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    btnDiarySelectProduct.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivityForResult(new Intent(DiaryFormActivity.this, DiarySelectProductActivity.class), 1);
      }
    });

    diaryDateField.setText(dateFormat.format(new Date()));

    diaryDateField.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond = c.get(Calendar.SECOND);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(DiaryFormActivity.this, new TimePickerDialog.OnTimeSetListener()
        {
          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute)
          {
            String hour, min;

            if (String.valueOf(hourOfDay).length() == 1)
            {
              hour = "0" + String.valueOf(hourOfDay);
            }
            else
            {
              hour = String.valueOf(hourOfDay);
            }

            if (String.valueOf(minute).length() == 1)
            {
              min = "0" + String.valueOf(minute);
            }
            else
            {
              min = String.valueOf(minute);
            }
            diaryDateField.setText(diaryDateField.getText() + " " + hour + ":" + min);
          }
        }, mHour, mMinute, true);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DiaryFormActivity.this, new DatePickerDialog.OnDateSetListener()
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

            diaryDateField.setText(year + "-" + month + "-" + day);
            timePickerDialog.show();
          }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
      }
    });

    button.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if (diaryProductBarcodeField.getText().equals("Barcode") || diaryProductBarcodeField.getText().equals(""))
        {
          Toast.makeText(DiaryFormActivity.this, R.string.stock_diary_error_submit_product, Toast.LENGTH_SHORT).show();
        }
        else if (diaryUnitField.getText().length() == 0 || diaryProductPriceField.getText().length() == 0)
        {
          Toast.makeText(DiaryFormActivity.this, R.string.stock_diary_error_submit_price_unit_not_filled, Toast.LENGTH_SHORT).show();
        }
        else
        {
          postStockDiary();
        }
      }
    });

    diaryProductPriceField.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        if (s.length() > 0 && diaryUnitField.getText().toString().length() > 0)
        {
          formatTotalPrice();
        }
        else
        {
          diaryProductTotal.setText("0");
        }
      }
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      {
      }
      @Override
      public void afterTextChanged(Editable s)
      {
        try
        {
          diaryProductPriceField.removeTextChangedListener(this);
          String originalString = s.toString();

          if (originalString.contains(",")) {
            originalString = originalString.replaceAll(",", "");
          }
          Long longval = Long.parseLong(originalString);

          DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
          diaryProductPriceField.setText(decimalFormat.format(longval));
          diaryProductPriceField.setSelection(diaryProductPriceField.getText().length());
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        diaryProductPriceField.addTextChangedListener(this);
      }
    });

    diaryUnitField.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {
      }
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
      {
        if (charSequence.length() > 0 && diaryProductPriceField.getText().toString().length() > 0)
        {
          formatTotalPrice();
        }
        else
        {
          diaryProductTotal.setText("0");
        }
      }
      @Override
      public void afterTextChanged(Editable editable)
      {
      }
    });

//    //Spinner Items
//    List<String> spinnerArray =  new ArrayList<>();
//    spinnerArray.add("Stock In (+)");
//    spinnerArray.add("Stock Out (-)");
//
//    ArrayAdapter<String> adapter = new ArrayAdapter<>(
//      this, android.R.layout.simple_spinner_item, spinnerArray);
//
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    diaryProductReasonSpinner.setAdapter(adapter);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1)
    {
      if (resultCode == RESULT_OK)
      {
        barcode = data.getStringExtra("barcode");
        name = data.getStringExtra("name");
        inStock = data.getDoubleExtra("instock", 0);
        buyPrice = data.getDoubleExtra("buyprice", 0);
        sellPrice = data.getDoubleExtra("sellprice", 0);

        diaryProductBarcodeField.setText(barcode);
        diaryProductNameField.setText(name);
        diaryProductInstockField.setText(String.valueOf(inStock));
        diaryProductBuyPriceField.setText(String.valueOf(buyPrice));
        diaryProductSellPriceField.setText(String.valueOf(sellPrice));
      }
    }
  }

  @Override
  protected void onResume()
  {
    super.onResume();
  }

  private void formatTotalPrice()
  {
    String originalString = diaryProductPriceField.getText().toString();

    if (originalString.contains(",")) {
      originalString = originalString.replaceAll(",", "");
    }
    Long longval = Long.parseLong(originalString);

    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    diaryProductTotal.setText(decimalFormat.format(
      Double.parseDouble(diaryUnitField.getText().toString()) *
        Double.parseDouble(longval.toString())));
  }

  private void postStockDiary()
  {
    try
    {
      Location location = new Location();
      location.setId("0");

      Product product = new Product();
      product.setId(tableProductHelper.getId(diaryProductBarcodeField.getText().toString()));

      stockDiary = new StockDiary();
      stockDiary.setId(UUID.randomUUID().toString());

      stockDiary.setDatenew(dateFormat.parse(diaryDateField.getText().toString()));
      int reason = 0;
      if (diaryProductReasonSpinner.getSelectedItem().toString().contains("+"))
      {
        reason = 1;
      }
      else if (diaryProductReasonSpinner.getSelectedItem().toString().contains("-"))
      {
        reason = -1;
      }
      stockDiary.setReason(reason);
      stockDiary.setUnits(Double.valueOf(diaryUnitField.getText().toString()));
      stockDiary.setPrice(Double.valueOf(diaryProductBuyPriceField.getText().toString()));
      stockDiary.setAppuser(IDs.getLoginUser());
      stockDiary.setSiteguid(null);
      stockDiary.setSflag(true);
      stockDiary.setAttributesetinstanceId(null);
      stockDiary.setLocation(location);
      stockDiary.setProduct(product);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    Call<HashMap<Integer, String>> call = diaryStockService.postStockDiary(kodeMerchant, stockDiary);
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

          }
          Toast.makeText(DiaryFormActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(DiaryFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }
}
