package com.mitrakreasindo.pos.main.stock.product;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.SharedPreferenceEditor;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.product.controller.ProductListAdapter;
import com.mitrakreasindo.pos.main.stock.product.fragment.ProductGeneralFragment;
import com.mitrakreasindo.pos.main.stock.product.fragment.ProductImageFragment;
import com.mitrakreasindo.pos.main.stock.product.fragment.ProductStockFragment;
import com.mitrakreasindo.pos.model.Category;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.model.TaxCategory;
import com.mitrakreasindo.pos.service.ProductService;

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

  //  @BindView(R.id.toolbar)
//  Toolbar toolbar;
//  @BindView(R.id.tabs)
//  TabLayout tabs;
//  @BindView(R.id.appbar)
//  AppBarLayout appbar;
//  @BindView(R.id.container)
//  ViewPager container;
//  @BindView(R.id.btn_save_product)
//  Button btnSaveProduct;
//  @BindView(R.id.main_content)
//  CoordinatorLayout mainContent;
//
  private Product product;
  private ProductService productService;
  private SharedPreferenceEditor sharedPreferenceEditor;
  private String kodeMerchant;
  private ProductListAdapter productListAdapter;
//
//  private SectionsPagerAdapter mSectionsPagerAdapter;
//
//  private ViewPager mViewPager;
//
//  private ProductService productService;
//
//  private EditText edittextReference;
//  private EditText edittextBarcode;
//  private EditText edittextGeneralName;
//  private EditText edittextGeneralShortname;
//  private Spinner spinnerGeneralProduct;
//  private EditText edittextGeneralBuyprice;
//  private EditText edittextGeneralSellPrice;

  private TabLayout tabLayout;
  public ViewPager viewPager;
  private ViewPagerAdapter viewPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_form);
    ButterKnife.bind(this);

    productService = ClientService.createService().create(ProductService.class);
    sharedPreferenceEditor = new SharedPreferenceEditor();
    kodeMerchant = sharedPreferenceEditor.LoadPreferences(this, "");

//    productListAdapter = new ProductListAdapter(this, new ArrayList<Product>());

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    viewPager = (ViewPager) findViewById(R.id.container);
    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

    viewPagerAdapter.addFragment(new ProductGeneralFragment(), "GENERAL");
    viewPagerAdapter.addFragment(new ProductStockFragment(), "STOCK");
    viewPagerAdapter.addFragment(new ProductImageFragment(), "IMAGE");
    viewPager.setAdapter(viewPagerAdapter);

    tabLayout.setupWithViewPager(viewPager);


//    if (viewPager.getCurrentItem() == 0)
//    {
//      ProductGeneralFragment generalFragment = (ProductGeneralFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
//      generalFragment.edittextReference.setText("NICE CASE");
//    }
//    edittextReference = (EditText) findViewById(R.id.edittext_reference);
//    edittextBarcode = (EditText) findViewById(R.id.edittext_barcode);
//    edittextGeneralName = (EditText) findViewById(R.id.edittext_general_name);
//    edittextGeneralShortname = (EditText) findViewById(R.id.edittext_general_shortname);
//    spinnerGeneralProduct = (Spinner) findViewById(R.id.spinner_general_product);
//    edittextGeneralBuyprice = (EditText) findViewById(R.id.edittext_general_buyprice);
//    edittextGeneralSellPrice = (EditText) findViewById(R.id.edittext_general_sell_price);

//    ProductStockFragment.edittextReference.setText("Tes tes Tes");

//    setSupportActionBar(toolbar);
//
//    productService = ClientService.createService().create(ProductService.class);
//
//    toolbar.setNavigationOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View v)
//      {
//        onBackPressed();
//      }
//    });
//
//    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//
//    mViewPager = (ViewPager) findViewById(R.id.container);
//    mViewPager.setAdapter(mSectionsPagerAdapter);
//
//    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//    tabLayout.setupWithViewPager(mViewPager);
//
//    btnSaveProduct.setOnClickListener(new View.OnClickListener()
//    {
//      @Override
//      public void onClick(View v)
//      {
//        Toast.makeText(ProductFormActivity.this, "Test Save", Toast.LENGTH_LONG).show();
//      }
//    });

  }

  private class ViewPagerAdapter extends FragmentPagerAdapter
  {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
      super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
      return mFragmentList.get(position);
    }

    @Override
    public int getCount()
    {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title)
    {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
      return mFragmentTitleList.get(position);
    }

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

    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

