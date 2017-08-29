package com.mitrakreasindo.pos.main.sales.payment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.centerm.smartpos.aidl.printer.AidlPrinter;
import com.centerm.smartpos.aidl.printer.AidlPrinterStateChangeListener;
import com.centerm.smartpos.aidl.printer.PrintDataObject;
import com.centerm.smartpos.aidl.sys.AidlDeviceManager;
import com.centerm.smartpos.constant.Constant;
import com.centerm.smartpos.util.LogUtil;
import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.common.EmbededPrinter.Print;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.common.WirelessPrinter.BluetoothService;
import com.mitrakreasindo.pos.common.WirelessPrinter.PrintReceipt;
import com.mitrakreasindo.pos.common.WirelessPrinter.Wireless_Activity;
import com.mitrakreasindo.pos.main.MainActivity;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.main.sales.payment.adapter.PaymentProductListAdapter;
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
import com.mitrakreasindo.pos.service.DiaryStockService;
import com.mitrakreasindo.pos.service.SalesService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

import static com.mitrakreasindo.pos.common.WirelessPrinter.Wireless_Activity.mService;

/**
 * Created by lisa on 01/08/17.
 */

public class PaymentActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.txt_payment_date)
  TextView txtPaymentDate;
  @BindView(R.id.edittext_payment_money)
  EditText edittextPaymentMoney;
  @BindView(R.id.list_payment_product)
  RecyclerView listPaymentProduct;
  @BindView(R.id.sales_payment_total)
  public TextView salesPaymentTotal;
  @BindView(R.id.btn_payment_confirm)
  Button btnPaymentConfirm;
  @BindView(R.id.textview_money)
  TextView textviewMoney;
  
  private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

  DefaultHelper defaultHelper = new DefaultHelper();

  String example = "Convert Java String";
  byte[] bytes = example.getBytes();

  private String originalString;
  private Long longval;
  private double changeMoney;
  private Bundle bundle;
  private String salesId;
  private boolean retur = false;
  private static boolean PrinterMessage;
  private String consumerName;
  
  private TableSalesItemHelper tableSalesItemHelper;
  private DiaryStockService diaryStockService;
  private PaymentProductListAdapter paymentProductListAdapter;

  private List<SalesItem> salesItemList;
  private List<Product> products = new ArrayList<>();
  private List<Integer> reason = new ArrayList<>();
  private EditText editTextExtraSalesInfo;
  
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
  private Tax tax;
  private Location location;

  private ViewSale viewsales;
  private ViewSalesItem viewsalesitem;
  private ViewReceipt viewreceipt;
  private ViewPayment viewpayment;
  private ViewStockDiary viewstockdiary;
  private ViewTaxLine viewtaxline;

  private Collection<ViewSalesItem> viewsalesitems = new ArrayList<>();
  private Collection<ViewPayment> viewpayments = new ArrayList<>();
  private Collection<ViewStockDiary> viewstockdiaries = new ArrayList<>();
  private Collection<ViewTaxLine> viewtaxlines = new ArrayList<>();

  private SalesService salesService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private TableProductHelper tableProductHelper;
  private String kodeMerchant;
  
  private AidlPrinter printDev = null;
  public AidlDeviceManager manager = null;
  private AidlPrinterStateChangeListener callback = new PrinterCallback();
  private Context mcontext;
  
  private TablePeopleHelper tablePeopleHelper;

  public class PrinterCallback extends AidlPrinterStateChangeListener.Stub {
    boolean message = true;
    @Override
    public void onPrintError(int arg0) throws RemoteException
    {
      message = false;
      GetMessage(message);
      showMessage(PaymentActivity.this, getString(R.string.text_suddenly) + arg0);
      return;
    }

    @Override
    public void onPrintFinish() throws RemoteException
    {
      message = true;
      GetMessage(message);
      showMessage(PaymentActivity.this, getString(R.string.text_PrintFinished));
    }

    @Override
    public void onPrintOutOfPaper() throws RemoteException
    {
      message = false;
      GetMessage(message);
      showMessage(PaymentActivity.this, getString(R.string.text_outOfPaper));
      return;
    }
  }
  
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);
    ButterKnife.bind(this);

    salesService = ClientService.createService().create(SalesService.class);
    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");
    tableProductHelper = new TableProductHelper(this);
    tableSalesItemHelper = new TableSalesItemHelper(this);
    tablePeopleHelper = new TablePeopleHelper(this);
    editTextExtraSalesInfo = new EditText(this);
    bundle = getIntent().getExtras();
    if (bundle != null)
    {
      salesId = bundle.getString("sales_id");
      retur = bundle.getBoolean("retur");
      if (retur)
      {
        edittextPaymentMoney.setVisibility(View.GONE);
        textviewMoney.setVisibility(View.GONE);
        edittextPaymentMoney.setText("0,0");
        toolbar.setTitle(R.string.returns);
      }
      else
      {
        toolbar.setTitle(R.string.payments);
        edittextPaymentMoney.setText("0");
        edittextPaymentMoney.addTextChangedListener(new TextWatcher()
        {
          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count)
          {
            if (s.length() > 0 && edittextPaymentMoney.getText().toString().length() > 0)
            {
        
            }
            else
            {
              edittextPaymentMoney.setText("0");
            }
      
          }
    
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after)
          {
      
          }
    
          @Override
          public void afterTextChanged(Editable s)
          {
      
            try
            {
              edittextPaymentMoney.removeTextChangedListener(this);
              String originalString = s.toString();
        
              if (originalString.contains(","))
              {
                originalString = originalString.replaceAll(",", "");
              }
              Long longval = Long.parseLong(originalString);
        
              DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
              edittextPaymentMoney.setText(decimalFormat.format(longval));
              edittextPaymentMoney.setSelection(edittextPaymentMoney.getText().length());
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
      
            edittextPaymentMoney.addTextChangedListener(this);
      
          }
        });
      }
      Log.d("SALES_ID", salesId);
      salesItemList = tableSalesItemHelper.getSalesItems(salesId);
      if (salesItemList.size() > 0)
        viewsalesitems.containsAll(salesItemList);
      else
      {
        tableSalesItemHelper.downloadSalesItems(kodeMerchant, salesId, EventCode.EVENT_VIEW_SALESITEMS_GET);
      }
