package com.seuic.boardcastscandemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView tv_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_code=findViewById(R.id.tv_code);
        BoardCastSetting();
    }

    private String SCANACTION="com.android.server.aa";//定义广播名
    @Override
    protected void onResume() {
        // 注册广播接收器
        IntentFilter intentFilter=new IntentFilter( SCANACTION );
        intentFilter.setPriority( Integer.MAX_VALUE );
        registerReceiver( scanReceiver,intentFilter );
        super.onResume();
    }


    BroadcastReceiver scanReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCANACTION)) {
                String code = intent.getStringExtra("scannerdata");
                tv_code.setText(code);
            }
        }
    };

    @Override
    protected void onPause() {
        //取消广播注册
        unregisterReceiver(scanReceiver);
        super.onPause();
    }


    //修改扫描工具参数：广播名、条码发送方式、声音、震动等
    private void BoardCastSetting()
    {
        Intent intent = new Intent("com.android.scanner.service_settings");
        //修改广播名称：修改扫描工具广播名，接收广播时也是这个广播名
        intent.putExtra("action_barcode_broadcast",SCANACTION);
        //条码发送方式：广播；其他设置看文档
        intent.putExtra("barcode_send_mode","BROADCAST");
        //键值，一般不改
        intent.putExtra("key_barcode_broadcast","scannerdata");
        //声音
        intent.putExtra("sound_play",true);
        //震动
        intent.putExtra("viberate",true);
        //其他参数设置参照：Android扫描服务设置.doc
        sendBroadcast(intent);
    }

}
