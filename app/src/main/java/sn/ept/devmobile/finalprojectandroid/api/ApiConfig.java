package sn.ept.devmobile.finalprojectandroid.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sn.ept.devmobile.finalprojectandroid.services.ProductService;

public class ApiConfig {
    private static final String API_URL =  "https://fakestoreapi.com";

    public static ProductService getApiClient(){
        return getApiService(API_URL).create(ProductService.class);
    }

    public static Retrofit getApiService(String url){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
