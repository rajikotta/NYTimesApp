package com.nytimes.febyelsa.nytimesapp.di;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;

import com.nytimes.febyelsa.nytimesapp.database.AppDataBase;
import com.nytimes.febyelsa.nytimesapp.database.ArticlesDao;
import com.nytimes.febyelsa.nytimesapp.network.ApiService;
import com.nytimes.febyelsa.nytimesapp.repository.Repository;
import com.nytimes.febyelsa.nytimesapp.viewmodel.ArticlesViewModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {



    @Provides
    @Singleton
    ApiService provideApiService() {
        return new ApiService();
    }

    @Provides
    @Singleton
    AppDataBase provideArticleDatabase(Application application) {
        return Room.databaseBuilder(application, AppDataBase.class, "articles.db").build();
    }

    @Provides
    @Singleton
    ArticlesDao provideArticleDao(AppDataBase articleDatabase) {
        return articleDatabase.articleDao();
    }

    @Provides
    @Singleton
    Repository providesRepository(Application application,ArticlesDao articlesDao, ApiService apiService) {
        return new Repository(application, articlesDao, apiService);
    }
}

