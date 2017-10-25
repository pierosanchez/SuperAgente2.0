package com.example.ctorres.superagentemovil3.superagente;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ctorres.superagentemovil3.R;
import com.example.ctorres.superagentemovil3.utils.Constante;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent loginIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, Constante.TIEMPO_SPLASH_SUPER_AGENTE);
    }
}
