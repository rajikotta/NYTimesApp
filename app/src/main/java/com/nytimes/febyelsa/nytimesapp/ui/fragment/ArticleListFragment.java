package com.nytimes.febyelsa.nytimesapp.ui.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nytimes.febyelsa.nytimesapp.interfaces.ItemClickCallBack;
import com.nytimes.febyelsa.nytimesapp.R;
import com.nytimes.febyelsa.nytimesapp.adapter.ArticleListAdapter;
import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;
import com.nytimes.febyelsa.nytimesapp.databinding.FragmentListArticlesBinding;
import com.nytimes.febyelsa.nytimesapp.utils.FragmentUtils;
import com.nytimes.febyelsa.nytimesapp.viewmodel.ArticlesViewModel;

public class ArticleListFragment extends BaseFragment<ArticlesViewModel,
        FragmentListArticlesBinding> implements ItemClickCallBack {

    public static ArticleListFragment newInstance() {
        Bundle args = new Bundle();
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<ArticlesViewModel> getViewModel() {
        return ArticlesViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_list_articles;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.recyclerView.setAdapter(new ArticleListAdapter(this));

        return dataBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getMutableLiveData()
                .observe(getActivity(), listResource -> {
                    ((ArticleListAdapter) dataBinding.recyclerView.getAdapter()).setData(viewModel.getMutableLiveData().getValue());
                    if (null != listResource) {
                        dataBinding.loginProgress.setVisibility(View.GONE);
                    } else {
                        viewModel.loadArticles();
                    }
                });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (null == getActivity())
            return;

        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        initiateSearchView(menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_search || super.onOptionsItemSelected(item);
    }

    private void initiateSearchView(Menu menu) {
        SearchView searchView;
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if (null != dataBinding.recyclerView.getAdapter())
                    ((ArticleListAdapter) dataBinding.recyclerView.getAdapter()).getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if (null != dataBinding.recyclerView.getAdapter())
                    ((ArticleListAdapter) dataBinding.recyclerView.getAdapter()).getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public void onArticleItemClicked(ArticleEntity articleEntity) {
        Bundle args = new Bundle();
        args.putLong("articleId",articleEntity.getId());
        args.putString("url",articleEntity.getUrl());
        FragmentUtils.replaceFragment((AppCompatActivity) getActivity(), ArticleDetailFragment.newInstance(args), R.id.fragContainer, true);
    }
}
