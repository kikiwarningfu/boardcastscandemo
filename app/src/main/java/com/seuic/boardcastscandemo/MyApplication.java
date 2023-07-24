package com.seuic.boardcastscandemo;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.PatternFlattener;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.DefaultXmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.DefaultStackTraceFormatter;
import com.elvishew.xlog.formatter.thread.DefaultThreadFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy;
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.seuic.boardcastscandemo.base.BaseApplication;


/**
 * author
 * time: 2019/8/21 19:37
 * email:
 */
public class MyApplication extends BaseApplication {
    private static MyApplication myApplication;
    public Dialog dialog = null;
    private static Context sContext;
    private boolean isDebug = true;//全局debug开关

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }


    //    //com.huaqing.air.MyApplication.getInstance().getContext()获取全局context
    public static Context getContext() {
        return sContext;
    }

    public static MyApplication getInstance() {
        if (myApplication != null && myApplication instanceof MyApplication) {
            return myApplication;
        } else {
            myApplication = new MyApplication();
            myApplication.onCreate();
            return myApplication;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        sContext = this.getApplicationContext();
        if (!BuildConfig.DEBUG) {
            setDebug(false);
        }


        initUM();
        initXLog();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "通知消息";
            String channelMaxName = "系统消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            int max = NotificationManager.IMPORTANCE_MAX;//优先级
            createNotificationChannel(CHANNELID, channelName, importance);
            createNotificationChannel(CHANNELMAX, channelMaxName, max);
        }

    }
    private void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(com.elvishew.xlog.BuildConfig.DEBUG ?
                        LogLevel.DEBUG : LogLevel.INFO)                         // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                .tag("Okhttp")                                               // Specify TAG, default: "X-LOG"
                //.t()                                                            // Enable thread info, disabled by default
                .st(1)                           // Enable stack trace info with depth 2, disabled by default
                // .b()                                                            // Enable border, disabled by default
                .jsonFormatter(new DefaultJsonFormatter())                      // Default: DefaultJsonFormatter
                .xmlFormatter(new DefaultXmlFormatter())                        // Default: DefaultXmlFormatter
                .throwableFormatter(new DefaultThrowableFormatter())            // Default: DefaultThrowableFormatter
                .threadFormatter(new DefaultThreadFormatter())                  // Default: DefaultThreadFormatter
                .stackTraceFormatter(new DefaultStackTraceFormatter())          // Default: DefaultStackTraceFormatter
                .build();

        Printer androidPrinter = new AndroidPrinter();                          // Printer that print the log using android.util.Log

        String flatPattern = "{d yy/MM/dd HH:mm:ss} {l}|{t}: {m}";
        Printer filePrinter = new FilePrinter                                   // Printer that print the log to the file system
                .Builder(UserUtil.appLogFolderPath(this))         // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())                 // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(new FileSizeBackupStrategy(
                        1 << 30))                         // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(new FileLastModifiedCleanStrategy(
                        1000 * 60 * 24 * 5))
                .flattener(new PatternFlattener(flatPattern))                   // Default: DefaultFlattener
                .build();

        XLog.init(                                                              // Initialize XLog
                config,                                                         // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter,
                filePrinter);
    }
    private void initUM() {
//        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this,"shardatainfo");
//        UMConfigure.init(this,"5fbc7f5f0fe0ee328538e94f","Umeng",UMConfigure.DEVICE_TYPE_PHONE,"1a8ab217fbe813d6cfe2a651891a4e75");
//        PushAgent pushAgent = PushAgent.getInstance(this);
//        pushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String s) {
//                Log.e("Umeng","deviceToken="+s);
//                if (!TextUtils.isEmpty(s)) {
//                    sharedPreferenceUtil.putValue("deviceToken", s);
//                }
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e("Umeng","注册失败"+s+"==="+s1);
//
//            }
//        });
//
//        pushAgent.setNotificaitonOnForeground(true);

    }



    public static String CHANNELID = "message";
    public static String CHANNELMAX = "system";

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

}
