package com.mitrakreasindo.pos.main.stock.diary.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryFormActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.diary_date_field)
  EditText diaryDateField;
  @BindView(R.id.diary_barcode_field)
  EditText diaryBarcodeField;
  @BindView(R.id.diary_category_field)
  EditText diaryCategoryField;
  @BindView(R.id.diary_unit_field)
  EditText diaryUnitField;
  @BindView(R.id.diary_buy_price_field)
  EditText diaryBuyPriceField;
  @BindView(R.id.diary_location_field)
  EditText diaryLocationField;
  @BindView(R.id.main_content)
  CoordinatorLayout mainContent;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_form);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

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
