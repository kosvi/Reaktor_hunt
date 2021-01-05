package logic;

/* 
 * For a step, we need to store coordinates and distance from finish
 * 
 * After initialization of the instance, we DO NOT want to change X or Y values
 * so, no setters there! 
 * 
 * We only want to compare coordinates to check equality to make sure we dont's
 * store duplicate coordinates to our List. However, we want to compare possible
 * duplicates to find which of them has shorter distance
 */

public class Step implements Comparable<Step> {

	private int x;
	private int y;
	private int distance;

	public Step(int x, int y) {
		this.x = x;
		this.y = y;
		this.distance = -1;
	}

	public Step(int x, int y, int distance) {
		this(x, y);
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Step s) {
		return Integer.compare(this.distance, s.getDistance());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
