package com.baiducloud.dawnoct.renovateproject.ZNetService;

import android.support.annotation.NonNull;

import com.baiducloud.dawnoct.renovateproject.AndroidApplication;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Comment;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.MsgInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Snippet;
import com.baiducloud.dawnoct.renovateproject.Zutils.NetUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 整个网络通信服务的启动控制，必须先调用初始化函数才能正常使用网络通信接口
 */
public class RetrofitService {

    private static final String HEAD_LINE_NEWS = "T1348647909107";

    //设缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/`.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    private static final String LOGIN_HOST = "http://43.254.88.206:9090/bridge_detection_server/action/";
    private static final String TEST_HOST = "http://192.168.1.106:8087/action/";
    //    private static final String RENOVATE_HOST = "http://192.168.1.97:8000/post_by_page/";post_list
//    private static final String RENOVATE_HOST = "http://192.168.1.97:8000/save_post/";
    public static final String RENOVATE_HOST = "http://192.168.1.96:8000/";
    public static final String RENOVATE_HOST_PHPTO = "http://192.168.1.96:8000";


    private static MainApi mainApi;
    // 递增页码
    private static final int INCREASE_PAGE = 10;


    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务
     * 在Application的onCreate()方法中已经被调用
     */
    public static void init() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(AndroidApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cache(cache)
//                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)//加入log拦截器
//                .addInterceptor(sRewriteCacheControlInterceptor)//加入缓存拦截器
//                .addNetworkInterceptor(sRewriteCacheControlInterceptor)//加入缓存拦截器
//                .authenticator(null)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();//build()返回OkHttpClient。上面的方法都返回Builder对象

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//序列化Json的方式
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加对Rxjava的适配
                .baseUrl(RENOVATE_HOST)
                .build();
        mainApi = retrofit.create(MainApi.class);
    }


    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //没有网络时的获得数据的方式：只读缓存数据
            if (!NetUtil.isNetworkAvailable(AndroidApplication.getContext())) {
                //网络不可用时，读缓存
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            request = request.newBuilder()
                    //表明你的浏览器类型。避免出现 HTTP 403 Forbidden
                    .header("User-Agent",
                            "Mozilla/`.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                    .build();

            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(AndroidApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();//读取注解
                //这里的设置的是有网络的缓存时间。
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                //这里的设置的是没有网络的缓存时间，想设置多少就是多少。
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.d("LogTAG", "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /************************************ API *******************************************/
    public static Observable<RespondedInfo> postLogin(MsgInfo msgInfo) {
        return mainApi
                .postLogin(new Gson().toJson(msgInfo))//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<RespondedInfo> getProjects(MsgInfo msgInfo) {
        String str = new Gson().toJson(msgInfo);
        return mainApi
                .getProjects(str)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<RespondedInfo> getCasesTest(int page, String district, String state) {
        String pageStr = String.valueOf(page);
        return mainApi
                .getCasesTest(pageStr, district, state)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> postCase(Post post) {
        String s = new Gson().toJson(post);
        Map map = new HashMap<String, Post>();
        map.put("data", s);
        return mainApi
                .postCase(s)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> postSnippet(Snippet snippet) {
        String s = new Gson().toJson(snippet);
        return mainApi
                .postSnippet(s)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 新增post,并上传图片
     */
    public static Observable<RespondedInfo> postSnippetWith(Post post, Map<String, RequestBody> photo) {
        String s = new Gson().toJson(post);
//        String start_in_size = String.valueOf(start_in_count);
        return mainApi
                .postSnippetWithImag(RequestBody.create(null, s), photo)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 更新post,并上传图片
     */
    public static Observable<RespondedInfo> updatePostSnippetWith(Post post, Map<String, RequestBody> photo) {
        String s = new Gson().toJson(post);
//        String start_in_size = String.valueOf(start_in_count);
        return mainApi
                .updatePostSnippetWithImag(RequestBody.create(null, s), photo)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据管家获得提交的posts
     */
    public static Observable<RespondedInfo> getCasesByWorker(String workerId) {
        return mainApi
                .getCasesByWorker(workerId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据PostId获得图片租
     */
    public static Observable<PhotoesInfo> getPhotosByPostId(String postId) {
        return mainApi
                .getPhotosByPostId(postId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 更新post,并上传图片
     */
    public static Observable<String> deletePhotoOfPost(String photoId, String photoType) {
        return mainApi
                .deletePhotoOfPost(photoId, photoType)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * worker登录
     */
    public static Observable<RespondedInfo> workerLogin(String tele, String password) {
        return mainApi
                .workerLogin(tele, password)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * worker找回密码
     */
    public static Observable<RespondedInfo> newPasswordWorker(String tele, String pass) {
        return mainApi
                .newPasswordWorker(tele, pass)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获得评论
     */
    public static Observable<RespondedInfo> getComments(String postId) {
        return mainApi
                .getCommentsInPost(postId)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 添加评论
     */
    public static Observable<RespondedInfo> addComment(Comment comment) {
        String s = new Gson().toJson(comment);
        return mainApi
                .addComment(s)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 回复评论
     */
    public static Observable<RespondedInfo> addCallBack(Comment comment) {
        String s = new Gson().toJson(comment);
        return mainApi
                .addCallBack(s)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 业主登录
     */
    public static Observable<RespondedInfo> ownerLogin(String telephone, String password) {
        return mainApi
                .ownerLoign(telephone, password)//传递json字符串
//                .delay(3, TimeUnit.SECONDS)//模拟耗时
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获得服务列表
     */
    public static Observable<RespondedInfo> getServices() {
        return mainApi
                .getServices()//传递json字符串
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
