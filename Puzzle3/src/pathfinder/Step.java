package pathfinder;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	// for debuggin purposes only
	@Override
	public String toString() {
		return this.x + "," + this.y + " " + this.distance;
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

	@Override
	public int compareTo(Step o) {
		return Integer.compare(this.distance, o.getDistance());
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

}
