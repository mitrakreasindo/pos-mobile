package com.mitrakreasindo.pos.main.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.DefaultHelper;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.EventCode;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.adapter.ListReportByCategoryAdapter;
import com.mitrakreasindo.pos.main.fragment.adapter.PendingTransactionListAdapter;
import com.mitrakreasindo.pos.main.fragment.menu.MasterDataFragment;
import com.mitrakreasindo.pos.main.maintenance.user.UserFormActivity;
import com.mitrakreasindo.pos.main.sales.SalesActivity;
import com.mitrakreasindo.pos.model.Money;
import com.mitrakreasindo.pos.model.ReportSelection;
import com.mitrakreasindo.pos.model.ViewPendingTransaction;
import com.mitrakreasindo.pos.service.DashboardService;
import com.mitrakreasindo.pos.service.PendingTransactionService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
  @BindView(R.id.item_revenue_daily_chart)
  PieChart itemRevenueDailyChart;
  @BindView(R.id.item_revenue_weekly_chart)
  PieChart itemRevenueWeeklyChart;
  @BindView(R.id.item_revenue_monthly_chart)
  PieChart itemRevenueMonthlyChart;
  @BindView(R.id.item_revenue_yearly_chart)
  PieChart itemRevenueYearlyChart;
  @BindView(R.id.queue_dashboard_layout)
  CardView queueDashboardLayout;
  @BindView(R.id.revenue_dashboard_layout_today_week)
  LinearLayout revenueTodayWeekDashboardLayout;
  @BindView(R.id.revenue_dashboard_layout_month_year)
  LinearLayout revenueMonthYearDashboardLayout;

  @BindView(R.id.txt_dashboard_chart_daily_revenue)
  TextView txtDashboardChartDailyRevenue;
  @BindView(R.id.txt_dashboard_chart_daily_cost)
  TextView txtDashboardChartDailyCost;
  @BindView(R.id.txt_dashboard_chart_daily_total)
  TextView txtDashboardChartDailyTotal;
  @BindView(R.id.txt_dashboard_chart_weekly_revenue)
  TextView txtDashboardChartWeeklyRevenue;
  @BindView(R.id.txt_dashboard_chart_weekly_cost)
  TextView txtDashboardChartWeeklyCost;
  @BindView(R.id.txt_dashboard_chart_weekly_total)
  TextView txtDashboardChartWeeklyTotal;
  @BindView(R.id.txt_dashboard_chart_monthly_revenue)
  TextView txtDashboardChartMonthlyRevenue;
  @BindView(R.id.txt_dashboard_chart_monthly_cost)
  TextView txtDashboardChartMonthlyCost;
  @BindView(R.id.txt_dashboard_chart_monthly_total)
  TextView txtDashboardChartMonthlyTotal;
  @BindView(R.id.txt_dashboard_chart_yearly_revenue)
  TextView txtDashboardChartYearlyRevenue;
  @BindView(R.id.txt_dashboard_chart_yearly_cost)
  TextView txtDashboardChartYearlyCost;
  @BindView(R.id.txt_dashboard_chart_yearly_total)
  TextView txtDashboardChartYearlyTotal;
  @BindView(R.id.main_fragment)
  CoordinatorLayout mainFragment;
  @BindView(R.id.bs_list_report_by_category)
  RecyclerView bsListReportByCategory;
  @BindView(R.id.bs_select)
  NestedScrollView bsSelect;

  private Unbinder unbinder;
  private List<ViewPendingTransaction> viewPendingTransactions;
  private RecyclerView listQueue;
  private PendingTransactionListAdapter pendingTransactionListAdapter;
  private Button menuSales, menuData, menuReceive, menuSetting, menuReport, menuExport;
  private View view;
  private PendingTransactionService queueService;
  private DashboardService dashboardService;
  private boolean shouldExecuteOnResume, shouldExcecuteOnCreate;
  private ProgressDialog progressDialog;
  private Money revenue;
  private Money cost;
  private String companyCode;
  private Bundle bundle;
  private TablePeopleHelper tablePeopleHelper;

  private PieDataSet dataSetDaily;
  private PieDataSet dataSetWeekly;
  private PieDataSet dataSetMonthly;
  private PieDataSet dataSetYearly;

  private PieData dataDaily;
  private PieData dataWeekly;
  private PieData dataMonthly;
  private PieData dataYearly;

  private List<PieEntry> dailyEntries;
  private List<PieEntry> weeklyEntries;
  private List<PieEntry> monthlyEntries;
  private List<PieEntry> yearlyEntries;

  private BottomSheetBehavior bsSelectReport;

  private ListReportByCategoryAdapter listReportByCategoryAdapter;


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);

    queueService = ClientService.createService().create(PendingTransactionService.class);
    dashboardService = ClientService.createService().create(DashboardService.class);

    tablePeopleHelper = new TablePeopleHelper(getContext());

    companyCode = SharedPreferenceEditor.LoadPreferences(getContext(), "Company Code", "");
    shouldExcecuteOnCreate = true;
    shouldExecuteOnResume = false;

    progressDialog = new ProgressDialog(getContext());
    progressDialog.setMessage(this.getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    view = inflater.inflate(R.layout.fragment_mainmenu, container, false);
    unbinder = ButterKnife.bind(this, view);

    View bottomSheet = view.findViewById(R.id.bs_select);
    bsSelectReport = BottomSheetBehavior.from(bottomSheet);
    bsSelectReport.setHideable(true);
    bsSelectReport.setState(BottomSheetBehavior.STATE_HIDDEN);

    if (!EventBus.getDefault().isRegistered(this))
    {
      Log.d("EVENT BUS", "not registered");
      EventBus.getDefault().register(this);
    } else
      Log.d("EVENT BUS", "already registered");

    TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(getContext());
    String role = tablePeopleHelper.getRoleID(IDs.getLoginUser());


//    //Owner & Manager role
//    if (role.equals("0") || role.equals("1"))
//      queueLayout.setVisibility(View.GONE);
//      //Cashier role
//    else if (role.equals("2"))
//      revenueLayout.setVisibility(View.GONE);
//      //Stockist role
//    else
//    {
//      revenueLayout.setVisibility(View.GONE);
//      queueLayout.setVisibility(View.GONE);
//    }
//    listQueue = (RecyclerView) view.findViewById(R.id.list_queue);
//
//    pendingTransactionListAdapter = new PendingTransactionListAdapter(getContext(),
//      new ArrayList<ViewPendingTransaction>());
//
//    listQueue.setAdapter(pendingTransactionListAdapter);
//    listQueue.setHasFixedSize(true);
//    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//    listQueue.setLayoutManager(layoutManager);
//    listQueue.setItemAnimator(new DefaultItemAnimator());
//    listQueue = (RecyclerView) view.findViewById(R.id.list_queue);
//    queueListAdapter = new MainQueueListAdapter(getContext(), Queue.queueData());

//    listQueue.setAdapter(queueListAdapter);
//    listQueue.setHasFixedSize(true);
//    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
//    listQueue.setLayoutManager(layoutManager);
//    listQueue.setItemAnimator(new DefaultItemAnimator());
    pendingTransactionListAdapter = new PendingTransactionListAdapter(getContext(),
      new ArrayList<ViewPendingTransaction>());
    listQueue = (RecyclerView) view.findViewById(R.id.list_queue);
    listQueue.setAdapter(pendingTransactionListAdapter);
    listQueue.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
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
//        SalesFragment salesFragment = new SalesFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//          .replace(R.id.main_content, salesFragment, "salesFragment")
//          .addToBackStack("salesFragment").commit();
//        getActivity().getSupportFragmentManager().executePendingTransactions();
//        startActivity(new Intent(getContext(), ReportActivity.class));

//        bsSelectReport.show();
        if (bsSelectReport.getState() == BottomSheetBehavior.STATE_HIDDEN)
        {
          bsSelectReport.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else
        {
          bsSelectReport.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
      }
    });

