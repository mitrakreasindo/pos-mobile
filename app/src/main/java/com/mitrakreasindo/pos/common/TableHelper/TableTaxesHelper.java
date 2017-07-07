package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.model.Tax;
import com.mitrakreasindo.pos.model.TaxCategory;
import com.mitrakreasindo.pos.service.TaxService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendric on 2017-06-06.
 */

public class TableTaxesHelper
{
  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "taxes";
  private static final String TAG = "DBAdapter";

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_CATEGORY = "category";
  private static final String KEY_CUSTCATEGORY = "custcategory";
  private static final String KEY_PARENTID = "parentid";
  private static final String KEY_RATE = "rate";
  private static final String KEY_RATECASCADE = "ratecascade";
  private static final String KEY_RATEORDER = "rateorder";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private TaxService service;

  public TableTaxesHelper(Context context)
  {
    this.context = context;
    DBHelper = new DatabaseHelper(context);
    service = ClientService.createService().create(TaxService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TableTaxesHelper open()
  {
    try
    {
      db = DBHelper.getWritableDatabase();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this;
  }

  public void close()
  {
    DBHelper.close();
  }

  public long insert(Tax[] list)
  {
    ContentValues initialValues = new ContentValues();

    for (int i = 0; i < list.length; i++)
    {
      initialValues.put(KEY_ID, list[i].getId());
      initialValues.put(KEY_NAME, list[i].getName());
      initialValues.put(KEY_CATEGORY, list[i].getCategory().getId());
      if (list[i].getCustcategory() != null)
      {
        initialValues.put(KEY_CUSTCATEGORY, list[i].getCustcategory().getId());
      }
      if (list[i].getParentid() != null)
      {
        initialValues.put(KEY_PARENTID, list[i].getParentid().getId());
      }
      initialValues.put(KEY_RATE, list[i].getRate());
      initialValues.put(KEY_RATECASCADE, list[i].isRatecascade());
      initialValues.put(KEY_RATEORDER, list[i].getRateorder());

      db.insert(DATABASE_TABLE, null, initialValues);
    }
    return 0;
  }

  public long insert(Tax tax)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(KEY_ID, tax.getId());
    initialValues.put(KEY_NAME, tax.getName());
    initialValues.put(KEY_CATEGORY, tax.getCategory().getId());
    initialValues.put(KEY_CUSTCATEGORY, tax.getCustcategory().getId());
    initialValues.put(KEY_PARENTID, tax.getParentid().getId());
    initialValues.put(KEY_RATE, tax.getRate());
    initialValues.put(KEY_RATECASCADE, tax.isRatecascade());
    initialValues.put(KEY_RATEORDER, tax.getRateorder());

    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public int deleteAll()
  {
    return db.delete(DATABASE_TABLE, null, null);
  }

  public int delete(String id)
  {
    return db.delete(DATABASE_TABLE, KEY_ID + "= '" + id + "'", null);
  }

  public List<Tax> populateTax(Cursor cursor)
  {
    try
    {
      List<Tax> list = new ArrayList<>();

      int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
      int nameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);
      int rateIndex = cursor.getColumnIndexOrThrow(KEY_RATE);
      int categoryIndex = cursor.getColumnIndexOrThrow(KEY_CATEGORY);

      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String id = cursor.getString(idIndex);
        String name = cursor.getString(nameIndex);
        Double rate = cursor.getDouble(rateIndex);
        String category = cursor.getString(categoryIndex);

        TaxCategory tc = new TaxCategory();
        tc.setId(category);

        Tax tax = new Tax();
        tax.setId(id);
        tax.setName(name);
        tax.setRate(rate);
        tax.setCategory(tc);
        list.add(tax);
      }
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public List<Tax> getData()
  {
    open();

    return populateTax(db.query(DATABASE_TABLE,
      new String[]{KEY_ID, KEY_NAME, KEY_CATEGORY, KEY_CUSTCATEGORY, KEY_PARENTID, KEY_RATE, KEY_RATECASCADE, KEY_RATEORDER},
      null, null, null, null, null));
  }

  public List<Tax> getData(String name)
  {
    open();

    return populateTax(db.query(DATABASE_TABLE,
      new String[]{KEY_ID, KEY_NAME, KEY_CATEGORY, KEY_CUSTCATEGORY, KEY_PARENTID, KEY_RATE, KEY_RATECASCADE, KEY_RATEORDER},
      KEY_NAME + " LIKE '%" + name + "%'", null, null, null, null));
  }

  private class HttpRequestTask extends AsyncTask<Void, Void, Tax[]>
  {
    private String kodeMerchant;

    public HttpRequestTask(String kodeMerchant)
    {
      this.kodeMerchant = kodeMerchant;
    }

    @Override
    protected Tax[] doInBackground(Void... params)
    {
      try
      {
        final String url = RestVariable.URL_GET_TAX;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Tax[] tax = restTemplate.getForObject(url, Tax[].class, kodeMerchant);
        return tax;
      }
      catch (Exception e)
      {
        Log.e("TableProductHelper", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Tax[] list)
    {
      open();
      deleteAll();
      insert(list);
      close();
    }
  }

  public void downloadData(String kodeMerchant)
  {
    new TableTaxesHelper.HttpRequestTask(kodeMerchant).execute();
  }
}
