package com.PechPech.pechpechprivate;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        YoYo.with(Techniques.FadeOut)
                .duration(1000)
                .repeat(0)
                .delay(1000)

                .playOn(findViewById(R.id.imageView3));
        YoYo.with(Techniques.FadeOut)
                .duration(1000)
                .delay(1000)
                .repeat(0)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
//
//                        YoYo.with(Techniques.FadeOut)
//                                .duration(1000)
//                                .repeat(0)
//                                .playOn(findViewById(R.id.imageView3));
//                        YoYo.with(Techniques.FadeOut)
//                                .duration(1000)
//                                .repeat(0)
//                                .playOn(findViewById(R.id.textView));


                            Intent intent = new Intent(ActivitySplash.this,ActivityLogin.class);
                            startActivity(intent);
                            finish();


                    }
                })
                .playOn(findViewById(R.id.textView3));

//        Intent intent = new Intent(ActivitySplash.this,ActivityLogin.class);
//        startActivity(intent);
//        finish();
    }
}
