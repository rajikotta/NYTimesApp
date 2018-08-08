package com.nytimes.febyelsa.nytimesapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.nytimes.febyelsa.nytimesapp.R;
import com.nytimes.febyelsa.nytimesapp.interfaces.ResponseListener;
import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;
import com.nytimes.febyelsa.nytimesapp.di.AppModule;
import com.nytimes.febyelsa.nytimesapp.di.DaggerAppComponent;
import com.nytimes.febyelsa.nytimesapp.repository.Repository;
import com.nytimes.febyelsa.nytimesapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class ArticlesViewModel extends AndroidViewModel {

    @Inject
    Repository repository;

    MutableLiveData<List<ArticleEntity>> mutableLiveData = new MutableLiveData<>();

    ResponseListener responseListener = (status, mutableLiveData) ->{

        this.mutableLiveData.setValue(mutableLiveData.getValue());

        if(Constants.STATUS_FAILURE.equalsIgnoreCase(status)){
            Toast.makeText(getApplication(),getApplication().getString(R.string.msg_failed),Toast.LENGTH_SHORT).show();
        }

    };

     ArticlesViewModel(Application application) {
        super(application);
        goDagger();
        loadArticles();
    }
    public void loadArticles() {
       getArticles();
    }
    private void getArticles(){
        repository.loadArticles(responseListener);
    }

    public MutableLiveData<List<ArticleEntity>> getMutableLiveData() {
        return mutableLiveData;
    }

    private void goDagger() {
        DaggerAppComponent.builder().application(getApplication()).build().inject(this);
    }



}
