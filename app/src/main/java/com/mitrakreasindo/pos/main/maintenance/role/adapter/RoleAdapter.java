package com.mitrakreasindo.pos.main.maintenance.role.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Roles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by error on 19/05/17.
 */

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {

    private List<Roles> roles = new ArrayList<Roles>();
    private Context context;
    private LayoutInflater inflater;

    public RoleAdapter(Context context, List<Roles> roles){
        this.context = context;
        this.roles = roles;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RoleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.adapter_role, parent, false);

        return new RoleAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoleAdapter.ViewHolder holder, int position) {

        final Roles role = roles.get(position);
        holder.txtName.setText(role.getName());

//        On Click
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UserDetailActivity.class);
//                intent.putExtra("name", user.getName());
//                intent.putExtra("role", user.getRole());
//                context.startActivity(intent);
//            }
//        });


//        On Long Click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Options");
                builder.setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
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

    public void addRole(Roles role){
        roles.add(role);
        notifyDataSetChanged();
    }

    public void addRole(List<Roles> roles){
        this.roles.addAll(roles);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    @Override
    public long getItemId(int id) {
        return 0;
    }

    public void clear(){
        roles.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_role_name);
        }
    }
}
