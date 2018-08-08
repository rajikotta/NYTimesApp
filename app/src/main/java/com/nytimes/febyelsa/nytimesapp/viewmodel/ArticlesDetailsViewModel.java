package com.nytimes.febyelsa.nytimesapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.nytimes.febyelsa.nytimesapp.interfaces.ResponseListener;
import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;
import com.nytimes.febyelsa.nytimesapp.di.AppModule;
import com.nytimes.febyelsa.nytimesapp.di.DaggerAppComponent;
import com.nytimes.febyelsa.nytimesapp.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class ArticlesDetailsViewModel extends AndroidViewModel {

    @Inject
    Repository repository;
    long articleId;
    String url;

    ResponseListener responseListener = new ResponseListener() {
        @Override
        public void onResponse(String status, MutableLiveData<List<ArticleEntity>> mutableLiveData) {

            selectedArticle.setValue(mutableLiveData.getValue().get(0));
        }
    };

    MutableLiveData<ArticleEntity> selectedArticle = new MutableLiveData<>();


    public MutableLiveData<ArticleEntity> getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(MutableLiveData<ArticleEntity> selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public ArticlesDetailsViewModel(Application application) {
        super(application);
        goDagger();
        getArticles();
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void getArticles() {
        selectedArticle.setValue(repository.getEntity(articleId,selectedArticle).getValue());

//        if(null!=selectedArticle.getValue()){
//            repository.loadArticleDetails(url,responseListener);
//        }
    }
    private void goDagger() {
        DaggerAppComponent.builder().application(getApplication()).build().inject(this);
    }



}
