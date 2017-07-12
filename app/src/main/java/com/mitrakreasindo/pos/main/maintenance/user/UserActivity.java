package com.mitrakreasindo.pos.main.maintenance.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.TableHelper.TablePeopleHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.user.adapter.UserListAdapter;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.service.PeopleService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
  LinearLayout mainContent;
  @BindView(R.id.edit_filter)
  EditText txtFilter;
  @BindView(R.id.button_filter)
  Button btnClearFilter;

  private UserListAdapter userListAdapter;
  private PeopleService peopleService;
  private TablePeopleHelper tablePeopleHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_users);
    ButterKnife.bind(this);

    btnClearFilter.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        txtFilter.setText("");
      }
    });

    peopleService = ClientService.createService().create(PeopleService.class);

//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        setSupportActionBar(toolbar);
    setSupportActionBar(toolbar);
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

    tablePeopleHelper = new TablePeopleHelper(this);

    userListAdapter.clear();
    userListAdapter.addUser(tablePeopleHelper.getData());

    txtFilter.addTextChangedListener(new TextWatcher()
    {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {
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

  @Override
  protected void onStart()
  {
    super.onStart();
    userListAdapter.clear();
    userListAdapter.addUser(tablePeopleHelper.getData());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_list_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_add)
    {
      startActivity(new Intent(this, UserFormActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }
}
