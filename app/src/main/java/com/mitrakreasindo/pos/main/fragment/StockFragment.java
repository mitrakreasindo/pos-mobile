package com.mitrakreasindo.pos.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.CategoryActivity;
import com.mitrakreasindo.pos.main.stock.diary.activity.DiaryActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductFormActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment
{
  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_stock, container, false);

    Button btnCategories = (Button) view.findViewById(R.id.btn_stok_mnt_categories);
    Button btnProducts = (Button) view.findViewById(R.id.btn_stok_mnt_products);
    Button btnDiary = (Button) view.findViewById(R.id.btn_stok_mnt_stock_diary);


    btnCategories.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), CategoryActivity.class));
      }
    });

    btnProducts.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), ProductActivity.class));
      }
    });
    btnDiary.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), DiaryActivity.class));
      }
    });

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
  }
}
