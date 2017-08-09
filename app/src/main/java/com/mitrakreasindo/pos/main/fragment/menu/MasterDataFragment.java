package com.mitrakreasindo.pos.main.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.ItemVisibility;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.R;

import java.util.List;

/**
 * Created by lisa on 24/07/17.
 */

public class MasterDataFragment extends Fragment
{
  private List<String> list;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_master_data, container, false);

    TableRoleHelper tableRoleHelper = new TableRoleHelper(getContext());
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());

    list = XMLHelper.XMLReader(getContext(), "maintenance", permission);
    ItemVisibility.hideButton(view, list);

    list = XMLHelper.XMLReader(getContext(), "stock", permission);
    ItemVisibility.hideButton(view, list);

    return view;
  }
}
