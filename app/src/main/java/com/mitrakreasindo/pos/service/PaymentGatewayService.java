package com.mitrakreasindo.pos.service;

import com.mitrakreasindo.pos.model.TransactionPurchaseCC;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hendric on 2017-08-10.
 */

public interface PaymentGatewayService
{
  @POST("trxPurchaseCC")
  Call<TransactionPurchaseCC> PostTransactionPurchaseCC(@Body TransactionPurchaseCC transactionPurchaseCC);
}
