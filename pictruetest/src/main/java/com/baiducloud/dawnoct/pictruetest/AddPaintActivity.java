package com.baiducloud.dawnoct.pictruetest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by DawnOct on 2017/7/19.
 */

public class AddPaintActivity extends AppCompatActivity implements MyRelativeLayout02.MyRelativeTouchCallBack {
    private MyRelativeLayout02 rela;
    public static final String TAG = "AddPaintActivity";
    private String imagePath;
    int screenWidth;
    int screenHeight;
    private Button eraser;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paint02);
//        EventBus.getDefault().register(this);
        initUI();
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
    }

    public void initUI() {
        screenWidth = ViewUtils.getScreenWidth(getApplicationContext());
        screenHeight = ViewUtils.getScreenHeight(getApplicationContext());
        imagePath = (String) getIntent().getCharSequenceExtra("path");
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(imagePath, screenWidth, screenHeight);
        rela = (MyRelativeLayout02) findViewById(R.id.id_rela);
        if (bitmap != null) {
            rela.setBackGroundBitmap(bitmap);
        }
        rela.setMyRelativeTouchCallBack(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14 && resultCode == -1) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();// find uri
            if (uri != null) {
                String imagePathFromUri = BitmapUtils.getImagePathFromUri(AddPaintActivity.this, uri);
                Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(imagePathFromUri, screenWidth, screenHeight);
                rela.setBackGroundBitmap(bitmap);
            }
        }
    }

    @Override
    public void touchMoveCallBack(int direction) {

    }


    @Override
    public void onTextViewMoving(TextView textView) {

    }

    @Override
    public void onTextViewMovingDone() {
    }

    @Override
    public void onOderViewMoving(RelativeLayout relativeLayout) {

    }

    @Override
    public void onOderViewMovingDone() {

    }

    public void btnClickLoad(View view) {
        Bitmap bitmap = ImageUtils.createViewBitmap(rela, rela.getWidth(), rela.getHeight());
        String fileName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        String result = ImageUtils.saveBitmapToFile(bitmap, fileName);//存入了/sdcard/cretin/目录下
        //方式1：手动发送广播，刷新本地数据库
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        if (!TextUtils.isEmpty(result))
            intent.setData(Uri.fromFile(new File(result)));
        sendBroadcast(intent);
        //将数据带回上个页面
        Intent intent_path = new Intent();
        intent_path.putExtra("path", result);
        setResult(13, intent_path);
        finish();
    }
}
