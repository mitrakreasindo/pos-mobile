package com.mitrakreasindo.pos.main.stock.diary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.diary.controller.DiarySelectProductController;
import com.mitrakreasindo.pos.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiarySelectProductFragment extends Fragment
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.barcode_scanner)
  DecoratedBarcodeView barcodeScanner;
  @BindView(R.id.list_select_product)
  RecyclerView listSelectProduct;
  Unbinder unbinder;

  private DiarySelectProductController diarySelectProductController;
  private TableProductHelper tableProductHelper;

  private CaptureManager capture;
  private DecoratedBarcodeView barcodeScannerView;

  public DiarySelectProductFragment()
  {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);


  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.activity_diary_select_product, container, false);
    unbinder = ButterKnife.bind(this, view);

    barcodeScannerView = (DecoratedBarcodeView) view.findViewById(R.id.barcode_scanner);

//    capture = new CaptureManager(getActivity(), barcodeScannerView);
//    capture.initializeFromIntent(getActivity().getIntent(), savedInstanceState);
//    capture.decode();

    new IntentIntegrator(getActivity()).initiateScan();
//    IntentIntegrator.forSupportFragment(this).initiateScan();

    diarySelectProductController = new DiarySelectProductController(getActivity(), new ArrayList<Product>());

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        getActivity().onBackPressed();
      }
    });

//    btnClearFilter.setOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View view)
//      {
//        txtFilter.setText("");
//      }
//    });

    listSelectProduct.setHasFixedSize(true);
    listSelectProduct.setAdapter(diarySelectProductController);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    listSelectProduct.setLayoutManager(layoutManager);

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        getActivity().onBackPressed();
      }
    });

//    if (!is_action_mode)
//    {
//      txtActionToolbar.setVisibility(View.GONE);
//    }
//    else
//    {
//      txtActionToolbar.setVisibility(View.VISIBLE);
//    }

    tableProductHelper = new TableProductHelper(getActivity());

    diarySelectProductController.clear();
    diarySelectProductController.addProduct(tableProductHelper.getData());

    return view;
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }
}
