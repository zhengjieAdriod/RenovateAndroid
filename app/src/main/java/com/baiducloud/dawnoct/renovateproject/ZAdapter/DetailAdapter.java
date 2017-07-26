package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/10.
 */

public class DetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = (ImageView) helper.getView(R.id.img);
        if(!TextUtils.isEmpty(item)){
            Picasso.with(imageView.getContext())
                    .load(item)
                    .placeholder(R.mipmap.qiaoqiao)
                    .error(R.mipmap.qiaoqiao)
                    .into(imageView);
        }

    }
}
