package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/10.
 */

public class DetailAdapter extends BaseQuickAdapter<PhotoesInfo.Photo, BaseViewHolder> {

    public DetailAdapter(int layoutResId, List<PhotoesInfo.Photo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoesInfo.Photo item) {
        ImageView imageView = (ImageView) helper.getView(R.id.img);
        if(!TextUtils.isEmpty(item.getPath())){
            Picasso.with(imageView.getContext())
                    .load(RetrofitService.RENOVATE_HOST_PHPTO+item.getPath())
//                    .resize(500,500)
                    .fit()
                    .placeholder(R.mipmap.qiaoqiao)
                    .error(R.mipmap.qiaoqiao)
                    .into(imageView);
        }

    }
}
