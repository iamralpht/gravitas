package com.infinite_imagination.physics;

/**
 * Gravity physics simulation. This actually just simulates
 * Newton's second law, F=ma integrated to x' = x + v*t + 0.5*a*t*t.
 * @author ralpht
 */
public class Gravity extends Simulation {
	float mX;
	float mV;
	float mA;
	
	float mTerminate;
	
	 // Say that 500px = 1m, arbitrarily.
	public static final float ACCELERATION = 9.8f * 500;
	
	public Gravity(float acceleration, float terminate) {
		mA = acceleration;
		mTerminate = terminate;
	}
	public Gravity(float acceleration) {
		this(acceleration, 32000f);
	}
	public Gravity() {
		this(ACCELERATION);
	}
	
	public void set(float x, float v) {
		mX = x;
		mV = v;
		mStartTime = now();
	}
	
	@Override public float x(float t) {
		float x = mX + mV * t + 0.5f * mA * t * t;
		return x;
	}

	@Override public float dx(float t) {
		float v = mV + t * mA;
		return v;
	}

	@Override public boolean done(float t) {
		return Math.abs(x(t)) >= mTerminate;
	}
}
