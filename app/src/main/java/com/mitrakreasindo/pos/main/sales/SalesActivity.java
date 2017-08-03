package com.mitrakreasindo.pos.main.sales;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;
import com.mitrakreasindo.pos.main.sales.payment.PaymentActivity;
import com.mitrakreasindo.pos.model.ClosedCash;
import com.mitrakreasindo.pos.model.Customer;
import com.mitrakreasindo.pos.model.Location;
import com.mitrakreasindo.pos.model.Payment;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.Receipt;
import com.mitrakreasindo.pos.model.Sales;
import com.mitrakreasindo.pos.model.SalesItem;
import com.mitrakreasindo.pos.model.SalesPack;
import com.mitrakreasindo.pos.model.StockDiary;
import com.mitrakreasindo.pos.model.Tax;
import com.mitrakreasindo.pos.model.TaxLine;
import com.mitrakreasindo.pos.model.Viewpayments;
import com.mitrakreasindo.pos.model.Viewreceipts;
import com.mitrakreasindo.pos.model.Viewsales;
import com.mitrakreasindo.pos.model.Viewsalesitems;
import com.mitrakreasindo.pos.model.Viewstockdiary;
import com.mitrakreasindo.pos.model.Viewtaxlines;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

  public static Activity sActivity;
  private final String siteguid = "a73c83f2-3c42-42a7-8f19-7d7cbea17286";
  private DecimalFormat decimalFormat;

  String example = "Convert Java String";
  byte[] bytes = example.getBytes();

  private DecoratedBarcodeView barcodeView;
  private BeepManager beepManager;
  private String lastText;

  private SalesService salesService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;

  private SalesListAdapter salesListAdapter;

  private TableProductHelper tableProductHelper;

  private Sales sales;
  private Product product;
  private SalesPack salesPack;
  private Receipt receipt;
  private SalesItem salesItem;
  private Payment payment;
  private StockDiary stockDiary;
  private TaxLine taxLine;
  private Customer customer;
  private People people;

  private Viewsales viewsales;
  private Viewsalesitems viewsalesitem;
  private Viewreceipts viewreceipt;
  private Viewpayments viewpayment;
  private Viewstockdiary viewstockdiary;
  private Viewtaxlines viewtaxline;

  private Collection<Viewsalesitems> viewsalesitems = new ArrayList<>();
  private Collection<Viewpayments> viewpayments = new ArrayList<>();
  private Collection<Viewstockdiary> viewstockdiaries = new ArrayList<>();
  private Collection<Viewtaxlines> viewtaxlines = new ArrayList<>();

  private BarcodeCallback callback = new BarcodeCallback()
  {
    @Override
    public void barcodeResult(BarcodeResult result)
    {

      product = tableProductHelper.getProduct(result.getText());

//      Sales sales = new Sales();
//      sales.setId("c3ea963f-b767-46fc-9e42-d247b9167bdb");
      data();

      Tax tax = new Tax();
      tax.setId("001");

      salesItem = new SalesItem();
      salesItem.setProduct(product);
      salesItem.setAttributes(bytes);
      salesItem.setUnits(1);
      salesItem.setPrice(product.getPricesell());
      salesItem.setSalesId(sales);
      salesItem.setSflag(true);
      salesItem.setTaxid(tax);

      viewtaxlines.add(viewtaxline);
      viewsalesitems.add(viewsalesitem);
      viewpayments.add(viewpayment);
      viewstockdiaries.add(viewstockdiary);

      if (product != null)
      {

//        TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
//        tableSalesItemHelper.open();
//        tableSalesItemHelper.insertSalesItem(salesItem);
//        tableSalesItemHelper.close();

        salesListAdapter.addSalesItem(salesItem);
        salesProductTotal.setText(decimalFormat.format(salesListAdapter.grandTotal()));

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
      salesProductTotal.setText(decimalFormat.format(salesListAdapter.grandTotal()));

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

    salesService = ClientService.createService().create(SalesService.class);

    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    salesListAdapter = new SalesListAdapter(this, new ArrayList<SalesItem>());

    final ClosedCash closedCash = new ClosedCash();
    if (IDs.getLoginCloseCashID().equals(""))
      closedCash.setMoney(UUID.randomUUID().toString());
    else
      closedCash.setMoney(IDs.getLoginCloseCashID());

    Tax tax = new Tax();
    tax.setId("001");

    people = new People();
    people.setId("1111111");

    customer = new Customer();
    customer.setId(null);

    receipt = new Receipt();
    receipt.setId(UUID.randomUUID().toString());
    receipt.setAttributes(null);
    receipt.setSales(sales);
    receipt.setMoney(closedCash);
    receipt.setDatenew(new Date());
    receipt.setPerson(IDs.getLoginUser());
    receipt.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    receipt.setSflag(true);

    sales = new Sales();
    sales.setId(receipt.getId());
    sales.setSalesnum(1);
    sales.setSalestype(1);
    sales.setStatus(1);
    sales.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    sales.setSflag(true);
    sales.setCustomer(customer);
    sales.setPerson(people);
    sales.setReceipt(receipt);

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

    btnSalesCheckout.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {

        if (salesListAdapter.salesItems.size() == 0)
        {
          Toast.makeText(SalesActivity.this, R.string.has_no_product, Toast.LENGTH_LONG).show();
        }
        else
        {
          TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
          tableSalesItemHelper.open();
          tableSalesItemHelper.insertSalesItem(salesListAdapter.salesItems);
          tableSalesItemHelper.close();

          Intent intent = new Intent(SalesActivity.this, PaymentActivity.class);
          intent.putExtra("salesid", sales.getId());
//          intent.putExtra("salesid", sales.getId());

          startActivity(intent);
        }


      }
    });

    btnSalesSave.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {

        if (salesListAdapter.salesItems.size() == 0)
        {
          Toast.makeText(SalesActivity.this, R.string.has_no_product, Toast.LENGTH_LONG).show();
        }
        else
        {
          TableSalesHelper tableSalesHelper = new TableSalesHelper(SalesActivity.this);
          tableSalesHelper.open();
          tableSalesHelper.insertSales(sales);
          tableSalesHelper.close();

          TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
          tableSalesItemHelper.open();
          tableSalesItemHelper.insertSalesItem(salesListAdapter.salesItems);
          tableSalesItemHelper.close();
        }
//        salesPack = new SalesPack();
//        salesPack.setSales(viewsales);
//        salesPack.setReceipts(viewreceipt);
//        salesPack.setSalesItems(viewsalesitems);
//        salesPack.setPayments(viewpayments);
//        salesPack.setStockdiary(viewstockdiaries);
//        salesPack.setTaxlines(viewtaxlines);

//        postSales();

//        for (Viewpayments lpayment : viewpayments)
//        {
//          Log.d("DATA payment", lpayment.getId());
//        }

//        TableSalesHelper tableSalesHelper = new TableSalesHelper(SalesActivity.this);
//        tableSalesHelper.open();
//        tableSalesHelper.insertSales(sales);
//        tableSalesHelper.close();

//        Toast.makeText(SalesActivity.this, "Success", Toast.LENGTH_LONG).show();

//        finish();

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

        product = (Product) adapter.getItem(position);
        adapter.getItem(position);

        Tax tax = new Tax();
        tax.setId("001");

        data();

        salesItem = new SalesItem();
        salesItem.setId(0);
        salesItem.setProduct(product);
        salesItem.setAttributes(bytes);
        salesItem.setUnits(1);
        salesItem.setPrice(product.getPricesell());
        salesItem.setSalesId(sales);
        salesItem.setSflag(true);
        salesItem.setTaxid(tax);

        viewtaxlines.add(viewtaxline);
        viewsalesitems.add(viewsalesitem);
        viewpayments.add(viewpayment);
        viewstockdiaries.add(viewstockdiary);

//        TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
//        tableSalesItemHelper.open();
//        tableSalesItemHelper.insertSalesItem(salesItem);
//        tableSalesItemHelper.close();

        salesListAdapter.addSalesItem(salesItem);
        edittextSearchProduct.setText("");
        salesProductTotal.setText(decimalFormat.format(salesListAdapter.grandTotal()));

      }

    });

    salesListAdapter.notifyDataSetChanged();

    decimalFormat = new DecimalFormat("###,###.###");
