package com.mitrakreasindo.pos.main.maintenance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by error on 16/05/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<User> users = new ArrayList<User>();
    private Context context;
    private LayoutInflater inflater;

    public UserListAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.adapter_user_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final User user = users.get(position);
        holder.txtName.setText(user.getName());
        holder.txtRole.setText(user.getRole());
    }

    public void addUser(User user){
        users.add(user);
        notifyDataSetChanged();
    }

    public void addUser(List<User> users){
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int id) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtRole;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtRole = (TextView) itemView.findViewById(R.id.txt_role);
        }
    }
}
