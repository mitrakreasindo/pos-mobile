package com.mitrakreasindo.pos.common;

/**
 * Created by error on 19/05/17.
 */

public class RestVariable
{
    public static final String SERVER_URL = "http://192.168.1.113:8082/api/";
    public static final String URL_GET_PRODUCT = "http://192.168.1.113:8082/api/products/{kodeMerchant}/";
    public static final String URL_GET_PEOPLE = "http://192.168.1.113:8082/api/peoples/{kodeMerchant}/";
    public static final String URL_GET_TAX = "http://192.168.1.113:8082/api/taxes/public/";

//    public static final String SERVER_URL = "http://192.168.4.126:8082/api/";//192.168.1.113
//    public static final String URL_GET_PRODUCT = "http://192.168.4.126:8082/api/products/{kodeMerchant}/";
//    public static final String URL_GET_PEOPLE = "http://192.168.4.126:8082/api/peoples/{kodeMerchant}/";
//    public static final String URL_GET_TAX = "http://192.168.4.126:8082/api/taxes/public/";

    public static final String URL_PAYMENT_GATEWAY = "http://118.97.80.85:8080/ProxyGateway/rest/";

    public static final String SITE_GUID = "a73c83f2-3c42-42a7-8f19-7d7cbea17286";
}
