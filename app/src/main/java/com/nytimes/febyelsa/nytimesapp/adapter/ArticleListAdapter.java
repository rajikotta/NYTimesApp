package com.nytimes.febyelsa.nytimesapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.nytimes.febyelsa.nytimesapp.interfaces.ItemClickCallBack;
import com.nytimes.febyelsa.nytimesapp.database.ArticleEntity;
import com.nytimes.febyelsa.nytimesapp.databinding.ItemArticleListBinding;

import java.util.ArrayList;
import java.util.List;

public class ArticleListAdapter extends BaseAdapter<ArticleListAdapter.ArticleViewHolder, ArticleEntity>
        implements Filterable {

    private List<ArticleEntity> articleEntities;
    private List<ArticleEntity> articleEntitiesFiltered;
    private ItemClickCallBack articleListItemClickCallback;

    public ArticleListAdapter(@NonNull ItemClickCallBack articleListCallback) {
        articleEntities = new ArrayList<>();
        articleEntitiesFiltered = new ArrayList<>();
        articleListItemClickCallback = articleListCallback;
    }

    public void setData(List<ArticleEntity> entities) {
        articleEntities = entities;
        articleEntitiesFiltered = entities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return ArticleViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, articleListItemClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder viewHolder, int i) {
        viewHolder.onBind(articleEntitiesFiltered.get(i));
    }

    @Override
    public int getItemCount() {
        return articleEntitiesFiltered == null || articleEntitiesFiltered.isEmpty()? 0: articleEntitiesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleEntitiesFiltered = articleEntities;
                } else {
                    List<ArticleEntity> filteredList = new ArrayList<>();
                    for (ArticleEntity row : articleEntities) {

                        // name match condition. this might differ depending on your requirement
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthors().toLowerCase().contains(charString.toLowerCase())
                                || row.getPublishedDate().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    articleEntitiesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articleEntitiesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articleEntitiesFiltered = (ArrayList<ArticleEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        private static ArticleViewHolder create(LayoutInflater inflater, ViewGroup parent, ItemClickCallBack callback) {
            ItemArticleListBinding itemMovieListBinding = ItemArticleListBinding.inflate(inflater, parent, false);
            return new ArticleViewHolder(itemMovieListBinding, callback);
        }

        final ItemArticleListBinding binding;

        private ArticleViewHolder(ItemArticleListBinding binding, ItemClickCallBack callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onArticleItemClicked(binding.getArticle()));
        }

        private void onBind(ArticleEntity article) {
            binding.setArticle(article);
            binding.executePendingBindings();
        }
    }
}
