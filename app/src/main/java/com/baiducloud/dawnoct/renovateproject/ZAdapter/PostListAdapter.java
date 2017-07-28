package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/24.
 */

public class PostListAdapter extends BaseItemDraggableAdapter<Post, BaseViewHolder> {
    public PostListAdapter(List<Post> list) {
        super(R.layout.cases_list_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post item) {
//        helper.addOnClickListener(R.id.duibi);
        ImageView imageView = (ImageView) helper.getView(R.id.img);
        Picasso.with(imageView.getContext())
                .load(RetrofitService.RENOVATE_HOST_PHPTO+item.getPost_imag())
                .resize(500,500)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.detele)
                .into(imageView);
        helper.setText(R.id.name, item.getDistrict() + item.getVillage());
        if(item.getService()!=null){
            helper.setText(R.id.product, item.getService().getName());
        }
        helper.setText(R.id.predict, "工期:" + item.getPredict() + "天");
        helper.setText(R.id.area, item.getArea()+"平");
    }



}
