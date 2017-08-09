package com.mitrakreasindo.pos.common;

/**
 * Created by error on 19/05/17.
 */

public class RestVariable
{
    public static final String SERVER_URL = "http://192.168.4.126:8082/api/";//192.168.1.113
    public static final String URL_GET_PRODUCT = "http://192.168.4.126:8082/api/products/{kodeMerchant}/";
    public static final String URL_GET_PEOPLE = "http://192.168.4.126:8082/api/peoples/{kodeMerchant}/";
    public static final String URL_GET_TAX = "http://192.168.4.126:8082/api/taxes/public/";

//    public static final String SERVER_URL = "http://192.168.4.126:8081/MKChromisServices/webresources/";
//    public static final String URL_GET_PRODUCT = "http://192.168.4.126:8081/MKChromisServices/webresources/chromis.products/{kodeMerchant}/";
//    public static final String URL_GET_PEOPLE = "http://192.168.4.126:8081/MKChromisServices/webresources/chromis.people/{kodeMerchant}/";
//    public static final String URL_GET_TAX = "http://192.168.4.126:8081/MKChromisServices/webresources/chromis.taxes/public/";
}
