package com.baiducloud.dawnoct.renovateproject.ZAdapter;


import android.widget.ImageView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Steven Tang on 2017/4/17.
 */

public class CasesListAdapter extends BaseQuickAdapter<Post, BaseViewHolder> {
    public CasesListAdapter(List<Post> list) {
        super(R.layout.cases_list_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {
//        helper.addOnClickListener(R.id.duibi);
        ImageView imageView = (ImageView) helper.getView(R.id.img);
        Picasso.with(imageView.getContext())
                .load(RetrofitService.RENOVATE_HOST_PHPTO+item.getPost_imag())
//                .resize(80,80)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.detele)
                .into(imageView);
        helper.setText(R.id.name, item.getDistrict() + item.getVillage());
        Post.ServiceBean service = item.getService();
        if(service!=null){
            helper.setText(R.id.product, service.getName());
        }else {
            helper.setText(R.id.product, "刷新服务");
        }
        helper.setText(R.id.predict, "工期:" + item.getPredict() + "天");
        helper.setText(R.id.area, "面积:"+item.getArea());
        helper.setText(R.id.state, "状态:"+item.getState());
    }
}
