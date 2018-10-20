package com.example.rina.ex4_threads;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Nullable
    private static Boolean isLeftStep = true;
    @Nullable
    private Thread leftThread;
    @Nullable
    private Thread rightThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        @NonNull
        private Boolean isRunning = true;

        @Override
        public void run() {
            while (isRunning) {
                if (Thread.interrupted()) return;
                synchronized (isLeftStep) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           setText("Left thread is running");
                        }
                    });
                    if (isLeftStep) {
                        System.out.println("Left step");
                        isLeftStep = false;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class RightLeg implements Runnable {
        @NonNull
        private Boolean isRunning = true;

        @Override
        public void run() {
            while (isRunning) {
                if (Thread.interrupted()) return;
                synchronized (isLeftStep) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setText("Right thread is running");
                        }
                    });
                    if (!isLeftStep) {
                        System.out.println("Right step");
                        isLeftStep = true;
                    }
                    //try {
                       // Thread.sleep(1000);
                    //} catch (InterruptedException e) {
                       // e.printStackTrace();
                    //}
                }
            }
        }
    }

    private void setText(String text){
        TextView textView = findViewById(R.id.tv_text);
        textView.setText(text);
    }
}