//    listReportByCategoryAdapter = new ListReportByCategoryAdapter(getContext(), new ArrayList<ReportSelection>());
    listReportByCategoryAdapter = new ListReportByCategoryAdapter(getContext(), ReportSelection.data());
    bsListReportByCategory.setAdapter(listReportByCategoryAdapter);
    bsListReportByCategory.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
//    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    bsListReportByCategory.setLayoutManager(layoutManager1);
    bsListReportByCategory.setItemAnimator(new DefaultItemAnimator());
//    bsListReportByCategory.hasNestedScrollingParent();
//    listReportByCategoryAdapter.addReportSelection();
    unbinder = ButterKnife.bind(this, view);
    return view;
  }


  @Override
  public void onResume()
  {
    super.onResume();
    if (shouldExecuteOnResume)
    {
      if (!EventBus.getDefault().isRegistered(this))
      {
        Log.d("EVENT BUS", "not registered");
        EventBus.getDefault().register(this);
      } else
        Log.d("EVENT BUS", "already registered");
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
      queueDashboardLayout.setVisibility(View.GONE);
      getRevenue(companyCode, EventCode.EVENT_MONEY_GET);
      getCost(companyCode, EventCode.EVENT_MONEY_GET);
      progressDialog.dismiss();
    }
    //Cashier role
    else if (role.equals("2"))
    {
      revenueMonthYearDashboardLayout.setVisibility(View.GONE);
      revenueTodayWeekDashboardLayout.setVisibility(View.GONE);
      Log.d("ROLE", "CASHIER");
//      new HttpRequestTask().execute();
      downloadUnpaidPendingTransaction(companyCode, EventCode.EVENT_PENDING_TRANSACTION_GET);
      progressDialog.dismiss();
    }
    //Stockist role
    else
    {
      queueDashboardLayout.setVisibility(View.GONE);
      revenueMonthYearDashboardLayout.setVisibility(View.GONE);
      revenueTodayWeekDashboardLayout.setVisibility(View.GONE);
      progressDialog.dismiss();
    }
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void dailyRevenueChart(Money revenue, Money cost)
  {

    txtDashboardChartDailyRevenue.setText(DefaultHelper.decimalFormat(revenue.getDay()));
    txtDashboardChartDailyCost.setText(DefaultHelper.decimalFormat(cost.getDay()));
    txtDashboardChartDailyTotal.setText(DefaultHelper.decimalFormat(revenue.getDay() - cost.getDay()));

    if (revenue.getDay() == 0.0)
    {
      Log.d("dailyrevenue", revenue.getDay().toString());

      dailyEntries = new ArrayList<PieEntry>();
      dailyEntries.add(new PieEntry(1, "no revenue"));
      dailyEntries.add(new PieEntry(1, "no cost"));

      Log.d("Entri", String.valueOf(dailyEntries.get(0).getValue()));

      dataSetDaily = new PieDataSet(dailyEntries, "");
      dataSetDaily.setColors(new int[]{R.color.gray400, R.color.gray400}, getContext());
      dataSetDaily.setValueTextColor(R.color.gray300);
      dataSetDaily.setValueLineColor(R.color.gray300);
      dataSetDaily.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataDaily = new PieData(dataSetDaily);
    }
    else
    {
      dailyEntries = new ArrayList<PieEntry>();

      dailyEntries.add(new PieEntry(Float.parseFloat(revenue.getDay().toString()), "revenue"));
      dailyEntries.add(new PieEntry(Float.parseFloat(cost.getDay().toString()), "cost"));

      dataSetDaily = new PieDataSet(dailyEntries, "Daily Revenue");
      dataSetDaily.setColors(new int[]{R.color.test1, R.color.test2}, getContext());
      dataSetDaily.setValueTextColor(R.color.gray300);
      dataSetDaily.setValueLineColor(R.color.gray300);
      dataSetDaily.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataDaily = new PieData(dataSetDaily);
    }

    itemRevenueDailyChart.setUsePercentValues(true);
    itemRevenueDailyChart.setData(dataDaily);
    itemRevenueDailyChart.getDescription().setText(getString(R.string.daily_revenue));

    itemRevenueDailyChart.setDrawHoleEnabled(true);
    itemRevenueDailyChart.setHoleColor(Color.WHITE);
    itemRevenueDailyChart.setTransparentCircleColor(Color.WHITE);
    itemRevenueDailyChart.setDrawCenterText(true);

    itemRevenueDailyChart.setDrawEntryLabels(false);
    String finalValue = DefaultHelper.decimalFormatToPercentage(
        (dailyEntries.get(0).getValue() - dailyEntries.get(1).getValue()) / dailyEntries.get(1).getValue() * 100).split(",")[0];
    itemRevenueDailyChart.setCenterText(finalValue);
    itemRevenueDailyChart.setCenterTextColor(R.color.gray300);
    itemRevenueDailyChart.invalidate();
  }

  private void weeklyRevenueChart(Money revenue, Money cost)
  {
    txtDashboardChartWeeklyRevenue.setText(DefaultHelper.decimalFormat(revenue.getWeek()));
    txtDashboardChartWeeklyCost.setText(DefaultHelper.decimalFormat(cost.getWeek()));
    txtDashboardChartWeeklyTotal.setText(DefaultHelper.decimalFormat(revenue.getWeek() - cost.getWeek()));

    if (revenue.getWeek() == 0.0)
    {

      weeklyEntries = new ArrayList<PieEntry>();
      weeklyEntries.add(new PieEntry(1, "no revenue"));
      weeklyEntries.add(new PieEntry(1, "no cost"));

      Log.d("Entri", String.valueOf(weeklyEntries.get(0).getValue()));

      dataSetWeekly = new PieDataSet(weeklyEntries, "");
      dataSetWeekly.setColors(new int[]{R.color.gray400, R.color.gray400}, getContext());
      dataSetWeekly.setValueTextColor(R.color.gray300);
      dataSetWeekly.setValueLineColor(R.color.gray300);
      dataSetWeekly.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataWeekly = new PieData(dataSetWeekly);

    }
    else
    {
      weeklyEntries = new ArrayList<PieEntry>();

      weeklyEntries.add(new PieEntry(Float.parseFloat(revenue.getWeek().toString()), "revenue"));
      weeklyEntries.add(new PieEntry(Float.parseFloat(cost.getWeek().toString()), "cost"));

      dataSetWeekly = new PieDataSet(weeklyEntries, "");
      dataSetWeekly.setColors(new int[]{R.color.test1, R.color.test2}, getContext());
      dataSetWeekly.setValueTextColor(R.color.gray300);
      dataSetWeekly.setValueLineColor(R.color.gray300);
      dataSetWeekly.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataWeekly = new PieData(dataSetWeekly);
    }

    itemRevenueWeeklyChart.setUsePercentValues(true);
    itemRevenueWeeklyChart.setData(dataWeekly);
    itemRevenueWeeklyChart.getDescription().setText(getString(R.string.weekly_revenue));

    itemRevenueWeeklyChart.setDrawHoleEnabled(true);
    itemRevenueWeeklyChart.setHoleColor(Color.WHITE);
    itemRevenueWeeklyChart.setTransparentCircleColor(Color.WHITE);
    itemRevenueWeeklyChart.setDrawCenterText(true);

    itemRevenueWeeklyChart.setDrawEntryLabels(false);
    String finalValue = DefaultHelper.decimalFormatToPercentage(
        (weeklyEntries.get(0).getValue() - weeklyEntries.get(1).getValue()) / weeklyEntries.get(1).getValue() * 100).split(",")[0];
    itemRevenueWeeklyChart.setCenterText(finalValue);
    itemRevenueWeeklyChart.setCenterTextColor(R.color.gray300);
    itemRevenueWeeklyChart.invalidate();
  }

  private void monthlyRevenueChart(Money revenue, Money cost)
  {

    txtDashboardChartMonthlyRevenue.setText(DefaultHelper.decimalFormat(revenue.getMonth()));
    txtDashboardChartMonthlyCost.setText(DefaultHelper.decimalFormat(cost.getMonth()));
    txtDashboardChartMonthlyTotal.setText(DefaultHelper.decimalFormat(revenue.getMonth() - cost.getMonth()));

    if (revenue.getMonth() == 0.0)
    {

      monthlyEntries = new ArrayList<PieEntry>();
      monthlyEntries.add(new PieEntry(1, "no revenue"));
      monthlyEntries.add(new PieEntry(1, "no cost"));

      dataSetMonthly = new PieDataSet(monthlyEntries, "");
      dataSetMonthly.setColors(new int[]{R.color.gray400, R.color.gray400}, getContext());
      dataSetMonthly.setValueTextColor(R.color.gray300);
      dataSetMonthly.setValueLineColor(R.color.gray300);
      dataSetMonthly.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataMonthly = new PieData(dataSetMonthly);
    }
    else
    {
      monthlyEntries = new ArrayList<PieEntry>();

      monthlyEntries.add(new PieEntry(Float.parseFloat(revenue.getMonth().toString()), "revenue"));
      monthlyEntries.add(new PieEntry(Float.parseFloat(cost.getMonth().toString()), "cost"));

      dataSetMonthly = new PieDataSet(monthlyEntries, "");
      dataSetMonthly.setColors(new int[]{R.color.test1, R.color.test2}, getContext());
      dataSetMonthly.setValueTextColor(R.color.gray300);
      dataSetMonthly.setValueLineColor(R.color.gray300);
      dataSetMonthly.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataMonthly = new PieData(dataSetMonthly);
    }

    itemRevenueMonthlyChart.setUsePercentValues(true);
    itemRevenueMonthlyChart.setData(dataMonthly);
    itemRevenueMonthlyChart.getDescription().setText(getString(R.string.monthly_revenue));

    itemRevenueMonthlyChart.setDrawHoleEnabled(true);
    itemRevenueMonthlyChart.setHoleColor(Color.WHITE);
    itemRevenueMonthlyChart.setTransparentCircleColor(Color.WHITE);
    itemRevenueMonthlyChart.setDrawCenterText(true);

    itemRevenueMonthlyChart.setDrawEntryLabels(false);
    String finalValue = DefaultHelper.decimalFormatToPercentage(
        (monthlyEntries.get(0).getValue() - monthlyEntries.get(1).getValue()) / monthlyEntries.get(1).getValue() * 100).split(",")[0];
    itemRevenueMonthlyChart.setCenterText(finalValue);
    itemRevenueMonthlyChart.setCenterTextColor(R.color.gray300);
    itemRevenueMonthlyChart.invalidate();

  }

  private void yearlyRevenueChart(Money revenue, Money cost)
  {

    txtDashboardChartYearlyRevenue.setText(DefaultHelper.decimalFormat(revenue.getYear()));
    txtDashboardChartYearlyCost.setText(DefaultHelper.decimalFormat(cost.getYear()));
    txtDashboardChartYearlyTotal.setText(DefaultHelper.decimalFormat(revenue.getYear() - cost.getYear()));

    if (revenue.getYear() == 0.0)
    {

      yearlyEntries = new ArrayList<PieEntry>();
      yearlyEntries.add(new PieEntry(1, "no revenue"));
      yearlyEntries.add(new PieEntry(1, "no cost"));

      dataSetYearly = new PieDataSet(yearlyEntries, "");
      dataSetYearly.setColors(new int[]{R.color.gray400, R.color.gray400}, getContext());
      dataSetYearly.setValueTextColor(R.color.gray300);
      dataSetYearly.setValueLineColor(R.color.gray300);
      dataSetYearly.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataMonthly = new PieData(dataSetYearly);
    }
    else
    {
      yearlyEntries = new ArrayList<PieEntry>();

      yearlyEntries.add(new PieEntry(Float.parseFloat(revenue.getYear().toString()), "revenue"));
      yearlyEntries.add(new PieEntry(Float.parseFloat(cost.getYear().toString()), "cost"));

      dataSetYearly = new PieDataSet(yearlyEntries, "");
      dataSetYearly.setColors(new int[]{R.color.test1, R.color.test2}, getContext());
      dataSetYearly.setValueTextColor(R.color.gray300);
      dataSetYearly.setValueLineColor(R.color.gray300);
      dataSetYearly.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

      dataYearly = new PieData(dataSetYearly);
    }

    itemRevenueYearlyChart.setUsePercentValues(true);
    itemRevenueYearlyChart.setData(dataYearly);
    itemRevenueYearlyChart.getDescription().setText(getString(R.string.yearly_revenue));

    itemRevenueYearlyChart.setDrawHoleEnabled(true);
    itemRevenueYearlyChart.setHoleColor(Color.WHITE);
    itemRevenueYearlyChart.setTransparentCircleColor(Color.WHITE);
    itemRevenueYearlyChart.setDrawCenterText(true);

    itemRevenueYearlyChart.setDrawEntryLabels(false);
    String finalValue = DefaultHelper.decimalFormatToPercentage(
        (yearlyEntries.get(0).getValue() - yearlyEntries.get(1).getValue()) / yearlyEntries.get(1).getValue() * 100).split(",")[0];
    itemRevenueYearlyChart.setCenterText(finalValue);
    itemRevenueYearlyChart.setCenterTextColor(R.color.gray300);
    itemRevenueYearlyChart.invalidate();

  }

  private void CheckIsNeededPasswordResetter()
  {
    if (tablePeopleHelper.getVisibility(IDs.getLoginUser()))
    {
      Log.d("VISIBILITY", "true");
      Intent intent = new Intent(getContext(), UserFormActivity.class);
      intent.putExtra("isNeedPasswordResetter", true);
      intent.putExtra("people", tablePeopleHelper.getPeople(IDs.getLoginUser()));
      startActivity(intent);
    }
    else
    {
      Log.d("VISIBILITY", "false");
    }
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
    final Call<List<ViewPendingTransaction>> call = queueService.getAllPendingTransaction(kodeMerchant);
    call.enqueue(new Callback<List<ViewPendingTransaction>>()
    {
      @Override
      public void onResponse(Call<List<ViewPendingTransaction>> call, Response<List<ViewPendingTransaction>> response)
      {
        Log.d("COMPLETE GET API", "get unpaid trans done");
        viewPendingTransactions = response.body();
        if (viewPendingTransactions != null)
        {
          pendingTransactionListAdapter.clear();
          pendingTransactionListAdapter.addPendingTransaction(viewPendingTransactions);
        }
      }

      @Override
      public void onFailure(Call<List<ViewPendingTransaction>> call, Throwable t)
      {
        Log.d("COMPLETE GET API", "get unpaid trans failed");
        Toast.makeText(getContext(), getString(R.string.error_webservice), Toast.LENGTH_LONG).show();
      }
    });
  }

  public void getRevenue(String kodeMerchant, final int id)
  {
    final Call<Money> call = dashboardService.getRevenue(kodeMerchant);
    call.enqueue(new Callback<Money>()
    {
      @Override
      public void onResponse(Call<Money> call, Response<Money> response)
      {
        revenue = response.body();
        Log.d("REVENUE", revenue.getMonth().toString());
        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<Money> call, Throwable t)
      {
        Log.d("COMPLETE GET API", "get revenue failed");
        Toast.makeText(getContext(), getString(R.string.error_webservice), Toast.LENGTH_LONG).show();
      }
    });
  }

  public void getCost(String kodeMerchant, final int id)
  {
    final Call<Money> call = dashboardService.getCost(kodeMerchant);
    call.enqueue(new Callback<Money>()
    {
      @Override
      public void onResponse(Call<Money> call, Response<Money> response)
      {
        cost = response.body();
        Log.d("REVENUE", cost.getMonth().toString());
        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<Money> call, Throwable t)
      {
        Log.d("COMPLETE GET API", "get cost failed");
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
      case EventCode.EVENT_MONEY_GET:
        if (event.getStatus() == Event.COMPLETE && revenue != null && cost != null)
        {
          dailyRevenueChart(revenue, cost);
          weeklyRevenueChart(revenue, cost);
          monthlyRevenueChart(revenue, cost);
          yearlyRevenueChart(revenue, cost);
        }
        break;
      case EventCode.EVENT_ROLE_GET:
        if (event.getStatus() == Event.COMPLETE)
        {
          if (shouldExcecuteOnCreate)
          {
            setupMenu();
            setupMainFragmentLayout();
            Log.d("fragment", "main fragment setup");
            shouldExcecuteOnCreate = false;
            CheckIsNeededPasswordResetter();
          }
        }
        break;
    }
  }
}
