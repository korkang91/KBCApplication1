package com.kangbc.kbcapplication1;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by TedPark on 2017. 2. 16..
 */

public abstract class BindActivity<B extends ViewDataBinding> extends BaseActivity {

    protected B binding;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

}
