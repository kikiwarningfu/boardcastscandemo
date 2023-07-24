package com.seuic.boardcastscandemo.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * 项目名 MyBase
 * 描述 说明: 尽量不用静态
 */
public abstract class BaseApplication extends Application {
    public static BaseApplication sApplication;
    //若Activity的context 会有内存泄漏 而 Application在整个生命周期不会
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication=this;
        OkGo.getInstance().init(this);
        initOKgo();
    }
    private void initOKgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //初始化OKGo,网络框架
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("okgo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(this).setOkHttpClient(builder.build());
    }
}
