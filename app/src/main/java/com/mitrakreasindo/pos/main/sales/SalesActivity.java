package com.mitrakreasindo.pos.main.sales;

import android.graphics.Color;
import android.os.Bundle;
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
import com.mitrakreasindo.pos.main.Queue;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.Sale;
import com.mitrakreasindo.pos.model.Tax;
import com.mitrakreasindo.pos.model.Ticket;
import com.mitrakreasindo.pos.model.TicketLine;

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
  @BindView(R.id.sales_product_total)
  TextView salesProductTotal;
  @BindView(R.id.btn_sales_cart)
  ImageView btnSalesCart;
  @BindView(R.id.btn_sales_save)
  ImageView btnSalesSave;
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

    salesListAdapter = new SalesListAdapter(this, new ArrayList<TicketLine>());
//    salesListAdapter = new SalesListAdapter(this, new ArrayList<Queue>());
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
//        Product product = tableProductHelper.getData().get(position);
        Product product = (Product) adapter.getItem(position);
        adapter.getItem(position);

        Ticket ticket = new Ticket();
        ticket.setId("c3ea963f-b767-46fc-9e42-d247b9167bdb");

        Tax tax = new Tax();
        tax.setId("001");

        String example = "Convert Java String";
        byte[] bytes = example.getBytes();

        TicketLine ticketLine = new TicketLine();
        ticketLine.setProduct(product);
        ticketLine.setAttributes(bytes);
        ticketLine.setUnits(1);
        ticketLine.setPrice(product.getPricesell());
        ticketLine.setTicket(ticket);
        ticketLine.setSflag(true);
        ticketLine.setTaxid(tax);
//        ticketLine.setAttributesetinstanceId();

        salesListAdapter.addTicketLine(ticketLine);
        Log.d("TICKET OPERAND", ticketLine.getProduct().toString());
//        Toast.makeText(SalesActivity.this, adapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
        edittextSearchProduct.setText("");
      }
    });

    salesListAdapter.notifyDataSetChanged();
    salesProductTotal.setText(String.valueOf(salesListAdapter.grandTotal()));

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
