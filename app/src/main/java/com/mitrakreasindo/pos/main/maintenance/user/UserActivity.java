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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.adapter.UserListAdapter;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.service.PeopleService;

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

public class UserActivity extends AppCompatActivity
{
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
  private EditText txtFilter;
  private TablePeopleHelper tablePeopleHelper;
  private SharedPreferenceEditor sharedPreferenceEditor = new SharedPreferenceEditor();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_users);
    ButterKnife.bind(this);

    txtFilter = (EditText)findViewById(R.id.edittext_filter);

    peopleService = ClientService.createService().create(PeopleService.class);

//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        setSupportActionBar(toolbar);
    toolbar.setTitle("Users");
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    userListAdapter = new UserListAdapter(this, new ArrayList<People>());
    listUsers.setAdapter(userListAdapter);
    listUsers.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    listUsers.setLayoutManager(layoutManager);
    listUsers.setItemAnimator(new DefaultItemAnimator());

    fabUsers.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        startActivity(new Intent(UserActivity.this, UserFormActivity.class));
      }
    });

    tablePeopleHelper = new TablePeopleHelper(this);

    userListAdapter.clear();
    userListAdapter.addUser(tablePeopleHelper.getData());

//    getPeoples();
    txtFilter.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        userListAdapter.clear();
        userListAdapter.addUser(tablePeopleHelper.getData(txtFilter.getText().toString()));
      }
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      {
      }
      @Override
      public void afterTextChanged(Editable s)
      {
      }
    });
  }

  public void Filter (View v)
  {
    txtFilter.setText("");
  }

  private void getPeoples(String kodeMerchant)
  {
    final Call<List<People>> people = peopleService.getPeopleAll(kodeMerchant);
    people.enqueue(new Callback<List<People>>()
    {
      @Override
      public void onResponse(Call<List<People>> call, Response<List<People>> response)
      {
        List<People> peopleList = response.body();
        userListAdapter.clear();
        userListAdapter.addUser(peopleList);
      }

      @Override
      public void onFailure(Call<List<People>> call, Throwable t)
      {

      }
    });
  }

  private void getPeopleByName(String kodeMerchant, String name)
  {
    final Call<List<People>> people = peopleService.getPeople(kodeMerchant, name);
    people.enqueue(new Callback<List<People>>()
    {
      @Override
      public void onResponse(Call<List<People>> call, Response<List<People>> response)
      {
        List<People> userList = response.body();
        if (userList == null)
        {
          Toast.makeText(UserActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
        else
        {
          userListAdapter.clear();
          userListAdapter.addUser(userList);
        }
      }

      @Override
      public void onFailure(Call<List<People>> call, Throwable t)
      {

      }
    });
  }
}
