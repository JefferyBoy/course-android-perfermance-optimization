package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class StackTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_test);
    }

    public void clickThreadTest(View view) {
        for (int i = 0; i < 1025; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("start thread: " + Thread.currentThread().getId());
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}