package com.da401a.mealplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class LogoActivity extends Activity implements Animation.AnimationListener {

    private ImageView image1;
    private ImageView image2;
    private boolean isFirstImage = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        image1 = (ImageView) findViewById(R.id.ImageView01);
        image2 = (ImageView) findViewById(R.id.ImageView02);
        image2.setVisibility(View.GONE);

//        image1.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                if (isFirstImage) {
//                    applyRotation(0, 90);
//                    isFirstImage = !isFirstImage;
//                } else {
//                    applyRotation(0, -90);
//                    isFirstImage = !isFirstImage;
//                }
//            }
//        });


        //Own thingy ma-jingie
        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;

        FlipAnimation rotation = new FlipAnimation(0, 90, centerX, centerY);
        rotation.setDuration(500);
        //rotation.setStartOffset(1000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(this);

        image1.startAnimation(rotation);

    }

    private void applyRotation(float start, float end) {
        // Find the center of image
        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final FlipAnimation rotation =
                new FlipAnimation(start, end, centerX, centerY);
        rotation.setDuration(500);
        rotation.setStartOffset(1000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, image1, image2));
        //rotation.setAnimationListener(this);

        if (isFirstImage) {
            image1.startAnimation(rotation);
        } else {
            image2.startAnimation(rotation);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //image1.post(new SwapViews(isFirstImage, image1, image2));

        final float centerX = image1.getWidth() / 2.0f;
        final float centerY = image1.getHeight() / 2.0f;
        FlipAnimation rotation;

        image2.setVisibility(View.VISIBLE);
        image1.setVisibility(View.GONE);
        image1.requestFocus();

        rotation = new FlipAnimation(90, 0, centerX, centerY);

        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());

        image2.startAnimation(rotation);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
