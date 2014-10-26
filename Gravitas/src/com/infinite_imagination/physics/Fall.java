package com.infinite_imagination.physics;

public class Fall extends Simulation {
	private Gravity mGravity;
	private boolean mSpringing = false;
	private Spring mSpring;
	private float mGround;
	
	public Fall(float position, float velocity, float ground) {
		mGravity = new Gravity();
		mGravity.set(position, velocity);
		mGround = ground;
		mSpring = new Spring(1, 180, 20);
		mStartTime = mGravity.mStartTime;
		
		if (position > ground) {
			mSpringing = true;
			mSpring.snapEnd(position);
			mSpring.setEnd(ground);
		}
	}
	
	@Override public float x(float t) {
		if (mSpringing)
			return mSpring.x();
		float x = mGravity.x(t);
		if (x > mGround) {
			mSpringing = true;
			mSpring.snapEnd(mGround);
			mSpring.setVelocity(mGravity.dx(t));
			mSpring.setEnd(mGround);
			x = mSpring.x();
		}
		return x;
	}

	@Override
	public float dx(float t) {
		if (mSpringing) return mSpring.dx();
		return mGravity.dx(t);
	}

	@Override
	public boolean done(float t) {
		return mSpringing ? mSpring.done() : mGravity.done(t);
	}

}
