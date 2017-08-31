package com.mitrakreasindo.pos.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;

/**
 * Created by hendric on 2017-08-31.
 */

public interface PaymentService
{
  @PUT("payments")
  Call<HashMap<Integer, String>> updatePayment(@Header("merchantCode") String kodeMerchant,
                                               @Header("receiptId") String receiptId,
                                               @Header("tendered_amount") Double tendered);
}
