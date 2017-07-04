package com.mitrakreasindo.pos.main.stock.diary.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.diary.activity.DiarySelectProductActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFormFragment extends Fragment
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
  @BindView(R.id.diary_reason_spinner)
  Spinner diaryReasonSpinner;
  @BindView(R.id.diary_location_field)
  EditText diaryLocationField;
  Unbinder unbinder;
  @BindView(R.id.diary_product_field)
  EditText diaryProductField;
  @BindView(R.id.btn_diary_select_product)
  Button btnDiarySelectProduct;

  private int mYear, mMonth, mDay, mHour, mMinute, mSecond;

  public DiaryFormFragment()
  {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_diary_form, container, false);
    unbinder = ButterKnife.bind(this, view);

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        getActivity().onBackPressed();
      }
    });

    btnDiarySelectProduct.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), DiarySelectProductActivity.class));
//        DiarySelectProductFragment diarySelectProductFragment = new DiarySelectProductFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.main_content, diarySelectProductFragment, diarySelectProductFragment.getTag()).addToBackStack(diarySelectProductFragment.getTag()).commit();
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

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
        {
          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute)
          {
            diaryDateField.setText(diaryDateField.getText() + " " + hourOfDay + ":" + minute);
          }
        }, mHour, mMinute, false);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
        {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
          {
            diaryDateField.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            timePickerDialog.show();
          }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
      }
    });
    return view;
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }
}
