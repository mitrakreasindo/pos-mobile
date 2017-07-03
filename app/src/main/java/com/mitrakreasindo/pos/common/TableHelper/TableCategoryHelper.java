package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.service.CategoryService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-06-08.
 */

public class TableCategoryHelper
{
  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "categories";
  private static final String TAG = "DBAdapter";

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_PARENTID = "parentid";
  private static final String KEY_TEXTTIP = "texttip";
  private static final String KEY_IMAGE = "image";
  private static final String KEY_COLOUR = "colour";
  private static final String KEY_CATORDER = "catorder";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private CategoryService service;

  public TableCategoryHelper(Context context)
  {
    this.context = context;
    DBHelper = new DatabaseHelper(context);
    service = ClientService.createService().create(CategoryService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TableCategoryHelper open()
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

  public long insert(List<Category> list)
  {
    ContentValues initialValues = new ContentValues();

    for(int i=0; i<list.size(); i++)
    {
      initialValues.put(KEY_ID, list.get(i).getId());
      initialValues.put(KEY_NAME, list.get(i).getName());
      if (list.get(i).getParentid() != null)
      {
        initialValues.put(KEY_PARENTID, list.get(i).getParentid().getId());
      }
      initialValues.put(KEY_TEXTTIP, list.get(i).getTexttip());
      initialValues.put(KEY_IMAGE, list.get(i).getImage());
      initialValues.put(KEY_COLOUR, list.get(i).getColour());
      initialValues.put(KEY_CATORDER, list.get(i).getCatorder());

      db.insert(DATABASE_TABLE, null, initialValues);
    }
    return 0;
  }

  public List<Category> populateCategory(Cursor cursor)
  {
    try
    {
      List<Category> list = new ArrayList<>();

      int nameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);
      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String name = cursor.getString(nameIndex);

        Category category = new Category();
        category.setName(name);
        list.add(category);
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

  public Cursor getAllData()
  {
    return db.query(DATABASE_TABLE,
      new String[]{KEY_ID, KEY_NAME, KEY_PARENTID, KEY_TEXTTIP, KEY_IMAGE, KEY_COLOUR, KEY_CATORDER},
      null, null, null, null, null);
  }

  public List<Category> getData()
  {
    open();

    return populateCategory(db.query(DATABASE_TABLE,
      new String[] {KEY_ID, KEY_NAME, KEY_PARENTID, KEY_TEXTTIP, KEY_IMAGE, KEY_COLOUR, KEY_CATORDER},
      null, null, null, null, null));
  }

  public List<Category> getData(String name)
  {
    open();

    return populateCategory(db.query(DATABASE_TABLE,
      new String[] {KEY_ID, KEY_NAME, KEY_PARENTID, KEY_TEXTTIP, KEY_IMAGE, KEY_COLOUR, KEY_CATORDER},
      KEY_NAME + " LIKE '%"+name+"%'", null, null, null, null));
  }

  public void downloadData(String kodeMerchant)
  {
    final Call<List<Category>> call = service.getCategoryAll(kodeMerchant);
    call.enqueue(new Callback<List<Category>>()
    {
      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response)
      {
        final List<Category> list = response.body();
//        new Thread(new Runnable()
//        {
//          @Override
//          public void run()
//          {
            open();
            deleteAll();
            insert(list);
            close();
//          }
//        }).start();
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t)
      {

      }
    });
  }
}
