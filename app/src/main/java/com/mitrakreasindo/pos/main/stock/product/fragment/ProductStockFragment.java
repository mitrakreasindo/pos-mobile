package com.mitrakreasindo.pos.main.stock.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lisa on 30/05/17.
 */

public class ProductStockFragment extends Fragment
{

  public ProductStockFragment()
  {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_product_stock, container, false);

    return view;
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
  }
}
