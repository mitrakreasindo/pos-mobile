package com.mitrakreasindo.pos.main.sales;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;
import com.mitrakreasindo.pos.model.Customer;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.Receipt;
import com.mitrakreasindo.pos.model.Sales;
import com.mitrakreasindo.pos.model.SalesItem;
import com.mitrakreasindo.pos.model.Tax;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
  @BindView(R.id.sales_product_total)
  TextView salesProductTotal;
  @BindView(R.id.btn_sales_save)
  ImageView btnSalesSave;
  @BindView(R.id.btn_sales_checkout)
  ImageView btnSalesCheckout;


  private DecoratedBarcodeView barcodeView;
  private BeepManager beepManager;
  private String lastText;

  private SalesListAdapter salesListAdapter;

  private TableProductHelper tableProductHelper;

  private Sales sales;

  private BarcodeCallback callback = new BarcodeCallback()
  {

    @Override
    public void barcodeResult(BarcodeResult result)
    {

      Product product = tableProductHelper.getProduct(result.getText());

//      Sales sales = new Sales();
//      sales.setId("c3ea963f-b767-46fc-9e42-d247b9167bdb");

      Tax tax = new Tax();
      tax.setId("001");

      String example = "Convert Java String";
      byte[] bytes = example.getBytes();

      final SalesItem salesItem = new SalesItem();
      salesItem.setProduct(product);
      salesItem.setAttributes(bytes);
      salesItem.setUnits(1);
      salesItem.setPrice(product.getPricesell());
      salesItem.setSalesId(sales);
      salesItem.setSflag(true);
      salesItem.setTaxid(tax);
      Log.e("PRODUCT VALUE", product.getId());

      if (product != null)
      {
        TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
        tableSalesItemHelper.open();
        tableSalesItemHelper.insertSalesItem(salesItem);
        tableSalesItemHelper.close();

        salesListAdapter.addSalesItem(salesItem);
        salesProductTotal.setText(String.valueOf(salesListAdapter.grandTotal()));

      }
      else
      {
        Toast.makeText(SalesActivity.this, "Product not found!", Toast.LENGTH_LONG).show();
      }


      if (result.getText() != null || result.getText().equals(lastText))
      {
        barcodeScanner.pause();

        new CountDownTimer(1000, 1000)
        {

          public void onTick(long millisUntilFinished)
          {
//            salesProductTotal.setText("seconds remaining: " + millisUntilFinished / 1000);
          }

          public void onFinish()
          {
            barcodeScanner.resume();
          }
        }.start();
        // Prevent duplicate scans
      }

      lastText = result.getText();
      barcodeView.setStatusText(result.getText());
      beepManager.playBeepSoundAndVibrate();

      //Added preview of scanned barcode
      ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
      imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
      salesProductTotal.setText(String.valueOf(salesListAdapter.grandTotal()));

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

    salesListAdapter = new SalesListAdapter(this, new ArrayList<SalesItem>());

    listSalesProduct.setAdapter(salesListAdapter);
    listSalesProduct.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listSalesProduct.setLayoutManager(layoutManager);
    listSalesProduct.setItemAnimator(new DefaultItemAnimator());

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    Customer customer = new Customer();
    customer.setId(null);

    People people = new People();
    people.setId("1111111");

    Receipt receipt = new Receipt();
    receipt.setId(null);

    sales = new Sales();
    sales.setId(UUID.randomUUID().toString());
    sales.setSalesnum(1);
    sales.setSalestype(1);
    sales.setStatus(1);
    sales.setSiteguid(UUID.randomUUID().toString());
    sales.setSflag(true);
    sales.setCustomer(customer);
    sales.setPerson(people);
    sales.setReceipt(receipt);

    btnSalesSave.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {

        TableSalesHelper tableSalesHelper = new TableSalesHelper(SalesActivity.this);
        tableSalesHelper.open();
        tableSalesHelper.insertSales(sales);
        tableSalesHelper.close();

        Toast.makeText(SalesActivity.this, "Success", Toast.LENGTH_LONG).show();
        
        finish();
        

      }
    });

    tableProductHelper = new TableProductHelper(this);

    final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tableProductHelper.getData());

    edittextSearchProduct.setAdapter(adapter);
    edittextSearchProduct.setDropDownBackgroundResource(R.color.white);
    edittextSearchProduct.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {

        Product product = (Product) adapter.getItem(position);
        adapter.getItem(position);

        Tax tax = new Tax();
        tax.setId("001");

        String example = "Convert Java String";
        byte[] bytes = example.getBytes();

        SalesItem salesItem = new SalesItem();
        salesItem.setProduct(product);
        salesItem.setAttributes(bytes);
        salesItem.setUnits(1);
        salesItem.setPrice(product.getPricesell());
        salesItem.setSalesId(sales);
        salesItem.setSflag(true);
        salesItem.setTaxid(tax);

        TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
        tableSalesItemHelper.open();
        tableSalesItemHelper.insertSalesItem(salesItem);
        tableSalesItemHelper.close();

        salesListAdapter.addSalesItem(salesItem);
        edittextSearchProduct.setText("");
        salesProductTotal.setText(String.valueOf(salesListAdapter.grandTotal()));

      }

    });

    salesListAdapter.notifyDataSetChanged();

    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    salesProductTotal.setText(decimalFormat
      .format(salesListAdapter.grandTotal()));

//    salesProductTotal.setText(String.valueOf(salesListAdapter.grandTotal()));

    barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
    barcodeView.decodeContinuous(callback);
    beepManager = new BeepManager(this);

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

  public void triggerScan(View view)
  {
    barcodeView.decodeSingle(callback);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
    return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
  }

  @Override
  public void onBackPressed()
  {
    final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
    confirmationDialog.setTitle("Cancel Transaction ?");
    confirmationDialog.setMessage("Are you sure want to cancel the transaction ?");
    confirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        finish();
      }
    });

    confirmationDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        dialog.dismiss();
      }
    });

    confirmationDialog.show();

  }
}
