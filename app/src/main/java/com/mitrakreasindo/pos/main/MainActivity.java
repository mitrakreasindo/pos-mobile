package com.mitrakreasindo.pos.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
//        new HttpRequestTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh)
        {
//            new HttpRequestTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private class HttpRequestTask extends AsyncTask<Void, Void, Register>
//    {
//        @Override
//        protected Register doInBackground(Void... params)
//        {
//            try
//            {
//                final String url = "http://rest-service.guides.spring.io/greeting";
//                RestTemplate restTemplate = new RestTemplate();
//                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//                Register register = restTemplate.getForObject(url, Register.class);
//                return register;
//            }
//            catch (Exception e)
//            {
//                Log.e("MainActivity", e.getMessage(), e);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Register register)
//        {
//            TextView registerText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            registerText.setText(register.getId());
//            greetingContentText.setText(register.getContent());
//        }
//    }
}