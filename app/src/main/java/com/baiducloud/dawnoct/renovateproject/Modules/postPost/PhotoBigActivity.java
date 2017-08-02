package com.baiducloud.dawnoct.renovateproject.Modules.postPost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/8/2.
 */

public class PhotoBigActivity extends BaseActivity{
    @BindView(R.id.imageView2)
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_big);
        //绑定activity
        ButterKnife.bind(this);
        String path = getIntent().getStringExtra("path");
        String uri = getIntent().getStringExtra("uri");
        if(TextUtils.isEmpty(uri)){
            Picasso.with(imageView.getContext())
                    .load(RetrofitService.RENOVATE_HOST_PHPTO+path)
//                    .resize(500, 500)//todo 避免内存溢出(改变的是图片的像素质量)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.detele)
                    .into(imageView);
        }else {
            Picasso.with(imageView.getContext())
                    .load(uri)
//                    .resize(500, 500)//todo 避免内存溢出(改变的是图片的像素质量)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.detele)
                    .into(imageView);
        }
    }
}
