package com.zivame.zivamechallenege.utils;

import android.view.View;
import android.content.Context;
import android.view.animation.Animation;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.view.animation.AccelerateInterpolator;

import com.zivame.zivamechallenege.R;


public class AnimUtils {

	public static void expand(final View v){
		int ANIMATION_DURATION=600;//in milisecond

		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();
		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);

		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? 
						LayoutParams.WRAP_CONTENT : (int)(targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		animation.setDuration(ANIMATION_DURATION);

		//a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(animation);
	}

	public static void collapse(final View v){
		int ANIMATION_DURATION=600;//in milisecond

		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if(interpolatedTime == 1){
					v.setVisibility(View.GONE);
				}else{
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration(ANIMATION_DURATION);
		// a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public static Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	public static Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	public static Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	//4)outToRightAnimation

	public static Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}
	
	public static void slideOutToLeft(View view){
		view.setVisibility(View.GONE);
		view.setAnimation(AnimUtils.outToLeftAnimation());
	}
	
	public static void slideInFromRight(View view){
		view.setVisibility(View.GONE);
		view.setAnimation(AnimUtils.inFromRightAnimation());
		view.setVisibility(View.VISIBLE);
	}
	
	public static void slideOutToRight(View view){
		view.setVisibility(View.GONE);
		view.setAnimation(AnimUtils.outToRightAnimation());
	}
	
	public static void slideInFromLeft(View view){
		view.setVisibility(View.GONE);
		view.setAnimation(AnimUtils.inFromLeftAnimation());
		view.setVisibility(View.VISIBLE);
	}

	public static void slideOutFromTop(Context context, View view){
		Animation topDown = AnimationUtils.loadAnimation(context, R.anim.enter_y);

		view.startAnimation(topDown);
		view.setVisibility(View.VISIBLE);
	}

	public static void slideOutToTop(Context context, View view){
		Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.exit_y);

		view.startAnimation(bottomUp);
		view.setVisibility(View.GONE);
	}


    public static void slideUpFromBottom(Context context, View view){
        Animation bottomUp = AnimationUtils.loadAnimation(context,
				R.anim.slide_up_from_bottom);

        view.startAnimation(bottomUp);
        view.setVisibility(View.VISIBLE);
    }

    public static void slideDownFromTop(Context context, View view){
        Animation bottomUp = AnimationUtils.loadAnimation(context,
				R.anim.slide_down_from_top);

        view.startAnimation(bottomUp);
        view.setVisibility(View.GONE);
    }
}
