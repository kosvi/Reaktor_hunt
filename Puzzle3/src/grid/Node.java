package grid;

public class Node {

	private int x;
	private int y;
	private char type;
	private int visited;
	// X = Wall and U is unknown, they are not safe to move into
	private static final char[] NON_SAFE_TYPES = new char[] { 'X', 'U' };

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.type = 'U'; // as in unknown
		this.visited = 0;
	}

	public Node(int x, int y, char type) {
		this(x, y);
		this.type = type;
	}

	public void visit() {
		this.visited++;
	}

	public boolean safeForMoving() {
		boolean status = true;
		for (char c : NON_SAFE_TYPES) {
			if (this.type == c) {
				status = false;
			}
		}
		return status;
	}

	@Override
	public String toString() {
		return this.type + " at location: (" + this.x + "," + this.y + ")";
	}

	// Everything under this comment is Eclipse generated code

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getType() {
		return type;
	}

	public int getVisited() {
		return visited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
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
		Node other = (Node) obj;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
