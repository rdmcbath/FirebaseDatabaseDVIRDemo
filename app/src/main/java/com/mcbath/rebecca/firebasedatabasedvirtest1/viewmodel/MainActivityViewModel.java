package com.mcbath.rebecca.firebasedatabasedvirtest1.viewmodel;
import androidx.lifecycle.ViewModel;

/**
 * Created by Rebecca McBath
 * on 2019-08-28.
 */
public class MainActivityViewModel extends ViewModel {

	private boolean mIsSigningIn;

	public MainActivityViewModel() {
		mIsSigningIn = false;
	}

	public boolean getIsSigningIn() {
		return mIsSigningIn;
	}

	public void setIsSigningIn(boolean mIsSigningIn) {
		this.mIsSigningIn = mIsSigningIn;
	}
}