package com.baiducloud.dawnoct.pictruetest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 这是个自定义布局View
 */
public class MyRelativeLayout02 extends RelativeLayout implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
    private Context context;
    private TextView textView;
    private TextViewParams tvParams;
    private EditText editText;
    private boolean flag = false;
    private boolean mflag = false;
    private boolean onefinger;
    private boolean tvOneFinger;
    //记录是否为TextView上的单击事件
    private boolean isClick = true;
    public static final int DEFAULT_TEXTSIZE = 25;
    //左边点的偏移量
    float tv_width;
    float tv_height;
    float mTv_width;
    float mTv_height;
    float tv_widths;
    float tv_heights;
    float mTv_widths;
    float mTv_heights;
    //用于保存创建的TextView
    private List<TextView> list;
    private List<TextViewParams> listTvParams;
    private List<Double> listDistance;
    private float oldDist = 0;
    private float textSize = 0;
    private int num = 0;
    private int width;
    private int height;
    private float startX;
    private float startY;

    private static final int INVALID_POINTER_ID = -1;
    private float fX, fY, sX, sY;
    private float mfX, mfY, msX, msY;
    private int ptrID1, ptrID2;
    private int mptrID1, mptrID2;
    private float mAngle;
    private float scale;
    //记录第一个手指下落时的位置
    private float firstX;
    private float firstY;
    private float defaultAngle;
    //记录当前点击坐标
    private float currentX;
    private float currentY;
    //记录当前设备的缩放倍数
    private double scaleTimes = 1;

    public MyRelativeTouchCallBack getMyRelativeTouchCallBack() {
        return myRelativeTouchCallBack;
    }

    public void setMyRelativeTouchCallBack(MyRelativeTouchCallBack myRelativeTouchCallBack) {
        this.myRelativeTouchCallBack = myRelativeTouchCallBack;
    }

    private MyRelativeTouchCallBack myRelativeTouchCallBack;

    public MyRelativeLayout02(Context context) {
        this(context, null, 0);
    }

    public MyRelativeLayout02(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRelativeLayout02(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //得到屏幕的分辨率
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels;
        testScaleTimes();
        initCanvas();
        init();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    //计算缩放倍数
    private void testScaleTimes() {
        TextView tv = new TextView(context);
        tv.setTextSize(1);
        scaleTimes = tv.getTextSize();
        Log.e("HHHHHHH", "缩放倍数" + scaleTimes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyRelativeLayout02(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint;//画笔
    Path mPath;//滑动路线
    int bitmapWidth;//屏幕宽度
    int bitmapHeight;//屏幕高度
    private ArrayList<DrawPath> savePath;
    private ArrayList<DrawPath> deletePath;
    private DrawPath dp;
    private float mX, mY;//ViewGroup ACTION_DOWN事件的位置
    private static final float TOUCH_TOLERANCE = 4;

    //路径对象
    class DrawPath {
        Path path;
        Paint paint;
    }

    private int[] mColors = new int[]{
            Color.parseColor("#eb6564"),
            Color.parseColor("#eb65c8"),
            Color.parseColor("#b264eb"),
            Color.parseColor("#47abff"),
            Color.parseColor("#64ebda"),
            Color.parseColor("#ecd865"),
            Color.parseColor("#eaae65"),
            Color.parseColor("#ec7e65"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#000000"),};

    private void initCanvas() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mColors[3]);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);

        Bitmap bmSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, bmSrc.getConfig());//决定了画笔的可以画的范围

        mCanvas = new Canvas(mBitmap);//

        mPath = new Path();//滑动路线
    }

    /**
     * 初始化操作
     */
    private void init() {
        ptrID1 = INVALID_POINTER_ID;
        ptrID2 = INVALID_POINTER_ID;
        mptrID1 = INVALID_POINTER_ID;
        mptrID2 = INVALID_POINTER_ID;
        savePath = new ArrayList<DrawPath>();
        deletePath = new ArrayList<DrawPath>();
        list = new ArrayList<>();
        listTvParams = new ArrayList<>();
        listDistance = new ArrayList<>();

        editText = new EditText(context);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setVisibility(GONE);
//        editText.setVisibility(VISIBLE);
        addView(editText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);//显示画布
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint);//画线
        }
    }

    private void touch_start(float x, float y) {
        mPath.reset();//清空path
        mPath.moveTo(x, y);
        mX = x; //down事件的位置
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //mPath.quadTo(mX, mY, x, y);
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        if (onefinger) {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);//画在了mCanvas所拥有的bitmap上，如mBitmap
            savePath.add(dp);
            mPath = null;
        }
    }

    //新添加的回调方法(此为OnTouchListener的回调方法，如果返回true则该ViewGroup的onTouchEvent()方法将不被执行)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        mScaleGestureDetector.onTouchEvent(event);//如此，缩放手势才能生效
        return false;//如果在activity中设置了setOnTouchListener,并且其回调方法返回了true则下面的onTouchEvent()方法将不再能执行
    }

    /**
     * 处理该ViewGroup上的单击事件
     */
    float downX_zoom, downY_zoom;
    float moveX_zoom, moveY_zoom;
    boolean zoom_flag;
    boolean slide_flag;//放大后，也可以有点击操作

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (textSize == 0 && textView != null) {
            textSize = textView.getTextSize();
        }
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {//与运算符的使用
            case MotionEvent.ACTION_DOWN:
                downX_zoom = event.getRawX();
                downY_zoom = event.getRawY();
                mPath = new Path();
                dp = new DrawPath();
                dp.path = mPath;
                dp.paint = mPaint;
                touch_start(x, y);//记录画笔的初始路径，点击的初始点mX,mY
                invalidate(); //清屏
                //此时有一个手指头落点
                onefinger = true;
                slide_flag = true;
                //给第一个手指落点记录落点的位置
                firstX = event.getX(); //记录第一个手指下落时的位置。与mX,mY无异
                firstY = event.getY();

                currentX = event.getX(); //记录当前点击坐标
                currentY = event.getY();

                ptrID1 = event.getPointerId(event.getActionIndex());//获得pointer的id值

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //第二个手指头落点 早已经不是点击事件
                onefinger = false;
                ptrID2 = event.getPointerId(event.getActionIndex());
                sX = event.getX(event.findPointerIndex(ptrID1));//第一个手指的相对位置
                sY = event.getY(event.findPointerIndex(ptrID1));
                fX = event.getX(event.findPointerIndex(ptrID2));//第二个手指的相对位置
                fY = event.getY(event.findPointerIndex(ptrID2));
//                flag = false;
                if (listTvParams != null && !listTvParams.isEmpty()) {
                    //当第二个手指落指的时候 开始计算寻找最近的点
                    listDistance.clear();
                    for (int i = 0; i < listTvParams.size(); i++) {
                        //将两指中间点到TextView中心点间的距离，存放
                        listDistance.add(spacing(getMidPiont((int) fX, (int) fY, (int) sX, (int) sY), listTvParams.get(i).getMidPoint()));
                    }
                    //寻找最近的点
                    if (list != null && !list.isEmpty()) {//list中存放的是TextView
                        double min = listDistance.get(0);
                        num = 0;
                        for (int i = 1; i < listDistance.size(); i++) {
                            if (min > listDistance.get(i)) {
                                min = listDistance.get(i);
                                num = i;
                            }
                        }
                        textView = null;
                        textView = list.get(num);
                        oldDist = spacing(event, ptrID1, ptrID2);//两个触摸点距离的平方
                        setTextViewParams(getTextViewParams(textView));//为TextView赋对应的值(角度、缩放值)
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (onefinger && !zoom_flag) {//避免缩放字体时，同时绘画
                    touch_move(x, y);//更新了初始位置mX,mY
                    invalidate();//实现滑动过程中不断绘制画
                }
                moveX_zoom = event.getRawX();
                moveY_zoom = event.getRawY();
                if (zoom_flag) {//如果是放大状态，就可以移动
                    this.setX(getX() + (moveX_zoom - downX_zoom));
                    this.setY(getY() + (moveY_zoom - downY_zoom));
                    float y1 = this.getY();
                    downX_zoom = moveX_zoom;
                    downY_zoom = moveY_zoom;
                    slide_flag = false;
                }
                //平移操作是点击在子view上的，因此由子View处理
                //旋转和缩放操作（手指是在ViewGroupon上的，并且在手指移动时实现缩放）
                if (ptrID1 != INVALID_POINTER_ID && ptrID2 != INVALID_POINTER_ID) {//两个id都存在的情况（两指头）
                    float nfX, nfY, nsX, nsY;
                    nsX = event.getX(event.findPointerIndex(ptrID1));//通过触摸点的id获得触摸点的index（index随着事件流在变化，而id不变），通过index获得触摸点的坐标
                    nsY = event.getY(event.findPointerIndex(ptrID1));
                    nfX = event.getX(event.findPointerIndex(ptrID2));
                    nfY = event.getY(event.findPointerIndex(ptrID2));
                    if (TextScaleFlag && textView != null) {
                        //处理旋转模块
                        mAngle = angleBetweenLines(fX, fY, sX, sY, nfX, nfY, nsX, nsY) + defaultAngle;
                        textView.setRotation(mAngle);
                    }
                    //处理缩放模块
                    float newDist = spacing(event, ptrID1, ptrID2);
                    scale = newDist / oldDist;
                    if (newDist > oldDist + 1) {
                        scaleBig = true;
                        if (TextScaleFlag && textView != null) {
                            zoom(scale);

                        }
                        oldDist = newDist;
                    }
                    if (newDist < oldDist - 1) {
                        scaleBig = false;
                        if (TextScaleFlag && textView != null) {
                            zoom(scale);
                        }
                        oldDist = newDist;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                ptrID2 = INVALID_POINTER_ID;
                if (viewGroupScaleFlag) {
                    if (scaleBig) {//放大
                        zoomGroup(scale);
                    }
                    if (!scaleBig) {//缩小
                        zoomGroupNo(scale);
                    }
                }
                if (list != null && !list.isEmpty()) {
                    updateTextViewParams(list.get(num), mAngle, scale);
                }
                break;
            case MotionEvent.ACTION_UP:
                //控制放大后，平移范围
                if (this.getY() < -bitmapHeight) {
                    this.setY(-bitmapHeight);
                }
                if (this.getY() > bitmapHeight) {
                    this.setY(bitmapHeight);
                }
                if (this.getX() < -bitmapWidth) {
                    this.setX(-bitmapWidth);
                }
                if (this.getX() > bitmapWidth) {
                    this.setX(bitmapWidth);
                }
                touch_up();//未更新初始位置mX,mY
                invalidate();
                //---end
                if (onefinger && slide_flag) {
                    if (spacing(firstX, firstY, event.getX(), event.getY()) < 5) {
                        if (clickModel) {
//                            addChildView(null, "66");//添加序号
                        } else {
                            showDialog("", true);//对话框输入文字
                        }
                    }
                }
                ptrID1 = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_CANCEL:
                ptrID1 = INVALID_POINTER_ID;
                ptrID2 = INVALID_POINTER_ID;
                break;
        }
        return true;
    }

    private boolean orderFlag;
    private boolean order_move_flag;
    private int order_move_left;
    private int order_move_top;
    private int order_move_right;
    private int order_move_botom;



    float m_firstX;
    float m_firstY;
    float downX, downY;
    float moveX, moveY;

    /**
     * 添加TextView到界面上
     * x,y:记录的点击坐标
     */
    public void addTextView(TextView tv, float x, float y, String content, int color, float mtextSize, float rotate) {
        if (tv == null) {
            if (mtextSize == 0) {
                mtextSize = DEFAULT_TEXTSIZE;
            }
            textView = new TextView(context);
            textView.setTag(System.currentTimeMillis());//区分每个textView
            textView.setText(content);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setTextSize(mtextSize);
            textView.setTextColor(color);
            textView.setRotation(rotate);
            textView.setX(x - textView.getWidth());
            textView.setY(y - textView.getHeight());
            textView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    textView = (TextView) v;
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            tvOneFinger = true;
                            isClick = true;
                            m_firstX = event.getX();
                            m_firstY = event.getY();
                            downX = event.getRawX();
                            downY = event.getRawY();
                            mptrID1 = event.getPointerId(event.getActionIndex());//获得第一个手指的id
                            //计算当前textView的位置和大小
                            width = textView.getWidth();
                            height = textView.getHeight();
                            if (event != null) {
                                mTv_width = event.getX() - textView.getX();
                                mTv_height = event.getY() - textView.getY();
                            }
                            mflag = true;
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            tvOneFinger = false;
                            isClick = false;
                            mptrID2 = event.getPointerId(event.getActionIndex());
                            msX = event.getX(event.findPointerIndex(mptrID1));
                            msY = event.getY(event.findPointerIndex(mptrID1));
                            mfX = event.getX(event.findPointerIndex(mptrID2));
                            mfY = event.getY(event.findPointerIndex(mptrID2));
                            mflag = false;
                            mTv_widths = getMidPiont((int) mfX, (int) mfY, (int) msX, (int) msY).x - textView.getX();
                            mTv_heights = getMidPiont((int) mfX, (int) mfY, (int) msX, (int) msY).y - textView.getY();
                            oldDist = spacing(event, mptrID1, mptrID2);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            moveX = event.getRawX();
                            moveY = event.getRawY();
                            float v1 = moveX - downX;
                            float v2 = moveY - downY;
                            int limit_left = ViewUtils.dp2px(textView.getContext(), 66);
                            float x1 = textView.getX();
                            float y1 = textView.getY();
                            if (mflag && (x1 > limit_left || v1 > 0) &&
                                    (x1 + textView.getWidth() < bitmapWidth - 50 || v1 < 0) &&
                                    (y1 > 0 || v2 > 0) &&
                                    (y1 + textView.getHeight() < bitmapHeight - 50 || v2 < 0)) {
                                textView.setX(textView.getX() + (moveX - downX));
                                textView.setY(textView.getY() + (moveY - downY));
                            }
                            downX = moveX;
                            downY = moveY;
                            if (spacing(m_firstX, m_firstY, event.getX(), event.getY()) > 2) {
                                isClick = false;
                            }
                            //旋转和缩放操作
                            if (mptrID1 != INVALID_POINTER_ID && mptrID2 != INVALID_POINTER_ID) {
                                float nfX, nfY, nsX, nsY;
                                nsX = event.getX(event.findPointerIndex(mptrID1));
                                nsY = event.getY(event.findPointerIndex(mptrID1));
                                nfX = event.getX(event.findPointerIndex(mptrID2));
                                nfY = event.getY(event.findPointerIndex(mptrID2));
                                //处理旋转模块
                                mAngle = angleBetweenLines(mfX, mfY, msX, msY, nfX, nfY, nsX, nsY);
                                textView.setRotation(mAngle);
                                //处理缩放模块
                                float newDist = spacing(event, mptrID1, mptrID2);
//                                scale = newDist / oldDist;
//                                if (newDist > oldDist + 1) {
//                                    textView.setTextSize(textSize *= scale);
//                                    oldDist = newDist;
////                                    text_move_right *= scale;
//                                }
//                                if (newDist < oldDist - 1) {
//                                    textView.setTextSize(textSize *= scale);
//                                    oldDist = newDist;
//                                }
                                //通知调用者我在旋转或者缩放
                                if (myRelativeTouchCallBack != null)
                                    myRelativeTouchCallBack.onTextViewMoving(textView);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            //通知调用者我滑动结束了
                            if (myRelativeTouchCallBack != null)
                                myRelativeTouchCallBack.onTextViewMovingDone();
                            mptrID1 = INVALID_POINTER_ID;
                            updateTextViewParams((TextView) v, mAngle, scale);
                            if (tvOneFinger && isClick) {
                                confirmDialogText("");
                            }
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            mptrID2 = INVALID_POINTER_ID;
//                            updateTextViewParams((TextView) v, mAngle, scale);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mptrID1 = INVALID_POINTER_ID;
                            mptrID2 = INVALID_POINTER_ID;
                            break;
                    }
                    return true;
                }
            });
            //保存到list中。
            list.add(textView);
            //保存textView的状态
            saveTextViewparams(textView);
            addView(textView);
        } else {
            textView = tv;
            textView.setText(content);
            textView.setTextColor(color);
        }

    }


    //添加标尺
