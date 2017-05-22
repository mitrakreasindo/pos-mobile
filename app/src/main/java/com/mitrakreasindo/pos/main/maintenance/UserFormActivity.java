package com.mitrakreasindo.pos.main.maintenance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.RegisterActivity;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.model.Roles;
import com.mitrakreasindo.pos.service.RoleService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.spinner_role)
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

    private List<Roles> roles = new ArrayList<>();

    private RoleService roleService;
    private Roles role;


    int RESULT_LOAD_IMG;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        ButterKnife.bind(this);

        roleService = ClientService.createService().create(RoleService.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_register);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Log.d("Data :", String.valueOf(new ArrayList<Roles>()));

//        roles = getRole();

//        Adapter Spinner
//        ArrayAdapter<Roles> rolesArrayAdapter = new ArrayAdapter<Roles>(this, android.R.layout.simple_spinner_item, roles);
//        spinnerRoles.setAdapter(rolesArrayAdapter);








        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getRole();

    }


    public void Select(View view)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            try
            {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageview.setImageBitmap(selectedImage);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
    }

    public void Confirm(View view) {

        finish();
    }

    public void Cancel(View view) {

        finish();

    }

    private void getRole() {

        final Call<List<Roles>> role = roleService.getRoleAll();
        role.enqueue(new Callback<List<Roles>>() {
            @Override
            public void onResponse(Call<List<Roles>> call, Response<List<Roles>> response) {
                List<Roles> data = response.body();
                ArrayAdapter<Roles> rolesArrayAdapter = new ArrayAdapter<Roles>(UserFormActivity.this, android.R.layout.simple_spinner_item, data);
                spinnerRoles.setAdapter(rolesArrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Roles>> call, Throwable t) {

            }
        });
    }
}