//    String value = decimalFormat.format(salesListAdapter.grandTotal());

    salesProductTotal.setText(decimalFormat.format(salesListAdapter.grandTotal()));

    barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
    barcodeView.decodeContinuous(callback);
    beepManager = new BeepManager(this);


    sActivity = this;
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

  public void data()
  {

    Location location = new Location();
    location.setId(UUID.randomUUID().toString());

    Tax tax = new Tax();
    tax.setId("001");

//    receipt = new Receipt();
//    receipt.setId(UUID.randomUUID().toString());
//    receipt.setAttributes(null);
//    receipt.setSales(sales);
//    receipt.setDatenew(new Date());
//    receipt.setPerson("test");
//    receipt.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
//    receipt.setSflag(true);
//
//    sales = new Sales();
//    sales.setId(receipt.getId());
//    sales.setSalesnum(1);
//    sales.setSalestype(1);
//    sales.setStatus(1);
//    sales.setSiteguid(UUID.randomUUID().toString());
//    sales.setSflag(true);
//    sales.setCustomer(customer);
//    sales.setPerson(people);
//    sales.setReceipt(receipt);

    salesItem = new SalesItem();
    salesItem.setProduct(product);
    salesItem.setAttributes(bytes);
    salesItem.setUnits(1);
    salesItem.setPrice(product.getPricesell());
    salesItem.setSalesId(sales);
    salesItem.setSflag(true);
    salesItem.setTaxid(tax);

    payment = new Payment();
    payment.setId(UUID.randomUUID().toString());
    payment.setPayment("cash");
    payment.setTotal(20000);
    payment.setTransid("tesssssss");
    payment.setNotes("test notes");
    payment.setTendered(20000);
    payment.setCardname("CardName");
    payment.setReturnmsg(null);
    payment.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    payment.setSflag(true);
    payment.setReceipt(receipt);

    taxLine = new TaxLine();
    taxLine.setId(UUID.randomUUID().toString());
    taxLine.setBase(1000);
    taxLine.setAmount(100);
    taxLine.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    taxLine.setSflag(true);
    taxLine.setReceipt(receipt);
    taxLine.setTaxid(tax);

    stockDiary = new StockDiary();
    stockDiary.setId(UUID.randomUUID().toString());
    stockDiary.setReason(0);
    stockDiary.setUnits(1);
    stockDiary.setPrice(salesItem.getProduct().getPricesell());
    stockDiary.setAppuser(IDs.getLoginUser());
    stockDiary.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    stockDiary.setSflag(true);
    stockDiary.setAttributesetinstanceId(null);
    stockDiary.setLocation(location);
    stockDiary.setProduct(salesItem.getProduct());

    viewtaxline = new Viewtaxlines();
    viewtaxline.setId(taxLine.getId());
    viewtaxline.setReceipt(taxLine.getReceipt().getId());
    viewtaxline.setTaxid(taxLine.getTaxid().getId());
    viewtaxline.setBase(taxLine.getBase());
    viewtaxline.setAmount(taxLine.getAmount());
    viewtaxline.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewtaxline.setSflag(taxLine.getSflag());
    viewtaxline.setTaxName(null);

    viewsales = new Viewsales();
    viewsales.setId(receipt.getId());
    viewsales.setSalesnum(sales.getSalesnum());
    viewsales.setPerson("0");
    viewsales.setCustomer(null);
    viewsales.setSalestype(sales.getSalestype());
    viewsales.setStatus(viewsales.getStatus());
    viewsales.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewsales.setSflag(sales.getSflag());
    viewsales.setCustomerName(null);
    viewsales.setPersonName(null);
    viewsales.setDatenew("2017-07-26 06:00:18");

    viewsalesitem = new Viewsalesitems();
    viewsalesitem.setId(0);
    viewsalesitem.setSalesId(sales.getId());
    viewsalesitem.setLine(salesItem.getLine());
    viewsalesitem.setProduct(salesItem.getProduct().getId());
    viewsalesitem.setAttributesetinstanceId(null);
    viewsalesitem.setUnits(salesItem.getUnits());
    viewsalesitem.setPrice(salesItem.getPrice());
    viewsalesitem.setTaxid(salesItem.getTaxid().getId());
    viewsalesitem.setAttributes(null);
    viewsalesitem.setRefundqty(salesItem.getRefundqty());
    viewsalesitem.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewsalesitem.setSflag(sales.getSflag());
    viewsalesitem.setProductName(null);
    viewsalesitem.setTaxName(null);
    viewsalesitem.setRate(null);


    viewreceipt = new Viewreceipts();
    viewreceipt.setId(receipt.getId());

    if (IDs.getLoginCloseCashID().equals(""))
      viewreceipt.setMoney(UUID.randomUUID().toString());
    else
      viewreceipt.setMoney(IDs.getLoginCloseCashID());

    viewreceipt.setDatenew(new Date().toString());
    viewreceipt.setPerson(receipt.getPerson());
    viewreceipt.setAttributes(null);
    viewreceipt.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewreceipt.setSflag(true);
    viewreceipt.setHost("");

    viewpayment = new Viewpayments();
    viewpayment.setId(payment.getId());
    viewpayment.setReceipt(receipt.getId());
    viewpayment.setPayment(payment.getPayment());
    viewpayment.setTotal(payment.getTotal());
    viewpayment.setTransid(payment.getTransid());
    viewpayment.setNotes(payment.getNotes());
    viewpayment.setTendered(payment.getTendered());
    viewpayment.setCardname(payment.getCardname());
    viewpayment.setReturnmsg(payment.getReturnmsg());
    viewpayment.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewpayment.setSflag(payment.getSflag());
    viewpayment.setDatenew(null);


    viewstockdiary = new Viewstockdiary();
    viewstockdiary.setId(stockDiary.getId());
    viewstockdiary.setProduct(stockDiary.getProduct().getId());
    viewstockdiary.setDatenew(new Date().toString());
    viewstockdiary.setReason(stockDiary.getReason());
    viewstockdiary.setUnits(stockDiary.getUnits());
    viewstockdiary.setPrice(stockDiary.getPrice());
    viewstockdiary.setAppuser(stockDiary.getAppuser());
    viewstockdiary.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewstockdiary.setSflag(true);
    viewstockdiary.setAttributesetinstanceId(null);
    viewstockdiary.setLocation("0");


  }

//  public void postSales()
//  {
//    final ProgressDialog progressDialog = new ProgressDialog(this);
//    progressDialog.setCancelable(false);
//    progressDialog.setMessage("Loading");
//    progressDialog.show();
//
//    Call<HashMap<Integer, String>> saveSales = salesService.postSales(kodeMerchant, salesPack);
//    saveSales.enqueue(new Callback<HashMap<Integer, String>>()
//    {
//
//      private int responseCode;
//      private String responseMessage;
//
//      @Override
//      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
//      {
//        final HashMap<Integer, String> data = response.body();
//        for (int resultKey : data.keySet())
//        {
//          responseCode = resultKey;
//          responseMessage = data.get(resultKey);
//
//          if (responseCode == 0)
//          {
//            progressDialog.dismiss();
//            Toast.makeText(SalesActivity.this, "success", Toast.LENGTH_LONG).show();
//          }
//        }
//      }
//
//      @Override
//      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
//      {
//
//      }
//    });
//  }

}
