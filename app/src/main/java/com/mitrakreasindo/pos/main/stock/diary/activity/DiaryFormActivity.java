package com.mitrakreasindo.pos.main.stock.diary.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Location;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.StockDiary;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

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
  private double inStock, buyPrice, sellPrice, unit, price;
  private String companyCode;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_form);
    ButterKnife.bind(this);

    companyCode = SharedPreferenceEditor.LoadPreferences(this, "");

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
            diaryDateField.setText(diaryDateField.getText() + " " + hourOfDay + ":" + minute);
          }
        }, mHour, mMinute, true);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DiaryFormActivity.this, new DatePickerDialog.OnDateSetListener()
        {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
          {
            diaryDateField.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
        Log.d("COMPANY CODE : ", companyCode);
        postStockDiary();
      }
    });

//      unit = Double.valueOf(diaryUnitField.getText().toString());
//      price = Double.valueOf(diaryProductPriceField.getText().toString());

//    diaryUnitField.addTextChangedListener(new TextWatcher()
//    {
//      @Override
//      public void onTextChanged(CharSequence s, int start, int before, int count)
//      {
//
//      }
//
//      @Override
//      public void beforeTextChanged(CharSequence s, int start, int count, int after)
//      {
//
//      }
//
//      @Override
//      public void afterTextChanged(Editable s)
//      {
//        int unit = Integer.parseInt(diaryUnitField.getText().toString());
//        int price = Integer.parseInt(diaryProductPriceField.getText().toString());
//        int total = unit * price;
//        diaryProductTotal.setText(String.valueOf(total));
//      }
//    });
//
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

        Toast.makeText(this, barcode, Toast.LENGTH_LONG).show();

        Log.d("DOUBLE : ", String.valueOf(inStock));

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
//    Toast.makeText(this, barcode, Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_form_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    if (item.getItemId() == R.id.action_confirm)
    {

    }
    return super.onOptionsItemSelected(item);
  }


  public void postStockDiary()
  {
    new HttpRequestTask().execute();
  }

  private class HttpRequestTask extends AsyncTask<Object, Object, StockDiary>
  {
    private String kodeMerchant;
    private StockDiary stockDiary;

    public HttpRequestTask(){
//      this.kodeMerchant = kodeMerchant;
    }

    @Override
    protected void onPreExecute()
    {

      Location location = new Location();
      location.setId("0");

      Product product = new Product();
      product.setId("ebbb2f8e-9283-4ca4-81cd-116313330b69");

      stockDiary = new StockDiary();
      stockDiary.setId(UUID.randomUUID().toString());
      stockDiary.setReason(1);
      stockDiary.setUnits(118);
      stockDiary.setPrice(400);
      stockDiary.setAppuser("a");
      stockDiary.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
      stockDiary.setSflag(true);
      stockDiary.setAttributesetInstanceId(null);
      stockDiary.setLocation(location);
      stockDiary.setProduct(product);

    }

    @Override
    protected StockDiary doInBackground(Object... params)
    {
      try
      {
//        final String url = RestVariable.SERVER_URL + "chromis.stockdiary/public/";
        final String url = "http://192.168.1.113:8080/MKChromisServices/webresources/chromis.stockdiary/public/";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        requestHeaders.add("Content-Type", MediaType.APPLICATION_JSON.toString());
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//        requestHeaders.setAccept((List<MediaType>) MediaType.APPLICATION_JSON);

        HttpEntity<StockDiary> request = new HttpEntity<StockDiary>(stockDiary, requestHeaders);


        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<StockDiary> responseEntity = restTemplate.postForEntity(url, request, StockDiary.class);

//        StockDiary result = restTemplate.postForObject(url, request, StockDiary.class);

//        Log.d("RESULT : ", responseEntity.toString());
//        return responseEntity.getBody();
//        System.out.print();
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Log.e("TableProductHelper", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(StockDiary list)
    {
      Toast.makeText(DiaryFormActivity.this, "Stock Diary Created!", Toast.LENGTH_LONG).show();
    }
  }

}
