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

import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.RegisterActivity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_register);
////        ButterKnife.bind(this);
//
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setTitle(R.string.action_register);
////        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onBackPressed();
////            }
////        });
////
////        buttonConfirm.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//    }


    int RESULT_LOAD_IMG;
//    EditText edtName, edtPass, edtRepass, edtRole;
//    RadioButton rdbVisible, rdbInvisible;
//    ImageView imageView;
//    String name, pass, repass, role;
//    String visibility;
//    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

//        edtName = (EditText) findViewById(R.id.edittext_name);
//        edtPass = (EditText) findViewById(R.id.edittext_pass);
//        edtRepass = (EditText) findViewById(R.id.edittext_repass);
//        rdbVisible = (RadioButton) findViewById(R.id.radiobutton_visible);
//        rdbInvisible = (RadioButton) findViewById(R.id.radiobutton_invisible);
//        imageView = (ImageView) findViewById(R.id.imageview);
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinner_roles);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.spinner_roles, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
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
}
