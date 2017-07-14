package com.mitrakreasindo.pos.main.stock.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitrakreasindo.pos.main.R;

/**
 * Created by lisa on 30/05/17.
 */

public class ProductImageFragment extends Fragment
{

  public ProductImageFragment(){}

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.fragment_product_image, container, false);
    return rootView;
  }
}
