package com.mitrakreasindo.pos.main.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.adapter.UserListAdapter;
import com.mitrakreasindo.pos.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by error on 16/05/17.
 */

public class UsersActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_users)
    RecyclerView listUsers;
    @BindView(R.id.main_content)
    ConstraintLayout mainContent;
    @BindView(R.id.fab_users)
    FloatingActionButton fabUsers;

    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        setSupportActionBar(toolbar);
        toolbar.setTitle("Users");
        toolbar.setSubtitle("news");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userListAdapter = new UserListAdapter(this, new User().data());
        listUsers.setAdapter(userListAdapter);
        listUsers.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listUsers.setLayoutManager(layoutManager);
        listUsers.setItemAnimator(new DefaultItemAnimator());

        fabUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsersActivity.this, UserFormActivity.class));
            }
        });

    }

    private void getUsers() {
//        List<User> users = respon
    }


}
