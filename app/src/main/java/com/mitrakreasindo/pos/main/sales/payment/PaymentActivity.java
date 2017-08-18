package com.mitrakreasindo.pos.main.sales.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.common.Wireless.BluetoothService;
import com.mitrakreasindo.pos.common.Wireless.PrintReceipt;
import com.mitrakreasindo.pos.common.Wireless.Wireless_Activity;
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
import com.mitrakreasindo.pos.service.SalesService;

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

import static com.mitrakreasindo.pos.common.Wireless.Wireless_Activity.mService;

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

  private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

  DefaultHelper defaultHelper = new DefaultHelper();

  String example = "Convert Java String";
  byte[] bytes = example.getBytes();

  private String originalString;
  private Long longval;

  private Bundle bundle;
  private String salesId;

  private TableSalesItemHelper tableSalesItemHelper;

  private PaymentProductListAdapter paymentProductListAdapter;

  private List<SalesItem> salesItemList;

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
  private String kodeMerchant;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);
    ButterKnife.bind(this);

    salesService = ClientService.createService().create(SalesService.class);

    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");

    tableSalesItemHelper = new TableSalesItemHelper(this);

    bundle = getIntent().getExtras();
    if (bundle != null)
    {

      salesId = bundle.getString("salesid");
      Log.d("SALES_ID", salesId);
      salesItemList = tableSalesItemHelper.getSalesItems(salesId);
      viewsalesitems.containsAll(salesItemList);

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
    people.setId("1111111");

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
    receipt.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    receipt.setSflag(true);

    sales = new Sales();
    sales.setId(salesId);
    sales.setSalesnum(1);
    sales.setSalestype(1);
    sales.setStatus(1);
    sales.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    sales.setSflag(true);
    sales.setCustomer(customer);
    sales.setPerson(people);
    sales.setReceipt(receipt);


    if (salesItemList != null)
    {
      paymentProductListAdapter = new PaymentProductListAdapter(this, salesItemList);
    }


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

    edittextPaymentMoney.setText("0");
    edittextPaymentMoney.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
        if (s.length() == 0 && edittextPaymentMoney.getText().toString().length() == 0)
          edittextPaymentMoney.setText("0");
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

          if (originalString.contains(",") || originalString.contains("."))
          {
            originalString = originalString.replaceAll("[,.]", "");
          }
          Long longval = Long.parseLong(originalString);

          DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
          edittextPaymentMoney.setText(decimalFormat.format(longval));
          edittextPaymentMoney.setSelection(edittextPaymentMoney.getText().length());
        } catch (Exception e)
        {
          e.printStackTrace();
        }
        edittextPaymentMoney.addTextChangedListener(this);
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
          final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
          confirmationDialog.setTitle(R.string.printOptions);
          confirmationDialog.setMessage(R.string.Qs_print);

          confirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              if (mService == null) {
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
                  final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
                  confirmationDialog.setTitle("Change money");
                  confirmationDialog.setMessage(decimalFormat.format(
                    Double.parseDouble(edittextPaymentMoney.getText().toString()) - paymentProductListAdapter.grandTotal()
                  ));
                  confirmationDialog.setCancelable(false);
                  confirmationDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener()
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
                      PrintReceipt.printReceipt(PaymentActivity.this,paymentProductListAdapter.getAllTickets(),paymentProductListAdapter.grandTotal(),Double.parseDouble(edittextPaymentMoney.getText().toString()));
                      Log.d("Grandtotal: ",Double.toString(paymentProductListAdapter.grandTotal()));
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
          });

          confirmationDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
              confirmationDialog.setTitle("Change money");
              confirmationDialog.setMessage(decimalFormat.format(
                formatTotalPrice() - paymentProductListAdapter.grandTotal()
              ));
              confirmationDialog.setCancelable(false);
              confirmationDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener()
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
                  Log.d("TAX: ",salesPack.getTaxlines().toString());

                  TableSalesHelper tableSalesHelper = new TableSalesHelper(PaymentActivity.this);
                  tableSalesHelper.open();
                  tableSalesHelper.insertSales(sales);
                  tableSalesHelper.close();

                  postSales();
                  SalesActivity.sActivity.finish();
                  finish();
                }
              });
              confirmationDialog.show();
            }
          });
          confirmationDialog.show();
        }
        else
        {
          Toast.makeText(PaymentActivity.this, "Money is not enough", Toast.LENGTH_LONG).show();
        }

      }
    });

    salesPaymentTotal.setText(decimalFormat.format(paymentProductListAdapter.grandTotal()));

  }

  private double formatTotalPrice()
  {
    originalString = edittextPaymentMoney.getText().toString();

    if (originalString.contains(",") || originalString.contains("."))
    {
      originalString = originalString.replaceAll("[,.]", "");
    }
    Long longval = Long.parseLong(originalString);
    double clearValue = Double.parseDouble(longval.toString());
    return clearValue;

  }

  @Override
  public void onBackPressed()
  {
    final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
    confirmationDialog.setTitle("Cancel Payment ?");
    confirmationDialog.setMessage("Are you sure want to cancel the payment ?");
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
    payment.setPayment("cash");
    payment.setTotal(paymentProductListAdapter.grandTotal());
    payment.setTransid("tesssssss");
    payment.setNotes("test notes");
//    payment.setTendered(Double.parseDouble(edittextPaymentMoney.getText().toString()));
    payment.setTendered(formatTotalPrice());
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

    viewtaxline = new ViewTaxLine();
    viewtaxline.setId(taxLine.getId());
    viewtaxline.setReceipt(taxLine.getReceipt().getId());
    viewtaxline.setTaxid(taxLine.getTaxid().getId());
    viewtaxline.setBase(taxLine.getBase());
    viewtaxline.setAmount(taxLine.getAmount());
    viewtaxline.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewtaxline.setSflag(taxLine.getSflag());
    viewtaxline.setTaxName(null);

    viewsales = new ViewSale();
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
    viewreceipt.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
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
    viewpayment.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    viewpayment.setSflag(payment.getSflag());
    viewpayment.setDatenew(defaultHelper.dateFormat(new Date()));

    for (int i = 0; i < salesItemList.size(); i++)
    {
      product = salesItemList.get(i).getProduct();

      salesItem = salesItemList.get(i);
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
      viewsalesitem.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
      viewsalesitem.setSflag(sales.getSflag());
      viewsalesitem.setProductName(null);
      viewsalesitem.setTaxName(null);
      viewsalesitem.setRate(null);
      viewsalesitems.add(viewsalesitem);

      stockDiary = new StockDiary();
      stockDiary.setId(UUID.randomUUID().toString());
      stockDiary.setReason(-1);
      stockDiary.setUnits(1);
      stockDiary.setPrice(salesItem.getProduct().getPricesell());
      stockDiary.setAppuser(IDs.getLoginUser());
      stockDiary.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
      stockDiary.setSflag(true);
      stockDiary.setAttributesetinstanceId(null);
      stockDiary.setLocation(location);
      stockDiary.setProduct(salesItem.getProduct());

      viewstockdiary = new ViewStockDiary();
      viewstockdiary.setId(stockDiary.getId());
      viewstockdiary.setProduct(stockDiary.getProduct().getId());
      viewstockdiary.setDatenew(defaultHelper.dateFormat(new Date()));
      viewstockdiary.setReason(stockDiary.getReason());
      viewstockdiary.setUnits(stockDiary.getUnits());
      viewstockdiary.setPrice(stockDiary.getPrice());
      viewstockdiary.setAppuser(stockDiary.getAppuser());
      viewstockdiary.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
      viewstockdiary.setSflag(true);
      viewstockdiary.setAttributesetinstanceId(null);
      viewstockdiary.setLocation("0");
      viewstockdiaries.add(viewstockdiary);
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
//            progressDialog.dismiss();
            Log.d("SUCCESS", "SSSSUUCCCEESSSS");
            Toast.makeText(PaymentActivity.this, "success", Toast.LENGTH_LONG).show();
          }
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {

      }
    });
  }

}
