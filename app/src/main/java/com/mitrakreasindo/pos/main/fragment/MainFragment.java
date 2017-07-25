package com.mitrakreasindo.pos.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.MainQueueListAdapter;
import com.mitrakreasindo.pos.main.Queue;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.fragment.menu.MasterDataFragment;
import com.mitrakreasindo.pos.main.fragment.menu.ReportsDataFragment;
import com.mitrakreasindo.pos.main.sales.SalesActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 15/06/17.
 */

public class MainFragment extends Fragment
{

  private RecyclerView listQueue;
  private MainQueueListAdapter queueListAdapter;
  private Queue queue;
  private Button menuSales, menuData, menuReceive, menuSetting, menuReport, menuExport;
  private View view;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

    EventBus.getDefault().register(this);

    listQueue = (RecyclerView) view.findViewById(R.id.list_queue);
    queueListAdapter = new MainQueueListAdapter(getContext(), Queue.queueData());

    listQueue.setAdapter(queueListAdapter);
    listQueue.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
    listQueue.setLayoutManager(layoutManager);
    listQueue.setItemAnimator(new DefaultItemAnimator());

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
        ReportsDataFragment reportsDataFragment = new ReportsDataFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
          .replace(R.id.main_content, reportsDataFragment, "ReportDataFragment")
          .addToBackStack("ReportDataFragment").commit();
        getActivity().getSupportFragmentManager().executePendingTransactions();
      }
    });


    return view;
  }


  @Override
  public void onResume() {
    super.onResume();
    setupMenu();
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  void onEvent(Event event)
  {
    if (event.getId() == EventCode.EVENT_ROLE_GET)
    {
      if (event.getStatus() == Event.COMPLATE)
      {
        setupMenu();
        Log.d("fragment","main fragment setup");
      }
    }
  }


  private void setupMenu()
  {
    Log.d(getClass().getSimpleName(), "id login user "+IDs.getLoginUser());

    TableRoleHelper tableRoleHelper = new TableRoleHelper(getActivity());
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());
    if (permission != null)
    {
      List<String> buttonList = XMLHelper.XMLReader(getActivity(), "main", permission);
      ItemVisibility.hideButton(view, buttonList);
    }

  }


  public void openUserActivity(){

  }
}
