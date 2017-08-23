package com.mitrakreasindo.pos.model;

import com.mitrakreasindo.pos.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 23/08/17.
 */

public class ReportSelection
{
  private int src;
  private int color;
  private String title;
  private String description;

  public int getColor()
  {
    return color;
  }

  public void setColor(int color)
  {
    this.color = color;
  }

  public int getSrc()
  {
    return src;
  }

  public void setSrc(int src)
  {
    this.src = src;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public static List<ReportSelection> data()
  {

    List<ReportSelection> reportSelections = new ArrayList<>();

    ReportSelection reportSelection = new ReportSelection();
    reportSelection.setSrc(R.drawable.ic_view_list_white_24dp);
    reportSelection.setColor(R.color.blue500);
    reportSelection.setTitle("Category");
    reportSelection.setDescription("Category");
    reportSelections.add(reportSelection);

    ReportSelection reportSelection1 = new ReportSelection();
    reportSelection1.setSrc(R.drawable.ic_format_list_bulleted_white_24dp);
    reportSelection1.setColor(R.color.lightblue500);
    reportSelection1.setTitle("Sub Category");
    reportSelection1.setDescription("Category");
    reportSelections.add(reportSelection1);

    ReportSelection reportSelection2 = new ReportSelection();
    reportSelection2.setSrc(R.drawable.ic_apps_white_24dp);
    reportSelection2.setColor(R.color.indigo500);
    reportSelection2.setTitle("Stock");
    reportSelection2.setDescription("Category");
    reportSelections.add(reportSelection2);

    ReportSelection reportSelection3 = new ReportSelection();
    reportSelection3.setSrc(R.drawable.ic_widgets_white_24dp);
    reportSelection3.setColor(R.color.purple500);
    reportSelection3.setTitle("Item");
    reportSelection3.setDescription("Category");
    reportSelections.add(reportSelection3);

    ReportSelection reportSelection4 = new ReportSelection();
    reportSelection4.setSrc(R.drawable.ic_attach_money_white_24dp);
    reportSelection4.setColor(R.color.yellow500);
    reportSelection4.setTitle("Debt");
    reportSelection4.setDescription("Category");
    reportSelections.add(reportSelection4);

    ReportSelection reportSelection5 = new ReportSelection();
    reportSelection5.setSrc(R.drawable.ic_monetization_on_white_24dp);
    reportSelection5.setColor(R.color.lightgreen500);
    reportSelection5.setTitle("Claim");
    reportSelection5.setDescription("Category");
    reportSelections.add(reportSelection5);

    ReportSelection reportSelection6 = new ReportSelection();
    reportSelection6.setSrc(R.drawable.ic_credit_card_white_24dp);
    reportSelection6.setColor(R.color.teal500);
    reportSelection6.setTitle("Payment");
    reportSelection6.setDescription("Category");
    reportSelections.add(reportSelection6);

    return reportSelections;
  }
}
