package com.mitrakreasindo.pos.main.stock.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.ClientService;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.product.controller.ProductListAdapter;
import com.mitrakreasindo.pos.main.stock.product.model.Product;
import com.mitrakreasindo.pos.main.stock.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductActivity extends AppCompatActivity
{

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list_product)
  RecyclerView listProduct;
  @BindView(R.id.txt_action_toolbar)
  public TextView txtActionToolbar;
  @BindView(R.id.main_content)
  ConstraintLayout mainContent;

  private ProductService productService;
  private ProductListAdapter productListAdapter;
  private Product product;

  public boolean is_action_mode = false;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    productService = ClientService.createService().create(ProductService.class);

    productListAdapter = new ProductListAdapter(this, new ArrayList<Product>());

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

    getProducts();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.menu_product, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();

    if (id == R.id.product_action_add)
    {
      startActivity(new Intent(this, ProductFormActivity.class));
    }
//    if (id == R.id.menu_action)
//    {
//      toolbar.getMenu().clear();
//      toolbar.inflateMenu(R.menu.menu_action_mode);
//      txtActionToolbar.setVisibility(View.VISIBLE);
//      is_action_mode = true;
//      productListAdapter.notifyDataSetChanged();
//
//      return true;
//    }

    return true;
  }

  private void getProducts()
  {
    final Call<List<Product>> listCall = productService.getProductAll();
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

  public void fabAddProduct(View view)
  {
    startActivity(new Intent(this, ProductFormActivity.class));
  }


}
