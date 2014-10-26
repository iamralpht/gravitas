package com.infinite_imagination.physics;

public class Spring extends Simulation {
	/** mass */
	private float m;
	/** spring constant */
	private float k;
	/** damping coefficient */
	private float c;
	
	private float mEndPosition;
	private Solution mSolution;
	
	private interface Solution {
		/**
		 * Return the position of the spring at time t.
		 * @param t	normalized time in seconds.
		 * @return	the position of the spring at time t.
		 */
		public float x(float t);
		/**
		 * Return the velocity of the spring at time t.
		 * @param t	normalized time in seconds.
		 * @return	the velocity of the spring at time t.
		 */
		public float dx(float t);
	}
	
	private Solution solve(float initial, float velocity) {
		// Solve the quadratic equation; root = (-c +/- sqrt(c^2 - 4mk)) / 2m.
		float cmk = c * c - 4 * m * k;
		if (cmk >= 0) {
			// The spring is overdamped or critically damped; no bounces.
			// x = c1*e^(r1*t) + c2*e^(r2t)
			// Need to find r1 and r2, the roots, then solve c1 and c2.
			final float r1 = (-c - (float)Math.sqrt(cmk)) / (2 * m);
			final float r2 = (-c + (float)Math.sqrt(cmk)) / (2 * m);
			final float c2 = (velocity - r1 * initial) / (r2 - r1);
			final float c1 = initial - c2;
			
			return new Solution() {
				@Override public float x(float t) {
					return (float)(c1 * Math.pow(Math.E, r1 * t) + c2 * Math.pow(Math.E, r2 * t));
				}
				@Override public float dx(float t) {
					return (float)(c1 * r1 * Math.pow(Math.E, r1 * t) + c2 * r2 * Math.pow(Math.E, r2 * t));
				}
			};
		} else {
			// The spring is underdamped, it has imaginary roots.
			// r = -(c / 2*m) +- w*i
			// w = sqrt(4mk - c^2) / 2m
			// x = (e^-(c/2m)t) * (c1 * cos(wt) + c2 * sin(wt))
			final float w = (float)Math.sqrt(4*m*k - c*c) / (2 * m);
			final float r = -(c / 2*m);
			final float c1= initial;
			final float c2= (velocity - r * initial) / w;
			
			return new Solution() {
				@Override public float x(float t) {
					return (float)(Math.pow(Math.E, r * t) * (c1 * Math.cos(w * t) + c2 * Math.sin(w * t)));
				}
				@Override public float dx(float t) {
					float power = (float) Math.pow(Math.E, r * t);
					float cos = (float)Math.cos(w * t);
					float sin = (float)Math.sin(w * t);
					return power * (c2 * w * cos - c1 * w * sin) + r * power * (c2 * sin + c1 * cos);
				}
			};
		}
	}
	
	public Spring(float mass, float springConstant, float damping) {
		m = mass;
		k = springConstant;
		c = damping;
	}
	
	public float x(float t) {
		if (mSolution == null) return 0;
		return mEndPosition + mSolution.x(t);
	}
	public float dx(float t) {
		if (mSolution == null) return 0;
		return mSolution.dx(t);
	}
    private static boolean isEqual(float a, float b, float epsilon) {
        return (a > (b - epsilon)) && (a < (b + epsilon));
    }
    private static boolean isZero(float a, float epsilon) {
        return isEqual(a, 0, epsilon);
    }
	public float end() { return mEndPosition; }
	public void setEnd(float x, float t) {
		if (x == mEndPosition) return;
		float velocity = 0;
		float position = mEndPosition;
		if (mSolution != null) {
			velocity = mSolution.dx(t - mStartTime);
			position = mSolution.x(t - mStartTime);
			if (isZero(velocity, epsilon)) velocity = 0;
			if (isZero(position, epsilon)) position = 0;
			position += mEndPosition;
		}
		if (mSolution != null && isZero(position - x, epsilon) && isZero(velocity, epsilon))
			return;
		mEndPosition = x;
		mSolution = solve(position - mEndPosition, velocity);
		mStartTime = t;
	}
	public void setVelocity(float x, float v, float t) {
		// XXX: Prolly bad; needs to consult current position and velocity.
		mSolution = solve(x, v);
		mStartTime = t;
	}
	public void setVelocity(float v) {
		float t = now();
		setVelocity(x(t), v, t);
	}
	public void setEnd(float x) { setEnd(x, now()); }
	public void snapEnd(final float x) {
		mStartTime = now();
		mEndPosition = x;
		mSolution = new Solution() {
			@Override public float x(float t) { return 0; }
			@Override public float dx(float t) { return 0; }
		};
	}
	public boolean done(float t) {
		return isEqual(x(), end(), epsilon) && isEqual(dx(), 0, epsilon);
	}
}
