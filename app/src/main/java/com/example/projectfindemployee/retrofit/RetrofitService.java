package com.example.projectfindemployee.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService(){
        initializeRetrofit();
    }
    private void  initializeRetrofit(){
        retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.0.103:9000")//url
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//использование библиотеки конвертера
                .build();//сборка
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
