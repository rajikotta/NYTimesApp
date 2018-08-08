package com.nytimes.febyelsa.nytimesapp.di;

import android.app.Application;

import com.nytimes.febyelsa.nytimesapp.repository.BaseRepository;
import com.nytimes.febyelsa.nytimesapp.repository.Repository;
import com.nytimes.febyelsa.nytimesapp.viewmodel.ArticlesDetailsViewModel;
import com.nytimes.febyelsa.nytimesapp.viewmodel.ArticlesViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {

    void inject(ArticlesViewModel articlesViewModel);
    void inject(ArticlesDetailsViewModel articlesViewModel);
    void inject(BaseRepository baseRepository);

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance
        Builder application(Application application);
    }

}
