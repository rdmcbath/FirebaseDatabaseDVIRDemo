package com.mcbath.rebecca.firebasedatabasedvirtest1;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Rebecca McBath
 * on 2019-08-30.
 */
public class Utils {

	/**
	 * Slide down animation
	 * @param ctx
	 * @param v
	 */
	public static void slide_down(Context ctx, View v){

		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
		if(a != null){
			a.reset();
			if(v != null){
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}

	/**
	 * Slide up animation
	 * @param ctx
	 * @param v
	 */
	public static void slide_up(Context ctx, View v){

		Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
		if(a != null){
			a.reset();
			if(v != null){
				v.clearAnimation();
				v.startAnimation(a);
			}
		}
	}
}
