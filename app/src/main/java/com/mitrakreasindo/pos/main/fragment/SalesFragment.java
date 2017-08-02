package com.mitrakreasindo.pos.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mitrakreasindo.pos.main.R;

/**
 * Created by error on 18/05/17.
 */

public class SalesFragment extends Fragment
{

  private BottomSheetBehavior bottomSheetBehavior;
  private Button button;
  boolean isClick = false;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
  {
    inflater.inflate(R.menu.sales_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_option)
    {
      switch (id)
      {
        case R.id.action_option:
          if (isClick == false)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
          else
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
          isClick = !isClick;
          break;
        default:
          break;
      }
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    final View view = inflater.inflate(R.layout.fragment_sales, container, false);
    button = (Button) view.findViewById(R.id.btn_example);
    View bottomSheet = view.findViewById(R.id.sales_bottom_sheet);

    bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    bottomSheetBehavior.setHideable(true);
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    button.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        switch (v.getId())
        {
          case R.id.btn_example:
            if (isClick == false)
              bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            else
              bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            isClick = !isClick;
            break;
          default:
            break;
        }
      }
    });
    return view;
  }
}