//    MyCanvas hView;
    int lastX;
    int lastY;
    boolean mmflag; //onLaypout()方法的标志位
    int mleft, mtop, mright, mbotom;
//    int HorizontalCanvasMarginLeft = (int) getContext().getResources().getDimension(R.dimen.HorizontalCanvasMarginLeft);
//    int HorizontalCanvasMarginTop = (int) getContext().getResources().getDimension(R.dimen.HorizontalCanvasMarginTop);



//    VerticalCanvas vViews;
    int vlastX;
    int vlastY;
    boolean vmflag; //onLaypout()方法的标志位
    int vleft, vtop, vright, vbotom;
//    int VerticalCanvasMarginLeft = (int) getContext().getResources().getDimension(R.dimen.VerticalCanvasMarginLeft);
//    int VerticalCanvasMarginTop = (int) getContext().getResources().getDimension(R.dimen.VerticalCanvasMarginTop);



    //删除文字的弹出框
    public void confirmDialogText(String style) {
        if (dialogText == null) {
            dialogText = DialogPlus.newDialog(getContext())
                    .setContentHolder(new ViewHolder(R.layout.dialog_delete_pain))
                    .setContentWidth(ViewUtils.getScreenWidth(getContext()) / 3)  // or any custom width ie: 300
                    .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setGravity(Gravity.CENTER)
                    .setExpanded(false, 0)  // This will enable the expand feature, (similar to android L share dialog)
                    .setOnClickListener(new com.orhanobut.dialogplus.OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialogPlus, View view) {
                            switch (view.getId()) {
                                case R.id.tv_cancel:
                                    dialogPlus.dismiss();
                                    break;
                                case R.id.tv_confirm:
                                    textView.setVisibility(GONE);
                                    list.remove(textView);
                                    listTvParams.remove(getTextViewParams(textView));
                                    dialogPlus.dismiss();
                                    break;
                            }
                        }
                    })
                    .create();
        }
        dialogText.show();
    }







    public static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];
    private boolean once = true;

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 缩放的手势检测，在onTouch()方法中生效
     */
    private ScaleGestureDetector mScaleGestureDetector = null;
    private final Matrix mScaleMatrix = new Matrix();

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
//            Drawable d = getDrawable();
//            if (d == null)
//                return;
//            Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
            int width = getWidth();
            int height = getHeight();
            // 拿到图片的宽和高
            int dw = bitmapWidth;
            int dh = bitmapHeight;
            float scale = 1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width) {
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            initScale = scale;

//            Log.e(TAG, "initScale = " + initScale);
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
//            mScaleMatrix.postScale(scale, scale, getWidth() / 2,
//                    getHeight() / 2);
            // 图片移动至屏幕中心
//            setImageMatrix(mScaleMatrix);
            once = false;
        }
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    LayoutInflater inflater;
    float order_firstX;
    float order_firstY;
    RelativeLayout relativeView;
    float order_Tv_width, order_Tv_height;
    boolean orderIsClick;
    boolean clickModel;

    //改变单击模式
    public void changeClickModel(Button button) {
        if ("字".equals(button.getText())) {
            clickModel = true;
        } else {
            clickModel = false;//标识点击的是添加序号
        }
    }

    class OrderLocation {
        public int orderleft, ordertop, orderright, orderbotom;

        public OrderLocation(int orderbotom, int orderleft, int orderright, int ordertop) {
            this.orderbotom = orderbotom;
            this.orderleft = orderleft;
            this.orderright = orderright;
            this.ordertop = ordertop;
        }
    }

    private List<RelativeLayout> orderList = new ArrayList<>();//存放序号
    private Map<RelativeLayout, OrderLocation> orderMap = new LinkedHashMap<>();//存放序号
    private int num0 = 0;


    /**
     * 删除序号的确认弹出框
     */
    DialogPlus dialog;
    DialogPlus dialogText;



    /**
     * 为自定义View设置背景图片 顺便隐藏VerticalSeekBar
     *
     * @param bitmap
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackGroundBitmap(Bitmap bitmap) {
        setBackground(new BitmapDrawable(bitmap));
    }


    /**
     * 显示自定义对话框（输入文字）
     */
    private void showDialog(String message, final boolean isNew) {
        final int[] colors = new int[1];
        colors[0] = Color.RED;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.dialog_edittext);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView ok = (TextView) view.findViewById(R.id.tv_ok);

        builder.setView(view);
        builder.setCancelable(true);
        editText.setText(message);
        final AlertDialog dialog = builder.show();
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "您没有任何输入!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNew) {
//                        EventBus.getDefault().post("添加文字");
                        if (!zoom_flag) {//标识，是否是放大状态
                            viewGroupScaleFlag = false;//画板缩放状态关闭
                            TextScaleFlag = true;//文字缩放状态打开
                        }
                        //添加文字
