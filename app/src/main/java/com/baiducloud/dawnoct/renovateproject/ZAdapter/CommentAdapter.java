package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Comment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by DawnOct on 2017/7/31.
 */

public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {
    public CommentAdapter(int layoutResId, List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.addOnClickListener(R.id.comment_back);//为子控件设置点击事件
        helper.setText(R.id.owner_name, item.getName() + item.getTelephone());
        helper.setText(R.id.comment_des, item.getText());
        helper.setText(R.id.comment_time, item.getCreated_time());
        helper.setText(R.id.comment_back_tv,item.getCall_back());

    }
}
