package com.seuic.boardcastscandemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.seuic.boardcastscandemo.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivBack;
    private EditText etName;
    private EditText etpassword;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //aaaa
        tvTitle = findViewById(R.id.tiltle);
        ivBack = findViewById(R.id.imv_back);
        tvTitle.setText("PDA码录入系统");
        etName = findViewById(R.id.login_username);
        etpassword = findViewById(R.id.login_password);
        btLogin = findViewById(R.id.login_button);
        btLogin.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                String username = etName.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = etpassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }



                login();
                break;
            case R.id.imv_back:

                finish();

                break;
        }
    }

    private void login() {


        String username = etName.getText().toString();
        String etpasswords = etpassword.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("userAccountNumber", username);

        map.put("password", etpasswords);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        OkGoUtils.PostRequest(ConstantsUtils.Login, this, json, new onNormalRequestListener() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e("+++", response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    if (jsonObject.getInt("code") == 200) {
//                        LoginInfoBean loginInfoBean = new Gson().fromJson(response.body(),
//                                LoginInfoBean.class);
//                        String token = loginInfoBean.getToken();
//
//                        SharedPreferenceUtil sharedPreferenceUtil =
//                                new SharedPreferenceUtil(LoginActivity.this, "shardatainfo");
//                        sharedPreferenceUtil.putValue("islogin", true);
//                        sharedPreferenceUtil.putValue("token", token);
//                        sharedPreferenceUtil.putValue("password", etpassword);
//                        sharedPreferenceUtil.putValue("username", username);
//
//                        getInfo(token);

                    } else if (jsonObject.getInt("code") == 500) {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("msg"),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("msg"),
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                Log.e("+++", "请求失败");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}