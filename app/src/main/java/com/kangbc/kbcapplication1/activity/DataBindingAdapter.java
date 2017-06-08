package com.kangbc.kbcapplication1.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kangbc.kbcapplication1.R;
import com.kangbc.kbcapplication1.common.BaseRecyclerViewAdapter;
import com.kangbc.kbcapplication1.common.model.User;
import com.kangbc.kbcapplication1.databinding.DatabindingItemBinding;

/**
 * Created by TedPark on 2017. 2. 15..
 */

public class DataBindingAdapter extends BaseRecyclerViewAdapter<User, DataBindingAdapter.UserViewHolder> {

    public DataBindingAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindView(UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.binding.setUser(user);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.databinding_item, parent, false);
        return new UserViewHolder(view);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        DatabindingItemBinding binding;
        public UserViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
