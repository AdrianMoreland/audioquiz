package com.audioquiz.feature.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.audioquiz.feature.home.databinding.CardCategoryBinding;
import com.audioquiz.feature.home.domain.HomeViewContract;

import java.util.List;

import timber.log.Timber;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<HomeViewContract.CategoryCardState> categories;
    private final OnCategoryClickListener listener;

    public CategoryAdapter(List<HomeViewContract.CategoryCardState> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardCategoryBinding binding = CardCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        HomeViewContract.CategoryCardState categoryUi = categories.get(position);
        holder.bind(categoryUi);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategories(List<HomeViewContract.CategoryCardState> newCategories, List<Integer> removedIndices) {
        if (categories.isEmpty()) {
            // If the list is empty, simply add the new categories
            categories.addAll(newCategories);
            notifyItemRangeInserted(0, newCategories.size());
        } else {
            // If the list is not empty, remove and add items as before
            for (int i = removedIndices.size() - 1; i >= 0; i--) {
                int position = removedIndices.get(i);
                if (position < categories.size()) { // Check if position is valid
                    categories.remove(position);
                    notifyItemRemoved(position);
                }
            }

            int oldSize = categories.size();
            categories.addAll(newCategories);
            notifyItemRangeInserted(oldSize, newCategories.size());
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final CardCategoryBinding binding;

        public CategoryViewHolder(CardCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                Timber.tag("CategoryAdapter").d("Category clicked at position: %s", getAdapterPosition());
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCategoryClick(categories.get(position));
                }
            });
        }

        public void bind(HomeViewContract.CategoryCardState categoryUi) {
            binding.setCategory(categoryUi);
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(HomeViewContract.CategoryCardState categoryUi);
    }
}
