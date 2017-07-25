package com.mitrakreasindo.pos.main.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitrakreasindo.pos.main.R;

/**
 * Created by lisa on 24/07/17.
 */

public class MasterDataFragment extends Fragment
{
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.fragment_master_data, container, false);

    return view;
  }
}
