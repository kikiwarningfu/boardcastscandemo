package com.seuic.boardcastscandemo.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.lzy.okgo.OkGo;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.seuic.boardcastscandemo.AppManager;
import com.seuic.boardcastscandemo.MyApplication;
import com.seuic.boardcastscandemo.R;
import com.seuic.boardcastscandemo.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * author    : ysy
 * introduce :
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected MyApplication mineApplication;
    protected String token="";
    private QMUILoadingView loadView;

    private boolean isWindow = true;
    protected Activity context;

    public int currentPage =1;
    public int pageSize = 10;

    public boolean isRefresh = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkGo.getInstance().init(getApplication());
        mineApplication = (MyApplication) getApplication();
//        token=mineApplication.getToken();
        AppManager.getAppManager().addActivity(this);
//        PushAgent.getInstance(this).onAppStart();
        //SOFT_INPUT_ADJUST_RESIZE 屏幕整体上移   SOFT_INPUT_ADJUST_PAN  覆盖屏幕
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//默认不弹出虚拟键盘
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏省去mainifest设置竖屏
        this.context = this;
        setTopBar();
        //加载 动画
        setLoadView();


    }

//    protected abstract int getTopBarLayout();

    protected abstract void initData();

    protected abstract void initViews();


    @Override
    protected void onStart() {
        super.onStart();


    }


    /**
     * 加载loading动画
     */
    private void setLoadView() {
        loadView = new QMUILoadingView(this);
        loadView.setVisibility(View.GONE);
        loadView.setSize(120);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        addContentView(loadView, layoutParams);
    }

    /**
     * 设置沉浸式状态栏
     * @param topbar
     */
    protected void setTopBar(QMUITopBar topbar) {
        topbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteA));
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        //设置状态栏黑色字体和图标，
        //支持4.4以上的MIUI和flyme  以及5.0以上的其他android
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
    protected void setTopBar() {
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        //设置状态栏黑色字体和图标，
        //支持4.4以上的MIUI和flyme  以及5.0以上的其他android
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    /**
     * 设置半透明沉浸
     */
    protected void setStatusBar(int color) {
//        StatusBarUtils.setStatusBarColor(getWindow(), Color.WHITE,true);

    }

    protected void showToast(String message) {
     //   ToastUtils.show(message);
    }

    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showLoading() {
        loadView.setVisibility(View.VISIBLE);
        loadView.start();
    }


    public void hideLoading() {
        loadView.stop();
        loadView.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancel();
        loadView.stop();
        AppManager.getAppManager().finishActivity(this);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void setWindow(boolean isWindow) {
        this.isWindow = isWindow;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isEMUI3_1() {
        if ("EmotionUI_3.1".equals(getEmuiVersion())) {
            return true;
        }
        return false;
    }

    @SuppressLint("PrivateApi")
    private static String getEmuiVersion() {
        Class<?> classType = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            return (String) getMethod.invoke(classType, "ro.build.version.emui");
        } catch (ClassNotFoundException e) {

        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 设置字体大小不随系统改变
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (startActivitySelfCheck(intent)) {
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    private String mActivityJumpTag;
    private long mActivityJumpTime;

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        } else {
            return result;
        }

        if (tag.equals(mActivityJumpTag) && mActivityJumpTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mActivityJumpTime = SystemClock.uptimeMillis();
        return result;
    }
    //add 获取的一个权限
    static boolean isOk = false;
    @SuppressLint("CheckResult")
    protected static boolean requestPermission(Activity activity, String permissions) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.requestEach(new String[]{permissions})
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            isOk = true;
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            isOk = false;
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                            isOk = false;
                        }
                    }
                });
        return isOk;
    }

    @SuppressLint("CheckResult")
    protected static void requestPermission(Activity activity, String[] permissions, final PermissionsResultListener listener) {
        RxPermissions rxPermission = new RxPermissions(activity);
        rxPermission.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            listener.onSuccessful();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            listener.onFailure();
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                          listener.onFailure();
                        }
                    }
                });

    }

    //权限接口
    public interface PermissionsResultListener {
        //成功
        void onSuccessful(int[] grantResults);
        void onSuccessful();
        //失败
        void onFailure();
    }
    private PermissionsResultListener mListener;
    private int mRequestCode;
    private List<String> mListPermissions = new ArrayList<>();

    /**
     * 动态权限申请
     * @param permissions
     * @param requestCode
     * @param listener
     */
    protected void checkPermissions(String[] permissions, int requestCode, PermissionsResultListener listener) {
        //权限不能为空
        if (permissions != null || permissions.length != 0) {
            mListener = listener;
            mRequestCode = requestCode;
            for (int i = 0; i < permissions.length; i++) {
                if (!isHavePermissions(permissions[i])) {
                    mListPermissions.add(permissions[i]);
                }
            }
            //遍历完后申请
            applyPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (grantResults.length > 0) {
                mListener.onSuccessful(grantResults);
            } else {
                mListener.onFailure();
            }
        }
    }
    // 判断是否有写入数据的权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//        //有权限
//    } else {
//        // 获取（请求）权限
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//    }
    //判断权限是否申请
    private boolean isHavePermissions(String permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //申请权限
    private void applyPermissions() {
        if (!mListPermissions.isEmpty()) {
            int size = mListPermissions.size();
            ActivityCompat.requestPermissions(this, mListPermissions.toArray(new String[size]), mRequestCode);
        }
    }
/*使用
*    checkPermissions(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA,}, 300, new PermissionsResultListener() {
        @Override
        public void onSuccessful(int[] grantResults) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "同意权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure() {
            Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    });*/

}