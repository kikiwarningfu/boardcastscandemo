package com.seuic.boardcastscandemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.seuic.boardcastscandemo.dialog.LoadingDialog;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class OkGoUtils {

    private static LoadingDialog loadingDialog = null;
    private static boolean isClickCancel = false;


    /**
     * 不带加载动画的普通的post请求
     *@param url                     请求的网络地址
     * @param onNormalRequestListener 请求的回调
     */
    public static void historyRequest(final Context context, String url, String json, String token,final onNormalRequestListener onNormalRequestListener) {
        System.out.println("dhshdio======="+json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        builder.readTimeout(120000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(120000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(120000, TimeUnit.MILLISECONDS);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        OkHttpClient okHttpClient = builder.build();

        OkGo.<String>post(url).
                tag(url).client(okHttpClient).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismisDialog();
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    /**
     * 不带加载动画的普通的post请求
     *@param url                     请求的网络地址
     * @param onNormalRequestListener 请求的回调
     */
    public static void normalRequest( String url, String json, String token,final onNormalRequestListener onNormalRequestListener) {
        System.out.println("dhshdio======="+json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    /**
     * 不带加载动画的普通的post请求
     *@param url                     请求的网络地址
     * @param onNormalRequestListener 请求的回调
     */
    public static void normalRequest(final Context context, String url, String json, String token,final onNormalRequestListener onNormalRequestListener) {
        System.out.println("dhshdio======="+json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismisDialog();
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    public static void PostRequest( final  String url, final Context context, String json, final onNormalRequestListener onNormalRequestListener) {
        System.out.println("dhshdio======="+json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismisDialog();
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }
    public static void CallRequest(String url, HttpParams params, String token, final onNormalRequestListener onNormalRequestListener) {

        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }
    public static void NormalPostRequest(String url, HttpParams params, String token, final onNormalRequestListener onNormalRequestListener) {

        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }
    public  static void NormalGetRequest(String url, HttpParams params, final onNormalRequestListener onNormalRequestListener){
        OkGo.<String>get(url). tag(url).
                retryCount(3).//超时重连次数
                cacheTime(5000).//缓存时间
                params(params).
                cacheMode(CacheMode.DEFAULT)
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }
    public  static void GetRequest(final Context context, String url, String token, HttpParams params, final onNormalRequestListener onNormalRequestListener){
        OkGo.<String>get(url). tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params).
                cacheMode(CacheMode.DEFAULT)
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    public  static void GetRequest(String url, String token,HttpParams params, final onNormalRequestListener onNormalRequestListener){
        OkGo.<String>get(url). tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params).
                cacheMode(CacheMode.DEFAULT)
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }
    public static void GetParamsRequest(final Context context, final String url, final String token, HttpParams params, final onNormalRequestListener onNormalRequestListener) {

        OkGo.<String>get(url). tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });
    }

    public static void GetParamsRequest(final String url, final String token, HttpParams params, final onNormalRequestListener onNormalRequestListener) {

        OkGo.<String>get(url). tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });
    }

    /**
     * 带加载动画的普通的post请求
     *
     * @param url                     请求的网络地址
     * @param context                 当前Avtivity
     * @param params                  请求的参数
     * @param onNormalRequestListener 请求的回调
     */
    public static void progressRequest(final String url, final Context context, HttpParams params, final onNormalRequestListener onNormalRequestListener) {
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                cacheTime(50000).//缓存时间
                params(params).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    /**
     * 下载文件请求
     *
     * @param url                    链接地址
     * @param tag                    tag，当前Activity
     * @param destFileDir            下载文件的存储路径
     * @param destFileName           下载文件的存储名称
     * @param onDownloadFileListener 下载任务的回调
     */
    public static void downloadFile(String url, Activity tag, String destFileDir, String destFileName, final onDownloadFileListener onDownloadFileListener) {
        OkGo.<File>get(url).tag(tag).
                execute(new FileCallback(destFileDir, destFileName) {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        onDownloadFileListener.onStart();
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        onDownloadFileListener.onSuccess(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        onDownloadFileListener.onDownloadProgress(progress);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        onDownloadFileListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        onDownloadFileListener.onFinish();
                    }
                });
    }

    /**
     * 上传单个文件的请求
     *
     * @param url                  链接地址
     * @param tag                  tag，当前Activity
     * @param params               参数，可以为null
     * @param fileKey              文件对应的key
     * @param file                 要上传的文件
     * @param onUploadFileListener 上传文件的回调
     */
    public static void upLoadFile(final String url, final Activity tag, HttpParams params, String fileKey, File file, final onUploadFileListener onUploadFileListener) {
        PostRequest<String> postRequest = OkGo.<String>post(url).tag(tag);
        //判断如果params不为空，因为上传文件时可能不会有params
        if (params != null) {
            postRequest.params(params);
        }
        postRequest.params(fileKey, file).
                execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(tag, url);
                        onUploadFileListener.onStart();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        onUploadFileListener.onUploadProgress(progress);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onUploadFileListener.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        onUploadFileListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                        onUploadFileListener.onFinish();
                    }
                });
    }

    /**
     * 上传多个文件的请求
     *
     * @param url                  链接地址
     * @param tag                  tag，当前Activity
     * @param params               参数，可以为null
     * @param filesKey             文件对应的key
     * @param fileList             要上传的文件集合
     * @param onUploadFileListener 上传文件的回调
     */
    public static void upLoadFiles(final String url, final Activity tag, HttpParams params, String filesKey, List<File> fileList, final onUploadFileListener onUploadFileListener) {
        PostRequest<String> postRequest = OkGo.<String>post(url).tag(tag);
        //判断如果params不为空，因为上传文件时可能不会有params
        if (params != null) {
            postRequest.params(params);
        }
        postRequest.addFileParams(filesKey, fileList).
                execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(tag, url);
                        onUploadFileListener.onStart();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        onUploadFileListener.onUploadProgress(progress);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onUploadFileListener.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        onUploadFileListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                        onUploadFileListener.onFinish();
                    }
                });
    }

    /**
     * 初始化加载过程dialog
     */
    private static void initDialog(final Context context, final String url) {
        if ( null != loadingDialog && loadingDialog.isShowing()) {
            isClickCancel = true;
            loadingDialog.dismiss();
            loadingDialog.cancel();
            loadingDialog = null;
        }
        loadingDialog = new LoadingDialog(context);
        loadingDialog.show();

        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = 255;
        attributes.height =255;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(attributes);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //去掉背景色（一些设备上由于系统主题原因会有背景边框）

        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //取消全局默认的OkHttpClient中标识为tag的请求
                if(!isClickCancel) {
                    OkGo.getInstance().cancelTag(url);
                }else{
                    isClickCancel = false;
                }
            }
        });
    }

    /**
     * 取消dialog
     */
    private static void dismisDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }


    public  static void GetRequest(String url, final onNormalRequestListener onNormalRequestListener){
//        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, json);
//        OkGo.<String>post(url).
//                tag(url).
//                retryCount(3).//超时重连次数
//                cacheTime(5000).//缓存时间
//                upRequestBody(body)
        OkGo.<String>get(url). tag(url).
                retryCount(3).//超时重连次数
//                headers("Authorization","Bearer " + token).
        cacheTime(5000).//缓存时间
                cacheMode(CacheMode.DEFAULT)
                .execute(new Callback<String>() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    public static void PostRequest( final  String url,String json, final onNormalRequestListener onNormalRequestListener) {
        System.out.println("dhshdio======="+json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }

    public static void RequestToken(String url, String json,String token, final onNormalRequestListener onNormalRequestListener) {

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        OkGo.<String>post(url).
                tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }


    public static void PutRequest( final  String url,String json,String token, final onNormalRequestListener onNormalRequestListener) {
        System.out.println("dhshdio======="+json);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkGo.<String>put(url).
                tag(url).
                headers("Authorization",token).
                retryCount(3).//超时重连次数
                cacheTime(5000).//缓存时间
                upRequestBody(body).
                execute(new Callback<String>() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        /*JSONObject jsonObject = JSONObject.parseObject(response.body());
                        switch (jsonObject.getInteger("status")) {
                            case -100://请重新登录

                                break;
                            default:
                                break;
                        }*/
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }


    public static void PutRequestH(final Context context, final  String url, HttpParams params, String token, final onNormalRequestListener onNormalRequestListener) {

        OkGo.<String>put(url).
                tag(url).
                retryCount(3).//超时重连次数
                headers("Authorization",token).
                cacheTime(5000).//缓存时间
                params(params).
                execute(new Callback<String>() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        initDialog(context, url);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {
                        dismisDialog();
                        onNormalRequestListener.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        dismisDialog();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }

                    @Override
                    public String convertResponse(okhttp3.Response response) throws Throwable {
                        String result = response.body().string();
                        return result;
                    }
                });

    }
}
