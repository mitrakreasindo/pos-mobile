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

public class StockFragment extends Fragment
{

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_stock, container, false);

    return view;
  }
}
