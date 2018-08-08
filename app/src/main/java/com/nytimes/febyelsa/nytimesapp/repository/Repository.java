package com.nytimes.febyelsa.nytimesapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.nytimes.febyelsa.nytimesapp.interfaces.ResponseListener;
import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;
import com.nytimes.febyelsa.nytimesapp.database.ArticlesDao;
import com.nytimes.febyelsa.nytimesapp.network.ApiService;
import com.nytimes.febyelsa.nytimesapp.utils.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository extends BaseRepository {

    private ResponseListener repositoryCallback;
    private ResponseListener responseListener = new ResponseListener() {
        @Override
        public void onResponse(String status, MutableLiveData<List<ArticleEntity>> mutableLiveData) {
            if (Constants.STATUS_SUCCESS.equalsIgnoreCase(status) && null != mutableLiveData) {
                saveArticles(status, mutableLiveData);
                return;
            }

            repositoryCallback.onResponse(status, mutableLiveData);
        }
    };
//    private ResponseListener responseListener = (status, mutableLiveData) ->{
//
//            if(Constants.STATUS_SUCCESS.equalsIgnoreCase(status) && null!= mutableLiveData){
//                getArticlesDao().saveArticles(mutableLiveData.getValue());
//            }
//
//            repositoryCallback.onResponse(status,mutableLiveData);
//        };

    public Repository(Application application, ArticlesDao articlesDao, ApiService service) {
        super(application, articlesDao, service);
    }

    private LiveData<List<ArticleEntity>> getArticlesFromDb() {
        return getArticlesDao().loadArticles();
    }

    private void initiateServiceCall() {
        getApiService().connectToNetwork();
        getApiService().startApiServiceCall(responseListener);
    }

    public void loadArticles(ResponseListener repositoryCallBack) {

        MutableLiveData<List<ArticleEntity>> articlesList = new MutableLiveData<>();
        this.repositoryCallback = repositoryCallBack;

        articlesList.setValue(getArticlesFromDb().getValue());
        if (articlesList != null && articlesList.getValue() != null && !articlesList.getValue().isEmpty()) {
            repositoryCallback.onResponse(Constants.STATUS_SUCCESS, articlesList);
            return;
        }

        initiateServiceCall();
    }

    private void saveArticles(String status, MutableLiveData<List<ArticleEntity>> listMutableLiveData) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                getArticlesDao().saveArticles(listMutableLiveData.getValue());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                repositoryCallback.onResponse(status, listMutableLiveData);
            }
        }.execute();
    }

    public  MutableLiveData<ArticleEntity> getEntity(long id, MutableLiveData<ArticleEntity> articleEntityMutableLiveData) {

        new AsyncTask<Void, Void, ArticleEntity>() {

            @Override
            protected ArticleEntity doInBackground(Void... voids) {
              return getArticlesDao().loadArticleById(id);

            }

            @Override
            protected void onPostExecute(ArticleEntity articleEntity) {
                articleEntityMutableLiveData.setValue(articleEntity);
            }
        }.execute();
        return articleEntityMutableLiveData;
    }
    public void loadArticleDetails(String url, ResponseListener responseListener) {
        ArticleEntity articleDetails = new ArticleEntity();
        Observable.fromCallable(() -> {
            Document document = Jsoup.connect(url).get();
            articleDetails.setTitle(document.title());
            articleDetails.setContent(document.select("p").text());
            return false;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            MutableLiveData mutableLiveData = new MutableLiveData();
                            mutableLiveData.setValue(articleDetails);
                            responseListener.onResponse(Constants.STATUS_SUCCESS,mutableLiveData);
                        } ,
                        (error -> responseListener.onResponse(Constants.STATUS_FAILURE,null)));

    }
}
