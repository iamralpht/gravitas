package com.infinite_imagination.physics;

public abstract class Simulation {
	public static float now() {
		return System.currentTimeMillis() / 1000.0f;
	}
	
	protected float mStartTime;
	protected static final float epsilon = 0.0001f;
	
	public abstract float x(float t);
	public abstract float dx(float t);
	public abstract boolean done(float t);
	public float x() { return x(now() - mStartTime); }
	public float dx() { return dx(now() - mStartTime); }
	public boolean done() { return done(now() - mStartTime); }
	
	protected static float timeSinceNow(float t) { return now() - t; }
}
