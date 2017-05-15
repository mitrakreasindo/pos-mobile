package com.mitrakreasindo.pos.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
    EditText edittext_name, edittext_pass, edittext_repass, edittext_role;
    RadioButton rdb_visible, rdb_invisible;
    ImageView imageview;
    String name, pass, repass, role;
    String visibility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edittext_name = (EditText) findViewById(R.id.edittext_name);
        edittext_pass = (EditText) findViewById(R.id.edittext_pass);
        edittext_repass = (EditText) findViewById(R.id.edittext_repass);
        edittext_role = (EditText) findViewById(R.id.edittext_role);
        rdb_visible = (RadioButton) findViewById(R.id.radiobutton_visible);
        rdb_invisible = (RadioButton) findViewById(R.id.radiobutton_invisible);
        imageview = (ImageView) findViewById(R.id.imageview);

        edittext_repass.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    ComparePassword();
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        rdb_visible.setChecked(true);
    }

    public void Cancel(View view)
    {
        finish();
    }

    public void Confirm(View view)
    {
        name = edittext_name.getText().toString();
        pass = edittext_pass.getText().toString();
        repass = edittext_repass.getText().toString();
        role = edittext_role.getText().toString();

        if (rdb_visible.isChecked())
            visibility = "true";
        else
            visibility = "false";

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        new HttpRequestTask().execute();
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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

    private class HttpRequestTask extends AsyncTask<Void, Void, String>
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



    private boolean ComparePassword()
    {
        if (pass != repass)
        {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT);
            return false;
        }
        else
            return true;
    }

}