//                        addTextView(null, currentX, currentY, content, colors[0], 0, 0);
                        addTextView(null, currentX, currentY, content, colors[0], 0, 0);
                    } else {
//                        addTextView(textView, textView.getX(), textView.getY(), content, colors[0], textView.getTextSize(), textView.getRotation());
                        addTextView(textView, textView.getX(), textView.getY(), content, colors[0], textView.getTextSize(), textView.getRotation());
                    }
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 对控件进行参数的更新操作
     *
     * @param tv
     */
    private void updateTextViewParams(TextView tv, float rotation, float scale) {
        for (int i = 0; i < listTvParams.size(); i++) {
            TextViewParams param = new TextViewParams();
            if (tv.getTag().toString().equals(listTvParams.get(i).getTag())) {
                param.setRotation(rotation);
                param.setTextSize((float) (tv.getTextSize() / scaleTimes));
                param.setMidPoint(getViewMidPoint(tv));
                param.setScale(scale);
                textSize = tv.getTextSize() / 2;
                param.setWidth(tv.getWidth());
                param.setHeight(tv.getHeight());
                param.setX(tv.getX());
                param.setY(tv.getY());
                param.setTag(listTvParams.get(i).getTag());
                param.setContent(tv.getText().toString());
                param.setTextColor(tv.getCurrentTextColor());
                listTvParams.set(i, param);
                return;
            }
        }
    }

    /**
     * //对状态进行保存操作
     *
     * @param textView
     * @return
     */
    private void saveTextViewparams(TextView textView) {
        if (textView != null) {
            tvParams = new TextViewParams();
            tvParams.setRotation(0);
            tvParams.setTextSize((float) (textView.getTextSize() / scaleTimes));
            tvParams.setX(textView.getX());
            tvParams.setY(textView.getY());
            tvParams.setWidth(textView.getWidth());
            tvParams.setHeight(textView.getHeight());
            tvParams.setContent(textView.getText().toString());
            tvParams.setMidPoint(getViewMidPoint(textView));
            tvParams.setScale(1);
            tvParams.setTag(String.valueOf((long) textView.getTag()));
            tvParams.setRotation(mAngle);
            tvParams.setTextColor(textView.getCurrentTextColor());
            listTvParams.add(tvParams);
        }
    }

    /**
     * 根据TextView获取到该TextView的配置文件
     *
     * @param tv
     * @return
     */
    private TextViewParams getTextViewParams(TextView tv) {
        for (int i = 0; i < listTvParams.size(); i++) {
            if (listTvParams.get(i).getTag().equals(String.valueOf((long) tv.getTag()))) {
                return listTvParams.get(i);
            }
        }
        return null;
    }

    //返回所有的TextView的参数
    public List<TextViewParams> getListTvParams() {
        List<TextViewParams> newImageList = new ArrayList<>();
        newImageList.addAll(listTvParams);
        return newImageList;
    }

    public void setListTvParams(List<TextViewParams> listTvParams) {
        List<TextViewParams> tempList = new ArrayList<>();
        tempList.addAll(listTvParams);
        this.listTvParams = tempList;
    }


    /**
     * 对控件重新赋值 防止出现错乱
     *
     * @param para
     */
    private void setTextViewParams(TextViewParams para) {
        scale = para.getScale();
        textSize = para.getTextSize();
        mAngle = para.getRotation();
        defaultAngle = mAngle;
        Log.d("HHH", "defaultAngle " + defaultAngle);
    }

    /**
     * 获取中间点
     *
     * @param p1
     * @param p2
     * @return 返回两个点连线的中点
     */
    private Point getMidPiont(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    /**
     * 获取中间点
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private Point getMidPiont(int x1, int y1, int x2, int y2) {
        return new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    /**
     * 该方法用于回一个View的中点坐标
     * 如果该View不存在则返回(0,0)
     *
     * @param view
     * @return
     */
    private Point getViewMidPoint(View view) {
        Point point = new Point();
        if (view != null) {
            float xx = view.getX();
            float yy = view.getY();
            int center_x = (int) (xx + view.getWidth() / 2);
            int center_y = (int) (yy + view.getHeight() / 2);
            point.set(center_x, center_y);
        } else {
            point.set(0, 0);
        }
        return point;
    }

    /**
     * 该方法用于判断某一个点是否某一个范围中
     *
     * @param width
     * @param height
     * @param startX
     * @param startY
     * @param point
     * @return
     */
    private boolean ifIsOnView(int width, int height, int startX, int startY, Point point) {
        return (point.x < (width + startX) && point.x > startX && point.y < (startY + height) && point.y > startY) ? true : false;
    }

    /**
     * 该方法用于判断某一个点是否在View上
     *
     * @param view
     * @param point
     * @return
     */
    private boolean ifIsOnView(View view, Point point) {
        int w = view.getWidth();
        int h = view.getHeight();
        float x = view.getX();
        float y = view.getY();
        return (point.x < (w + x) && point.x > x && point.y < (y + h) && point.y > y) ? true : false;
    }

    /**
     * 计算刚开始触摸的两个点构成的直线和滑动过程中两个点构成直线的角度
     *
     * @param fX  初始点一号x坐标
     * @param fY  初始点一号y坐标
     * @param sX  初始点二号x坐标
     * @param sY  初始点二号y坐标
     * @param nfX 终点一号x坐标
     * @param nfY 终点一号y坐标
     * @param nsX 终点二号x坐标
     * @param nsY 终点二号y坐标
     * @return 构成的角度值
     */
    private float angleBetweenLines(float fX, float fY, float sX, float sY, float nfX, float nfY, float nsX, float nsY) {
        float angle1 = (float) Math.atan2((fY - sY), (fX - sX));
        float angle2 = (float) Math.atan2((nfY - nsY), (nfX - nsX));
        float angle = ((float) Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return -angle;
    }

    //缩放实现
    private void zoom(float f) {
        textView.setTextSize(textSize *= f);
//        textView.setWidth(1000);
//        textView.setHeight(1000);
    }

    //Group缩放实现
    boolean TextScaleFlag = false;
    boolean scaleBig = false;
    boolean viewGroupScaleFlag = true;//限制文字缩放，还是父容器缩放
    //    float startScale;
    float translationX;
    float translationY;
    float startScale;

    private void zoomGroup(float f) {
        startScale = getScaleY();
        translationX = getTranslationX();
        translationY = getTranslationY();
//        translationX = getTranslationX();
//        int width = getWidth();
        float x = sX;
        float y = sY;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleY", startScale, startScale * (f + 1));
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", startScale, startScale * (f + 1));
        ObjectAnimator moveInX = ObjectAnimator.ofFloat(this, "translationX", getTranslationX(), getWidth() / 2 - x - 20);
        ObjectAnimator moveInY = ObjectAnimator.ofFloat(this, "translationY", getTranslationY(), getHeight() / 2 - y);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animatorX).with(moveInX).with(moveInY);
        animSet.setDuration(500);
        animSet.start();
        zoom_flag = true;
    }

    boolean fff = false;

    private void zoomGroupNo(float f) {
//        fff = true;
        float startScale = getScaleY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleY", startScale, 1);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", startScale, 1);
        ObjectAnimator moveInX = ObjectAnimator.ofFloat(this, "translationX", getTranslationX(), 0);
        ObjectAnimator moveInY = ObjectAnimator.ofFloat(this, "translationY", getTranslationY(), 0);

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animator).with(animatorX).with(moveInX).with(moveInY);
        animSet.setDuration(500);
        animSet.start();
        zoom_flag = false;
        //计算当前textView的位置和大小
