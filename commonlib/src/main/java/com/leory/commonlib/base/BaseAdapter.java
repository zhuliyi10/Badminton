package com.leory.commonlib.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Despcription : BaseAdapter
 * Author : Leory
 * Time : 2018-04-15
 */

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder>{

    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

}
