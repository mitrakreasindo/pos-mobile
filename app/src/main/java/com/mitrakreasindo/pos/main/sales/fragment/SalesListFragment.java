package com.mitrakreasindo.pos.main.sales.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lisa on 18/08/17.
 */

public class SalesListFragment extends Fragment
{

  @BindView(R.id.list_sales_product)
  RecyclerView listSalesProduct;
  Unbinder unbinder;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.item_list_sales_product, container, false);

    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }
}
