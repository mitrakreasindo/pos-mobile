package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.Event;
import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.model.People;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.Role;
import com.mitrakreasindo.pos.service.PeopleService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.greenrobot.eventbus.EventBus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
  private static final String KEY_FULLNAME = "fullname";
  private static final String KEY_PERSONAL_ID_TYPE = "personal_id_type";
  private static final String KEY_PERSONAL_ID = "personal_id";
  private static final String KEY_NPWP = "npwp_pribadi";
  private static final String KEY_PHONE = "phone_number";
  private static final String KEY_GENDER = "gender";
  private static final String KEY_BIRTHDATE = "birthdate";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private PeopleService service;
  private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private int id;

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

  public long insert(People[] list)
  {
    long response = 0;

    ContentValues initialValues = new ContentValues();
    for (int i = 0; i < list.length; i++)
    {
      initialValues.put(KEY_ID, list[i].getId());
      initialValues.put(KEY_NAME, list[i].getName());
      initialValues.put(KEY_APPPASSWORD, list[i].getApppassword());
      initialValues.put(KEY_CARD, list[i].getCard());
      initialValues.put(KEY_ROLE, list[i].getRole().getId());
      initialValues.put(KEY_VISIBLE, list[i].isVisible());
      initialValues.put(KEY_IMAGE, list[i].getImage());
      initialValues.put(KEY_FULLNAME, list[i].getFullname());
      initialValues.put(KEY_PERSONAL_ID_TYPE, list[i].getPersonalIdType());
      initialValues.put(KEY_PERSONAL_ID, list[i].getPersonalId());
      initialValues.put(KEY_NPWP, list[i].getNpwpPribadi());
      initialValues.put(KEY_PHONE, list[i].getPhoneNumber());
      initialValues.put(KEY_GENDER, list[i].getGender());
      if (list[i].getBirthdate() != null)
      {
        initialValues.put(KEY_BIRTHDATE, dateFormat.format(list[i].getBirthdate()));
      }
      response += db.insert(DATABASE_TABLE, null, initialValues);
    }
    return response;
  }

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
      initialValues.put(KEY_FULLNAME, list.get(i).getFullname());
      initialValues.put(KEY_PERSONAL_ID_TYPE, list.get(i).getPersonalIdType());
      initialValues.put(KEY_PERSONAL_ID, list.get(i).getPersonalId());
      initialValues.put(KEY_NPWP, list.get(i).getNpwpPribadi());
      initialValues.put(KEY_PHONE, list.get(i).getPhoneNumber());
      initialValues.put(KEY_GENDER, list.get(i).getGender());
      initialValues.put(KEY_BIRTHDATE, dateFormat.format(list.get(i).getBirthdate()));

      db.insert(DATABASE_TABLE, null, initialValues);
    }
    return 0;
  }

  public long insert(People people)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(KEY_ID, people.getId());
    initialValues.put(KEY_NAME, people.getName());
    initialValues.put(KEY_APPPASSWORD, people.getApppassword());
    initialValues.put(KEY_CARD, people.getCard());
    initialValues.put(KEY_ROLE, people.getRole().getId());
    initialValues.put(KEY_VISIBLE, people.isVisible());
    initialValues.put(KEY_IMAGE, people.getImage());
    initialValues.put(KEY_FULLNAME, people.getFullname());
    initialValues.put(KEY_PERSONAL_ID_TYPE, people.getPersonalIdType());
    initialValues.put(KEY_PERSONAL_ID, people.getPersonalId());
    initialValues.put(KEY_NPWP, people.getNpwpPribadi());
    initialValues.put(KEY_PHONE, people.getPhoneNumber());
    initialValues.put(KEY_GENDER, people.getGender());
    initialValues.put(KEY_BIRTHDATE, dateFormat.format(people.getBirthdate()));

    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public long update(People people)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(KEY_ID, people.getId());
    initialValues.put(KEY_NAME, people.getName());
    initialValues.put(KEY_APPPASSWORD, people.getApppassword());
    initialValues.put(KEY_CARD, people.getCard());
    initialValues.put(KEY_ROLE, people.getRole().getId());
    initialValues.put(KEY_VISIBLE, people.isVisible());
    initialValues.put(KEY_IMAGE, people.getImage());
    initialValues.put(KEY_FULLNAME, people.getFullname());
    initialValues.put(KEY_PERSONAL_ID_TYPE, people.getPersonalIdType());
    initialValues.put(KEY_PERSONAL_ID, people.getPersonalId());
    initialValues.put(KEY_NPWP, people.getNpwpPribadi());
    initialValues.put(KEY_PHONE, people.getPhoneNumber());
    initialValues.put(KEY_GENDER, people.getGender());
    initialValues.put(KEY_BIRTHDATE, dateFormat.format(people.getBirthdate()));

    return db.update(DATABASE_TABLE, initialValues, "id=?", new String[]{people.getId()});
  }

  public int deleteAll()
  {
    return db.delete(DATABASE_TABLE, null, null);
  }

  public int delete(String id)
  {
    return db.delete(DATABASE_TABLE, KEY_ID + "= '" + id + "'", null);
  }

  public List<People> getData()
  {
    open();
    List<People> list = populatePeople(db.query(DATABASE_TABLE,
      new String[] {KEY_ID, KEY_NAME, KEY_APPPASSWORD, KEY_CARD, KEY_ROLE, KEY_VISIBLE, KEY_IMAGE,
        KEY_FULLNAME, KEY_PERSONAL_ID_TYPE, KEY_PERSONAL_ID, KEY_NPWP, KEY_PHONE, KEY_GENDER, KEY_BIRTHDATE},
      null, null, null, null, null));
    close();

    return list;
  }

  public List<People> populatePeople(Cursor cursor)
  {
    try
    {
      List<People> list = new ArrayList<>();

      int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
      int nameIndex = cursor.getColumnIndexOrThrow(KEY_NAME);
      int roleIndex = cursor.getColumnIndexOrThrow(KEY_ROLE);
      int visibleIndex = cursor.getColumnIndexOrThrow(KEY_VISIBLE);
      int imageIndex = cursor.getColumnIndexOrThrow(KEY_IMAGE);
      int fullnameIndex = cursor.getColumnIndexOrThrow(KEY_FULLNAME);
      int phoneIndex = cursor.getColumnIndexOrThrow(KEY_PHONE);
      int genderIndex = cursor.getColumnIndexOrThrow(KEY_GENDER);
      int birthdateIndex = cursor.getColumnIndexOrThrow(KEY_BIRTHDATE);

      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String role = cursor.getString(roleIndex);
        Role r = new Role();
        r.setId(role);

        People people = new People();
        people.setId(cursor.getString(idIndex));
        people.setName(cursor.getString(nameIndex));
        people.setRole(r);
        people.setVisible(cursor.getInt(visibleIndex) > 0);
        people.setImage(cursor.getBlob(imageIndex));
        people.setFullname(cursor.getString(fullnameIndex));
        people.setPhoneNumber(cursor.getString(phoneIndex));
        people.setGender(cursor.getString(genderIndex));
        if (cursor.getString(birthdateIndex) != null)
        {
          people.setBirthdate(dateFormat.parse(cursor.getString(birthdateIndex)));
        }
        list.add(people);
      }
      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public List<People> getData(String name)
  {
    open();
    List<People> list = populatePeople(db.query(DATABASE_TABLE,
      new String[] {KEY_ID, KEY_NAME, KEY_APPPASSWORD, KEY_CARD, KEY_ROLE, KEY_VISIBLE, KEY_IMAGE,
        KEY_FULLNAME, KEY_PERSONAL_ID_TYPE, KEY_PERSONAL_ID, KEY_NPWP, KEY_PHONE, KEY_GENDER, KEY_BIRTHDATE},
      KEY_NAME + " LIKE '%"+name+"%'", null, null, null, null));
    close();

    return list;
  }

  public String getRoleID(String username)
  {
    String role;

    open();
    Cursor cursor = db.query(DATABASE_TABLE,
      new String[]{KEY_ID, KEY_NAME, KEY_APPPASSWORD, KEY_CARD, KEY_ROLE, KEY_VISIBLE, KEY_IMAGE,
        KEY_FULLNAME, KEY_PERSONAL_ID_TYPE, KEY_PERSONAL_ID, KEY_NPWP, KEY_PHONE, KEY_GENDER, KEY_BIRTHDATE},
      KEY_NAME + " = '" + username + "'", null, null, null, null);

    if (cursor.moveToFirst())
      role = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ROLE));
    else
      role = "";

    close();
    return role;
  }

  public void downloadDataAlternate(String kodeMerchant, final int id)
  {
    this.id = id;
    new HttpRequestTask(kodeMerchant).execute();
  }

  private class HttpRequestTask extends AsyncTask<Void, Void, People[]>
  {
    private String kodeMerchant;

    public HttpRequestTask(String kodeMerchant)
    {
      this.kodeMerchant = kodeMerchant;
    }

    @Override
    protected People[] doInBackground(Void... params)
    {
      try
      {
        final String url = RestVariable.URL_GET_PEOPLE;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.set("merchantCode", kodeMerchant);
        HttpEntity<People[]> entity = new HttpEntity<People[]>(headers);
//        People[] people = restTemplate.getForObject(url, People[].class, entity);
        ResponseEntity<People[]> people = restTemplate.exchange(url, HttpMethod.GET, entity, People[].class);
        return people.getBody();
      }
      catch (Exception e)
      {
        Log.e("TablePeopleHelper", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(People[] list)
    {
      open();
      deleteAll();
      insert(list);
      close();

      EventBus.getDefault().post(new Event(id, Event.COMPLETE));
    }
  }

  public void downloadData(final String kodeMerchant)
  {
    final Call<List<People>> call = service.getPeopleAll(kodeMerchant);
    call.enqueue(new Callback<List<People>>()
    {
      @Override
      public void onResponse(Call<List<People>> call, Response<List<People>> response)
      {
        final List<People> list = response.body();
        open();
        deleteAll();
        insert(list);
        close();

        EventBus.getDefault().post(new Event(id, Event.COMPLETE));
      }

      @Override
      public void onFailure(Call<List<People>> call, Throwable t)
      {

      }
    });
  }
}
