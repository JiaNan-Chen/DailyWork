package com.example.administrator.dailywork;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import okhttp3.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("time",System.currentTimeMillis()+"create ms");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.i("time",System.currentTimeMillis()+"runner create ms");
            }
        });
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.i("time",System.currentTimeMillis()+"idle ms");
                return false;
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.tv)).setTextColor(0xffff0000);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((TextView)findViewById(R.id.tv)).setTextColor(0xff000000);
                    }
                });
            }
        },3000);
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("OkHttpClient",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("OkHttpClient",response.body().string());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.i("time",System.currentTimeMillis()+"runner resume ms");
            }
        });
        Log.i("time",System.currentTimeMillis()+"resume ms");
    }
}
