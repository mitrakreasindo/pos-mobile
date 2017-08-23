package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.SalesPack;
import com.mitrakreasindo.pos.model.ViewSalesItem;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lisa on 31/07/17.
 */

public interface SalesService
{
  @POST("sales/")
  Call<HashMap<Integer, String>> postSales(@Header("merchantCode") String kodeMerchant,
                                           @Body SalesPack salesPack);

  @GET("viewsales/{salesid}/salesitems")
  Call<List<ViewSalesItem>> getSalesItemBySalesId(@Header("merchantCode") String kodeMerchant,
                                                  @Path("salesid") String salesId);
}
