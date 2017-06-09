package com.mitrakreasindo.pos.main.stock.product;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.main.R;

import com.mitrakreasindo.pos.model.Product;
import com.mitrakreasindo.pos.service.ProductService;


public class ProductFormActivity extends AppCompatActivity
{

  private SectionsPagerAdapter mSectionsPagerAdapter;

  private ViewPager mViewPager;

  private ProductService productService;


  private Product product;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_form);

    productService = ClientService.createService().create(ProductService.class);


    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        onBackPressed();
      }
    });

    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);



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

  public static class PlaceholderFragment extends Fragment
  {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {}

    public static PlaceholderFragment newInstance(int sectionNumber)
    {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
      if(getArguments().getInt(ARG_SECTION_NUMBER) == 1)
      {
        View rootView = inflater.inflate(R.layout.fragment_product_general, container, false);
        return rootView;
      }
      else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2)
      {
        View rootView = inflater.inflate(R.layout.fragment_product_stock, container, false);
        return rootView;
      }

      else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3)
      {
        View rootView = inflater.inflate(R.layout.fragment_product_image, container, false);
        return rootView;
      }

      View rootView = inflater.inflate(R.layout.fragment_product, container, false);
      TextView textView = (TextView) rootView.findViewById(R.id.section_label);
      textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
      return rootView;

    }
  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter
  {

    public SectionsPagerAdapter(FragmentManager fm)
    {
      super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
      return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount()
    {
      return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
      switch (position)
      {
        case 0:
          return "General";
        case 1:
          return "Stock";
        case 2:
          return "Image";
      }
      return null;
    }
  }

  private void getProducts(){

  }
}
