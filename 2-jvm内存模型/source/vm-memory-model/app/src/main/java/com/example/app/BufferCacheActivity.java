package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * buffer和cache内存测试
 */
public class BufferCacheActivity extends AppCompatActivity {

    private FileOutputStream fileOutputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer_cache);
    }

    public void clickCacheTest(View view) {
        try {
            File file = new File(getCacheDir(), "cacheTest.txt");
            if (fileOutputStream == null) {
                fileOutputStream = new FileOutputStream(file, true);
            }
            fileOutputStream.write(new byte[1024 * 1024]);
//            FileDescriptor fileDescriptor = fileOutputStream.getFD();
//            Field descriptorField = fileDescriptor.getClass().getDeclaredField("descriptor");
//            descriptorField.setAccessible(true);
//            int fd = (int) descriptorField.get(fileDescriptor);
//            System.out.println("fd = " + fd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}