package com.mitrakreasindo.pos.common;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by lisa on 16/08/17.
 */

public class ChartValueFormatter implements IValueFormatter
{

  private DecimalFormat percentageFormat;

  public ChartValueFormatter() {
    percentageFormat = new DecimalFormat("###,###,##");
  }

  @Override
  public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
    return String.valueOf(percentageFormat.format(value) + " %").replaceAll("[,.]", "");
  }
}