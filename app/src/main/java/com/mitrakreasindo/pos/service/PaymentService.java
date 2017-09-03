package com.mitrakreasindo.pos.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by hendric on 2017-08-31.
 */

public interface PaymentService
{
  @PUT("payments")
  Call<HashMap<Integer, String>> updatePayment(@Header("merchantCode") String kodeMerchant,
                                               @Query("receiptId") String receiptId,
                                               @Query("tendered_amount") Double tendered);
}
