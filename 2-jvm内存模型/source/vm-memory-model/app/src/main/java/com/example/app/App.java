package com.example.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jeffery
 * @date 2022/10/28
 */
public class App extends Application {
    private ReferenceQueue<Activity> referenceQueue = new ReferenceQueue<>();
    private Map<Activity, Reference<Activity>> runningActivity = new HashMap<>();
    private BlockingQueue<Reference<Activity>> destroyedActivityQueue = new LinkedBlockingQueue<>();

    private Thread leakMonitor = new Thread(new Runnable() {
        @Override
        public void run() {
            Reference<Activity> ref = null;
            while (true) {
                try {
                    if ((ref = destroyedActivityQueue.take()) == null) {
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(100);
                        Runtime.getRuntime().gc();
                        Activity activity = ref.get();
                        if (activity != null) {
                            System.out.println("Found leak activity: " + activity.getLocalClassName());
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        leakMonitor.start();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                WeakReference<Activity> ref = new WeakReference<>(activity, referenceQueue);
                runningActivity.put(activity, ref);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Reference<Activity> ref = runningActivity.get(activity);
                runningActivity.remove(activity);
                destroyedActivityQueue.add(ref);
            }
        });
    }
}
