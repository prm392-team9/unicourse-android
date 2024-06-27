package com.example.unicourse;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unicourse.ui.activities.ActivitySupportContact;
import com.example.unicourse.ui.activities.ControllerActivity;
import com.example.unicourse.ui.activities.LoginActivity;
import com.example.unicourse.ui.activities.MapsActivity;

public class UnicourseApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_default);

        new CountDownTimer(3000, 1000){


            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //thực hiện khi hoàn thành
                Intent intent = new Intent(UnicourseApplication.this, ControllerActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}