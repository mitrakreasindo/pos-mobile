package com.mitrakreasindo.pos.main.closecash;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.mitrakreasindo.pos.model.Viewclosedcash;
import com.mitrakreasindo.pos.service.ClosedCashService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

  private CloseCashListAdapter closeCashListAdapter;
  private List<Viewclosedcash> viewclosedcashList;
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_close_cash);
    ButterKnife.bind(this);

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

    Log.d("CLOSECASH_ID", IDs.getLoginCloseCashID());

    downloadClosedCashByMoney(SharedPreferenceEditor.LoadPreferences(this, "Company Code", ""),
      IDs.getLoginCloseCashID(), EventCode.EVENT_CLOSECASH_TRANSACTION_GET);
  }

  public static String GetToday()
  {
    Date presentTime_Date = Calendar.getInstance().getTime();
    return dateFormat.format(presentTime_Date);
  }

  public void downloadClosedCashByMoney(String kodeMerchant, String moneyID, final int id)
  {
    ClosedCashService service = ClientService.createService().create(ClosedCashService.class);
    final Call<List<Viewclosedcash>> call = service.getClosedCashByMoney(kodeMerchant, moneyID);
    call.enqueue(new Callback<List<Viewclosedcash>>()
    {
      @Override
      public void onResponse(Call<List<Viewclosedcash>> call, Response<List<Viewclosedcash>> response)
      {
        List<Viewclosedcash> list = response.body();
        viewclosedcashList = list;

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<List<Viewclosedcash>> call, Throwable t)
      {
        Toast.makeText(CloseCashActivity.this, getString(R.string.error_webservice), Toast.LENGTH_LONG).show();
      }
    });
  }

  @NonNull
  private String CalculateTotalPayment()
  {
    double total = 0.0;
    for (int i=0; i<viewclosedcashList.size(); i++)
    {
      total += viewclosedcashList.get(i).getTotalAllPayment();
    }
    return String.valueOf(total);
  }

  @NonNull
  private String CalculateTotalCash()
  {
    double total = 0.0;
    for (int i=0; i<viewclosedcashList.size(); i++)
    {
      total += viewclosedcashList.get(i).getTotalCashPayment();
    }
    return String.valueOf(total);
  }

  @NonNull
  private String CalculateTotalUnits()
  {
    double total = 0.0;
    for (int i=0; i<viewclosedcashList.size(); i++)
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
        }
        break;
    }
  }
}
