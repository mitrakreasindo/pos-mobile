package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.Sales;
import com.mitrakreasindo.pos.model.SalesItem;
import com.mitrakreasindo.pos.service.CategoryService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 27/07/17.
 */

public class TableSalesItemHelper
{

  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "sales_items";
  private static final String TAG = "DBAdapter";

  private static final String KEY_ID = "id";
  private static final String SALES_ID = "salesId";
  private static final String LINE = "line";
  private static final String PRODUCT = "product";
  private static final String UNITS = "units";
  private static final String PRICE = "price";
  private static final String TAX_ID = "taxid";
  private static final String REFUND_QTY = "refundqty";

  private final Context context;
  private TableSalesItemHelper.DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private CategoryService service;

  private TableProductHelper tableProductHelper;

  public TableSalesItemHelper(Context context)
  {
    this.context = context;
    DBHelper = new TableSalesItemHelper.DatabaseHelper(context);
    service = ClientService.createService().create(CategoryService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TableSalesItemHelper open()
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

//  public long insert(List<SalesItem> list)
//  {
//    ContentValues initialValues = new ContentValues();
//
//    for(int i=0; i<list.size(); i++)
//    {
//      initialValues.put(KEY_ID, list.get(i).getId());
//      initialValues.put(SALES_NUM, list.get(i).getSalesnum());
//      initialValues.put(SALES_TYPE, list.get(i).getSalestype());
//      initialValues.put(PERSON, list.get(i).getPerson().getId());
//      initialValues.put(CUSTOMER, list.get(i).getCustomer().getId());
//      initialValues.put(STATUS, list.get(i).getStatus());
//
//      db.insert(DATABASE_TABLE, null, initialValues);
//    }
//    return 0;
//  }

  public long insertSalesItem(List<SalesItem> salesItems)
  {
    ContentValues initialValues = new ContentValues();
    for (int position = 0; position < salesItems.size(); position++)
    {
      initialValues.put(SALES_ID, salesItems.get(position).getSalesId().getId());
      initialValues.put(LINE, salesItems.get(position).getLine());
      initialValues.put(PRODUCT, salesItems.get(position).getProduct().getId());
      initialValues.put(UNITS, salesItems.get(position).getUnits());
      initialValues.put(PRICE, salesItems.get(position).getPrice());
      initialValues.put(TAX_ID, salesItems.get(position).getTaxid().getId());
      initialValues.put(REFUND_QTY, salesItems.get(position).getRefundqty());
      db.insert(DATABASE_TABLE, null, initialValues);
    }

//    return db.insert(DATABASE_TABLE, null, initialValues);
    return 0;
  }

  public long deleteSalesItem(List<SalesItem> salesItems)
  {
    ContentValues initialValues = new ContentValues();
    for (int position = 0; position < salesItems.size(); position++)
    {
      db.delete(DATABASE_TABLE, SALES_ID + "='" + salesItems.get(position).getSalesId().getId() + "'", null);
    }

    return 0;
  }

  public long updateSalesItem(SalesItem salesItem)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(KEY_ID, salesItem.getId());
    initialValues.put(SALES_ID, salesItem.getSalesId().getId());
    initialValues.put(LINE, salesItem.getLine());
    initialValues.put(PRODUCT, salesItem.getProduct().getId());
    initialValues.put(UNITS, salesItem.getUnits());
    initialValues.put(PRICE, salesItem.getPrice());
    initialValues.put(TAX_ID, salesItem.getTaxid().getId());
    initialValues.put(REFUND_QTY, salesItem.getRefundqty());

    return db.update(DATABASE_TABLE, initialValues, "id=?", new String[]{String.valueOf(salesItem.getId())});
  }


  public List<SalesItem> populateSalesItem(Cursor cursor)
  {
    try
    {
      List<SalesItem> list = new ArrayList<>();

      int salesIdIndex = cursor.getColumnIndexOrThrow(SALES_ID);
      int idIndex = cursor.getColumnIndexOrThrow(KEY_ID);
      int productIndex = cursor.getColumnIndexOrThrow(PRODUCT);
      int unitIndex = cursor.getColumnIndexOrThrow(UNITS);

      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String salesId = cursor.getString(salesIdIndex);
        String productId = cursor.getString(productIndex);
        int id = cursor.getInt(idIndex);
        int unit = cursor.getInt(unitIndex);

        tableProductHelper = new TableProductHelper(context);
        tableProductHelper.getProductModule(productId);

        Product product = tableProductHelper.getProductModule(productId);

//        Product product = new Product();
//        product.setId(productId);

        Sales sales = new Sales();
        sales.setId(salesId);

        SalesItem salesItem = new SalesItem();
        salesItem.setId(id);
        salesItem.setSalesId(sales);
        salesItem.setUnits(unit);
        salesItem.setProduct(product);
        list.add(salesItem);
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

//  public Cursor getAllData()
//  {
//    return db.query(DATABASE_TABLE,
//      new String[]{KEY_ID, SALES_NUM, SALES_TYPE, PERSON, CUSTOMER, STATUS},
//      null, null, null, null, null);
//  }

  public List<SalesItem> getSalesItems(String saleId)
  {
    open();

    return populateSalesItem(db.query(DATABASE_TABLE,
      new String[]{KEY_ID, SALES_ID, LINE, PRODUCT, UNITS, PRICE, TAX_ID, REFUND_QTY},
      SALES_ID + " = '" + saleId + "'", null, null, null, null));
  }
//  public List<Sales> getData()
//  {
//    open();
//    return populateSales(db.query(DATABASE_TABLE,
//      new String[] {KEY_ID, SALES_NUM, SALES_TYPE, PERSON, CUSTOMER, STATUS},
//      null, null, null, null, null));
//    return null;
//  }

//  public List<Sales> getData(String name)
//  {
//    open();

//  public List<Sales> getData(String name)
//  {
//    open();
//    return populateCategory(db.query(DATABASE_TABLE,
//      new String[] {KEY_ID, SALES_NUM, SALES_TYPE, PERSON, CUSTOMER, STATUS},
//      KEY_NAME + " LIKE '%"+name+"%'", null, null, null, null));
//    return null;
//  }

//  public void downloadData(String kodeMerchant)
//  {
//    final Call<List<SalesItem>> call = service.getCategoryAll(kodeMerchant);
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
