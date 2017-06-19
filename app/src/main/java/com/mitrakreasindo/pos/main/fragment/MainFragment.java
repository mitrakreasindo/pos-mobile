package com.mitrakreasindo.pos.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitrakreasindo.pos.main.R;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by lisa on 15/06/17.
 */

public class MainFragment extends Fragment
{

  private TableView<String[]> table_product;
  private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
          { "and", "a", "second", "test" } };

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

    table_product = (TableView<String[]>) view.findViewById(R.id.table_product);
    table_product.setColumnCount(4);

    table_product.setHeaderAdapter(new SimpleTableHeaderAdapter(getContext(), "No", "Nama", "Jumlah", "nilai"));
    table_product.setDataAdapter(new SimpleTableDataAdapter(getContext(), DATA_TO_SHOW));

    SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getContext(), "No", "Nama", "Jumlah", "nilai");

    TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(4);
    columnWeightModel.setColumnWeight(1, 2);
    columnWeightModel.setColumnWeight(2, 2);
    columnWeightModel.setColumnWeight(3, 2);
    columnWeightModel.setColumnWeight(4, 2);

//    TableColumnDpWidthModel widthModel = new TableColumnDpWidthModel(getContext(), 4, 200);
//    widthModel.setColumnWidth(1, 50);
//    widthModel.setColumnWidth(2, 400);
//    widthModel.setColumnWidth(3, 50);
//    widthModel.setColumnWidth(4, 100);
    return view;
  }
}
