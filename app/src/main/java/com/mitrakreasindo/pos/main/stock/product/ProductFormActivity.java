package com.mitrakreasindo.pos.main.stock.product;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.ImageHelper;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.product.controller.ProductListAdapter;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.TaxCategory;
import com.mitrakreasindo.pos.service.ProductService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFormActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.appbar)
  AppBarLayout appbar;
  @BindView(R.id.main_content)
  CoordinatorLayout mainContent;
  @BindView(R.id.edittext_barcode)
  EditText edittextBarcode;
  @BindView(R.id.edittext_general_name)
  EditText edittextGeneralName;
  @BindView(R.id.edittext_general_shortname)
  EditText edittextGeneralShortname;
  @BindView(R.id.spinner_general_category)
  Spinner spinnerGeneralCategory;
  @BindView(R.id.edittext_general_sell_price)
  EditText edittextGeneralSellPrice;
  @BindView(R.id.edittext_stock_by_year)
  EditText edittextStockByYear;
  @BindView(R.id.edittext_stock_volume)
  EditText edittextStockVolume;
  @BindView(R.id.edittext_stock_pack_quantity)
  EditText edittextStockPackQuantity;
  @BindView(R.id.spinner_stock_of_product)
  Spinner spinnerStockOfProduct;
  @BindView(R.id.btn_save_product)
  Button btnSaveProduct;
  @BindView(R.id.switch_multi_pack)
  Switch switchMultiPack;
  @BindView(R.id.edittext_general_buy_price)
  EditText edittextGeneralBuyPrice;
  @BindView(R.id.btn_select_product_image)
  Button btnSelectProductImage;
  @BindView(R.id.imageview_product)
  ImageView imageviewProduct;

  private int RESULT_TAKE_PHOTO = 0;
  private int RESULT_PICK_GALLERY = 1;
  private Product product;
  private Category category;
  private ProductService productService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;
  private ArrayAdapter<Category> categoryArrayAdapter;
  private List<Category> dataCategory;
  private ArrayAdapter<Product> productArrayAdapter;
  private List<Product> dataProduct;
  private ProductListAdapter productListAdapter;
  private Bundle bundle;
  private boolean isPack = false;
  private String productId;
  private byte[] imageInByte;
  private Bitmap bitmap;
  private ByteArrayOutputStream baos;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_form);
    ButterKnife.bind(this);

    productService = ClientService.createService().create(ProductService.class);
    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "Company Code", "");
    bundle = getIntent().getExtras();

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    TableCategoryHelper tableCategoryHelper = new TableCategoryHelper(this);
    dataCategory = tableCategoryHelper.getData();
    categoryArrayAdapter = new ArrayAdapter<>(ProductFormActivity.this, android.R.layout.simple_spinner_dropdown_item, dataCategory);
    spinnerGeneralCategory.setAdapter(categoryArrayAdapter);

    TableProductHelper tableProductHelper = new TableProductHelper(this);
    dataProduct = tableProductHelper.getData();
    productArrayAdapter = new ArrayAdapter<>(ProductFormActivity.this, android.R.layout.simple_spinner_dropdown_item, dataProduct);
    spinnerStockOfProduct.setAdapter(productArrayAdapter);

    if (!switchMultiPack.isChecked())
    {
      edittextStockPackQuantity.setVisibility(View.GONE);
      spinnerStockOfProduct.setVisibility(View.GONE);
    }
    else
    {
      edittextStockPackQuantity.setVisibility(View.VISIBLE);
      spinnerStockOfProduct.setVisibility(View.VISIBLE);
      isPack = true;
    }

    switchMultiPack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
      {
        if (!switchMultiPack.isChecked())
        {
          edittextStockPackQuantity.setVisibility(View.GONE);
          spinnerStockOfProduct.setVisibility(View.GONE);
        }
        else
        {
          edittextStockPackQuantity.setVisibility(View.VISIBLE);
          spinnerStockOfProduct.setVisibility(View.VISIBLE);
        }
      }
    });

    productListAdapter = new ProductListAdapter(ProductFormActivity.this, new ArrayList<Product>());

    if (bundle != null)
    {
      product = (Product) bundle.getSerializable("product");
//      String id = bundle.getString("id");
//      String name = bundle.getString("name");
//      String barcode = bundle.getString("barcode");
//      String shortName = bundle.getString("shortName");
//      String buyPrice = bundle.getString("buyPrice");
//      String sellPrice = bundle.getString("sellPrice");
//      String stockCost = bundle.getString("stockCost");
//      String stockVolume = bundle.getString("stockVolume");
//      String categoryId = bundle.getString("category");
      byte[] image = product.getImage();
      Toast.makeText(this, product.getName(), Toast.LENGTH_LONG).show();

      productId = product.getId();
      edittextBarcode.setText(product.getCode());
      edittextGeneralName.setText(product.getName());
      edittextGeneralShortname.setText(product.getAlias());
      edittextGeneralBuyPrice.setText(product.getPricebuy().toString());
      edittextGeneralSellPrice.setText(product.getPricesell().toString());
      edittextStockByYear.setText(product.getStockcost().toString());
      edittextStockVolume.setText(product.getStockvolume().toString());
      int spinnerPosition = 0;
      if (!product.getCategory().getId().equals(null))
      {
        int i = 0;
        while (i < dataCategory.size())
        {
          if (dataCategory.get(i).getId().equals(product.getCategory().getId()))
          {
            spinnerPosition = i;
            break;
          }
          i++;
        }
        Log.d("ROLE_ID", String.valueOf(spinnerPosition));
        spinnerGeneralCategory.setSelection(spinnerPosition);
      }

      if (image != null)
      {
        if (image.length != 0)
        {
          Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
          DisplayMetrics dm = new DisplayMetrics();
          getWindowManager().getDefaultDisplay().getMetrics(dm);
          imageviewProduct.setMinimumHeight(dm.heightPixels);
          imageviewProduct.setMinimumWidth(dm.widthPixels);
          imageviewProduct.setImageBitmap(bm);
          imageviewProduct.setVisibility(View.VISIBLE);
        }
        else
        {
          imageviewProduct.setVisibility(View.INVISIBLE);
        }
      }
      else
      {
        imageviewProduct.setVisibility(View.INVISIBLE);
      }
    }

    btnSaveProduct.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if (bundle != null)
        {
          updateProduct();
        }
        else
        {
          postProduct();
        }
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_form_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_confirm)
    {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void SelectProductImage(View view)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.dialog_pick_image_title);
    builder.setItems(new String[]{getString(R.string.dialog_pick_image_camera), getString(R.string.dialog_pick_image_gallery)},
      new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
          switch (which)
          {
            case 0:
              Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              startActivityForResult(takePicture, RESULT_TAKE_PHOTO);
              break;

            case 1:
              Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(photoPickerIntent, RESULT_PICK_GALLERY);
              break;
          }
        }
      });
    builder.show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK)
    {
      try
      {
        final Uri imageUri = data.getData();

        switch (requestCode)
        {
          case 0:
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageviewProduct.setImageBitmap(bitmap);
            imageviewProduct.setVisibility(View.VISIBLE);
            break;

          case 1:
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageviewProduct.setImageBitmap(selectedImage);
            imageviewProduct.setVisibility(View.VISIBLE);
            break;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Toast.makeText(this, R.string.error_general, Toast.LENGTH_LONG).show();
      }
    }
    else
    {
      imageviewProduct.setVisibility(View.INVISIBLE);
    }
  }

  private void postProduct()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

    Category category = new Category();
    category.setId(dataCategory.get(spinnerGeneralCategory.getSelectedItemPosition()).getId());

    TaxCategory taxCategory = new TaxCategory();
    taxCategory.setId("001");

    final Product product = new Product();
    product.setId(UUID.randomUUID().toString());
    product.setName(edittextGeneralName.getText().toString());
    product.setAttributes(null);

    if (imageviewProduct.getVisibility() == View.VISIBLE)
    {
      bitmap = ((BitmapDrawable) imageviewProduct.getDrawable()).getBitmap();
      bitmap = ImageHelper.getResizedBitmap(bitmap, 150);
      baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
      imageInByte = baos.toByteArray();
    }
    else
    {
      imageInByte = null;
    }
    product.setImage(imageInByte);
    product.setReference(edittextBarcode.getText().toString());
    product.setCode(edittextBarcode.getText().toString());
    product.setCodetype(null);
    product.setPricebuy(Double.valueOf(edittextGeneralBuyPrice.getText().toString()));
    product.setPricesell(Double.valueOf(edittextGeneralSellPrice.getText().toString()));
    product.setStockcost(Double.valueOf(edittextStockByYear.getText().toString()));
    product.setStockvolume(Double.valueOf(edittextStockVolume.getText().toString()));
    product.setIscom(false);
    product.setIsscale(false);
    product.setIskitchen(false);
    product.setPrintkb(false);
    product.setSendstatus(false);
    product.setIsservice(false);
    product.setDisplay("");
    product.setIsvprice(false);
    product.setIsverpatrib(false);
    product.setTexttip("");
    product.setWarranty(false);
    product.setStockunits(30.0);
    product.setAlias("");
    product.setAlwaysavailable(false);
    product.setDiscounted("no");
    product.setCandiscount(false);
    product.setIscatalog(true);
    product.setCatorder(10);
    product.setIspack(false);
    product.setPackquantity(2.0);
    product.setAllproducts(false);
    product.setManagestock(false);
    product.setAlias(edittextGeneralShortname.getText().toString());
    product.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    product.setSflag(true);
    product.setAttributesetId(null);
    product.setCategory(category);
    product.setPackproduct(null);
    product.setPromotionid(null);
    product.setTaxcat(taxCategory);

    Call<HashMap<Integer, String>> call = productService.postProduct(kodeMerchant, product);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0)
          {
            TableProductHelper tableProductHelper = new TableProductHelper(ProductFormActivity.this);
            tableProductHelper.open();
            tableProductHelper.insert(product);
            tableProductHelper.close();
            productListAdapter.addProduct(product);
            productListAdapter.notifyDataSetChanged();

            progressDialog.dismiss();
          }
          Toast.makeText(ProductFormActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        if (responseCode == 0)
        {
          TableProductHelper tableProductHelper = new TableProductHelper(ProductFormActivity.this);
          tableProductHelper.open();
          tableProductHelper.insert(product);
          tableProductHelper.close();
          productListAdapter.addProduct(product);
          productListAdapter.notifyDataSetChanged();

          progressDialog.dismiss();
        }
        progressDialog.dismiss();
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(ProductFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }

  private void updateProduct()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    progressDialog.show();

    Category category = new Category();
    category.setId(dataCategory.get(spinnerGeneralCategory.getSelectedItemPosition()).getId());

    TaxCategory taxCategory = new TaxCategory();
    taxCategory.setId("001");

    final Product product = new Product();
    product.setId(productId);
    product.setName(edittextGeneralName.getText().toString());
    product.setAttributes(null);

    if (imageviewProduct.getVisibility() == View.VISIBLE)
    {
      bitmap = ((BitmapDrawable) imageviewProduct.getDrawable()).getBitmap();
      bitmap = ImageHelper.getResizedBitmap(bitmap, 150);
      baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
      imageInByte = baos.toByteArray();
    }
    else
    {
      imageInByte = null;
    }

    product.setImage(imageInByte);
    product.setReference(edittextBarcode.getText().toString());
    product.setCode(edittextBarcode.getText().toString());
    product.setCodetype(null);
    product.setPricebuy(Double.valueOf(edittextGeneralBuyPrice.getText().toString()));
    product.setPricesell(Double.valueOf(edittextGeneralSellPrice.getText().toString()));
    product.setStockcost(Double.valueOf(edittextStockByYear.getText().toString()));
    product.setStockvolume(Double.valueOf(edittextStockVolume.getText().toString()));
    product.setIscom(false);
    product.setIsscale(false);
    product.setIskitchen(false);
    product.setPrintkb(false);
    product.setSendstatus(false);
    product.setIsservice(false);
    product.setDisplay("");
    product.setIsvprice(false);
    product.setIsverpatrib(false);
    product.setTexttip("");
    product.setWarranty(false);
    product.setStockunits(30.0);
    product.setAlias("");
    product.setAlwaysavailable(false);
    product.setDiscounted("no");
    product.setCandiscount(false);
    product.setIscatalog(true);
    product.setCatorder(10);
    product.setIspack(false);
    product.setPackquantity(2.0);
    product.setAllproducts(false);
    product.setManagestock(false);
    product.setAlias(edittextGeneralShortname.getText().toString());
    product.setSiteguid("a73c83f2-3c42-42a7-8f19-7d7cbea17286");
    product.setSflag(true);
    product.setAttributesetId(null);
    product.setCategory(category);
    product.setPackproduct(null);
    product.setPromotionid(null);
    product.setTaxcat(taxCategory);

    Call<HashMap<Integer, String>> call = productService.updateProduct(kodeMerchant, product.getId(), product);
    call.enqueue(new Callback<HashMap<Integer, String>>()
    {
      private int responseCode;
      private String responseMessage;

      @Override
      public void onResponse(Call<HashMap<Integer, String>> call, Response<HashMap<Integer, String>> response)
      {
        final HashMap<Integer, String> data = response.body();
        for (int resultKey : data.keySet())
        {
          responseCode = resultKey;
          responseMessage = data.get(resultKey);

          Log.e("RESPONSE ", responseMessage);
          if (responseCode == 0)
          {
            TableProductHelper tableProductHelper = new TableProductHelper(ProductFormActivity.this);
            tableProductHelper.open();
            tableProductHelper.update(product);
            tableProductHelper.close();

            progressDialog.dismiss();
          }
          Toast.makeText(ProductFormActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
        }
        finish();
      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
        progressDialog.dismiss();
        responseCode = -1;
        responseMessage = getString(R.string.error_webservice);
        Toast.makeText(ProductFormActivity.this, responseMessage, Toast.LENGTH_LONG).show();
      }
    });
  }
}
