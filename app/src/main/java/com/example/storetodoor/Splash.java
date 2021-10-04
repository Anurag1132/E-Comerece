package com.example.storetodoor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

  /*  FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser fuser = firebaseAuth.getCurrentUser();*/

    private static int SPLASH_SCREEN=3000;
    Animation topAnim,bottomAnim;
    ImageView imageView;
    TextView logo,slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);




        //Animations
        topAnim=AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Hooks
        imageView=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView3);
        slogan=findViewById(R.id.textView4);
       /* imageView.startAnimation(animation);
        imageView.animate().scaleX(0.6f).scaleY(0.6f).setDuration(5000);*/

        imageView.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
/*

        Thread timer=new Thread()
        {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    if ( !(fuser == null))
                    {
                        Intent i = new Intent(Splash.this, UserActivity.class);
                        finish();
                        startActivity(i);
                    }



                    Intent i = new Intent(Splash.this, LogInActivity.class);
                    finish();
                    startActivity(i);

                }
            }
        };
        timer.start();
*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              /*  if ( !(fuser == null))
                {
                    Intent i = new Intent(Splash.this, UserActivity.class);
                    finish();
                    startActivity(i);
                }
            */
                Intent intent= new Intent(Splash.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}