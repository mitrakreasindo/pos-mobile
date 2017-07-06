package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.RoleService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-06-06.
 */

public class TableRoleHelper
{
  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "roles";
  private static final String TAG = "DBAdapter";

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_PERMISSION = "permissions";
  private static final String KEY_RIGHTSLEVEL = "rightslevel";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private RoleService service;

  public TableRoleHelper(Context context)
  {
    this.context = context;
    DBHelper = new DatabaseHelper(context);
    service = ClientService.createService().create(RoleService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TableRoleHelper open()
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

  public long insert(List<Role> list)
  {
    ContentValues initialValues = new ContentValues();

    for(int i=0; i<list.size(); i++)
    {
      initialValues.put(KEY_ID, list.get(i).getId());
      initialValues.put(KEY_NAME, list.get(i).getName());
      initialValues.put(KEY_PERMISSION, list.get(i).getPermissions());
      initialValues.put(KEY_RIGHTSLEVEL, list.get(i).getRightslevel());

      db.insert(DATABASE_TABLE, null, initialValues);
    }
    return 0;
  }

  public int deleteAll()
  {
    return db.delete(DATABASE_TABLE, null, null);
  }

  public List<Role> populateRole(Cursor cursor)
  {
    try
    {
      List<Role> list = new ArrayList<>();

      int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
      int nameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);

      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String id = cursor.getString(idIndex);
        String name = cursor.getString(nameIndex);

        Role role = new Role();
        role.setId(id);
        role.setName(name);
        list.add(role);
      }
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public Cursor getAllData()
  {
    return db.query(DATABASE_TABLE,
      new String[]{KEY_ID, KEY_NAME, KEY_PERMISSION, KEY_RIGHTSLEVEL},
      null, null, null, null, null);
  }

  public List<Role> getData()
  {
    open();

    return populateRole(db.query(DATABASE_TABLE,
            new String[] {"CAST(id AS INTEGER)", KEY_ID, KEY_NAME, KEY_PERMISSION, KEY_RIGHTSLEVEL},
            null, null, null, null, "1"));
  }

  public List<Role> getDataByName(String name)
  {
    open();

    return populateRole(db.query(DATABASE_TABLE,
            new String[] {KEY_ID, KEY_NAME, KEY_PERMISSION, KEY_RIGHTSLEVEL},
            KEY_NAME + " LIKE '%"+name+"%'", null, null, null, null));
  }

  public List<Role> getDataById(String id)
  {
    open();

    return populateRole(db.query(DATABASE_TABLE,
      new String[] {KEY_ID, KEY_NAME, KEY_PERMISSION, KEY_RIGHTSLEVEL},
      KEY_ID + " LIKE '%"+id+"%'", null, null, null, null));
  }

  public byte[] getPermission (String name)
  {
    byte[] permissions = null;

    open();
    final String QUERY = "SELECT r.permissions FROM people p JOIN roles r ON p.role = r.id WHERE p.name =?";
    Cursor cursor = db.rawQuery(QUERY, new String[]{name});

    if (cursor.moveToFirst())
    {
      permissions = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_PERMISSION));
    }
    return permissions;
  }

  public void downloadData(final String kodeMerchant)
  {
    final Call<List<Role>> call = service.getRoleAll(kodeMerchant);
    call.enqueue(new Callback<List<Role>>()
    {
      @Override
      public void onResponse(Call<List<Role>> call, Response<List<Role>> response)
      {
        final List<Role> list = response.body();
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
      public void onFailure(Call<List<Role>> call, Throwable t)
      {

      }
    });
  }
}
