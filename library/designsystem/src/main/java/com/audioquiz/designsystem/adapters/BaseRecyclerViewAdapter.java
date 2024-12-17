package com.audioquiz.designsystem.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.audioquiz.designsystem.ViewInflater;

import java.util.List;

public abstract class BaseRecyclerViewAdapter <T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecyclerViewAdapter<T, B>.ViewHolder> {

    private final ViewInflater<B> viewInflater;
    private List<T> data = List.of();

    public BaseRecyclerViewAdapter(ViewInflater<B> viewInflater) {
        this.viewInflater = viewInflater;
    }

    public static <T, B extends ViewDataBinding> BaseRecyclerViewAdapter<T, B> build(ViewInflater<B> viewInflater, BindFunction<T, B> bind) {
        return new BaseRecyclerViewAdapter<T, B>(viewInflater) {
        @Override
        public void bindData(T item, B binding) {
            bind.bind(item, binding);
        }
    };
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public abstract void bindData(T item, B binding);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        B binding = viewInflater.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = data.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final B binding;

        public ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T item) {
            bindData(item, binding);
            binding.executePendingBindings();
        }
    }

    @FunctionalInterface
    public interface BindFunction<T, B extends ViewDataBinding> {
        void bind(T item, B binding);
    }
}