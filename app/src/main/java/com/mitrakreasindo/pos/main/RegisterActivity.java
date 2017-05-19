package com.mitrakreasindo.pos.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    int RESULT_LOAD_IMG;
    EditText edtName, edtPass, edtRepass, edtRole;
    RadioButton rdbVisible, rdbInvisible;
    ImageView imageView;
    String name, pass, repass, role;
    String visibility;
    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = (EditText) findViewById(R.id.edittext_name);
        edtPass = (EditText) findViewById(R.id.edittext_pass);
        edtRepass = (EditText) findViewById(R.id.edittext_repass);
        rdbVisible = (RadioButton) findViewById(R.id.radiobutton_visible);
        rdbInvisible = (RadioButton) findViewById(R.id.radiobutton_invisible);
        imageView = (ImageView) findViewById(R.id.imageview);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_roles);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_roles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        rdbVisible.setChecked(true);
    }

    public void Cancel(View view)
    {
        finish();
    }

    public void Confirm(View view)
    {
        if (attemptRegister())
        {
            if (rdbVisible.isChecked())
                visibility = "true";
            else
                visibility = "false";

            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            new HttpRequestTask().execute();
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
        else
            focusView.requestFocus();
    }

    private boolean attemptRegister()
    {
        edtName.setError(null);
        edtPass.setError(null);
        edtRepass.setError(null);

        name = edtName.getText().toString();
        pass = edtPass.getText().toString();
        repass = edtRepass.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            edtName.setError(getString(R.string.error_empty_name));
            focusView = edtName;
            return false;
        }
        else if (TextUtils.isEmpty(pass))
        {
            edtPass.setError(getString(R.string.error_empty_pass));
            focusView = edtPass;
            return false;
        }
        else if (TextUtils.isEmpty(repass))
        {
            edtRepass.setError(getString(R.string.error_empty_repass));
            focusView = edtRepass;
            return false;
        }
        else if (!pass.equals(repass))
        {
            edtRepass.setError(getString(R.string.error_incorrect_reinput_password));
            focusView = edtRepass;
            return false;
        }
        else
            return true;
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
                imageView.setImageBitmap(selectedImage);
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

    public class HttpRequestTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            try
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("name", name);
                vars.put("pass", pass);
                vars.put("card", "test_android");
                vars.put("role", role);
                vars.put("visible", visibility);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                final String url = "http://192.168.1.113:8080/ManualServices/resources/PeopleServices/QueryString/" +
                        "?name={name}&apppassword={pass}&card={card}&role={role}&visible={visible}";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String register = restTemplate.getForObject(url, String.class, vars);

//                Register register = new Register();
//                register.setId("9");
//                register.setName(name);
//                register.setApppassword(pass);
//                register.setCard("testing");
//                register.setRole(role);
//                register.setVisible(visibility);
//                register.setImage(null);

//                HttpEntity<Register> entity = new HttpEntity<>(register, headers);
                System.out.println(role);
                System.out.println(register);
                return register;
            }
            catch (Exception e)
            {
                Log.e("RegisterActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String register)
        {
            Toast.makeText(RegisterActivity.this, "Done "+register, Toast.LENGTH_LONG);
            finish();
//            TextView registerText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            registerText.setText(register.getId());
//            greetingContentText.setText(register.getContent());
        }
    }
}
