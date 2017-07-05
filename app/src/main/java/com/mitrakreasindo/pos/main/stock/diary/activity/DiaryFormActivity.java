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

import com.mitrakreasindo.pos.main.R;

import java.util.Calendar;

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

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_form);
    ButterKnife.bind(this);


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
}
