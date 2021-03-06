package com.mitrakreasindo.pos.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.CategoryActivity;
import com.mitrakreasindo.pos.main.stock.diary.activity.DiaryFormActivity;
import com.mitrakreasindo.pos.main.stock.product.ProductActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment
{
  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_stock, container, false);

    TableRoleHelper tableRoleHelper = new TableRoleHelper(getContext());
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());

    List<String> list = XMLHelper.XMLReader(getContext(), "stock", permission);
    ItemVisibility.hideButton(view, list);

    Button btnCategories = (Button) view.findViewById(R.id.btn_stok_mnt_categories);
    Button btnProducts = (Button) view.findViewById(R.id.btn_stok_mnt_products);
    Button btnDiary = (Button) view.findViewById(R.id.btn_stok_mnt_stock_diary);

    btnCategories.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), CategoryActivity.class));
      }
    });

    btnProducts.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), ProductActivity.class));
      }
    });

    btnDiary.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), DiaryFormActivity.class));
      }
    });

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
  }
}
