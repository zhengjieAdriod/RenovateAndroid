package com.baiducloud.dawnoct.renovateproject.ZNetService;


import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.PhotoesInfo;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.Post;
import com.baiducloud.dawnoct.renovateproject.ZNetService.bean.RespondedInfo;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Steven Tang on 2017/4/12.
 */

public interface MainApi {
    /*更新token的同步请求*/
    @FormUrlEncoded
    @Headers({"Content-Type: application/json", "Accept:  application/json"})
    @POST(".")
    Call<RespondedInfo> getNewToken(@Field("msg") String msgInfo);

    @FormUrlEncoded
    @POST(".")
    Observable<RespondedInfo> postLogin(@Field("msg") String msgInfo);

    @FormUrlEncoded
    @POST(".")
    Observable<RespondedInfo> getProjects(@Field("msg") String msgInfo);//1\返回项目列表;2\返回两个项目的对比信息

    @FormUrlEncoded
    @POST(".")
    Observable<RespondedInfo> postProject(@Field("msg") String msgInfo);


    @FormUrlEncoded
    @POST(".")
    Observable<String> postCase(@Field("data") String post);

    @FormUrlEncoded
    @POST(".")
    @Headers("content-type': 'application/json")
    Observable<String> postSnippet(@Field("data") String s);

//    /**
//     * 获得post列表
//     */
//    @GET("post_by_page/")
//    Observable<List<Post>> getCases();

    /**
     * 获得post列表(条件分页)
     */
    @GET("post_by_page/")
    Observable<RespondedInfo> getCasesTest(@Query("page") String page);


    /**
     * 表单提交(管家提交的post)
     */
    @Multipart
    @POST("save_post/")
    Observable<RespondedInfo> postSnippetWithImag(@Part("res") RequestBody s, @PartMap Map<String, RequestBody> map);
//    Observable<String> postSnippetWithImag(@Part("res") RequestBody s, @Part MultipartBody.Part photo);
    /**
     * 更新Post ,表单提交(管家提交的post)
     */
    @Multipart
    @POST("update_post/")
    Observable<RespondedInfo> updatePostSnippetWithImag(@Part("res") RequestBody s, @PartMap Map<String, RequestBody> map);
    /**
     * 获得post列表根据管家
     */
    @GET("post_by_worker/")
    Observable<RespondedInfo> getCasesByWorker(@Query("workerId") String workerId);

    /**
     * 获得photos列表根据post_Id
     */
    @GET("photos_in_post/")
    Observable<PhotoesInfo> getPhotosByPostId(@Query("postId") String workerId);
    /**
     * 删除post下的图片
     */
    @GET("delete_photo/")
    Observable<String> deletePhotoOfPost(@Query("photoId") String photoId,@Query("photoType") String photoType);
    /**
     * worker登录
     */
    @GET("login_worker/")
    Observable<RespondedInfo> workerLogin(@Query("telephone") String tele,@Query("password") String password);
    /**
     * worker修改密码
     */
    @GET("new_password_worker/")
    Observable<RespondedInfo> newPasswordWorker(@Query("telephone") String tele,@Query("password") String pass);
}


