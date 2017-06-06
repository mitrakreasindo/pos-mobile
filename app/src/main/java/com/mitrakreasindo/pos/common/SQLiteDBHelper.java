package com.mitrakreasindo.pos.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hendric on 2017-06-06.
 */

public class SQLiteDBHelper
{
  private static final String DATABASE_TABLE = "people";
  private static final int DATABASE_VERSION = 1;

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_APPPASSWORD = "apppassword";
  private static final String KEY_CARD = "card";
  private static final String KEY_ROLE = "role";
  private static final String KEY_VISIBLE = "visible";
  private static final String KEY_IMAGE = "image";

  private static final String TAG = "DBAdapter";
  private static final String DATABASE_NAME = "chromispos";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;

  public SQLiteDBHelper(Context context)
  {
    this.context = context;
    DBHelper = new DatabaseHelper(context);
  }

  private static class DatabaseHelper extends SQLiteOpenHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
      try
      {
//        db.execSQL(DATABASE_CREATE);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
      Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS sample");
      onCreate(db);
    }
  }

  //---open SQLite DB---
  public SQLiteDBHelper open() throws SQLException
  {
    db = SQLiteDatabase.openDatabase("/chromis/chromispos.sqlite", null, 0);
//    db = DBHelper.getWritableDatabase();
    return this;
  }

  //---close SQLite DB---
  public void close()
  {
    DBHelper.close();
  }

  //---insert data into SQLite DB---
  public long insertPeople(String name, String apppasword, String card, String role,
                     boolean visible, byte[] image)
  {
    ContentValues initialValues = new ContentValues();
    initialValues.put(KEY_NAME, name);
    initialValues.put(KEY_APPPASSWORD, apppasword);
    initialValues.put(KEY_CARD, card);
    initialValues.put(KEY_ROLE, role);
    initialValues.put(KEY_VISIBLE, visible);
    initialValues.put(KEY_IMAGE, image);

    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  //---Delete All Data from table in SQLite DB---
  public void deleteAll()
  {
    db.delete(DATABASE_TABLE, null, null);
  }

  //---Get All Contacts from table in SQLite DB---
  public Cursor getAllData()
  {
    return db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_APPPASSWORD, KEY_CARD, KEY_ROLE, KEY_VISIBLE, KEY_IMAGE},
      null, null, null, null, null);
  }
}
