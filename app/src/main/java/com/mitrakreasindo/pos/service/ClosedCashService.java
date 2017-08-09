package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.ClosedCash;
import com.mitrakreasindo.pos.model.Viewclosedcash;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-08-07.
 */

public interface ClosedCashService
{
  @GET("closedcash/{kodeMerchant}")
  Call<List<Viewclosedcash>> getClosedCashAll(@Path("kodeMerchant") String kodeMerchant);

  @GET("closedcash/{kodeMerchant}/money/{moneyId}")
  Call<List<Viewclosedcash>> getClosedCashByMoney(@Path("kodeMerchant") String kodeMerchant,
                                                  @Path("moneyId") String moneyId);

  @GET("closedcash/{kodeMerchant}/{receipt_id}")
  Call<List<Viewclosedcash>> getClosedCashByReceipt(@Path("kodeMerchant") String kodeMerchant,
                                                    @Path("receipt_id") String receipt_id);

  @POST("closedcashes/{kodeMerchant}/")
  Call<HashMap<Integer, String>> postClosedCash(@Path("kodeMerchant") String kodeMerchant,
                                                @Body ClosedCash closedCash);
}
