package com.nytimes.febyelsa.nytimesapp.interfaces;

import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;

public interface ItemClickCallBack {
    void onArticleItemClicked(ArticleEntity articleEntity);
}
