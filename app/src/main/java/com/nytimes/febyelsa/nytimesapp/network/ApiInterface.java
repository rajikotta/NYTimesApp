package com.nytimes.febyelsa.nytimesapp.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=54e5496eb75443aea29abca3eda6dbf6")
    Call<ServiceResponse> getArticlesList();
}
