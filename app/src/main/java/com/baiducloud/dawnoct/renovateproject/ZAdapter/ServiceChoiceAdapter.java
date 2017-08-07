package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by DawnOct on 2017/8/7.
 */

public class ServiceChoiceAdapter extends BaseQuickAdapter<Post.ServiceBean,BaseViewHolder> {
    public ServiceChoiceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post.ServiceBean item) {
        int i = mData.indexOf(item);
        helper.setText(R.id.tv_name,item.getName());
            helper.getView(R.id.iv).setVisibility(View.INVISIBLE);
        if(i==p){
            helper.getView(R.id.iv).setVisibility(View.VISIBLE);
        }
    }
    int p;
    public void setPositon(int p){
        this.p = p;
    }
}
