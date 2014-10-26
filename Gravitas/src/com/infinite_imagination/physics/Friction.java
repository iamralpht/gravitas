package com.infinite_imagination.physics;


public class Friction extends Simulation {
	private float mX;
	private float mV;
	private float mDrag;
	private float mDragNaturalLog;
	
	public Friction(float drag) {
		mDrag = drag;
		mDragNaturalLog = (float) Math.log(mDrag);
	}

	public void set(float x, float v) {
		mX = x;
		mV = v;
		mStartTime = now();
	}
	
	public float x(float t) {
		float x = mX + mV * (float) Math.pow(mDrag, t) / mDragNaturalLog - mV / mDragNaturalLog;
		return x;
	}
	public float dx(float t) {
		return mV * (float) Math.pow(mDrag, t);
	}
	public boolean done(float t) {
		return Math.abs(dx(t)) < 1;
	}
}
