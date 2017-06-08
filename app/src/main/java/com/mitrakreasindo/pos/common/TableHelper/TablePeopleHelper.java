package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.service.PeopleService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-06-06.
 */

public class TablePeopleHelper
{
  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "people";
  private static final String TAG = "DBAdapter";

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_APPPASSWORD = "apppassword";
  private static final String KEY_CARD = "card";
  private static final String KEY_ROLE = "role";
  private static final String KEY_VISIBLE = "visible";
  private static final String KEY_IMAGE = "image";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private PeopleService service;

  public TablePeopleHelper(Context context)
  {
    this.context = context;
    DBHelper = new DatabaseHelper(context);
    service = ClientService.createService().create(PeopleService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TablePeopleHelper open()
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

//  public long insert(String id, String name, String apppasword, String card, String role,
//                     boolean visible, byte[] image)
//  {
//    ContentValues initialValues = new ContentValues();
//    initialValues.put(KEY_ID, id);
//    initialValues.put(KEY_NAME, name);
//    initialValues.put(KEY_APPPASSWORD, apppasword);
//    initialValues.put(KEY_CARD, card);
//    initialValues.put(KEY_ROLE, role);
//    initialValues.put(KEY_VISIBLE, visible);
//    initialValues.put(KEY_IMAGE, image);
//
//    return db.insert(DATABASE_TABLE, null, initialValues);
//  }

  public long insert(List<People> list)
  {
    ContentValues initialValues = new ContentValues();

    for(int i=0; i<list.size(); i++)
    {
      initialValues.put(KEY_ID, list.get(i).getId());
      initialValues.put(KEY_NAME, list.get(i).getName());
      initialValues.put(KEY_APPPASSWORD, list.get(i).getApppassword());
      initialValues.put(KEY_CARD, list.get(i).getCard());
      initialValues.put(KEY_ROLE, list.get(i).getRole().getId());
      initialValues.put(KEY_VISIBLE, list.get(i).isVisible());
      initialValues.put(KEY_IMAGE, list.get(i).getImage());
    }
    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public int deleteAll()
  {
    return db.delete(DATABASE_TABLE, null, null);
  }

  public Cursor getAllData()
  {
    return db.query(DATABASE_TABLE,
      new String[] {KEY_ID, KEY_NAME, KEY_APPPASSWORD, KEY_CARD, KEY_ROLE, KEY_VISIBLE, KEY_IMAGE},
      null, null, null, null, null);
  }

  public void downloadData()
  {
    final Call<List<People>> call = service.getPeopleAll();
    call.enqueue(new Callback<List<People>>()
    {
      @Override
      public void onResponse(Call<List<People>> call, Response<List<People>> response)
      {
        List<People> list = response.body();
        open();
        deleteAll();
        insert(list);
        close();
      }

      @Override
      public void onFailure(Call<List<People>> call, Throwable t)
      {

      }
    });
  }
}
