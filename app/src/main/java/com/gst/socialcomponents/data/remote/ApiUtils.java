package com.gst.socialcomponents.data.remote;

public class ApiUtils {


    private ApiUtils() {}

   // public static final String BASE_URL = "http://egms.tn10.net:90";
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";


    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
