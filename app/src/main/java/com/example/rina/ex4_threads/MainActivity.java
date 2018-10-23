package com.example.rina.ex4_threads;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity {

    @Nullable
    private static Boolean isLeftStep = true;
    @Nullable
    private static final Object lock = new Object();
    @Nullable
    private Thread leftThread;
    @Nullable
    private Thread rightThread;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
    }

    @Override
    public void onStart() {
        super.onStart();
        leftThread = new Thread(new LeftLeg());
        leftThread.start();

        rightThread = new Thread(new RightLeg());
        rightThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (leftThread != null) {
            leftThread.interrupt();
        }
        leftThread = null;

        if (rightThread != null) {
            rightThread.interrupt();
        }
        rightThread = null;
    }

    private class LeftLeg implements Runnable {
        Boolean isRunning = true;

        @Override
        public void run() {
            while (isRunning) {
                if (Thread.interrupted()) return;
                synchronized (lock) {
                    if (isLeftStep) {
                        isLeftStep = false;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setText("left thread is running");
                            }
                        });
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    private class RightLeg implements Runnable {
        Boolean isRunning = true;

        @Override
        public void run() {
            while (isRunning) {
                if (Thread.interrupted()) return;
                synchronized (lock) {
                    if (!isLeftStep) {
                        isLeftStep = true;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setText("right thread is running");
                            }
                        });
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    synchronized private void setText(String newText) {
        textView.setText(newText);
    }
}
