package com.hxj.sms.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class QuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public QuickAdapter(int layout) {
        super(layout);
    }
    public QuickAdapter(int layout, List list) {
        super(layout,list);
    }
    @Override
    protected void convert(BaseViewHolder helper, T  item) {

    }

}
