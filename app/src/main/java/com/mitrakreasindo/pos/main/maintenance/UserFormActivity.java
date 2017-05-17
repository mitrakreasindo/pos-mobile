package com.mitrakreasindo.pos.main.maintenance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.main.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by error on 17/05/17.
 */

public class UserFormActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textview_name)
    TextView textviewName;
    @BindView(R.id.edittext_name)
    EditText edittextName;
    @BindView(R.id.textview_pass)
    TextView textviewPass;
    @BindView(R.id.edittext_pass)
    EditText edittextPass;
    @BindView(R.id.textview_repass)
    TextView textviewRepass;
    @BindView(R.id.edittext_repass)
    EditText edittextRepass;
    @BindView(R.id.textview_role)
    TextView textviewRole;
    @BindView(R.id.spinner_roles)
    Spinner spinnerRoles;
    @BindView(R.id.textview_visibility)
    TextView textviewVisibility;
    @BindView(R.id.radiobutton_visible)
    RadioButton radiobuttonVisible;
    @BindView(R.id.radiobutton_invisible)
    RadioButton radiobuttonInvisible;
    @BindView(R.id.radiogroup_visibility)
    RadioGroup radiogroupVisibility;
    @BindView(R.id.textview_image)
    TextView textviewImage;
    @BindView(R.id.button_imageselect)
    Button buttonImageselect;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.button_confirm)
    Button buttonConfirm;
    @BindView(R.id.button_cancel)
    Button buttonCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_form);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_register);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
