package com.mitrakreasindo.pos.main.stock.diary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.main.stock.diary.controller.DiarySelectProductAdapter;
import com.mitrakreasindo.pos.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lisa on 03/07/17.
 */

public class DiarySelectProductActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.barcode_scanner)
  DecoratedBarcodeView barcodeScanner;
  @BindView(R.id.list_select_product)
  RecyclerView listSelectProduct;
  @BindView(R.id.edit_filter)
  EditText editFilter;
  @BindView(R.id.button_filter)
  Button buttonFilter;

  private DiarySelectProductAdapter diarySelectProductAdapter;
  private TableProductHelper tableProductHelper;

  private static final String TAG = SalesActivity.class.getSimpleName();
  private DecoratedBarcodeView barcodeView;
  private BeepManager beepManager;
  private String lastText;

  private BarcodeCallback callback = new BarcodeCallback()
  {
    @Override
    public void barcodeResult(BarcodeResult result)
    {
      if (result.getText() == null || result.getText().equals(lastText))
      {
        // Prevent duplicate scans
        return;
      }

      lastText = result.getText();
      barcodeView.setStatusText(result.getText());
      beepManager.playBeepSoundAndVibrate();

      editFilter.setText(lastText);
      Toast.makeText(DiarySelectProductActivity.this, lastText, Toast.LENGTH_LONG).show();

    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints)
    {
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_diary_select_product);
    ButterKnife.bind(this);

    diarySelectProductAdapter = new DiarySelectProductAdapter(this, new ArrayList<Product>());

    barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
    barcodeView.decodeContinuous(callback);

    beepManager = new BeepManager(this);

    listSelectProduct.setHasFixedSize(true);
    listSelectProduct.setAdapter(diarySelectProductAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listSelectProduct.setLayoutManager(layoutManager);

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    tableProductHelper = new TableProductHelper(this);

    diarySelectProductAdapter.clear();
    diarySelectProductAdapter.addProduct(tableProductHelper.getData());

    editFilter.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        diarySelectProductAdapter.clear();
        diarySelectProductAdapter.addProduct(tableProductHelper.getData(editFilter.getText().toString()));
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      {

      }

      @Override
      public void afterTextChanged(Editable s)
      {

      }
    });

    buttonFilter.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        editFilter.setText("");
      }
    });
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    barcodeView.resume();
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    barcodeView.pause();
  }

  public void pause(View view)
  {
    barcodeView.pause();
  }

  public void resume(View view)
  {
    barcodeView.resume();
  }

  public void triggerScan(View view)
  {
    barcodeView.decodeSingle(callback);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
    return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
  }
}