//        width = textView.getWidth();
//        height = textView.getHeight();
//        startX = textView.getX();
//        startY = textView.getY();
    }

    //切换缩放模式
    public boolean setScaleFlag() {
        if (TextScaleFlag && !viewGroupScaleFlag) {
            viewGroupScaleFlag = true;
            TextScaleFlag = false;
//            ToastUtlis.getInstance().showToast("此为画板缩放模式");
        } else {
            viewGroupScaleFlag = false;
            TextScaleFlag = true;
//            ToastUtlis.getInstance().showToast("此为文字缩放模式");
        }
        return viewGroupScaleFlag;
    }

    //选择画笔颜色
    public void choicePenColor() {
        showDialogForPainCorlor();
    }

    //橡皮擦
    int lost_color = Color.parseColor("#47abff");
    boolean toSave = false;

    public void eraser() {
//        if (mPaint.getColor() != Color.WHITE) {
//            mPaint.setColor(Color.WHITE);
//            mPaint.setStrokeWidth(10);
//            toSave = true;
//        } else {
//            mPaint.setColor(lost_color);
//            mPaint.setStrokeWidth(3);
//            toSave = false;
//        }
    }

    /**
     * 显示自定义对话框（选择画笔颜色）
     */
    private void showDialogForPainCorlor() {
        final int[] colors = new int[1];
        colors[0] = Color.RED;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_pain_corlor_dialog, null);
        final TextView editText = (TextView) view.findViewById(R.id.dialog_text);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView ok = (TextView) view.findViewById(R.id.tv_ok);
        //变色条

        builder.setView(view);
        builder.setCancelable(true);
