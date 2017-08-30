package com.mitrakreasindo.pos.main.sales;

import android.app.Activity;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
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
import com.mitrakreasindo.pos.model.ViewPayment;
import com.mitrakreasindo.pos.model.ViewReceipt;
import com.mitrakreasindo.pos.model.ViewSale;
import com.mitrakreasindo.pos.model.ViewSalesItem;
import com.mitrakreasindo.pos.model.ViewStockDiary;
import com.mitrakreasindo.pos.model.ViewTaxLine;
import com.mitrakreasindo.pos.service.SalesService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.UUID.randomUUID;


public class SalesActivity extends AppCompatActivity
{

  private static final String TAG = SalesActivity.class.getSimpleName();

  public static Activity sActivity;
  private final String siteguid = "a73c83f2-3c42-42a7-8f19-7d7cbea17286";
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.edittext_search_product)
  AutoCompleteTextView edittextSearchProduct;
  @BindView(R.id.barcodePreview)
  ImageView barcodePreview;
  @BindView(R.id.barcode_scanner)
  DecoratedBarcodeView barcodeScanner;
  @BindView(R.id.list_sales_product)
  RecyclerView listSalesProduct;
  @BindView(R.id.sales_product_total)
  TextView salesProductTotal;
  @BindView(R.id.btn_sales_checkout)
  ImageView btnSalesCheckout;
  @BindView(R.id.btn_sales_save)
  ImageView btnSalesSave;
  @BindView(R.id.btn_sales_returns)
  ImageView btnSalesReturns;
  
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
  private TablePeopleHelper tablePeopleHelper;

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

  private ViewSale viewsales;
  private ViewSalesItem viewsalesitem;
  private ViewReceipt viewreceipt;
  private ViewPayment viewpayment;
  private ViewStockDiary viewstockdiary;
  private ViewTaxLine viewtaxline;

  private List<ViewSalesItem> viewsalesitems = new ArrayList<>();
  private List<ViewPayment> viewpayments = new ArrayList<>();
  private List<ViewStockDiary> viewstockdiaries = new ArrayList<>();
  private List<ViewTaxLine> viewtaxlines = new ArrayList<>();
  private EditText editTextExtraSalesInfo;

