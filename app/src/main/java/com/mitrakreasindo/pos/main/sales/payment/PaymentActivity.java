package com.mitrakreasindo.pos.main.sales.payment;

import android.app.LauncherActivity;
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

import com.mitrakreasindo.pos.common.TableHelper.TableSalesItemHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.main.sales.adapter.SalesListAdapter;
import com.mitrakreasindo.pos.main.sales.payment.adapter.PaymentProductListAdapter;
import com.mitrakreasindo.pos.model.Sales;
import com.mitrakreasindo.pos.model.SalesItem;

import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

  private Bundle bundle;
  private String salesId;

  private TableSalesItemHelper tableSalesItemHelper;

  private PaymentProductListAdapter paymentProductListAdapter;

  private List<SalesItem> salesItemList;

  private SalesItem salesItem;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);
    ButterKnife.bind(this);

    tableSalesItemHelper = new TableSalesItemHelper(this);


    bundle = getIntent().getExtras();
    if (bundle != null)
    {

      salesId = bundle.getString("salesid");
      Log.d("SALES_ID", salesId);
      salesItemList = tableSalesItemHelper.getSalesItems(salesId);

    }

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
}
