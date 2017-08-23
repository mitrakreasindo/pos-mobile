package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.ClosedCash;
import com.mitrakreasindo.pos.model.Viewclosedcash;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-08-07.
 */

public interface ClosedCashService
{
  @GET("closedcash/")
  Call<List<Viewclosedcash>> getClosedCashAll(@Header("merchantCode") String kodeMerchant);

  @GET("closedcash/money/{moneyId}")
  Call<List<Viewclosedcash>> getClosedCashByMoney(@Header("merchantCode") String kodeMerchant,
                                                  @Path("moneyId") String moneyId);

  @GET("closedcash/{receipt_id}")
  Call<List<Viewclosedcash>> getClosedCashByReceipt(@Header("merchantCode") String kodeMerchant,
                                                    @Path("receipt_id") String receipt_id);

  @POST("closedcashes/")
  Call<HashMap<Integer, String>> postClosedCash(@Header("merchantCode") String kodeMerchant,
                                                @Body ClosedCash closedCash);
}
