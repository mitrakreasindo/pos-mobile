package com.mitrakreasindo.pos.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.RoleActivity;
import com.mitrakreasindo.pos.main.maintenance.user.UserActivity;

/**
 * Created by error on 15/05/17.
 */

public class MaintenanceFragment extends Fragment
{


  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
  {

    Button btnUsers, btnRoles;
    View view = inflater.inflate(R.layout.fragment_maintenance, container, false);

    btnUsers = (Button) view.findViewById(R.id.btn_users);
    btnRoles = (Button) view.findViewById(R.id.btn_roles);

    btnRoles.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), RoleActivity.class));
      }
    });


    btnUsers.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(getActivity(), UserActivity.class));
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
