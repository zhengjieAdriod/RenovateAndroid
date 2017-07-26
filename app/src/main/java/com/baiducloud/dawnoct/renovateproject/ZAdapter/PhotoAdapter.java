package com.baiducloud.dawnoct.renovateproject.ZAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baiducloud.dawnoct.renovateproject.Modules.postPost.AddNewCaseActivity;
import com.baiducloud.dawnoct.renovateproject.Modules.postPost.bean.Imagepxh;
import com.baiducloud.dawnoct.renovateproject.R;
import com.baiducloud.dawnoct.renovateproject.Views.BaseActivity;
import com.baiducloud.dawnoct.renovateproject.ZNetService.RetrofitService;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.Zutils.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DawnOct on 2017/7/21.
 */

public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private List<Imagepxh> list;// this image file uri
    private ImageView image;
    private TextView tv_delete;
    int requestCode;
    int mNum;//区别与四个图片组
    BaseActivity activity;

    public PhotoAdapter(Context context, List<Imagepxh> list, int num) {
        this.context = context;
        this.list = list;
        this.mNum = num;
        this.activity = (BaseActivity) context;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    public void addAllPath(List<String> paths) {
        for (String path : paths) {
            Uri uri1 = Uri.fromFile(new File(path));
            if (uri1 != null) {
                galleryAddPic(uri1);
                Imagepxh image = new Imagepxh(uri1, path);
                list.add(image);
//                boolean local = image.isLocal();
//                if (!list.contains(image)) {
//                    list.add(image);
//                } else {
//                    Toast.makeText(context, "图片已选过", Toast.LENGTH_LONG).show();
//                }
            }

        }

    }

    public void setPath(String path) {
        Uri uri1 = Uri.fromFile(new File(path));
        if (uri1 != null) {
            galleryAddPic(uri1);
            Imagepxh image = new Imagepxh(uri1, path);
            list.clear();
            list.add(image);
        }
    }

    public List<Imagepxh> getList() {
        return this.list;
    }

    public void addList(Uri uri) {
        String imagePathFromUri = BitmapUtils.getImagePathFromUri(context, uri);
        Imagepxh image = new Imagepxh(uri, imagePathFromUri);
        if (!list.contains(image)) {
            list.add(image);
        } else {
            Toast.makeText(context, "图片已选过", Toast.LENGTH_LONG).show();
        }
    }

    public void setNetPhotos(List<PhotoesInfo.Photo> photos) {
        List<Imagepxh> temp = new ArrayList<>();
        boolean b = list.addAll(photos);
        if (b) {
            for (Imagepxh imagepxh : list) {
                String des = imagepxh.getDes();
                if (TextUtils.isEmpty(des)) {
                    temp.add(imagepxh);
//                    list.remove(imagepxh);
                }
            }
            list.removeAll(temp);//去除本地图片,替换为网络图片
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.gridview_item, null);
//            image = (ImageView) convertView.findViewById(R.id.image);
        image = (ImageView) convertView.findViewById(R.id.image);
        tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
        if (position < list.size()) {
            Uri uri = list.get(position).getImage_uri();
            if (uri != null) {
                String imagePathFromUri = BitmapUtils.getImagePathFromUri(context, uri);//由uri获得图片路径
                //压缩图片
                Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(imagePathFromUri, 80, 80);
//                image.setImageBitmap(bitmap);
                Picasso.with(image.getContext())
                        .load(uri)
                        .placeholder(R.mipmap.qiaoqiao)
                        .error(R.mipmap.qiaoqiao)
                        .into(image);
                image.setTag(position);//实现再次点击时查看大图
            } else if (!TextUtils.isEmpty(list.get(position).getPath())) {
                PhotoesInfo.Photo photo = (PhotoesInfo.Photo) list.get(position);
                String path = RetrofitService.RENOVATE_HOST_PHPTO + photo.getPath();
                Picasso.with(image.getContext())
                        .load(path)
                        .placeholder(R.mipmap.qiaoqiao)
                        .error(R.mipmap.qiaoqiao)
                        .into(image);
                image.setTag(position);//实现再次点击时查看大图
            }
        } else if (position == list.size()) {
            image.setImageDrawable(context.getResources().getDrawable(
                    R.mipmap.add_photo));
            tv_delete.setVisibility(View.INVISIBLE);
        }

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 给一个用户提示,真的删除么?
                Imagepxh remove = list.remove(position);
                notifyDataSetChanged();
                //todo 删除网络图片组中的图片,接口
                String pk = remove.getPk();
                if (!TextUtils.isEmpty(pk)) {
                    activity.delete(pk, mNum);
                }

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    if (v.getTag().equals(position)) {
                        Uri uri = list.get(position).getImage_uri();
                        if (uri != null) {
//                                Log.e("zj", position + "看大图" + uri.toString());
//                                Intent intent = new Intent(AddNewCaseActivity.this, AddPaintActivity.class);
//                                intent.putExtra("from", "add_picture");
//                                intent.putExtra("uri", uri.toString());
//                                startActivityForResult(intent, 111);
                            return;
                        }
                    }
                }

                switch (mNum) {
                    case 1:
                        requestCode = 100;
                        break;
                    case 2:
                        requestCode = 101;
                        break;
                    case 3:
                        requestCode = 102;
                        break;
                    case 4:
                        requestCode = 103;
                        break;
                    case 5:
                        requestCode = 105;
                        break;
                }
                Album.startAlbum(activity, requestCode
                        , 9                                                         // 指定选择数量。
                        , ContextCompat.getColor(context, R.color.colorPrimary)        // 指定Toolbar的颜色。
                        , ContextCompat.getColor(context, R.color.colorPrimaryDark));  // 指定状态栏的颜色。

            }
        });
        return convertView;
    }

    //将图片添加进手机相册
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        context.sendBroadcast(mediaScanIntent);
    }
}
