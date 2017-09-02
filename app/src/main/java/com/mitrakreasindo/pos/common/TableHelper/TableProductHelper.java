package com.mitrakreasindo.pos.common.TableHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.RestVariable;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.service.ProductService;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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

  public long insert(Product[] list)
  {
    long response = 0;

    ContentValues initialValues = new ContentValues();
    for (int i = 0; i < list.length; i++)
    {
      initialValues.put(context.getString(R.string.tproduct_id), list[i].getId());
      initialValues.put(context.getString(R.string.tproduct_reference), list[i].getReference());
      initialValues.put(context.getString(R.string.tproduct_code), list[i].getCode());
      initialValues.put(context.getString(R.string.tproduct_codetype), list[i].getCodetype());
      initialValues.put(context.getString(R.string.tproduct_name), list[i].getName());
      initialValues.put(context.getString(R.string.tproduct_pricebuy), list[i].getPricebuy());
      initialValues.put(context.getString(R.string.tproduct_pricesell), list[i].getPricesell());
      initialValues.put(context.getString(R.string.tproduct_category), list[i].getCategory().getId());
      initialValues.put(context.getString(R.string.tproduct_taxcat), list[i].getTaxcat().getId());
      initialValues.put(context.getString(R.string.tproduct_attributeset_id), list[i].getAttributesetId());
      initialValues.put(context.getString(R.string.tproduct_stockcost), list[i].getStockcost());
      initialValues.put(context.getString(R.string.tproduct_stockvolume), list[i].getStockvolume());
      initialValues.put(context.getString(R.string.tproduct_iscom), list[i].getIscom());
      initialValues.put(context.getString(R.string.tproduct_isscale), list[i].getIsscale());
      initialValues.put(context.getString(R.string.tproduct_iskitchen), list[i].getIskitchen());
      initialValues.put(context.getString(R.string.tproduct_printkb), list[i].isPrintkb());
      initialValues.put(context.getString(R.string.tproduct_sendstatus), list[i].isSendstatus());
      initialValues.put(context.getString(R.string.tproduct_isservice), list[i].getIsservice());
      initialValues.put(context.getString(R.string.tproduct_display), list[i].getDisplay());
      initialValues.put(context.getString(R.string.tproduct_attributes), list[i].getAttributes());
      initialValues.put(context.getString(R.string.tproduct_isvprice), list[i].getIsvprice());
      initialValues.put(context.getString(R.string.tproduct_isverpatrib), list[i].getIsverpatrib());
      initialValues.put(context.getString(R.string.tproduct_texttip), list[i].getTexttip());
      initialValues.put(context.getString(R.string.tproduct_warranty), list[i].isWarranty());
      initialValues.put(context.getString(R.string.tproduct_image), list[i].getImage());
      initialValues.put(context.getString(R.string.tproduct_stockunits), list[i].getStockunits());
      initialValues.put(context.getString(R.string.tproduct_alias), list[i].getAlias());
      initialValues.put(context.getString(R.string.tproduct_alwaysavailable), list[i].isAlwaysavailable());
      initialValues.put(context.getString(R.string.tproduct_discounted), list[i].getDiscounted());
      initialValues.put(context.getString(R.string.tproduct_candiscount), list[i].isCandiscount());
      initialValues.put(context.getString(R.string.tproduct_iscatalog), list[i].getIscatalog());
      initialValues.put(context.getString(R.string.tproduct_catorder), list[i].getCatorder());
      initialValues.put(context.getString(R.string.tproduct_ispack), list[i].getIspack());
      initialValues.put(context.getString(R.string.tproduct_packquantity), list[i].getPackquantity());
      initialValues.put(context.getString(R.string.tproduct_packproduct), list[i].getPackproduct());
      initialValues.put(context.getString(R.string.tproduct_promotionid), list[i].getPromotionid());
      initialValues.put(context.getString(R.string.tproduct_allproducts), list[i].getAllproducts());
      initialValues.put(context.getString(R.string.tproduct_managestock), list[i].getManagestock());
      initialValues.put(context.getString(R.string.tproduct_siteguid), list[i].getSiteguid());
      initialValues.put(context.getString(R.string.tproduct_sflag), list[i].getSflag());
      response += db.insert(DATABASE_TABLE, null, initialValues);
    }
    return response;
  }

  public long insert(List<Product> list)
  {
    ContentValues initialValues = new ContentValues();
    for (int i = 0; i < list.size(); i++)
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
      initialValues.put(context.getString(R.string.tproduct_iscom), list.get(i).getIscom());
      initialValues.put(context.getString(R.string.tproduct_isscale), list.get(i).getIsscale());
      initialValues.put(context.getString(R.string.tproduct_iskitchen), list.get(i).getIskitchen());
      initialValues.put(context.getString(R.string.tproduct_printkb), list.get(i).isPrintkb());
      initialValues.put(context.getString(R.string.tproduct_sendstatus), list.get(i).isSendstatus());
      initialValues.put(context.getString(R.string.tproduct_isservice), list.get(i).getIsservice());
      initialValues.put(context.getString(R.string.tproduct_display), list.get(i).getDisplay());
      initialValues.put(context.getString(R.string.tproduct_attributes), list.get(i).getAttributes());
      initialValues.put(context.getString(R.string.tproduct_isvprice), list.get(i).getIsvprice());
      initialValues.put(context.getString(R.string.tproduct_isverpatrib), list.get(i).getIsverpatrib());
      initialValues.put(context.getString(R.string.tproduct_texttip), list.get(i).getTexttip());
      initialValues.put(context.getString(R.string.tproduct_warranty), list.get(i).isWarranty());
      initialValues.put(context.getString(R.string.tproduct_image), list.get(i).getImage());
      initialValues.put(context.getString(R.string.tproduct_stockunits), list.get(i).getStockunits());
      initialValues.put(context.getString(R.string.tproduct_alias), list.get(i).getAlias());
      initialValues.put(context.getString(R.string.tproduct_alwaysavailable), list.get(i).isAlwaysavailable());
      initialValues.put(context.getString(R.string.tproduct_discounted), list.get(i).getDiscounted());
      initialValues.put(context.getString(R.string.tproduct_candiscount), list.get(i).isCandiscount());
      initialValues.put(context.getString(R.string.tproduct_iscatalog), list.get(i).getIscatalog());
      initialValues.put(context.getString(R.string.tproduct_catorder), list.get(i).getCatorder());
      initialValues.put(context.getString(R.string.tproduct_ispack), list.get(i).getIspack());
      initialValues.put(context.getString(R.string.tproduct_packquantity), list.get(i).getPackquantity());
      initialValues.put(context.getString(R.string.tproduct_packproduct), list.get(i).getPackproduct());
      initialValues.put(context.getString(R.string.tproduct_promotionid), list.get(i).getPromotionid());
      initialValues.put(context.getString(R.string.tproduct_allproducts), list.get(i).getAllproducts());
      initialValues.put(context.getString(R.string.tproduct_managestock), list.get(i).getManagestock());
      initialValues.put(context.getString(R.string.tproduct_siteguid), list.get(i).getSiteguid());
      initialValues.put(context.getString(R.string.tproduct_sflag), list.get(i).getSflag());
    }
    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public long insert(Product product)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(context.getString(R.string.tproduct_id), product.getId());
    initialValues.put(context.getString(R.string.tproduct_reference), product.getReference());
    initialValues.put(context.getString(R.string.tproduct_code), product.getCode());
    initialValues.put(context.getString(R.string.tproduct_codetype), product.getCodetype());
    initialValues.put(context.getString(R.string.tproduct_name), product.getName());
    initialValues.put(context.getString(R.string.tproduct_pricebuy), product.getPricebuy());
    initialValues.put(context.getString(R.string.tproduct_pricesell), product.getPricesell());
    initialValues.put(context.getString(R.string.tproduct_category), product.getCategory().getId());
    initialValues.put(context.getString(R.string.tproduct_taxcat), product.getTaxcat().getId());
    initialValues.put(context.getString(R.string.tproduct_attributeset_id), product.getAttributesetId());
    initialValues.put(context.getString(R.string.tproduct_stockcost), product.getStockcost());
    initialValues.put(context.getString(R.string.tproduct_stockvolume), product.getStockvolume());
    initialValues.put(context.getString(R.string.tproduct_iscom), product.getIscom());
    initialValues.put(context.getString(R.string.tproduct_isscale), product.getIsscale());
    initialValues.put(context.getString(R.string.tproduct_iskitchen), product.getIskitchen());
    initialValues.put(context.getString(R.string.tproduct_printkb), product.isPrintkb());
    initialValues.put(context.getString(R.string.tproduct_sendstatus), product.isSendstatus());
    initialValues.put(context.getString(R.string.tproduct_isservice), product.getIsservice());
    initialValues.put(context.getString(R.string.tproduct_display), product.getDisplay());
    initialValues.put(context.getString(R.string.tproduct_attributes), product.getAttributes());
    initialValues.put(context.getString(R.string.tproduct_isvprice), product.getIsvprice());
    initialValues.put(context.getString(R.string.tproduct_isverpatrib), product.getIsverpatrib());
    initialValues.put(context.getString(R.string.tproduct_texttip), product.getTexttip());
    initialValues.put(context.getString(R.string.tproduct_warranty), product.isWarranty());
    initialValues.put(context.getString(R.string.tproduct_image), product.getImage());
    initialValues.put(context.getString(R.string.tproduct_stockunits), product.getStockunits());
    initialValues.put(context.getString(R.string.tproduct_alias), product.getAlias());
    initialValues.put(context.getString(R.string.tproduct_alwaysavailable), product.isAlwaysavailable());
    initialValues.put(context.getString(R.string.tproduct_discounted), product.getDiscounted());
    initialValues.put(context.getString(R.string.tproduct_candiscount), product.isCandiscount());
    initialValues.put(context.getString(R.string.tproduct_iscatalog), product.getIscatalog());
    initialValues.put(context.getString(R.string.tproduct_catorder), product.getCatorder());
    initialValues.put(context.getString(R.string.tproduct_ispack), product.getIspack());
    initialValues.put(context.getString(R.string.tproduct_packquantity), product.getPackquantity());
    initialValues.put(context.getString(R.string.tproduct_packproduct), product.getPackproduct());
    initialValues.put(context.getString(R.string.tproduct_promotionid), product.getPromotionid());
    initialValues.put(context.getString(R.string.tproduct_allproducts), product.getAllproducts());
    initialValues.put(context.getString(R.string.tproduct_managestock), product.getManagestock());
    initialValues.put(context.getString(R.string.tproduct_siteguid), product.getSiteguid());
    initialValues.put(context.getString(R.string.tproduct_sflag), product.getSflag());

    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public int delete(String id)
  {
    return db.delete(DATABASE_TABLE, context.getString(R.string.tproduct_id) + "='" + id + "'", null);
  }


  public long update(Product product)
  {
    ContentValues initialValues = new ContentValues();

    initialValues.put(context.getString(R.string.tproduct_id), product.getId());
    initialValues.put(context.getString(R.string.tproduct_reference), product.getReference());
    initialValues.put(context.getString(R.string.tproduct_code), product.getCode());
    initialValues.put(context.getString(R.string.tproduct_codetype), product.getCodetype());
    initialValues.put(context.getString(R.string.tproduct_name), product.getName());
    initialValues.put(context.getString(R.string.tproduct_pricebuy), product.getPricebuy());
    initialValues.put(context.getString(R.string.tproduct_pricesell), product.getPricesell());
    initialValues.put(context.getString(R.string.tproduct_category), product.getCategory().getId());
    initialValues.put(context.getString(R.string.tproduct_taxcat), product.getTaxcat().getId());
    initialValues.put(context.getString(R.string.tproduct_attributeset_id), product.getAttributesetId());
    initialValues.put(context.getString(R.string.tproduct_stockcost), product.getStockcost());
    initialValues.put(context.getString(R.string.tproduct_stockvolume), product.getStockvolume());
    initialValues.put(context.getString(R.string.tproduct_iscom), product.getIscom());
    initialValues.put(context.getString(R.string.tproduct_isscale), product.getIsscale());
    initialValues.put(context.getString(R.string.tproduct_iskitchen), product.getIskitchen());
    initialValues.put(context.getString(R.string.tproduct_printkb), product.isPrintkb());
    initialValues.put(context.getString(R.string.tproduct_sendstatus), product.isSendstatus());
    initialValues.put(context.getString(R.string.tproduct_isservice), product.getIsservice());
    initialValues.put(context.getString(R.string.tproduct_display), product.getDisplay());
    initialValues.put(context.getString(R.string.tproduct_attributes), product.getAttributes());
    initialValues.put(context.getString(R.string.tproduct_isvprice), product.getIsvprice());
    initialValues.put(context.getString(R.string.tproduct_isverpatrib), product.getIsverpatrib());
    initialValues.put(context.getString(R.string.tproduct_texttip), product.getTexttip());
    initialValues.put(context.getString(R.string.tproduct_warranty), product.isWarranty());
    initialValues.put(context.getString(R.string.tproduct_image), product.getImage());
    initialValues.put(context.getString(R.string.tproduct_stockunits), product.getStockunits());
    initialValues.put(context.getString(R.string.tproduct_alias), product.getAlias());
    initialValues.put(context.getString(R.string.tproduct_alwaysavailable), product.isAlwaysavailable());
    initialValues.put(context.getString(R.string.tproduct_discounted), product.getDiscounted());
    initialValues.put(context.getString(R.string.tproduct_candiscount), product.isCandiscount());
    initialValues.put(context.getString(R.string.tproduct_iscatalog), product.getIscatalog());
    initialValues.put(context.getString(R.string.tproduct_catorder), product.getCatorder());
    initialValues.put(context.getString(R.string.tproduct_ispack), product.getIspack());
    initialValues.put(context.getString(R.string.tproduct_packquantity), product.getPackquantity());
    initialValues.put(context.getString(R.string.tproduct_packproduct), product.getPackproduct());
    initialValues.put(context.getString(R.string.tproduct_promotionid), product.getPromotionid());
    initialValues.put(context.getString(R.string.tproduct_allproducts), product.getAllproducts());
    initialValues.put(context.getString(R.string.tproduct_managestock), product.getManagestock());
    initialValues.put(context.getString(R.string.tproduct_siteguid), product.getSiteguid());
    initialValues.put(context.getString(R.string.tproduct_sflag), product.getSflag());

    return db.update(DATABASE_TABLE, initialValues, "id=?", new String[]{product.getId()});
  }

  public long updateProductStock(Product product, int reason)
  {
    Product p = getProductById(product.getId());

    ContentValues initialValues = new ContentValues();
    if (reason == 1)
      initialValues.put(context.getString(R.string.tproduct_stockunits),
        p.getStockunits() + product.getStockunits());
    else
      initialValues.put(context.getString(R.string.tproduct_stockunits),
        p.getStockunits() - product.getStockunits());
    return db.update(DATABASE_TABLE, initialValues, "id=?", new String[]{product.getId()});
  }
  
  public void updateProductStockBylist(List<Product> products, List<Integer> reason)
  {
      ContentValues initialValues = new ContentValues();
      for (int i = 0; i < products.size() ; i++)
      {
        Product p = getProductById(products.get(i).getId());
        if (reason.get(i) == 1)
          initialValues.put(context.getString(R.string.tproduct_stockunits),
            p.getStockunits() + products.get(i).getStockunits());
        else
          initialValues.put(context.getString(R.string.tproduct_stockunits),
            p.getStockunits() - products.get(i).getStockunits());
        db.update(DATABASE_TABLE, initialValues, "id=?", new String[]{products.get(i).getId()});
      }
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

      int idIndex = cursor.getColumnIndexOrThrow("id");
      int codeIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_code));
      int nameIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_name));
      int shortNameIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_alias));
      int categoryIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_category));
      int inStockIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_stockunits));
      int priceSellIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_pricesell));
      int priceBuyIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_pricebuy));
      int stockCostIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_stockcost));
      int stockVolumeIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_stockvolume));
      int imageIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_image));

      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
      {
        String id = cursor.getString(idIndex);
        String code = cursor.getString(codeIndex);
        String name = cursor.getString(nameIndex);
        String shortName = cursor.getString(shortNameIndex);
        String category = cursor.getString(categoryIndex);
        double inStock = cursor.getDouble(inStockIndex);
        double priceSell = cursor.getDouble(priceSellIndex);
        double priceBuy = cursor.getDouble(priceBuyIndex);
        double stockCost = cursor.getDouble(stockCostIndex);
        double stockVolume = cursor.getDouble(stockVolumeIndex);
        byte[] image = cursor.getBlob(imageIndex);

        Category c = new Category();
        c.setId(category);

        Product product = new Product();
        product.setId(id);
        product.setCode(code);
        product.setName(name);
        product.setStockunits(inStock);
        product.setPricesell(priceSell);
        product.setPricebuy(priceBuy);
        product.setCategory(c);
        product.setAlias(shortName);
        product.setStockcost(stockCost);
        product.setStockvolume(stockVolume);
        product.setImage(image);
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

  public Product populateProduct(Cursor cursor)
  {
    try
    {
      Product product = new Product();

      int idIndex = cursor.getColumnIndexOrThrow("id");
      int codeIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_code));
      int nameIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_name));
      int shortNameIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_alias));
      int categoryIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_category));
      int inStockIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_stockunits));
      int priceSellIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_pricesell));
      int priceBuyIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_pricebuy));
      int stockCostIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_stockcost));
      int stockVolumeIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_stockvolume));
      int imageIndex = cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_image));

        String id = cursor.getString(idIndex);
        String code = cursor.getString(codeIndex);
        String name = cursor.getString(nameIndex);
        String shortName = cursor.getString(shortNameIndex);
        String category = cursor.getString(categoryIndex);
        double inStock = cursor.getDouble(inStockIndex);
        double priceSell = cursor.getDouble(priceSellIndex);
        double priceBuy = cursor.getDouble(priceBuyIndex);
        double stockCost = cursor.getDouble(stockCostIndex);
        double stockVolume = cursor.getDouble(stockVolumeIndex);
        byte[] image = cursor.getBlob(imageIndex);

        Category c = new Category();
        c.setId(category);

        product.setId(id);
        product.setCode(code);
        product.setName(name);
        product.setStockunits(inStock);
        product.setPricesell(priceSell);
        product.setPricebuy(priceBuy);
        product.setCategory(c);
        product.setAlias(shortName);
        product.setStockcost(stockCost);
        product.setStockvolume(stockVolume);
        product.setImage(image);

      return product;
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
    List<Product> list = populateProducts(db.query(DATABASE_TABLE,
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
          context.getString(R.string.tproduct_sendstatus),
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
          context.getString(R.string.tproduct_candiscount),
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
    close();

    return list;
  }

  public List<Product> getData(String str)
  {
    open();
    List<Product> list = populateProducts(db.query(DATABASE_TABLE,
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
          context.getString(R.string.tproduct_sendstatus),
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
          context.getString(R.string.tproduct_candiscount),
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
      context.getString(R.string.tproduct_code) + " LIKE '%" + str + "%' OR " +
        context.getString(R.string.tproduct_name) + " LIKE '%" + str + "%'",
      null, null, null, null));
    close();

    return list;
  }

  public Product getProductById(String id)
  {
    open();
    Cursor cursor = db.query(DATABASE_TABLE,
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
          context.getString(R.string.tproduct_sendstatus),
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
          context.getString(R.string.tproduct_candiscount),
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
      context.getString(R.string.tproduct_id) + " = '" + id + "'",
      null, null, null, null);

    if (cursor.moveToFirst())
      return populateProduct(cursor);
    else
      return null;
  }

  public Product getProduct(String code)
  {
    open();
    Cursor cursor = db.query(DATABASE_TABLE,
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
          context.getString(R.string.tproduct_sendstatus),
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
          context.getString(R.string.tproduct_candiscount),
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
      context.getString(R.string.tproduct_code) + " = '" + code + "'",
      null, null, null, null);

    if (cursor.moveToFirst())
      return populateProduct(cursor);
    else
      return null;
  }

  public Product getProductModule(String id)
  {
    Product product;

    open();
    Cursor cursor = db.query(DATABASE_TABLE,
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
          context.getString(R.string.tproduct_sendstatus),
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
          context.getString(R.string.tproduct_candiscount),
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
      context.getString(R.string.tproduct_id) + " = '" + id + "'",
      null, null, null, null);

    if (cursor.moveToFirst())
      product = populateProduct(cursor);
    else
      product = null;

    return product;
  }

  public String getId (String code)
  {
    String id = null;

    open();
    final String QUERY = "SELECT id FROM products WHERE code =?";
    Cursor cursor = db.rawQuery(QUERY, new String[]{code});
    if (cursor.moveToFirst())
    {
      id = cursor.getString(cursor.getColumnIndexOrThrow(context.getString(R.string.tproduct_id)));
      Log.e("Product ID: ", id);
    }
    close();

    return id;
  }

  private class HttpRequestTask extends AsyncTask<Void, Void, Product[]>
  {
    private String kodeMerchant;

    public HttpRequestTask(String kodeMerchant)
    {
      this.kodeMerchant = kodeMerchant;
    }

    @Override
    protected Product[] doInBackground(Void... params)
    {
      try
      {
        final String url = RestVariable.URL_GET_PRODUCT;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.set("merchantCode", kodeMerchant);
        HttpEntity<Product[]> entity = new HttpEntity<Product[]>(headers);
//        Product[] product = restTemplate.getForObject(url, Product[].class);
        ResponseEntity<Product[]> product = restTemplate.exchange(url, HttpMethod.GET, entity, Product[].class);
        return product.getBody();
      }
      catch (Exception e)
      {
        Log.e("TableProductHelper", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Product[] list)
    {
      open();
      deleteAll();
      if (list != null)
      {
        insert(list);
      }
      close();
    }
  }

  public void downloadDataAlternate(String kodeMerchant)
  {
    new HttpRequestTask(kodeMerchant).execute();
  }

  public void downloadData(final String kodeMerchant)
  {
    final Call<List<Product>> call = service.getProductAll(kodeMerchant);
    call.enqueue(new Callback<List<Product>>()
    {
      @Override
      public void onResponse(Call<List<Product>> call, Response<List<Product>> response)
      {
        final List<Product> list = response.body();
        open();
        deleteAll();
        if (list != null)
        {
          insert(list);
        }
        close();
      }

      @Override
      public void onFailure(Call<List<Product>> call, Throwable t)
      {
      }
    });
  }
}
