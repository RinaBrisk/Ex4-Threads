package com.example.rina.ex4_threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        new Thread(new LeftLeg()).start();
        new Thread(new RightLeg()).start();
    }

    private class LeftLeg implements Runnable {
        private boolean isRunning = true;
        @Override
        public void run() {
            while (isRunning) System.out.println("Left step");
        }
    }

    private class RightLeg implements Runnable {
        private boolean isRunning = true;
        @Override
        public void run() {
            while (isRunning) System.out.println("Right step");
        }
    }

}
