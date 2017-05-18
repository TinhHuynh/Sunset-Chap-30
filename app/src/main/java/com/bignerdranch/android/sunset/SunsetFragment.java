package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

/**
 * Created by TINH HUYNH on 5/18/2017.
 */

public class SunsetFragment extends Fragment {


    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private View mSunShadowView;
    private View mSeaView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean mSunset;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        mSunShadowView = view.findViewById(R.id.shadow_sun);
        mSeaView = view.findViewById(R.id.sea);

        Resources resource = getResources();
        mBlueSkyColor = resource.getColor(R.color.blue_sky);
        mSunsetSkyColor = resource.getColor(R.color.sunset_sky);
        mNightSkyColor = resource.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSunset) {
                    startSunSetAnimation();
                } else {
                    startSunRiseAnimation();
                }
                mSunset = !mSunset;
            }
        });


        return view;
    }

    private void startSunSetAnimation() {
        float sunYEnd = mSkyView.getHeight();
        float shadowSunYEnd = mSeaView.getTop();
        Toast.makeText(getActivity(), mSunShadowView.getTop() + "", Toast.LENGTH_LONG).show();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "translationY", sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator shadowHeightAnimator = ObjectAnimator
                .ofFloat(mSunShadowView, "translationY", -(mSkyView.getHeight() - mSunShadowView.getHeight()))
                .setDuration(5000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = new ObjectAnimator()
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator)
                .with(sunsetSkyAnimator)
                .with(shadowHeightAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();

    }


    private void startSunRiseAnimation() {

        ObjectAnimator sunriseSkyAnimator = new ObjectAnimator()
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(3000);

        sunriseSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "translationY", 0)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator shadowHeightAnimator = ObjectAnimator
                .ofFloat(mSunShadowView, "translationY", 0)
                .setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator daySkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(1500);
        daySkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator)
                .with(sunriseSkyAnimator)
                .with(shadowHeightAnimator)
                .before(daySkyAnimator);
        animatorSet.start();

    }

}
