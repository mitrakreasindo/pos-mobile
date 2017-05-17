package com.mitrakreasindo.pos.main.maintenance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by error on 17/05/17.
 */

public class UserDetailActivity extends AppCompatActivity {

    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_role)
    TextView txtRole;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String name = bundle.getString("name");
            String role = bundle.getString("role");

            txtName.setText(name);
            txtRole.setText(role);
        }

    }
}
