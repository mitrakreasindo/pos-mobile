package com.mitrakreasindo.pos;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by error on 19/05/17.
 */

public class ClientService {

    public static Retrofit createService(){
        return new Retrofit.Builder()
                .baseUrl(RestVariable.SERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
