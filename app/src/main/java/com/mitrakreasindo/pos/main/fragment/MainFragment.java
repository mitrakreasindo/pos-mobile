package com.mitrakreasindo.pos.main.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.adapter.PendingTransactionListAdapter;
import com.mitrakreasindo.pos.main.fragment.menu.MasterDataFragment;
import com.mitrakreasindo.pos.main.report.ReportActivity;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.model.ViewPendingTransaction;
import com.mitrakreasindo.pos.service.PendingTransactionService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 15/06/17.
 */

public class MainFragment extends Fragment
{
  private Unbinder unbinder;
  private List<ViewPendingTransaction> viewPendingTransactions;
  private RecyclerView listQueue;
  private PendingTransactionListAdapter pendingTransactionListAdapter;
//  private Queue queue;
  private Button menuSales, menuData, menuReceive, menuSetting, menuReport, menuExport;
  private View view;
  private CardView revenueLayout, queueLayout;
  private PendingTransactionService service;
  private boolean shouldExecuteOnResume;
  private String kodeMerchant;
  private ProgressDialog progressDialog;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    shouldExecuteOnResume = false;
    kodeMerchant = SharedPreferenceEditor.LoadPreferences(getContext(), "Company Code", "");

    view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

    if (!EventBus.getDefault().isRegistered(this))
    {
      Log.d("EVENT BUS","not registered");
      EventBus.getDefault().register(this);
    }
    else
      Log.d("EVENT BUS", "already registered");

    service = ClientService.createService().create(PendingTransactionService.class);

    revenueLayout = (CardView) view.findViewById(R.id.revenue_dashboard_layout);
    queueLayout = (CardView) view.findViewById(R.id.queue_dashboard_layout);
    listQueue = (RecyclerView) view.findViewById(R.id.list_queue);

    pendingTransactionListAdapter = new PendingTransactionListAdapter(getContext(),
      new ArrayList<ViewPendingTransaction>());

    listQueue.setAdapter(pendingTransactionListAdapter);
    listQueue.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    listQueue.setLayoutManager(layoutManager);
//    listQueue.setItemAnimator(new DefaultItemAnimator());

    menuSales = (Button) view.findViewById(R.id.btn_menu_sales);
    menuData = (Button) view.findViewById(R.id.btn_menu_data);
    menuReceive = (Button) view.findViewById(R.id.btn_menu_receive);
    menuSetting = (Button) view.findViewById(R.id.btn_menu_setting);
    menuReport = (Button) view.findViewById(R.id.btn_menu_report);
    menuExport = (Button) view.findViewById(R.id.btn_menu_export);

    menuSales.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), SalesActivity.class));
      }
    });

    menuData.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        MasterDataFragment masterDataFragment = new MasterDataFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.main_content, masterDataFragment, "MasterDataFragment")
          .addToBackStack("MasterDataFragment").commit();
        getActivity().getSupportFragmentManager().executePendingTransactions();
      }
    });

    menuReport.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
//        ReportsDataFragment reportsDataFragment = new ReportsDataFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//          .replace(R.id.main_content, reportsDataFragment, "ReportDataFragment")
//          .addToBackStack("ReportDataFragment").commit();
//        getActivity().getSupportFragmentManager().executePendingTransactions();
        startActivity(new Intent(getContext(), ReportActivity.class));
      }
    });

    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onResume()
  {
    super.onResume();
    if(shouldExecuteOnResume)
    {
      EventBus.getDefault().register(this);
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage(this.getString(R.string.progress_message));
      progressDialog.setCancelable(false);
      progressDialog.show();
      Log.d("MAIN FRAGMENT", "RESUME");
      setupMenu();
      setupMainFragmentLayout();
    }
    else
      shouldExecuteOnResume = true;
  }

  @Override
  public void onStop()
  {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  @Override
  public void onPause()
  {
    EventBus.getDefault().unregister(this);
    super.onPause();
  }

  private void setupMenu()
  {
    Log.d(getClass().getSimpleName(), "id login user " + IDs.getLoginUser());

    TableRoleHelper tableRoleHelper = new TableRoleHelper(getActivity());
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());
    if (permission != null)
    {
      List<String> buttonList = XMLHelper.XMLReader(getActivity(), "main", permission);
      ItemVisibility.hideButton(view, buttonList);
    }
  }

  //Setting choice of main fragment layout
  private void setupMainFragmentLayout()
  {
    TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(getContext());
    final String role = tablePeopleHelper.getRoleID(IDs.getLoginUser());

    //Owner & Manager role
    if (role.equals("0") || role.equals("1"))
    {
      queueLayout.setVisibility(View.GONE);
      progressDialog.dismiss();
    }
    //Cashier role
    else if (role.equals("2"))
    {
      revenueLayout.setVisibility(View.GONE);
      Log.d("ROLE", "CASHIER");
//      new HttpRequestTask().execute();
      downloadUnpaidPendingTransaction(kodeMerchant, EventCode.EVENT_PENDING_TRANSACTION_GET);
    }
    //Stockist role
    else
    {
      revenueLayout.setVisibility(View.GONE);
      queueLayout.setVisibility(View.GONE);
      progressDialog.dismiss();
    }
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }

//  private class HttpRequestTask extends AsyncTask<Void, Void, ViewPendingTransaction[]>
//  {
//    @Override
//    protected ViewPendingTransaction[] doInBackground(Void... params)
//    {
//      try
//      {
//        final String url = RestVariable.URL_GET_UNPAID_TRANSACTION;
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        ViewPendingTransaction[] transactions = restTemplate.getForObject(url, ViewPendingTransaction[].class, kodeMerchant);
//        return transactions;
//      }
//      catch (Exception e)
//      {
//        Log.e("MainFragment", e.getMessage(), e);
//      }
//      return null;
//    }
//
//    @Override
//    protected void onPostExecute(ViewPendingTransaction[] list)
//    {
//      Log.d("COMPLETE GET UNPAID", "executing add queue");
//      if (list != null)
//      {
//        pendingTransactionListAdapter.clear();
//        pendingTransactionListAdapter.addPendingTransaction(list);
//      }
//    }
//  }

  public void downloadUnpaidPendingTransaction(final String kodeMerchant, final int id)
  {
    Log.d("GET API", "get unpaid trans");
    final Call<List<ViewPendingTransaction>> call = service.getAllPendingTransaction(kodeMerchant);
    call.enqueue(new Callback<List<ViewPendingTransaction>>()
    {
      @Override
      public void onResponse(Call<List<ViewPendingTransaction>> call, Response<List<ViewPendingTransaction>> response)
      {
        Log.d("COMPLETE GET API", "get unpaid trans done");
        viewPendingTransactions = response.body();
        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<List<ViewPendingTransaction>> call, Throwable t)
      {
        progressDialog.dismiss();
        Log.d("COMPLETE GET API", "get unpaid trans failed");
        Toast.makeText(getContext(), getString(R.string.error_webservice), Toast.LENGTH_LONG).show();
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(Event event)
  {
    Log.d("EVENT BUS", "masuk");
    switch (event.getId())
    {
      case EventCode.EVENT_PENDING_TRANSACTION_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          if(viewPendingTransactions != null)
          {
            pendingTransactionListAdapter.clear();
            pendingTransactionListAdapter.addPendingTransaction(viewPendingTransactions);
          }
          progressDialog.dismiss();
        }
        break;
      case EventCode.EVENT_ROLE_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          setupMenu();
          setupMainFragmentLayout();
          Log.d("fragment", "main fragment setup");
        }
        break;
    }
  }
}
