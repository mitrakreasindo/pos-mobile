package com.mitrakreasindo.pos.main.sales;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.Queue;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesActivity extends AppCompatActivity
{

  private static final String TAG = SalesActivity.class.getSimpleName();
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.barcodePreview)
  ImageView barcodePreview;
  @BindView(R.id.barcode_scanner)
  DecoratedBarcodeView barcodeScanner;
  @BindView(R.id.main_content)
  LinearLayout mainContent;
  @BindView(R.id.edittext_search_product)
  AutoCompleteTextView edittextSearchProduct;
  @BindView(R.id.list_sales_product)
  RecyclerView listSalesProduct;
  private DecoratedBarcodeView barcodeView;
  private BeepManager beepManager;
  private String lastText;

  private Queue queue;
  private SalesListAdapter salesListAdapter;

  String[] languages = {"Hello ", "Hi", "Hey", "He-yah", "What's up?"};

  private TableProductHelper tableProductHelper;

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

      //Added preview of scanned barcode
      ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
      imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints)
    {
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sales);
    ButterKnife.bind(this);

    salesListAdapter = new SalesListAdapter(this, Queue.queueData());
    listSalesProduct.setAdapter(salesListAdapter);
    listSalesProduct.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listSalesProduct.setLayoutManager(layoutManager);
    listSalesProduct.setItemAnimator(new DefaultItemAnimator());

    tableProductHelper = new TableProductHelper(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tableProductHelper.getData());
    edittextSearchProduct.setAdapter(adapter);
    edittextSearchProduct.setDropDownBackgroundResource(R.color.white);
    edittextSearchProduct.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
        Toast.makeText(SalesActivity.this, adapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
        edittextSearchProduct.setText("");
      }
    });

    barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
    barcodeView.decodeContinuous(callback);

    beepManager = new BeepManager(this);
  }

//  @Override
//  public boolean onCreateOptionsMenu(Menu menu)
//  {
//    getMenuInflater().inflate(R.menu.default_form_menu, menu);
//
//    return super.onCreateOptionsMenu(menu);
//  }
//
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item)
//  {
//    int id = item.getItemId();
//
//    if (id == R.id.action_confirm)
//    {
//      Toast.makeText(this, "Oke!", Toast.LENGTH_SHORT).show();
//    }
//    return super.onOptionsItemSelected(item);
//  }

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
