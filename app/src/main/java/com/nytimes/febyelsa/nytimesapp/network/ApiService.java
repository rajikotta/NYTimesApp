package com.nytimes.febyelsa.nytimesapp.network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nytimes.febyelsa.nytimesapp.interfaces.ResponseListener;
import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;
import com.nytimes.febyelsa.nytimesapp.utils.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String TAG = "API SERVICE";
    private Retrofit retrofit = null;
    private ApiInterface apiInterface;

    public void connectToNetwork() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);

    }

    public void startApiServiceCall(ResponseListener responseListener) {

        MutableLiveData<List<ArticleEntity>> listLiveData = new MutableLiveData<>();

        Call<ServiceResponse> call = apiInterface.getArticlesList();
        call.enqueue(new Callback<ServiceResponse>() {

            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                Log.v(TAG, "getting response");
                Log.v(TAG, "" + response.body());
                listLiveData.setValue(response.body().getPopularArticles());
                responseListener.onResponse(Constants.STATUS_SUCCESS,listLiveData);
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Log.v(TAG, "CAll failed");
                call.cancel();
                responseListener.onResponse(Constants.STATUS_FAILURE,listLiveData);

            }
        });
    }

}
