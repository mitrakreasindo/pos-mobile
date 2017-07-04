package com.mitrakreasindo.pos.main.stock.diary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.fragment.MaintenanceFragment;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.main.stock.diary.fragment.DiaryFormFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryFormActivity extends AppCompatActivity
{

  private static final String TAG = SalesActivity.class.getSimpleName();
//  private DecoratedBarcodeView barcodeView;
//  private BeepManager beepManager;
//  private String lastText;
//
//  private BarcodeCallback callback = new BarcodeCallback()
//  {
//    @Override
//    public void barcodeResult(BarcodeResult result)
//    {
//      if (result.getText() == null || result.getText().equals(lastText))
//      {
//        // Prevent duplicate scans
//        return;
//      }
//
//      lastText = result.getText();
//      barcodeView.setStatusText(result.getText());
//      beepManager.playBeepSoundAndVibrate();
//
//      //Added preview of scanned barcode
//      ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
//      imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
//    }
//
//    @Override
//    public void possibleResultPoints(List<ResultPoint> resultPoints) {
//    }
//  };


    @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_diary_form);
    ButterKnife.bind(this);

//    barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
//    barcodeView.decodeContinuous(callback);
//
//    beepManager = new BeepManager(this);

//    setSupportActionBar(toolbar);
//    toolbar.setNavigationOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View v)
//      {
//        onBackPressed();
//      }
//    });

    DiaryFormFragment diaryFormFragment = new DiaryFormFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.main_content, diaryFormFragment, diaryFormFragment.getTag()).commit();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_form_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    if (item.getItemId() == R.id.action_confirm)
    {

    }
    return super.onOptionsItemSelected(item);
  }
}
