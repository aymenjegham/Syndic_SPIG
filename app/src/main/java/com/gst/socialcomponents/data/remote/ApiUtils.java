package com.gst.socialcomponents.data.remote;

public class ApiUtils {


    private ApiUtils() {}

   //public static final String BASE_URL = "http://egms.tn10.net:90";
    //public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    //public static final String BASE_URL = "http://192.168.1.42:81";
   public static final String BASE_URL = "http://syndicspig.gloulougroupe.com";



    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
