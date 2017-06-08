package com.kangbc.kbcapplication1.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.kangbc.kbcapplication1.BaseActivity;
import com.kangbc.kbcapplication1.R;
import com.kangbc.kbcapplication1.common.SimpleDividerItemDecoration;
import com.kangbc.kbcapplication1.common.util.DataUtil;
import com.kangbc.kbcapplication1.databinding.DatabindingActivityBinding;

/**
 * Created by TedPark on 2017. 2. 2..
 */

public class DataBindingActivity extends BaseActivity {


    DatabindingActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.databinding_activity);
        binding.setActivity(this);

        setRecyclerView();

    }


    private void setRecyclerView() {

        binding.rcContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcContent.addItemDecoration(new SimpleDividerItemDecoration(this));
        DataBindingAdapter adapter = new DataBindingAdapter(this);
        binding.rcContent.setAdapter(adapter);

        adapter.updateItems(DataUtil.getUsers());

    }

    public void onButtonClick(View view){
        Toast.makeText(this,"Button Click",Toast.LENGTH_SHORT).show();
    }
}
