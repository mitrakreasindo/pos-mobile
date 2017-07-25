package com.mitrakreasindo.pos.main.maintenance.user.controller;

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
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.UserFormActivity;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.service.PeopleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by error on 16/05/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>
{
  private List<People> peoples = new ArrayList<>();
  private Context context;
  private PeopleService peopleService;
  private People user;
  private List<String> inactive;

  public UserListAdapter(Context context, List<People> peoples)
  {
    this.context = context;
    this.peoples = peoples;
    inactive = PermissionUtil.getInactive(context, "maintenance_user_action");
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater
      .from(parent.getContext())
      .inflate(R.layout.adapter_user_list, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position)
  {
    user = peoples.get(position);
    final People people = peoples.get(position);
    holder.txtName.setText(people.getName());
    holder.txtRole.setText(people.getEmail());

    if (!inactive.contains(MenuIds.rp_mtc_usr_action_update))
    {
      //On Click
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, UserFormActivity.class);
          intent.putExtra("id", people.getId());
          intent.putExtra("name", people.getName());
          intent.putExtra("password", people.getApppassword());
          intent.putExtra("role", people.getRole().getId());
          intent.putExtra("visible", people.isVisible());
          intent.putExtra("image", people.getImage());
          intent.putExtra("fullname", people.getFullname());
          intent.putExtra("birthdate", people.getBirthdate());
          intent.putExtra("gender", people.getGender());
          intent.putExtra("phone", people.getPhoneNumber());
          context.startActivity(intent);
        }
      });
    }

    if (!inactive.contains(MenuIds.rp_mtc_usr_action_delete))
    {
      //On Long Click
      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View v) {
          final AlertDialog.Builder builder = new AlertDialog.Builder(context);
          builder.setTitle("Options");
          builder.setItems(new String[]{"Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                case 0:
                  new AlertDialog.Builder(context)
                          .setTitle(context.getString(R.string.alert_dialog_delete_people))
                          .setMessage(context.getString(R.string.alert_dialog_delete_people_message))
                          .setIcon(android.R.drawable.ic_dialog_alert)
                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                              deletePeople(SharedPreferenceEditor.LoadPreferences(context, "Company Code", ""), people.getId());
                            }
                          })
                          .setNegativeButton(android.R.string.no, null).show();
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

  public void addUser(People people)
  {
    peoples.add(people);
    notifyDataSetChanged();
  }

  public void clear()
  {
    peoples.clear();
    notifyDataSetChanged();
  }

  public void addUser(List<People> peoples)
  {
    this.peoples.addAll(peoples);
    notifyDataSetChanged();
  }

  public void removePeople(People people)
  {
    peoples.remove(people);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return peoples.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView txtName, txtRole;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtName = (TextView) itemView.findViewById(R.id.txt_name);
      txtRole = (TextView) itemView.findViewById(R.id.txt_role);
    }
  }

  private void deletePeople(String kodeMerchant, final String id)
  {
    peopleService = ClientService.createService().create(PeopleService.class);
    final Call<HashMap<Integer, String>> people = peopleService.deletePeople(kodeMerchant, id);
    people.enqueue(new Callback<HashMap<Integer, String>>()
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
          Log.d("RESPONSE WEBSERVICE: ", String.valueOf(responseCode) + responseMessage);

          if (responseCode == 0)
          {
            TablePeopleHelper tablePeopleHelper = new TablePeopleHelper(context);
            tablePeopleHelper.open();
            tablePeopleHelper.delete(id);
            tablePeopleHelper.close();
          }
          Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        responseCode = -1;
        responseMessage = "Cannot delete user. :( There is something wrong.";
        Toast.makeText(context, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
    removePeople(user);
  }
}
