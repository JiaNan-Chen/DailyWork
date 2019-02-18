package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import okhttp3.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
//        }
//    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient okHttpClient = new OkHttpClient();
        EventBus.getDefault().register(this);
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("OkHttpClient", e.toString());
//                Message message = new Message();
//                message.obj = "onFailure" + e.toString();
//                handler.sendMessage(message);
                EventBus.getDefault().post("onFailure" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("OkHttpClient", response.body().string());
//                Message message = new Message();
//                message.obj = "onResponse" + response.body().string();
//                handler.sendMessage(message);
                EventBus.getDefault().post("onResponse" + response.body().string());
            }
        });
    }
}
