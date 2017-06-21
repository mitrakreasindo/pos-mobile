package com.mitrakreasindo.pos.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mitrakreasindo.pos.main.MainQueueListAdapter;
import com.mitrakreasindo.pos.main.Queue;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.sales.SalesActivity;

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

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

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


    return view;
  }
}
