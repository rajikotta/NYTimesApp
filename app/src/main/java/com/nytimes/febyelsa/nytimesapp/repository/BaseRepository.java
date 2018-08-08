package com.nytimes.febyelsa.nytimesapp.repository;

import android.app.Application;

import com.nytimes.febyelsa.nytimesapp.database.ArticlesDao;
import com.nytimes.febyelsa.nytimesapp.di.AppModule;
import com.nytimes.febyelsa.nytimesapp.di.DaggerAppComponent;
import com.nytimes.febyelsa.nytimesapp.network.ApiService;

import javax.inject.Inject;

public class BaseRepository {

     ArticlesDao articlesDao;
     ApiService apiService;
     Application application;

    @Inject
    public BaseRepository(Application application, ArticlesDao articlesDao, ApiService service) {
        this.application = application;
        this.articlesDao = articlesDao;
        this.apiService = service;
        goDagger();
    }

    public BaseRepository() {
        //default constructor
    }

    private void goDagger() {
        DaggerAppComponent.builder().application(application).build().inject(this);
    }

    public ArticlesDao getArticlesDao() {
        return articlesDao;
    }

    public ApiService getApiService() {
        return apiService;
    }

}
