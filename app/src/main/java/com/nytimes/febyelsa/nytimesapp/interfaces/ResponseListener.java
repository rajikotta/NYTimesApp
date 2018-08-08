package com.nytimes.febyelsa.nytimesapp.interfaces;

import android.arch.lifecycle.MutableLiveData;

import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;

import java.util.List;

public interface ResponseListener {
    void onResponse(String status, MutableLiveData<List<ArticleEntity>> mutableLiveData);
}
