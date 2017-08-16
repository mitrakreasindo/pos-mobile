package com.mitrakreasindo.pos.main.closecash;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.closecash.controller.CloseCashListAdapter;
import com.mitrakreasindo.pos.model.ClosedCash;
import com.mitrakreasindo.pos.model.Viewclosedcash;
import com.mitrakreasindo.pos.service.ClosedCashService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseCashActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_transaction)
  RecyclerView listTransaction;
  @BindView(R.id.open_cash_date)
  TextView openCashDate;
  @BindView(R.id.closecash_total_payment)
  TextView closecashTotalPayment;
  @BindView(R.id.closecash_total_cash)
  TextView closecashTotalCash;
  @BindView(R.id.closecash_total_units)
  TextView closecashTotalUnits;

  private String companyCode, moneyID, userID;
  private CloseCashListAdapter closeCashListAdapter;
  private List<Viewclosedcash> viewclosedcashList;
  private ClosedCashService service;
  private ProgressDialog progressDialog;
  private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_close_cash);
    ButterKnife.bind(this);

    companyCode = SharedPreferenceEditor.LoadPreferences(this, "Company Code", "");
    moneyID = IDs.getLoginCloseCashID();
    userID = IDs.getLoginUser();

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });
    closeCashListAdapter = new CloseCashListAdapter(this, new ArrayList<Viewclosedcash>());

    EventBus.getDefault().register(this);

    listTransaction.setHasFixedSize(true);
    listTransaction.setAdapter(closeCashListAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listTransaction.setLayoutManager(layoutManager);

    openCashDate.setText(GetToday());
    service = ClientService.createService().create(ClosedCashService.class);

    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(this.getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

    DownloadClosedCashByMoney(companyCode, moneyID, EventCode.EVENT_CLOSECASH_TRANSACTION_GET);
  }

  public static String GetToday()
  {
    Date presentTime_Date = Calendar.getInstance().getTime();
    return dateFormat.format(presentTime_Date);
  }

  public void ConfirmCloseCash(View view)
  {
    new AlertDialog.Builder(this)
      .setIcon(android.R.drawable.ic_dialog_info)
      .setTitle(R.string.closecash_question)
      .setMessage(R.string.closecash_question_message)
      .setPositiveButton
        (
          R.string.yes, new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              progressDialog.setMessage(CloseCashActivity.this.getString(R.string.progress_message));
              progressDialog.setCancelable(false);
              progressDialog.show();
              PostCloseCash(companyCode, PrepareData(Calendar.getInstance().getTime(), userID, moneyID, IDs.SITE_GUID));
            }
          }
        )
      .setNegativeButton(R.string.no, null)
      .show();
  }

  public void CancelCloseCash(View view)
  {
    finish();
  }

  private ClosedCash PrepareData(Date dateEnd, String userID, String moneyID, String siteGUID)
  {
    ClosedCash closedCash = new ClosedCash();
    closedCash.setDateend(dateEnd);
    closedCash.setDatestart(null);
    closedCash.setHost(userID);
    closedCash.setHostsequence(0);
    closedCash.setMoney(moneyID);
    closedCash.setNosales(0);
    closedCash.setSflag(true);
    closedCash.setSiteguid(siteGUID);

    return closedCash;
  }

  private void DownloadClosedCashByMoney(String kodeMerchant, String moneyID, final int id)
  {
    Call<List<Viewclosedcash>> call = service.getClosedCashByMoney(kodeMerchant, moneyID);
    call.enqueue(new Callback<List<Viewclosedcash>>()
    {
      @Override
      public void onResponse(Call<List<Viewclosedcash>> call, Response<List<Viewclosedcash>> response)
      {
        viewclosedcashList = response.body();

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<List<Viewclosedcash>> call, Throwable t)
      {
        Toast.makeText(CloseCashActivity.this, getString(R.string.error_webservice),
          Toast.LENGTH_LONG).show();
      }
    });
  }

  private void PostCloseCash(String kodeMerchant, ClosedCash closedCash)
  {
    Call<HashMap<Integer, String>> call = service.postClosedCash(kodeMerchant, closedCash);
    call.enqueue(new Callback<HashMap<Integer, String>>()
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
          Log.d("RESPONSE WEBSERVICE: ", String.valueOf(responseCode) + responseMessage);

          if (responseCode == 0)
          {
            progressDialog.dismiss();
            IDs.setLoginCloseCashID(null);
            finish();
          }
          Toast.makeText(CloseCashActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        progressDialog.dismiss();
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(CloseCashActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  @NonNull
  private String CalculateTotalPayment()
  {
    double total = 0.0;
    for (int i = 0; i < viewclosedcashList.size(); i++)
    {
      total += viewclosedcashList.get(i).getTotalAllPayment();
    }
    return decimalFormat.format(total);
  }

  @NonNull
  private String CalculateTotalCash()
  {
    double total = 0.0;
    for (int i = 0; i < viewclosedcashList.size(); i++)
    {
      total += viewclosedcashList.get(i).getTotalCashPayment();
    }
    return decimalFormat.format(total);
  }

  @NonNull
  private String CalculateTotalUnits()
  {
    double total = 0.0;
    for (int i = 0; i < viewclosedcashList.size(); i++)
    {
      total += viewclosedcashList.get(i).getTotalSoldUnits();
    }
    return String.valueOf(total);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(Event event)
  {
    switch (event.getId())
    {
      case EventCode.EVENT_CLOSECASH_TRANSACTION_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          closeCashListAdapter.clear();
          closeCashListAdapter.addCloseCashTransaction(viewclosedcashList);

          openCashDate.setText(dateFormat.format(viewclosedcashList.get(0).getDateStart()));
          closecashTotalPayment.setText(CalculateTotalPayment());
          closecashTotalCash.setText(CalculateTotalCash());
          closecashTotalUnits.setText(CalculateTotalUnits());

          progressDialog.dismiss();
        }
        break;
    }
  }
}
