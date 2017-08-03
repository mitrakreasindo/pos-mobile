package com.mitrakreasindo.pos.main.maintenance.role.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.IDs;
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;
import com.mitrakreasindo.pos.common.XMLHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.role.RolePermissionActivity;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.RoleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by error on 19/05/17.
 */

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder>
{
  private List<Role> roles = new ArrayList<Role>();
  private Context context;
  private LayoutInflater inflater;
  private RoleService roleService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private List<String> inActiveList = new ArrayList<>();

  public RoleAdapter(Context context, List<Role> roles)
  {
    sharedPreferenceEditor = new SharedPreferenceEditor();
    this.context = context;
    this.roles = roles;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    setPermission();
  }

  @Override
  public RoleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.adapter_role, parent, false);

    return new RoleAdapter.ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(RoleAdapter.ViewHolder holder, int position)
  {

    final Role role = roles.get(position);
    holder.txtName.setText(role.getName());

    if (!inActiveList.contains(MenuIds.rp_mtc_rl_action_update))
    {
      //On Click
      holder.itemView.setOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View view) {
          Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
          Intent intent = new Intent(context, RolePermissionActivity.class);
          intent.putExtra("id", role.getId());
          intent.putExtra("name", role.getName());
          intent.putExtra("permission", role.getPermissions());

          Log.d(getClass().getSimpleName(), " data edit " + role.getName() + " " + role.getPermissions());

          context.startActivity(intent);
        }
      });
    }

    if (!inActiveList.contains(MenuIds.rp_mtc_rl_action_delete))
    {
//        On Long Click
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
      {
        @Override
        public boolean onLongClick(final View v)
        {
          final AlertDialog.Builder builder = new AlertDialog.Builder(context);
          builder.setTitle("Options");
          builder.setItems(new String[]{"Delete"}, new DialogInterface.OnClickListener()
          {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              switch (which)
              {
                case 0:
                  deleteRole(SharedPreferenceEditor.LoadPreferences(context, "Company Code", ""), role);
                  break;
              }
            }
          });
          builder.show();
          return false;
        }
      });
    }
  }

  public void addRole(Role role)
  {
    roles.add(role);
    notifyDataSetChanged();
  }

  public void removeRole(Role role)
  {
    roles.remove(role);
    notifyDataSetChanged();
  }

  public void addRole(List<Role> roles)
  {
    this.roles.addAll(roles);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return roles.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public void clear()
  {
    roles.clear();
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView txtName;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtName = (TextView) itemView.findViewById(R.id.txt_role_name);
    }
  }

  private void deleteRole (String kodeMerchant, final Role role)
  {
    roleService = ClientService.createService().create(RoleService.class);
    Call<HashMap<Integer, String>> call = roleService.deleteRole(kodeMerchant, role.getId());
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0)
          {
            TableRoleHelper tableRoleHelper = new TableRoleHelper(context);
            tableRoleHelper.open();
            tableRoleHelper.delete(role.getId());
            tableRoleHelper.close();

            removeRole(role);
          }
          Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show();
        }
      }
      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = context.getString(R.string.error_webservice);
        Toast.makeText(context, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  public void setPermission()
  {
    TableRoleHelper tableRoleHelper = new TableRoleHelper(context);
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());
    if (permission != null)
    {
      inActiveList = XMLHelper.XMLReader(context, "maintenance_role_action", permission);
    }
  }
}
