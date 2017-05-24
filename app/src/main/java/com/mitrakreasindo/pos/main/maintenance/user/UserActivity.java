package com.mitrakreasindo.pos.main.maintenance.user;

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

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.adapter.UserListAdapter;
import com.mitrakreasindo.pos.main.maintenance.user.model.People;
import com.mitrakreasindo.pos.main.maintenance.user.service.PeopleService;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 23/05/17.
 */

public class UserActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_users)
    RecyclerView listUsers;
    @BindView(R.id.main_content)
    ConstraintLayout mainContent;
    @BindView(R.id.fab_users)
    FloatingActionButton fabUsers;

    private UserListAdapter userListAdapter;
    private PeopleService peopleService;
    private People people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        peopleService = ClientService.createService().create(PeopleService.class);

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

        userListAdapter = new UserListAdapter(this, new ArrayList<People>());
        listUsers.setAdapter(userListAdapter);
        listUsers.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listUsers.setLayoutManager(layoutManager);
        listUsers.setItemAnimator(new DefaultItemAnimator());

        fabUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, UserFormActivity.class));
            }
        });

        getPeoples();

    }

    private void getPeoples() {
        final Call<List<People>> people = peopleService.getPeopleAll();
        people.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                List<People> peopleList = response.body();
                userListAdapter.clear();
                userListAdapter.addUser(peopleList);
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {

            }
        });

    }


}
