package com.nytimes.febyelsa.nytimesapp.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nytimes.febyelsa.nytimesapp.R;
import com.nytimes.febyelsa.nytimesapp.databinding.FragmentArticleDetailsBinding;
import com.nytimes.febyelsa.nytimesapp.viewmodel.ArticlesDetailsViewModel;
import com.nytimes.febyelsa.nytimesapp.viewmodel.ArticlesViewModel;

public class ArticleDetailFragment extends BaseFragment<ArticlesDetailsViewModel, FragmentArticleDetailsBinding> {
    @Override
    protected Class<ArticlesDetailsViewModel> getViewModel() {
        return ArticlesDetailsViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_details;
    }

    public static ArticleDetailFragment newInstance(Bundle args) {
        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        articleDetailFragment.setArguments(args);
        return articleDetailFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(null!=getArguments()){
            viewModel.setArticleId(getArguments().getLong("articleId"));
            viewModel.setUrl(getArguments().getString("url"));
            viewModel.getArticles();
        }
        viewModel.getSelectedArticle()
                .observe(getActivity(), listResource -> {
                    dataBinding.setArticleDetailViewModel(viewModel);
                });

    }
}