//        editText.setText(message);
        final AlertDialog dialog = builder.show();
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaint.setColor(colors[0]);
                lost_color = mPaint.getColor();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 计算两点之间的距离
     *
     * @param event
     * @return 两点之间的距离
     */
    private float spacing(MotionEvent event, int ID1, int ID2) {
        float x = event.getX(ID1) - event.getX(ID2);
        float y = event.getY(ID1) - event.getY(ID2);
//        Float.parseFloat()
        return x * x + y * y;
    }

    /**
     * 求两个一直点的距离
     *
     * @param p1
     * @param p2
     * @return
     */
    private double spacing(Point p1, Point p2) {
        double x = p1.x - p2.x;
        double y = p1.y - p2.y;
        return Math.sqrt((x * x + y * y));
    }

    /**
     * 返回两个点之间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private double spacing(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt((x * x + y * y));
    }


    /**
     * Created by cretin on 15/12/21
     * 用于记录每个TextView的状态
     */
    public class TextViewParams {
        private String tag;
        private float textSize;
        private Point midPoint;
        private float rotation;
        private float scale;
        private String content;
        private int width;
        private int height;
        private float x;
        private float y;
        private int textColor;

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        @Override
        public String toString() {
            return "TextViewParams{" +
                    "tag='" + tag + '\'' +
                    ", textSize=" + textSize +
                    ", midPoint=" + midPoint +
                    ", rotation=" + rotation +
                    ", scale=" + scale +
                    ", content='" + content + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", x=" + x +
                    ", y=" + y +
                    ", textColor=" + textColor +
                    '}';
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public float getTextSize() {
            return textSize;
        }

        public void setTextSize(float textSize) {
            this.textSize = textSize;
        }

        public Point getMidPoint() {
            return midPoint;
        }

        public void setMidPoint(Point midPoint) {
            this.midPoint = midPoint;
        }

        public float getRotation() {
            return rotation;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        public float getScale() {
            return scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public interface MyRelativeTouchCallBack {
        void touchMoveCallBack(int direction);

        void onTextViewMoving(TextView textView);

        void onTextViewMovingDone();

        void onOderViewMoving(RelativeLayout relativeLayout);

        void onOderViewMovingDone();

    }

}