//  String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

  private BarcodeCallback callback = new BarcodeCallback()
  {
    @Override
    public void barcodeResult(BarcodeResult result)
    {
      Product productByCode = tableProductHelper.getProduct(result.getText());

      if (productByCode != null)
      {

//        PrepareData();

        Tax tax = new Tax();
        tax.setId("001");

        Log.d("PRODUCTOPER", productByCode.getName());

        SalesItem itemByBarcode = new SalesItem();
        itemByBarcode.setProduct(productByCode);
        itemByBarcode.setAttributes(bytes);
        itemByBarcode.setUnits(1);
        itemByBarcode.setPrice(productByCode.getPricesell());
        itemByBarcode.setSalesId(sales);
        itemByBarcode.setSflag(true);
        itemByBarcode.setTaxid(tax);

        salesListAdapter.addSalesItem(itemByBarcode);
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

    editTextExtraSalesInfo = new EditText(this);

    salesService = ClientService.createService().create(SalesService.class);

    tableProductHelper = new TableProductHelper(this);
    tablePeopleHelper = new TablePeopleHelper(this);

    Log.d("PERSONID", tablePeopleHelper.getPeopleID(IDs.getLoginUser()));

    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    salesListAdapter = new SalesListAdapter(this, new ArrayList<SalesItem>());

    final ClosedCash closedCash = new ClosedCash();
    if (IDs.getLoginCloseCashID() == null)
    {
      String id = randomUUID().toString();
      closedCash.setMoney(id);
      IDs.setLoginCloseCashID(id);
    }
    else
      closedCash.setMoney(IDs.getLoginCloseCashID());

    Tax tax = new Tax();
    tax.setId("001");

    people = new People();
    people.setId(tablePeopleHelper.getPeopleID(IDs.getLoginUser()));

    customer = new Customer();
    customer.setId(null);

    receipt = new Receipt();
    receipt.setId(randomUUID().toString());
    receipt.setAttributes(null);
    receipt.setSales(sales);
    receipt.setMoney(closedCash);
    receipt.setDatenew(new Date());
    receipt.setPerson(IDs.getLoginUser());
    receipt.setSiteguid(IDs.SITE_GUID);
    receipt.setSflag(true);

    sales = new Sales();
    sales.setId(receipt.getId());
    sales.setSalesnum(1);
    sales.setSalestype(1);
    sales.setStatus(1);
    sales.setSiteguid(IDs.SITE_GUID);
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
    
    btnSalesReturns.setOnClickListener(new View.OnClickListener()
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
          intent.putExtra("retur",true);
          intent.putExtra("sales_id", sales.getId());
          startActivity(intent);
        }
  
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
          intent.putExtra("retur",false);
          intent.putExtra("sales_id", sales.getId());
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
//          TableSalesHelper tableSalesHelper = new TableSalesHelper(SalesActivity.this);
//          tableSalesHelper.open();
//          tableSalesHelper.insertSales(sales);
//          tableSalesHelper.close();

          if(editTextExtraSalesInfo.getParent() != null)
            ((ViewGroup)editTextExtraSalesInfo.getParent()).removeView(editTextExtraSalesInfo);

          editTextExtraSalesInfo.setHint(R.string.hint_sales_extra_info);
          new AlertDialog.Builder(SalesActivity.this)
            .setTitle(R.string.additional_info)
            .setView(editTextExtraSalesInfo)
            .setCancelable(false)
            .setPositiveButton
            (
              R.string.ok, new DialogInterface.OnClickListener()
              {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                  TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(SalesActivity.this);
                  tableSalesItemHelper.open();
                  tableSalesItemHelper.insertSalesItem(salesListAdapter.salesItems);
                  tableSalesItemHelper.close();

                  PrepareData();
                  salesPack = new SalesPack();
                  salesPack.setSales(viewsales);
                  salesPack.setReceipts(viewreceipt);
                  salesPack.setSalesItems(viewsalesitems);
                  salesPack.setPayments(viewpayments);
                  salesPack.setStockdiary(viewstockdiaries);
                  salesPack.setTaxlines(viewtaxlines);

                  TableSalesHelper tableSalesHelper = new TableSalesHelper(SalesActivity.this);
                  tableSalesHelper.open();
                  tableSalesHelper.insertSales(sales);
                  tableSalesHelper.close();

                  postSales(kodeMerchant, salesPack);

                  finish();
                }
              }
            )
            .show();
        }
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

        Product productBySearch = (Product) adapter.getItem(position);
        adapter.getItem(position);

        Tax tax = new Tax();
        tax.setId("001");

//        PrepareData();

        SalesItem itemBySearch = new SalesItem();
        itemBySearch.setId(0);
        itemBySearch.setProduct(productBySearch);
        itemBySearch.setAttributes(bytes);
        itemBySearch.setUnits(1);
        itemBySearch.setPrice(productBySearch.getPricesell());
        itemBySearch.setSalesId(sales);
        itemBySearch.setSflag(true);
        itemBySearch.setTaxid(tax);

        salesListAdapter.addSalesItem(itemBySearch);
        edittextSearchProduct.setText("");
        salesProductTotal.setText(decimalFormat.format(salesListAdapter.grandTotal()));

      }

    });

    salesListAdapter.notifyDataSetChanged();

    decimalFormat = new DecimalFormat("###,###.###");

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
    confirmationDialog.setTitle(R.string.cancel_sales);
    confirmationDialog.setMessage(R.string.cancel_sales_message);
    confirmationDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        finish();
      }
    });

    confirmationDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        dialog.dismiss();
      }
    });

    confirmationDialog.show();

  }

  public void PrepareData()
  {
    Tax tax = new Tax();
    tax.setId("001");

    Location location = new Location();
    location.setId(UUID.randomUUID().toString());

//    salesItem = new SalesItem();
//    salesItem.setProduct(product);
//    salesItem.setAttributes(bytes);
//    salesItem.setUnits(1);
//    salesItem.setPrice(product.getPricesell());
//    salesItem.setSalesId(sales);
//    salesItem.setSflag(true);
//    salesItem.setTaxid(tax);

    payment = new Payment();
    payment.setId(UUID.randomUUID().toString());
    payment.setPayment("cash");
    payment.setTotal(formatTotalPrice());
    payment.setTransid(null);
    payment.setNotes(editTextExtraSalesInfo.getText().toString());
    payment.setTendered(0);
    payment.setCardname(null);
    payment.setReturnmsg(null);
    payment.setSiteguid(IDs.SITE_GUID);
    payment.setSflag(true);
    payment.setReceipt(receipt);

    taxLine = new TaxLine();
    taxLine.setId(UUID.randomUUID().toString());
    taxLine.setBase(0);
    taxLine.setAmount(0);
    taxLine.setSiteguid(IDs.SITE_GUID);
    taxLine.setSflag(true);
    taxLine.setReceipt(receipt);
    taxLine.setTaxid(tax);

//    stockDiary = new StockDiary();
//    stockDiary.setId(randomUUID().toString());
//    stockDiary.setReason(0);
//    stockDiary.setUnits(1);
//    stockDiary.setPrice(salesItem.getProduct().getPricesell());
//    stockDiary.setAppuser(IDs.getLoginUser());
//    stockDiary.setSiteguid(IDs.SITE_GUID);
//    stockDiary.setSflag(true);
//    stockDiary.setAttributesetinstanceId(null);
//    stockDiary.setLocation(location);
//    stockDiary.setProduct(salesItem.getProduct());

    viewtaxline = new ViewTaxLine();
    viewtaxline.setId(taxLine.getId());
    viewtaxline.setReceipt(taxLine.getReceipt().getId());
    viewtaxline.setTaxid(taxLine.getTaxid().getId());
    viewtaxline.setBase(taxLine.getBase());
    viewtaxline.setAmount(taxLine.getAmount());
    viewtaxline.setSiteguid(IDs.SITE_GUID);
    viewtaxline.setSflag(taxLine.getSflag());
    viewtaxline.setTaxName(null);

    viewsales = new ViewSale();
    viewsales.setId(receipt.getId());
    viewsales.setSalesnum(sales.getSalesnum());
    viewsales.setPerson(sales.getPerson().getId());
    viewsales.setCustomer(null);
    viewsales.setSalestype(sales.getSalestype());
    viewsales.setStatus(viewsales.getStatus());
    viewsales.setSiteguid(IDs.SITE_GUID);
    viewsales.setSflag(sales.getSflag());
    viewsales.setCustomerName(null);
    viewsales.setPersonName(IDs.getLoginUserFullname());
    viewsales.setDatenew(DefaultHelper.dateFormat(new Date()));

    viewreceipt = new ViewReceipt();
    viewreceipt.setId(receipt.getId());

    if (IDs.getLoginCloseCashID() == null)
    {
      String id = UUID.randomUUID().toString();
      viewreceipt.setMoney(id);
      IDs.setLoginCloseCashID(id);
    }
    else
      viewreceipt.setMoney(IDs.getLoginCloseCashID());

    viewreceipt.setDatenew(DefaultHelper.dateFormat(new Date()));
    viewreceipt.setPerson(receipt.getPerson());
    viewreceipt.setAttributes(null);
    viewreceipt.setSiteguid(IDs.SITE_GUID);
    viewreceipt.setSflag(true);
    viewreceipt.setHost(IDs.getLoginUser());

    viewpayment = new ViewPayment();
    viewpayment.setId(payment.getId());
    viewpayment.setReceipt(receipt.getId());
    viewpayment.setPayment(payment.getPayment());
    viewpayment.setTotal(payment.getTotal());
    viewpayment.setTransid(payment.getTransid());
    viewpayment.setNotes(payment.getNotes());
    viewpayment.setTendered(payment.getTendered());
    viewpayment.setCardname(payment.getCardname());
    viewpayment.setReturnmsg(payment.getReturnmsg());
    viewpayment.setSiteguid(IDs.SITE_GUID);
    viewpayment.setSflag(payment.getSflag());
    viewpayment.setDatenew(DefaultHelper.dateFormat(new Date()));

    for (int i = 0; i < salesListAdapter.salesItems.size(); i++)
    {
      product = salesListAdapter.salesItems.get(i).getProduct();

      salesItem = salesListAdapter.salesItems.get(i);
      salesItem.setProduct(product);
      salesItem.setAttributes(bytes);
      salesItem.setUnits(salesListAdapter.salesItems.get(i).getUnits());
      salesItem.setPrice(product.getPricesell());
      salesItem.setSalesId(sales);
      salesItem.setSflag(true);
      salesItem.setTaxid(tax);

      viewsalesitem = new ViewSalesItem();
      viewsalesitem.setId(0);
      viewsalesitem.setSales_id(sales.getId());
      viewsalesitem.setLine(salesItem.getLine());
      viewsalesitem.setProduct(salesItem.getProduct().getId());
      viewsalesitem.setAttributesetinstanceId(null);
      viewsalesitem.setUnits(salesItem.getUnits());
      viewsalesitem.setPrice(salesItem.getPrice());
      viewsalesitem.setTaxid(salesItem.getTaxid().getId());
      viewsalesitem.setAttributes(null);
      viewsalesitem.setRefundqty(salesItem.getRefundqty());
      viewsalesitem.setSiteguid(IDs.SITE_GUID);
      viewsalesitem.setSflag(sales.getSflag());
      viewsalesitem.setProductName(null);
      viewsalesitem.setTaxName(null);
      viewsalesitem.setRate(null);
      viewsalesitems.add(viewsalesitem);

      stockDiary = new StockDiary();
      stockDiary.setId(UUID.randomUUID().toString());
      stockDiary.setReason(0);
      stockDiary.setUnits(1);
      stockDiary.setPrice(salesItem.getProduct().getPricesell());
      stockDiary.setAppuser(IDs.getLoginUser());
      stockDiary.setSiteguid(IDs.SITE_GUID);
      stockDiary.setSflag(true);
      stockDiary.setAttributesetinstanceId(null);
      stockDiary.setLocation(location);
      stockDiary.setProduct(salesItem.getProduct());

      viewstockdiary = new ViewStockDiary();
      viewstockdiary.setId(stockDiary.getId());
      viewstockdiary.setProduct(stockDiary.getProduct().getId());
      viewstockdiary.setDatenew(DefaultHelper.dateFormat(new Date()));
      viewstockdiary.setReason(stockDiary.getReason());
      viewstockdiary.setUnits(stockDiary.getUnits());
      viewstockdiary.setPrice(stockDiary.getPrice());
      viewstockdiary.setAppuser(stockDiary.getAppuser());
      viewstockdiary.setSiteguid(IDs.SITE_GUID);
      viewstockdiary.setSflag(true);
      viewstockdiary.setAttributesetinstanceId(null);
      viewstockdiary.setLocation("0");
      viewstockdiaries.add(viewstockdiary);
    }
    viewtaxlines.add(viewtaxline);
    viewpayments.add(viewpayment);
  }

  public void refreshData()
  {
    salesProductTotal.setText(decimalFormat.format(salesListAdapter.grandTotal()));
  }

  public void setTextTotal(double total)
  {
    salesProductTotal.setText(decimalFormat.format(total));
  }

  public void postSales(String kodeMerchant, SalesPack salesPack)
  {
    Call<HashMap<Integer, String>> saveSales = salesService.postSales(kodeMerchant, salesPack);
    saveSales.enqueue(new Callback<HashMap<Integer, String>>()
    {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          if (responseCode == 0)
          {
//            progressDialog.dismiss();
            Log.d("SUCCESS", "SUCCESS");
            Toast.makeText(SalesActivity.this, responseMessage, Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(SalesActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  private double formatTotalPrice()
  {
    String originalString = salesProductTotal.getText().toString();

    if (originalString.contains(",") || originalString.contains("."))
    {
      originalString = originalString.replaceAll("[,.]", "");
    }
    Long longval = Long.parseLong(originalString);
    double clearValue = Double.parseDouble(longval.toString());
    return clearValue;
  }
}
