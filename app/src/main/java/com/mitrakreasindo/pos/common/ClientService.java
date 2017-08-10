package com.mitrakreasindo.pos.common;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by error on 19/05/17.
 */

public class ClientService
{
  public static Retrofit createService()
  {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // add your other interceptors …
    // add logging as last interceptor
    httpClient.addInterceptor(logging);  // <-- this is the important line!

    return new Retrofit.Builder()
      .baseUrl(RestVariable.SERVER_URL)
      .addConverterFactory(JacksonConverterFactory.create())
      .client(httpClient.build())
      .build();
  }

  public static Retrofit createPaymentGatewayService()
  {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // add your other interceptors …
    // add logging as last interceptor
    httpClient.addInterceptor(logging);  // <-- this is the important line!

    return new Retrofit.Builder()
      .baseUrl(RestVariable.URL_PAYMENT_GATEWAY)
      .addConverterFactory(JacksonConverterFactory.create())
      .client(httpClient.build())
      .build();
  }
}
