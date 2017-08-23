package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.ViewPendingTransaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by hendric on 2017-08-15.
 */

public interface PendingTransactionService
{
  @GET("viewunpaidtrans/")
  Call<List<ViewPendingTransaction>> getAllPendingTransaction(@Header("merchantCode") String kodeMerchant);
}