//      product = salesItemList.get(1).getProduct();
    }

    final ClosedCash closedCash = new ClosedCash();
    if (IDs.getLoginCloseCashID() == null)
    {
      String id = UUID.randomUUID().toString();
      closedCash.setMoney(id);
      IDs.setLoginCloseCashID(id);
    }
    else
      closedCash.setMoney(IDs.getLoginCloseCashID());

    customer = new Customer();
    customer.setId(null);

    people = new People();
    people.setId(tablePeopleHelper.getPeopleID(IDs.getLoginUser()));

    location = new Location();
    location.setId(UUID.randomUUID().toString());

    tax = new Tax();
    tax.setId("001");

    receipt = new Receipt();
    receipt.setId(salesId);
    receipt.setAttributes(null);
    receipt.setSales(sales);
    receipt.setMoney(closedCash);
    receipt.setDatenew(new Date());
    receipt.setPerson(IDs.getLoginUser());
    receipt.setSiteguid(IDs.SITE_GUID);
    receipt.setSflag(true);

    sales = new Sales();
    sales.setId(salesId);
    sales.setSalesnum(1);
    sales.setSalestype(1);
    sales.setStatus(1);
    sales.setSiteguid(IDs.SITE_GUID);
    sales.setSflag(true);
    sales.setCustomer(customer);
    sales.setPerson(people);
    sales.setReceipt(receipt);


    if (salesItemList != null)
    {
      paymentProductListAdapter = new PaymentProductListAdapter(this, salesItemList);
    }

    txtPaymentDate.setText(DefaultHelper.dateOnlyFormat(new Date()));

    listPaymentProduct.setAdapter(paymentProductListAdapter);
    listPaymentProduct.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listPaymentProduct.setLayoutManager(layoutManager);
    listPaymentProduct.setItemAnimator(new DefaultItemAnimator());

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });
    btnPaymentConfirm.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        
    Log.d("price", String.valueOf(formatTotalPrice()));
    if (paymentProductListAdapter.grandTotal() <= formatTotalPrice())
    {
      if(editTextExtraSalesInfo.getParent() != null)
        ((ViewGroup)editTextExtraSalesInfo.getParent()).removeView(editTextExtraSalesInfo);
      
      editTextExtraSalesInfo.setHint(R.string.hint_sales_extra_info);
      final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
      confirmationDialog.setTitle(R.string.additional_sales_info);
      confirmationDialog.setView(editTextExtraSalesInfo);
      confirmationDialog.setCancelable(false);
      
      confirmationDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
          Log.d( "name: ", String.valueOf(editTextExtraSalesInfo.getText().length()));
          if (editTextExtraSalesInfo.getText().length() == 0)
          {
            consumerName = "-";
          }
          else
          {
            consumerName = editTextExtraSalesInfo.getText().toString();
          }
          Log.d("consumerName1: ",consumerName);
          final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
          confirmationDialog.setTitle(R.string.printOptions);
          confirmationDialog.setMessage(R.string.Qs_print);
          confirmationDialog.setCancelable(false);
          confirmationDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              if (manager != null)
              {
                cetak();
              }
              else
              {
                if (mService == null)
                {
                  Toast.makeText(PaymentActivity.this, R.string.not_connected, Toast.LENGTH_SHORT)
                    .show();
                  Intent intent = new Intent(PaymentActivity.this, Wireless_Activity.class);
                  startActivity(intent);
                  return;
                }
                else
                {
                  if (mService.getState() == BluetoothService.STATE_CONNECTED)
                  {
                    PrintReceipt.printReceipt(PaymentActivity.this, paymentProductListAdapter.getAllTickets(), paymentProductListAdapter.grandTotal(),
                      Double.parseDouble(edittextPaymentMoney.getText().toString().replaceAll(",", "")),consumerName);
                    final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
                    confirmationDialog.setTitle(R.string.text_Change);
                    changeMoney();
                    confirmationDialog.setMessage(decimalFormat.format(changeMoney));
                    confirmationDialog.setCancelable(false);
                    confirmationDialog.setPositiveButton(R.string.text_finish, new DialogInterface.OnClickListener()
                    {
                      @Override
                      public void onClick(DialogInterface dialog, int which)
                      {
                        data();
                        salesPack = new SalesPack();
                        salesPack.setSales(viewsales);
                        salesPack.setReceipts(viewreceipt);
                        salesPack.setSalesItems(viewsalesitems);
                        salesPack.setPayments(viewpayments);
                        salesPack.setStockdiary(viewstockdiaries);
                        salesPack.setTaxlines(viewtaxlines);
                
                        TableSalesHelper tableSalesHelper = new TableSalesHelper(PaymentActivity.this);
                        tableSalesHelper.open();
                        tableSalesHelper.insertSales(sales);
                        tableSalesHelper.close();
                
                        postSales();
                        SalesActivity.sActivity.finish();
                        finish();
                      }
                    });
                    confirmationDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                    {
                      @Override
                      public void onClick(DialogInterface dialog, int which)
                      {
                        dialog.dismiss();
                      }
                    });
                    confirmationDialog.show();
                    return;
                  }
                  else
                  {
                    Toast.makeText(PaymentActivity.this, R.string.not_connected, Toast.LENGTH_SHORT)
                      .show();
                    Intent intent = new Intent(PaymentActivity.this, Wireless_Activity.class);
                    startActivity(intent);
                    return;
                  }
                }
              }
            }
          });
  
          confirmationDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
              confirmationDialog.setTitle(R.string.text_Change);
              changeMoney();
              confirmationDialog.setMessage(decimalFormat.format(changeMoney));
              confirmationDialog.setCancelable(false);
              confirmationDialog.setPositiveButton(R.string.text_finish, new DialogInterface.OnClickListener()
              {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
          
                  data();
                  salesPack = new SalesPack();
                  salesPack.setSales(viewsales);
                  salesPack.setReceipts(viewreceipt);
                  salesPack.setSalesItems(viewsalesitems);
                  salesPack.setPayments(viewpayments);
                  salesPack.setStockdiary(viewstockdiaries);
                  salesPack.setTaxlines(viewtaxlines);
                  Log.d("TAX: ", salesPack.getTaxlines().toString());
          
                  TableSalesHelper tableSalesHelper = new TableSalesHelper(PaymentActivity.this);
                  tableSalesHelper.open();
                  tableSalesHelper.insertSales(sales);
                  tableSalesHelper.close();
          
                  postSales();
                  if (SalesActivity.sActivity != null)
                    SalesActivity.sActivity.finish();
                  finish();
                }
              });
              confirmationDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
              {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                  dialog.dismiss();
                }
              });
              confirmationDialog.show();
            }
          });
          confirmationDialog.show();
        }
      });
      confirmationDialog.show();
    }
    else
    {
      Toast.makeText(PaymentActivity.this, R.string.text_notEnough, Toast.LENGTH_LONG).show();
    }
        
      }
    });

    salesPaymentTotal.setText(decimalFormat.format(paymentProductListAdapter.grandTotal()));

  }

  private double formatTotalPrice()
  {
    double clearValue = 0.0;
    if (retur)
    {
      clearValue = paymentProductListAdapter.grandTotal();
    }
    else
    {
      originalString = edittextPaymentMoney.getText().toString();
      if (originalString.contains(","))
      {
        originalString = originalString.replaceAll(",", "");
      }
      Long longval = Long.parseLong(originalString);
      clearValue = Double.parseDouble(longval.toString());
    }
    return clearValue;
  }

  @Override
  public void onBackPressed()
  {
    final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
    confirmationDialog.setTitle(R.string.text_cancelPayment);
    confirmationDialog.setMessage(R.string.Qs_payment);
    confirmationDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {

        TableSalesItemHelper tableSalesItemHelper = new TableSalesItemHelper(PaymentActivity.this);
        tableSalesItemHelper.open();
        tableSalesItemHelper.deleteSalesItem(salesItemList);
        tableSalesItemHelper.close();
        
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

  public void data()
  {

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

//    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    String date = formater.format(new Date());

    
    payment = new Payment();
    payment.setId(UUID.randomUUID().toString());
    String Payment = "";
    double Total = 0.0;
    double Tendered = 0.0;
    if (retur)
    {
      Payment = "returns";
      Total = -paymentProductListAdapter.grandTotal();
      Tendered = Total;
    }
    else
    {
      Payment = "cash";
      Total = paymentProductListAdapter.grandTotal();
      Tendered = formatTotalPrice();
    }
    payment.setPayment(Payment);
    payment.setTotal(Total);
    payment.setTransid("tesssssss");
    payment.setNotes(editTextExtraSalesInfo.getText().toString());
//    payment.setTendered(Double.parseDouble(edittextPaymentMoney.getText().toString()));
    payment.setTendered(Tendered);
    payment.setCardname("CardName");
    payment.setReturnmsg(null);
    payment.setSiteguid(IDs.SITE_GUID);
    payment.setSflag(true);
    payment.setReceipt(receipt);

    taxLine = new TaxLine();
    taxLine.setId(UUID.randomUUID().toString());
    taxLine.setBase(1000);
    taxLine.setAmount(100);
    taxLine.setSiteguid(IDs.SITE_GUID);
    taxLine.setSflag(true);
    taxLine.setReceipt(receipt);
    taxLine.setTaxid(tax);

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
    viewsales.setPerson(people.getId());
    viewsales.setCustomer(null);
    viewsales.setSalestype(sales.getSalestype());
    viewsales.setStatus(viewsales.getStatus());
    viewsales.setSiteguid(IDs.SITE_GUID);
    viewsales.setSflag(sales.getSflag());
    viewsales.setCustomerName(null);
    viewsales.setPersonName(null);
    viewsales.setDatenew(defaultHelper.dateFormat(new Date()));


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

    viewreceipt.setDatenew(defaultHelper.dateFormat(new Date()));
    viewreceipt.setPerson(receipt.getPerson());
    viewreceipt.setAttributes(null);
    viewreceipt.setSiteguid(IDs.SITE_GUID);
    viewreceipt.setSflag(true);
    viewreceipt.setHost("");

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
    viewpayment.setDatenew(defaultHelper.dateFormat(new Date()));

    for (int i = 0; i < salesItemList.size(); i++)
    {
      product = salesItemList.get(i).getProduct();
      salesItem = salesItemList.get(i);
      product.setStockunits(salesItem.getUnits());
      
      Log.d("productId: ",product.getId());
      Log.d("ProductStock: ", String.valueOf(product.getStockunits()));
      Log.d("location: ", String.valueOf(location.getId()));
      
      salesItem.setProduct(product);
      salesItem.setAttributes(bytes);
      salesItem.setUnits(salesItemList.get(i).getUnits());
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
      if (retur)
      {
        stockDiary.setReason(1);
      }
      else
      {
        stockDiary.setReason(-1);
      }
      stockDiary.setUnits(salesItem.getUnits());
      stockDiary.setPrice(salesItem.getProduct().getPricesell());
      stockDiary.setAppuser(IDs.getLoginUser());
      stockDiary.setSiteguid(IDs.SITE_GUID);
      stockDiary.setSflag(true);
      stockDiary.setAttributesetinstanceId(null);
      stockDiary.setLocation(location);
      stockDiary.setProduct(product);

      viewstockdiary = new ViewStockDiary();
      viewstockdiary.setId(stockDiary.getId());
      viewstockdiary.setProduct(stockDiary.getProduct().getId());
      viewstockdiary.setDatenew(defaultHelper.dateFormat(new Date()));
      viewstockdiary.setReason(stockDiary.getReason());
      viewstockdiary.setUnits(stockDiary.getUnits());
      viewstockdiary.setPrice(stockDiary.getPrice());
      viewstockdiary.setAppuser(stockDiary.getAppuser());
      viewstockdiary.setSiteguid(IDs.SITE_GUID);
      viewstockdiary.setSflag(true);
      viewstockdiary.setAttributesetinstanceId(null);
      viewstockdiary.setLocation("0");
      viewstockdiaries.add(viewstockdiary);
      
      int reasons = stockDiary.getReason();
      product = salesItemList.get(i).getProduct();
      products.add(product);
      reason.add(reasons);
    }

    viewtaxlines.add(viewtaxline);
    viewpayments.add(viewpayment);

  }

  public void postSales()
  {
//    final ProgressDialog progressDialog = new ProgressDialog(this);
//    progressDialog.setCancelable(false);
//    progressDialog.setMessage("Loading");
//    progressDialog.show();

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
            TableProductHelper tableProductHelper = new TableProductHelper(PaymentActivity.this);
            tableProductHelper.open();
            tableProductHelper.updateProductStockBylist(products, reason);
            tableProductHelper.close();
//            progressDialog.dismiss();
            Log.d("SUCCESS", "SUCCESS");
            Toast.makeText(PaymentActivity.this, "success", Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(PaymentActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(Event event)
  {
    switch (event.getId())
    {
      case EventCode.EVENT_VIEW_SALESITEMS_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          salesItemList = tableSalesItemHelper.getSalesItems(salesId);
          viewsalesitems.containsAll(salesItemList);
          if (salesItemList != null)
          {
            paymentProductListAdapter.addSalesItem(salesItemList);
          }
        }
        break;
    }
  }

  public void cetak()
  {
    List<PrintDataObject> list = new ArrayList<PrintDataObject>();
    Print.IsiStruk(PaymentActivity.this,paymentProductListAdapter.getAllTickets(),
      list,paymentProductListAdapter.grandTotal(),Double.parseDouble(edittextPaymentMoney.getText().toString().replaceAll(",","")),consumerName);
    try
    {
      this.printDev.printText(list, callback);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    if (!this.getClass().getName().equals(MainActivity.class))
    {
      bindService();
    }
  }
  
  public void bindService()
  {
    Intent intent = new Intent();
    intent.setPackage("com.centerm.smartposservice");
    intent.setAction("com.centerm.smartpos.service.MANAGER_SERVICE");
    bindService(intent, conn, Context.BIND_AUTO_CREATE);
  }
  
  public ServiceConnection conn = new ServiceConnection()
  {
    @Override
    public void onServiceDisconnected(ComponentName name)
    {
      manager = null;
      LogUtil.print("服务绑定失败");
      LogUtil.print("manager = " + manager);
    }
    
    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
      manager = AidlDeviceManager.Stub.asInterface(service);
      LogUtil.print("服务绑定成功");
      LogUtil.print("manager = " + manager);
      if (null != manager)
      {
        onDeviceConnected(manager);
      }
    }
  };

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    if (!this.getClass().getName().equals(MainActivity.class))
    {
      unbindService(conn);
    }
  }

  public void onDeviceConnected(AidlDeviceManager deviceManager)
  {
    try
    {
      printDev = AidlPrinter.Stub.asInterface(deviceManager.getDevice(Constant.DEVICE_TYPE.DEVICE_TYPE_PRINTERDEV));
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }
  
  private Handler handler = new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      // TODO Auto-generated method stub
      super.handleMessage(msg);
      Bundle bundle = msg.getData();
      String msg1 = bundle.getString("msg1");
      Toast.makeText(mcontext, msg1, Toast.LENGTH_SHORT).show();
    }
  };
  
  private Handler handler2 = new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      // TODO Auto-generated method stub
      super.handleMessage(msg);
      Bundle bundle = msg.getData();
      boolean msg1 = bundle.getBoolean("msg1");
      PrinterMessage = msg1;
      Log.d("handleMessage: ", String.valueOf(PrinterMessage));
      if (PrinterMessage)
      {
        final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
        confirmationDialog.setTitle(R.string.text_Change);
        changeMoney();
        confirmationDialog.setMessage(decimalFormat.format(changeMoney));
        confirmationDialog.setCancelable(false);
        confirmationDialog.setPositiveButton(R.string.text_finish, new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
          data();
          salesPack = new SalesPack();
          salesPack.setSales(viewsales);
          salesPack.setReceipts(viewreceipt);
          salesPack.setSalesItems(viewsalesitems);
          salesPack.setPayments(viewpayments);
          salesPack.setStockdiary(viewstockdiaries);
          salesPack.setTaxlines(viewtaxlines);
    
          TableSalesHelper tableSalesHelper = new TableSalesHelper(PaymentActivity.this);
          tableSalesHelper.open();
          tableSalesHelper.insertSales(sales);
          tableSalesHelper.close();
    
          postSales();
          SalesActivity.sActivity.finish();
          finish();
        }
      });
        confirmationDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
            dialog.dismiss();
          }
        });
        confirmationDialog.show();
        return;
      }
    }
  };

  public void GetMessage(final boolean pesan)
  {
    Message a = new Message();
    Bundle bundle = new Bundle();
    bundle.putBoolean("msg1", pesan);
    a.setData(bundle);
    handler2.sendMessage(a);
  }
  
  public void showMessage(Context context, final String msg1)
  {
    Message msg = new Message();
    Bundle bundle = new Bundle();
    bundle.putString("msg1", msg1);
    msg.setData(bundle);
    this.mcontext = context;
    handler.sendMessage(msg);
  }
  
  private double changeMoney()
  {
    double change;
    if(retur)
    {
      change = paymentProductListAdapter.grandTotal();
    }else
    {
      change = Double.parseDouble(edittextPaymentMoney.getText().toString().replaceAll(",", "")) - paymentProductListAdapter.grandTotal();
    }
    this.changeMoney = change;
    return changeMoney;
  }
  
}
