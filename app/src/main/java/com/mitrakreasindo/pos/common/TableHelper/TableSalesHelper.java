package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Sale;
import com.mitrakreasindo.pos.model.Sales;
import com.mitrakreasindo.pos.service.CategoryService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 26/07/17.
 */

public class TableSalesHelper
{
  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "sales";
  private static final String TAG = "DBAdapter";

  private static final String KEY_ID = "id";
  private static final String SALES_NUM = "salesnum";
  private static final String SALES_TYPE = "salestype";
  private static final String PERSON = "person";
  private static final String CUSTOMER = "customer";
  private static final String STATUS = "status";
  private static final String RECEIPT = "receipt";

  private final Context context;
  private TableSalesHelper.DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private CategoryService service;

  public TableSalesHelper(Context context)
  {
    this.context = context;
    DBHelper = new TableSalesHelper.DatabaseHelper(context);
    service = ClientService.createService().create(CategoryService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TableSalesHelper open()
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

  public long insert(List<Sales> list)
  {
    ContentValues initialValues = new ContentValues();

    for(int i=0; i<list.size(); i++)
    {
      initialValues.put(KEY_ID, list.get(i).getId());
      initialValues.put(SALES_NUM, list.get(i).getSalesnum());
      initialValues.put(SALES_TYPE, list.get(i).getSalestype());
      initialValues.put(PERSON, list.get(i).getPerson().getId());
      initialValues.put(CUSTOMER, list.get(i).getCustomer().getId());
      initialValues.put(STATUS, list.get(i).getStatus());

      db.insert(DATABASE_TABLE, null, initialValues);
    }
    return 0;
  }

  public long insertSales(Sales sales)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(KEY_ID, sales.getId());
    initialValues.put(SALES_NUM, sales.getSalesnum());
    initialValues.put(SALES_TYPE, sales.getSalestype());
    initialValues.put(PERSON, sales.getPerson().getId());
    initialValues.put(CUSTOMER, sales.getCustomer().getId());
    initialValues.put(STATUS, sales.getStatus());

    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public long update(Sales sales)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(KEY_ID, sales.getId());
    initialValues.put(SALES_NUM, sales.getSalesnum());
    initialValues.put(SALES_TYPE, sales.getSalestype());
    initialValues.put(PERSON, sales.getPerson().getId());
    initialValues.put(CUSTOMER, sales.getCustomer().getId());
    initialValues.put(STATUS, sales.getStatus());

    return db.update(DATABASE_TABLE, initialValues, "id=?", new String[] {sales.getId()});
  }

  public List<Sales> populateSales(Cursor cursor)
  {
    try
    {
      List<Sales> list = new ArrayList<>();

//      int nameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);
      int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
//        String name = cursor.getString(nameIndex);
        String id = cursor.getString(idIndex);

        Sales sales = new Sales();
        sales.setId(id);
        list.add(sales);
      }

      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public int deleteAll()
  {
    return db.delete(DATABASE_TABLE, null, null);
  }

  public int delete(String id)
  {
    return db.delete(DATABASE_TABLE, KEY_ID + "='" + id + "'", null);
  }

  public Cursor getAllData()
  {
    return db.query(DATABASE_TABLE,
      new String[]{KEY_ID, SALES_NUM, SALES_TYPE, PERSON, CUSTOMER, STATUS},
      null, null, null, null, null);
  }

  public List<Sales> getData()
  {
    open();

//    return populateSales(db.query(DATABASE_TABLE,
//      new String[] {KEY_ID, SALES_NUM, SALES_TYPE, PERSON, CUSTOMER, STATUS},
//      null, null, null, null, null));
    return null;
  }

  public List<Sales> getData(String name)
  {
    open();

//    return populateCategory(db.query(DATABASE_TABLE,
//      new String[] {KEY_ID, SALES_NUM, SALES_TYPE, PERSON, CUSTOMER, STATUS},
//      KEY_NAME + " LIKE '%"+name+"%'", null, null, null, null));
    return null;
  }

//  public void downloadData(String kodeMerchant)
//  {
//    final Call<List<Category>> call = service.getCategoryAll(kodeMerchant);
//    call.enqueue(new Callback<List<Category>>()
//    {
//      @Override
//      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
//      {
//        final List<Category> list = response.body();
////        new Thread(new Runnable()
////        {
////          @Override
////          public void run()
////          {
//        open();
//        deleteAll();
//        insert(list);
//        close();
////          }
////        }).start();
//      }
//
//      @Override
//      public void onFailure(Call<List<Category>> call, Throwable t)
//      {
//
//      }
//    });
//  }
}