//  public void getFromGeneral(String message) {
//    Log.e("MESSAGE", message);
//  }


  private void postProduct()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();

    Category category = new Category();
    category.setId("000");

    TaxCategory taxCategory = new TaxCategory();
    taxCategory.setId("001");

    final Product product = new Product();
    product.setId(UUID.randomUUID().toString());
    product.setName("Product Dummy");
    product.setAttributes(null);
    product.setImage(null);
    product.setReference("98979695");
    product.setCode("98979695");
    product.setCodetype("CODE998");
    product.setPricebuy(20.0);
    product.setPricesell(30.0);
    product.setStockcost(19.0);
    product.setStockvolume(200.0);
    product.setIscom(false);
    product.setIsscale(false);
    product.setIskitchen(false);
    product.setPrintkb(false);
    product.setSendstatus(false);
    product.setIsservice(false);
    product.setDisplay("<html>Tes");
    product.setIsvprice(false);
    product.setIsverpatrib(false);
    product.setTexttip("tessss");
    product.setWarranty(false);
    product.setStockunits(30.0);
    product.setAlias("tessssss");
    product.setAlwaysavailable(false);
    product.setDiscounted("no");
    product.setCandiscount(false);
    product.setIscatalog(true);
    product.setCatorder(10);
    product.setIspack(false);
    product.setPackquantity(2.0);
    product.setAllproducts(false);
    product.setManagestock(false);
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
//            TableProductHelper tableProductHelper = new TableProductHelper(ProductFormActivity.this);
//            tableProductHelper.open();
//            tableProductHelper.insert(product);
//            tableProductHelper.close();
//            productListAdapter.addProduct(product);
//            productListAdapter.notifyDataSetChanged();
          }
          Log.d(getClass().getSimpleName(), "Success Post Product !!!");
          Toast.makeText(ProductFormActivity.this, "Succesfull add product", Toast.LENGTH_SHORT).show();
        }

        finish();

      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
      }
    });


  }

  private void updateProduct()
  {
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Please wait...");
    progressDialog.show();

    Category category = new Category();
    category.setId("000");

    TaxCategory taxCategory = new TaxCategory();
    taxCategory.setId("001");

    final Product product = new Product();
    product.setId(UUID.randomUUID().toString());
    product.setName("Product Dummy");
    product.setAttributes(null);
    product.setImage(null);
    product.setReference("98979695");
    product.setCode("98979695");
    product.setCodetype("CODE998");
    product.setPricebuy(20.0);
    product.setPricesell(30.0);
    product.setStockcost(19.0);
    product.setStockvolume(200.0);
    product.setIscom(false);
    product.setIsscale(false);
    product.setIskitchen(false);
    product.setPrintkb(false);
    product.setSendstatus(false);
    product.setIsservice(false);
    product.setDisplay("<html>Tes");
    product.setIsvprice(false);
    product.setIsverpatrib(false);
    product.setTexttip("tessss");
    product.setWarranty(false);
    product.setStockunits(30.0);
    product.setAlias("tessssss");
    product.setAlwaysavailable(false);
    product.setDiscounted("no");
    product.setCandiscount(false);
    product.setIscatalog(true);
    product.setCatorder(10);
    product.setIspack(false);
    product.setPackquantity(2.0);
    product.setAllproducts(false);
    product.setManagestock(false);
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
//            TableProductHelper tableProductHelper = new TableProductHelper(ProductFormActivity.this);
//            tableProductHelper.open();
//            tableProductHelper.insert(product);
//            tableProductHelper.close();
//            productListAdapter.addProduct(product);
//            productListAdapter.notifyDataSetChanged();
          }
          Log.d(getClass().getSimpleName(), "Success Post Product !!!");
          Toast.makeText(ProductFormActivity.this, "Succesfull add product", Toast.LENGTH_SHORT).show();
        }

        finish();

      }

      @Override
      public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
      {
      }
    });


  }

}
