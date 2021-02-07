package com.javacodegeeks.androidBluetoothExample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javacodegeeks.R;

import java.util.ArrayList;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter {

    private List<String> mItems = new ArrayList<>();

    public void setItems(List<String> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0)
            return TextViewHolder.inflate(parent);
        else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).bind(mItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        public static TextViewHolder inflate(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.terminal_item_list, parent, false);
            return new TextViewHolder(view);
        }

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.til_text);
        }

        public void bind(String text) {
            mTextView.setText(text);
        }
    }
}
