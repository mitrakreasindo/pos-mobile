package com.mitrakreasindo.pos.main.stock.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.MenuIds;
import com.mitrakreasindo.pos.common.PermissionUtil;
import com.mitrakreasindo.pos.common.TableHelper.TableProductHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.product.controller.ProductListAdapter;
import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar_layout)
  public Toolbar toolbar;
  @BindView(R.id.list_product)
  RecyclerView listProduct;
  @BindView(R.id.txt_action_toolbar)
  public TextView txtActionToolbar;
  @BindView(R.id.main_content)
  LinearLayout mainContent;
  @BindView(R.id.edit_filter)
  EditText txtFilter;
  @BindView(R.id.button_filter)
  Button btnClearFilter;

  private ProductService productService;
  private ProductListAdapter productListAdapter;
  private TableProductHelper tableProductHelper;

//  private SharedPreferenceEditor sharedPreferenceEditor;
  public boolean is_action_mode = false;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product);
    ButterKnife.bind(this);

    productService = ClientService.createService().create(ProductService.class);
    productListAdapter = new ProductListAdapter(this, new ArrayList<Product>());

    setSupportActionBar(toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    btnClearFilter.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
        txtFilter.setText("");
      }
    });

    listProduct.setHasFixedSize(true);
    listProduct.setAdapter(productListAdapter);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    listProduct.setLayoutManager(layoutManager);

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    if (!is_action_mode)
    {
      txtActionToolbar.setVisibility(View.GONE);
    }
    else
    {
      txtActionToolbar.setVisibility(View.VISIBLE);
    }

    tableProductHelper = new TableProductHelper(this);

    productListAdapter.clear();
    productListAdapter.addProduct(tableProductHelper.getData());

//    getProducts();
    txtFilter.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        productListAdapter.clear();
        productListAdapter.addProduct(tableProductHelper.getData(txtFilter.getText().toString()));
      }
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      {
      }
      @Override
      public void afterTextChanged(Editable s)
      {
      }
    });


  }

  @Override
  public void onResume(){
    super.onResume();
    productListAdapter.clear();
    productListAdapter.addProduct(tableProductHelper.getData());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.default_list_menu, menu);
    MenuItem menuInsert = menu.findItem(R.id.action_add);
    if (PermissionUtil.getInactive(this, "stock_product_action").contains(MenuIds.rp_stk_product_action_insert))
    {
      menuInsert.setVisible(false);
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.action_add)
    {
      startActivity(new Intent(this, ProductFormActivity.class));
    }
    else if (id == R.id.menu_delete)
    {
//      productListAdapter.updateCounter(productListAdapter.list_product);
      productListAdapter.deleteMultipleProduct();
    }
    return super.onOptionsItemSelected(item);
  }

  private void getProducts(String kodeMerchant)
  {
    final Call<List<Product>> listCall = productService.getProductAll(kodeMerchant);
    listCall.enqueue(new Callback<List<Product>>()
    {
      @Override
      public void onResponse(Call<List<Product>> call, Response<List<Product>> response)
      {
        List<Product> productList = response.body();
        Log.d("DATA :: ", productList.toString());
        productListAdapter.clear();
        productListAdapter.addProduct(productList);
      }

      @Override
      public void onFailure(Call<List<Product>> call, Throwable t)
      {

      }
    });
  }
}
