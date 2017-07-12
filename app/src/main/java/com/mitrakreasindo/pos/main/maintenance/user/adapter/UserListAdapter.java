package com.mitrakreasindo.pos.main.maintenance.user.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.UserDetailActivity;
import com.mitrakreasindo.pos.main.maintenance.user.UserFormActivity;
import com.mitrakreasindo.pos.model.Category;
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
  private Cursor cursor;
  private Context context;
  private String kodeMerchant;
  private LayoutInflater inflater;
  private PeopleService peopleService;
  private People user;

  public UserListAdapter(Context context, List<People> peoples)
  {
    this.context = context;
    this.peoples = peoples;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public UserListAdapter(Context context, Cursor cursor)
  {
    this.context = context;
    this.cursor = cursor;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
//    holder.txtName.setText(cursor.getColumnIndexOrThrow("name"));
//    holder.txtRole.setText(String.valueOf(cursor.getColumnIndexOrThrow("role")));
    user = peoples.get(position);
    final People people = peoples.get(position);
    holder.txtName.setText(people.getName());
    holder.txtRole.setText(people.getEmail());

//        On Click
    holder.itemView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("name", people.getName());
        intent.putExtra("email", people.getEmail());
        intent.putExtra("role", people.getRole().getId());
        context.startActivity(intent);
      }
    });

//        On Long Click
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
    {
      @Override
      public boolean onLongClick(final View v)
      {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Options");
        builder.setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
            switch (which)
            {
              case 0:
                Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, UserFormActivity.class);
                intent.putExtra("id", people.getId());
                intent.putExtra("name", people.getName());
                intent.putExtra("password", people.getApppassword());
                intent.putExtra("role", people.getRole().getId());
                context.startActivity(intent);
                break;

              case 1:
                deletePeople(SharedPreferenceEditor.LoadPreferences(context, ""), people.getId());
                Toast.makeText(context, "User deleted!", Toast.LENGTH_LONG).show();
                break;
            }
          }
        });
        builder.show();
        return false;
      }
    });
  }

  public void addUser(People people)
  {
    peoples.add(people);
    notifyDataSetChanged();
  }

  public void clear(){
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

//  public void addUser(Cursor cursor)
//  {
//    this.cursor = cursor;
//    notifyDataSetChanged();
//  }

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
