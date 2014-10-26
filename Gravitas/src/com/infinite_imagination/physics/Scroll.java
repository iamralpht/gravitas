package com.infinite_imagination.physics;

/**
 * Scroll combines Friction and Spring to provide the
 * classic "flick-with-bounce" behavior.
 * @author ralpht
 *
 */
public class Scroll extends Simulation {
	private Friction mFriction;
	private boolean mSpringing = false;
	private Spring mSpring;
	private float mYExtent;
	private float mSpringOffset = 0;
	
	public Scroll(float position, float velocity, float extent) {
		mFriction = new Friction(0.01f);
		mFriction.set(position, velocity);
		mYExtent = extent;
		mSpring = new Spring(1, 90, 20);
		mStartTime = mFriction.mStartTime;
		
		if (position > 0 && velocity >= 0) {
			mSpringing = true;
			mSpring.snapEnd(position);
			mSpring.setEnd(0);
		} else if (position < -extent && velocity <= 0) {
			mSpringing = true;
			mSpring.snapEnd(position);
			mSpring.setEnd(-extent);
		}
	}
	
	@Override public float x(float t) {
		if (mSpringing) {
			float x = mSpring.x() + mSpringOffset;
			return x;
		}
		float x = mFriction.x(t);
		if ((x > 0 && dx(t) >= 0) || (x < -mYExtent && dx(t) <= 0)) {
			// Start springing.
			mSpringing = true;
			mSpring.setVelocity(mFriction.dx(t));
			if (x < -mYExtent) mSpringOffset = -mYExtent;
			x = mSpring.x() + mSpringOffset;
		}
		return x;
	}

	@Override public float dx(float t) {
		return mSpringing? mSpring.dx() : mFriction.dx(t);
	}

	@Override public boolean done(float t) {
		return mSpringing ? mSpring.done() : mFriction.done(t);
	}

}
