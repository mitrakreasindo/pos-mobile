package com.mitrakreasindo.pos.main.maintenance.user.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.UserDetailActivity;
import com.mitrakreasindo.pos.main.maintenance.user.model.People;
import com.mitrakreasindo.pos.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by error on 16/05/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>
{

  private List<People> peoples = new ArrayList<People>();
  private Context context;
  private LayoutInflater inflater;

  public UserListAdapter(Context context, List<People> peoples)
  {
    this.context = context;
    this.peoples = peoples;
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
        intent.putExtra("role", people.getRole().getName());
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
                break;

              case 1:
                Toast.makeText(context, "User Deleted", Toast.LENGTH_LONG).show();
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
}
