package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.service.ProductService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-06-08.
 */

public class TableProductHelper
{
  private static final String DATABASE_NAME = "chromispos.db";
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_TABLE = "products";
  private static final String TAG = "DBAdapter";

  private final Context context;
  private DatabaseHelper DBHelper;
  private SQLiteDatabase db;
  private ProductService service;

  public TableProductHelper(Context context)
  {
    this.context = context;
    DBHelper = new DatabaseHelper(context);
    service = ClientService.createService().create(ProductService.class);
  }

  private class DatabaseHelper extends SQLiteAssetHelper
  {
    DatabaseHelper(Context context)
    {
      super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
  }

  public TableProductHelper open()
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

  public long insert(List<Product> list)
  {
    ContentValues initialValues = new ContentValues();
    System.out.println("LIST PRODUCT:"+list.size());
    for(int i=0; i<list.size(); i++)
    {
      initialValues.put(context.getString(R.string.tproduct_id), list.get(i).getId());
      initialValues.put(context.getString(R.string.tproduct_reference), list.get(i).getReference());
      initialValues.put(context.getString(R.string.tproduct_code), list.get(i).getCode());
      initialValues.put(context.getString(R.string.tproduct_codetype), list.get(i).getCodetype());
      initialValues.put(context.getString(R.string.tproduct_name), list.get(i).getName());
      initialValues.put(context.getString(R.string.tproduct_pricebuy), list.get(i).getPricebuy());
      initialValues.put(context.getString(R.string.tproduct_pricesell), list.get(i).getPricesell());
      initialValues.put(context.getString(R.string.tproduct_category), list.get(i).getCategory().getId());
      initialValues.put(context.getString(R.string.tproduct_taxcat), list.get(i).getTaxcat().getId());
      initialValues.put(context.getString(R.string.tproduct_attributeset_id), list.get(i).getAttributesetId());
      initialValues.put(context.getString(R.string.tproduct_stockcost), list.get(i).getStockcost());
      initialValues.put(context.getString(R.string.tproduct_stockvolume), list.get(i).getStockvolume());
      initialValues.put(context.getString(R.string.tproduct_iscom), list.get(i).iscom());
      initialValues.put(context.getString(R.string.tproduct_isscale), list.get(i).isscale());
      initialValues.put(context.getString(R.string.tproduct_iskitchen), list.get(i).iskitchen());
      initialValues.put(context.getString(R.string.tproduct_printkb), list.get(i).isPrintkb());
      initialValues.put(context.getString(R.string.tproduct_isservice), list.get(i).isservice());
      initialValues.put(context.getString(R.string.tproduct_display), list.get(i).getDisplay());
      initialValues.put(context.getString(R.string.tproduct_attributes), list.get(i).getAttributes());
      initialValues.put(context.getString(R.string.tproduct_isvprice), list.get(i).isvprice());
      initialValues.put(context.getString(R.string.tproduct_isverpatrib), list.get(i).isverpatrib());
      initialValues.put(context.getString(R.string.tproduct_texttip), list.get(i).getTexttip());
      initialValues.put(context.getString(R.string.tproduct_warranty), list.get(i).isWarranty());
      initialValues.put(context.getString(R.string.tproduct_image), list.get(i).getImage());
      initialValues.put(context.getString(R.string.tproduct_stockunits), list.get(i).getStockunits());
      initialValues.put(context.getString(R.string.tproduct_alias), list.get(i).getAlias());
      initialValues.put(context.getString(R.string.tproduct_alwaysavailable), list.get(i).isAlwaysavailable());
      initialValues.put(context.getString(R.string.tproduct_discounted), list.get(i).getDiscounted());
      initialValues.put(context.getString(R.string.tproduct_iscatalog), list.get(i).getIscatalog());
      initialValues.put(context.getString(R.string.tproduct_catorder), list.get(i).getCatorder());
      initialValues.put(context.getString(R.string.tproduct_ispack), list.get(i).ispack());
      initialValues.put(context.getString(R.string.tproduct_packquantity), list.get(i).getPackquantity());
      initialValues.put(context.getString(R.string.tproduct_packproduct), list.get(i).getPackproduct().getId());
      initialValues.put(context.getString(R.string.tproduct_promotionid), list.get(i).getPromotionid());
      initialValues.put(context.getString(R.string.tproduct_allproducts), list.get(i).getAllproducts());
      initialValues.put(context.getString(R.string.tproduct_managestock), list.get(i).getManagestock());
      initialValues.put(context.getString(R.string.tproduct_siteguid), list.get(i).getSiteguid());
      initialValues.put(context.getString(R.string.tproduct_sflag), list.get(i).getSflag());
    }
    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public int deleteAll()
  {
    return db.delete(DATABASE_TABLE, null, null);
  }

  public List<Product> populateProducts(Cursor cursor)
  {
    try
    {
      List<Product> list = new ArrayList<>();

      int codeIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_code));
      int nameIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_name));
      int priceSellIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_pricesell));
      int priceBuyIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_pricebuy));

      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String code = cursor.getString(codeIndex);
        System.out.println("Product:"+code);
        String name = cursor.getString(nameIndex);
        System.out.println("Product:"+name);
        double priceSell = cursor.getDouble(priceSellIndex);
        double priceBuy = cursor.getDouble(priceBuyIndex);

        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setPricesell(priceSell);
        product.setPricebuy(priceBuy);
        list.add(product);
      }

      return list;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public List<Product> getData()
  {
    open();

    return populateProducts(db.query(DATABASE_TABLE,
      new String[]
        {
          context.getString(R.string.tproduct_id),
          context.getString(R.string.tproduct_reference),
          context.getString(R.string.tproduct_code),
          context.getString(R.string.tproduct_codetype),
          context.getString(R.string.tproduct_name),
          context.getString(R.string.tproduct_pricebuy),
          context.getString(R.string.tproduct_pricesell),
          context.getString(R.string.tproduct_category),
          context.getString(R.string.tproduct_taxcat),
          context.getString(R.string.tproduct_attributeset_id),
          context.getString(R.string.tproduct_stockcost),
          context.getString(R.string.tproduct_stockvolume),
          context.getString(R.string.tproduct_iscom),
          context.getString(R.string.tproduct_isscale),
          context.getString(R.string.tproduct_iskitchen),
          context.getString(R.string.tproduct_printkb),
          context.getString(R.string.tproduct_isservice),
          context.getString(R.string.tproduct_display),
          context.getString(R.string.tproduct_attributes),
          context.getString(R.string.tproduct_isvprice),
          context.getString(R.string.tproduct_isverpatrib),
          context.getString(R.string.tproduct_texttip),
          context.getString(R.string.tproduct_warranty),
          context.getString(R.string.tproduct_image),
          context.getString(R.string.tproduct_stockunits),
          context.getString(R.string.tproduct_alias),
          context.getString(R.string.tproduct_alwaysavailable),
          context.getString(R.string.tproduct_discounted),
          context.getString(R.string.tproduct_iscatalog),
          context.getString(R.string.tproduct_catorder),
          context.getString(R.string.tproduct_ispack),
          context.getString(R.string.tproduct_packquantity),
          context.getString(R.string.tproduct_packproduct),
          context.getString(R.string.tproduct_promotionid),
          context.getString(R.string.tproduct_allproducts),
          context.getString(R.string.tproduct_managestock),
          context.getString(R.string.tproduct_siteguid),
          context.getString(R.string.tproduct_sflag)
        },
      null, null, null, null, null));
  }

  public void downloadData(String kodeMerchant)
  {
    final Call<List<Product>> call = service.getProductAll(kodeMerchant);
    call.enqueue(new Callback<List<Product>>()
    {
      @Override
      public void onResponse(Call<List<Product>> call, Response<List<Product>> response)
      {
        List<Product> list = response.body();
        System.out.println("List Size"+list.size());
        open();
        deleteAll();
        insert(list);
        close();
      }

      @Override
      public void onFailure(Call<List<Product>> call, Throwable t)
      {
      }
    });
  }
}
