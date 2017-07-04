package com.mitrakreasindo.pos.main.stock.diary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lisa on 03/07/17.
 */

public class DiarySelectProductActivity extends AppCompatActivity
{

//  @BindView(R.id.toolbar)
//  Toolbar toolbar;
//  @BindView(R.id.appbar)
//  AppBarLayout appbar;
//  @BindView(R.id.barcode_scanner)
//  DecoratedBarcodeView barcodeScanner;
//  @BindView(R.id.list_select_product)
//  RecyclerView listSelectProduct;


  private static final String TAG = SalesActivity.class.getSimpleName();
  private DecoratedBarcodeView barcodeView;
  private BeepManager beepManager;
  private String lastText;

  private BarcodeCallback callback = new BarcodeCallback() {
    @Override
    public void barcodeResult(BarcodeResult result) {
      if(result.getText() == null || result.getText().equals(lastText)) {
        // Prevent duplicate scans
        return;
      }

      lastText = result.getText();
      barcodeView.setStatusText(result.getText());
      beepManager.playBeepSoundAndVibrate();

      //Added preview of scanned barcode
//      ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
//      imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_diary_select_product);
    ButterKnife.bind(this);

    barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
    barcodeView.decodeContinuous(callback);

    beepManager = new BeepManager(this);

  }

  @Override
  protected void onResume() {
    super.onResume();

    barcodeView.resume();
  }

  @Override
  protected void onPause() {
    super.onPause();

    barcodeView.pause();
  }

  public void pause(View view) {
    barcodeView.pause();
  }

  public void resume(View view) {
    barcodeView.resume();
  }

  public void triggerScan(View view) {
    barcodeView.decodeSingle(callback);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
  }

}
