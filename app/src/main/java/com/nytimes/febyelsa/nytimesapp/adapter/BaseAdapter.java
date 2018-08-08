package com.nytimes.febyelsa.nytimesapp.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T>{

    public abstract void setData(List<D> data);
}