package com.mitrakreasindo.pos.main.sales.payment;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.main.sales.SalesService;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;
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
import com.mitrakreasindo.pos.model.Viewpayments;
import com.mitrakreasindo.pos.model.Viewreceipts;
import com.mitrakreasindo.pos.model.Viewsales;
import com.mitrakreasindo.pos.model.Viewsalesitems;
import com.mitrakreasindo.pos.model.Viewstockdiary;
import com.mitrakreasindo.pos.model.Viewtaxlines;

import java.text.Collator;
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
  TextView salesPaymentTotal;
  @BindView(R.id.btn_payment_confirm)
  Button btnPaymentConfirm;

  private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

  String example = "Convert Java String";
  byte[] bytes = example.getBytes();

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

    }

    final ClosedCash closedCash = new ClosedCash();
    if (IDs.getLoginCloseCashID().equals(""))
      closedCash.setMoney(UUID.randomUUID().toString());
    else
      closedCash.setMoney(IDs.getLoginCloseCashID());

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
    sales.setId(receipt.getId());
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
        if (s.length() > 0 && edittextPaymentMoney.getText().toString().length() > 0)
        {

          if (s.length() > 0 && edittextPaymentMoney.getText().toString().length() > 0)
          {

          }
          else
          {
            edittextPaymentMoney.setText("0");
          }

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

      }
    });

    btnPaymentConfirm.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if (paymentProductListAdapter.grandTotal() <= Double.parseDouble(edittextPaymentMoney.getText().toString()))
        {
          final AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(PaymentActivity.this);
          confirmationDialog.setTitle("Change money");
          confirmationDialog.setMessage(decimalFormat.format(
            Double.parseDouble(edittextPaymentMoney.getText().toString()) - paymentProductListAdapter.grandTotal()
          ));
          postSales();

          confirmationDialog.setPositiveButton("Finish", new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              SalesActivity.sActivity.finish();
              finish();
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

    customer = new Customer();
    customer.setId(null);

    people = new People();
    people.setId("1111111");

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
    payment.setTotal(paymentProductListAdapter.grandTotal());
    payment.setTransid("tesssssss");
    payment.setNotes("test notes");
    payment.setTendered(Double.parseDouble(edittextPaymentMoney.getText().toString()));
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

  public void postSales()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setCancelable(false);
    progressDialog.setMessage("Loading");
    progressDialog.show();

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
            progressDialog.dismiss();
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
