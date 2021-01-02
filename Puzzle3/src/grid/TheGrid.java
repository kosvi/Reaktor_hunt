package grid;

import java.util.List;
import java.util.ArrayList;

public class TheGrid {

	// Store movements in an array for easier handling of strands
	private static final char[] MOVEMENTS = new char[] { 'D', 'U', 'R', 'L' };
	// Store special mapmarkers
	private static final char[] SPECIAL_LOCATIONS = new char[] { 'X', 'F', 'S' };
	private Node[][] matrix;
	private int maxX;
	private int maxY;
	// if we have nodes outside our max X or max Y, we keep that knowledge here
	private boolean maxValuesNeedUpdate;
	private List<Node> knownCoordinates;

	public TheGrid() {
		this.knownCoordinates = new ArrayList<>();
		this.maxX = 0;
		this.maxY = 0;
		this.maxValuesNeedUpdate = false;
	}

	public Node[][] getMatrix() {
		return this.matrix;
	}

	public void generateMatrix() {
		// initialize matrix
		this.matrix = new Node[this.maxY + 1][this.maxX + 1];
		// insert nodes from list
		for (Node n : this.knownCoordinates) {
			// for easier writing
			int x = n.getX();
			int y = n.getY();
			if (this.matrix[y][x] == null) {
				// nothing in this coordinate yet, safe to insert
				this.matrix[y][x] = n;
			} else {
				// there was already something!
				// if it's unknown -> safe to update
				if (this.matrix[y][x].getType() == 'U') {
					System.out.println(this.matrix[y][x] + " replaced with " + n);
					this.matrix[y][x] = n;
				} else {
					for (char c : SPECIAL_LOCATIONS) {
						if (n.getType() == c) {
							// our n is special location and needs to be in matrix
							System.out.println(this.matrix[y][x] + " replaced with " + n);
							this.matrix[y][x] = n;
						}
					}
				}
			}
		}
	}

	public void insertStrand(String strand) {
		// I think for now I can expect the data to be valid.
		// After all, this is just quick hacking to save the humanoid!
		String[] strandPieces = strand.split(" ");
		String[] coordinates = strandPieces[0].split(",");
		String[] movements;
		if (strandPieces.length > 1) {
			movements = strandPieces[1].split(",");
		} else {
			movements = new String[] {};
		}
		int x = 0;
		int y = 0;
		try {
			x = Integer.valueOf(coordinates[0]);
			y = Integer.valueOf(coordinates[1]);
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
		// now we know where the strand begins, and it's safe! (O = ok)
		Node current = new Node(x, y, 'O');
		int i = 0;
		do {
			if (!this.knownCoordinates.contains(current)) {
				// current node is not in the List
				this.knownCoordinates.add(current);
				this.checkMatrixUpdateNeeds(current);
			}
			// now let's get next node in the strand
			if (i < movements.length) {
				String nextMovement = "U"; // as in unknown, yeah, hardcoding!!
				// check if we know what happens after this movement.
				// if we don't, let's go with the unknown
				if (i < movements.length - 1) {
					nextMovement = movements[i + 1];
				}
				current = this.moveCurrent(current, movements[i].charAt(0), nextMovement.charAt(0));
			}
			i++;
		} while (i < movements.length);
		System.out.print("added " + i + " nodes");
		if (this.maxValuesNeedUpdate) {
			this.maxValuesNeedUpdate = false;
			System.out.print("\nNew matrix size: " + this.maxX + "x" + this.maxY);
		}
	}

	private Node moveCurrent(Node current, char movement, char nextMovement) {
		int x = current.getX();
		int y = current.getY();
		char type = nextMovement;
		for (char c : MOVEMENTS) {
			if (nextMovement == c) {
				type = 'O';
			}
		}
		switch (movement) {
		case 'D':
			y++;
			break;
		case 'U':
			y--;
			break;
		case 'R':
			x++;
			break;
		case 'L':
			x--;
			break;
		}
		return new Node(x, y, type);
	}

	private void checkMatrixUpdateNeeds(Node n) {
		// if current node is out of our current max X or max Y, let's update our max
		// values
		if (this.maxX < n.getX()) {
			this.maxValuesNeedUpdate = true;
			this.maxX = n.getX();
		}
		if (this.maxY < n.getY()) {
			this.maxValuesNeedUpdate = true;
			this.maxY = n.getY();
		}
	}
}
