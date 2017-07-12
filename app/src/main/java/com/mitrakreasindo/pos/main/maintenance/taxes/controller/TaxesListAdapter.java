package com.mitrakreasindo.pos.main.maintenance.taxes.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mitrakreasindo.pos.common.ClientService;
import com.mitrakreasindo.pos.common.TableHelper.TableTaxesHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.maintenance.taxes.TaxesFormActivity;
import com.mitrakreasindo.pos.main.maintenance.taxes.service.TaxService;
import com.mitrakreasindo.pos.model.Tax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hendric on 2017-05-29.
 */

public class TaxesListAdapter extends RecyclerView.Adapter<TaxesListAdapter.ViewHolder>
{
  private List<Tax> taxes = new ArrayList<Tax>();
  private Context context;
  private LayoutInflater inflater;
  private TaxService taxService;


  public TaxesListAdapter(Context context, List<Tax> taxes)
  {
    this.context = context;
    this.taxes = taxes;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.adapter_tax, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(TaxesListAdapter.ViewHolder holder, int position)
  {

    final Tax tax = taxes.get(position);
    holder.txtTax.setText(tax.getName());

    //On Long Click
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
    {
      @Override
      public boolean onLongClick(final View v)
      {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Options");
        builder.setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
            switch (which)
            {
              case 0:
                Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, TaxesFormActivity.class);
                intent.putExtra("id", tax.getId());
                intent.putExtra("name", tax.getName());
                intent.putExtra("rate", String.valueOf(tax.getRate()));
                intent.putExtra("category", tax.getCategory().getId());
                context.startActivity(intent);
                break;

              case 1:
                Toast.makeText(context, "Tax Deleted", Toast.LENGTH_LONG).show();
                taxService = ClientService.createService().create(TaxService.class);
                Call<HashMap<Integer, String>> call = taxService.deleteTax(tax.getId());
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
                        TableTaxesHelper tableTaxesHelper = new TableTaxesHelper(context);
                        tableTaxesHelper.open();
                        tableTaxesHelper.delete(tax.getId());
                        tableTaxesHelper.close();
                      }
                      Toast.makeText(context, "Succesfull delete taxes", Toast.LENGTH_SHORT).show();
                    }
                  }
                  @Override
                  public void onFailure(Call<HashMap<Integer, String>> call, Throwable t)
                  {
                  }
                });

                removeTax(tax);
                Toast.makeText(context, "Tax deleted!", Toast.LENGTH_LONG).show();
                break;
            }
          }
        });
        builder.show();
        return false;
      }
    });
  }

  public void addTax(Tax tax)
  {
    taxes.add(tax);
    notifyDataSetChanged();
  }

  public void clear()
  {
    taxes.clear();
    notifyDataSetChanged();
  }

  public void addTax(List<Tax> taxes)
  {
    this.taxes.addAll(taxes);
    notifyDataSetChanged();
  }

  public void removeTax(Tax tax)
  {
    taxes.remove(tax);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount()
  {
    return taxes.size();
  }

  @Override
  public long getItemId(int id)
  {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {

    private TextView txtTax;

    public ViewHolder(View itemView)
    {
      super(itemView);
      txtTax = (TextView) itemView.findViewById(R.id.txt_tax);
    }
  }

}
