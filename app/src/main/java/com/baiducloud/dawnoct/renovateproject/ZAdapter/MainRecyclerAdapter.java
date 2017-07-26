package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import android.content.Context;
import android.widget.ImageView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.Zutils.ScreenHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/7.
 */

public class MainRecyclerAdapter extends BaseQuickAdapter<Post.ServiceBean, BaseViewHolder> {
    int[] screenSize;

    public MainRecyclerAdapter(Context context, int layoutResId, List<Post.ServiceBean> data) {
        super(layoutResId, data);
        screenSize = ScreenHelper.getScreenSize(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post.ServiceBean item) {

        ImageView imageView = (ImageView) helper.convertView.findViewById(R.id.image);
        int resourceId = R.drawable.a;
        Glide.with(imageView.getContext()).//加载本地资源
                load(resourceId).
                asBitmap().
                into(imageView);
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_price, "仅需" + item.getPrice() + "元/平米");
    }
}
