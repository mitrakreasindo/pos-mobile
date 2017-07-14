package com.mitrakreasindo.pos.main.stock.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lisa on 30/05/17.
 */

public class ProductGeneralFragment extends Fragment
{

  EditText edittextReference;

  EditText edittextBarcode;

  EditText edittextGeneralName;

  EditText edittextGeneralShortname;

  Spinner spinnerGeneralCategory;

  EditText edittextGeneralBuyprice;
  @BindView(R.id.edittext_general_sell_price)
  EditText edittextGeneralSellPrice;
  @BindView(R.id.product_general_fragment)
  FrameLayout productGeneralFragment;

  Unbinder unbinder;

  EditText edittextStockByYear;

  EditText edittextStockVolume;

  EditText edittextStockCatalogRanking;

  EditText edittextStockPackQuantity;

  Spinner spinnerStockOfProduct;

//  private OnGetFromGeneralClickListener listener;

  public ProductGeneralFragment()
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
    View generalFragment = inflater.inflate(R.layout.fragment_product_general, container, false);
    View stockFragment = inflater.inflate(R.layout.fragment_product_stock, container, false);

    edittextReference = (EditText) generalFragment.findViewById(R.id.edittext_general_reference);
    edittextStockByYear = (EditText) stockFragment.findViewById(R.id.edittext_stock_by_year);

    edittextStockByYear.setText("12344567989");
    edittextReference.setText("12344567989");
    unbinder = ButterKnife.bind(this, generalFragment);
    return generalFragment;
  }

//  @Override
//  public void onAttach(Context context)
//  {
//    super.onAttach(context);
//    try
//    {
//      listener = (OnGetFromGeneralClickListener) context;
//    }
//    catch (ClassCastException e)
//    {
//      throw new ClassCastException(context.toString() + " must implement OnGetFromGeneralClickListener");
//    }
//  }
//
//  public void getFromGeneral(View v){
//    if (listener != null)
//    {
//      listener.getFromGeneral(edittextReference.getText().toString());
//    }
//
//  }

//  public interface OnGetFromGeneralClickListener {
//    void getFromGeneral(String message);
//  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }
}
