package com.mitrakreasindo.pos.main.maintenance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by error on 17/05/17.
 */

public class UserDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.item_detail_container)
    NestedScrollView itemDetailContainer;
//    @BindView(R.id.fab)
//    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String name = bundle.getString("name");
            String role = bundle.getString("role");

            getSupportActionBar().setTitle(name);
//            toolbar.setSubtitle(role);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
