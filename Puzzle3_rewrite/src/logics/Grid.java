package Logic;

import java.util.ArrayList;
import java.util.List;
import config.Characters;

public class Grid {

	private List<Coordinate> knownCoordinates;
	private boolean gridGenerated;
	private char[][] grid;
	private int maxX;
	private int maxY;

	public Grid() {
		this.knownCoordinates = new ArrayList<>();
		this.gridGenerated = false;
		maxX = 0;
		maxY = 0;
	}

	public char[][] getGrid() {
		return this.grid;
	}

	public boolean getGridGenerated() {
		return this.gridGenerated;
	}

	public void generateGrid() {
		this.grid = new char[maxY][maxX];
		// let's initialize grid
		for (int y = 0; y < this.grid.length; y++) {
			for (int x = 0; x < this.grid[y].length; x++) {
				this.grid[y][x] = Characters.EMPTY_SPACE;
			}
		}
		// and now add our known coordinates to it
		for (Coordinate c : this.knownCoordinates) {
			int x = c.getX();
			int y = c.getY();
			this.grid[y][x] = c.getType();
		}
		this.gridGenerated = true;
	}

	public boolean insertStrand(String strand) {
		String[] strandPieces = strand.split(" ");
		String[] startingCoordinates = strandPieces[0].split(",");
		String[] movements;
		if (strandPieces.length > 1) {
			movements = strandPieces[1].split(",");
		} else {
			movements = new String[] {};
		}
		try {
			this.addCoordinatesToList(Integer.valueOf(startingCoordinates[0]), Integer.valueOf(startingCoordinates[1]),
					movements);
			return true;
		} catch (Exception e) {
			// we could write a log:
			System.err.println("insertStrand failed!");
			return false;
		}
	}

	private void addCoordinatesToList(int x, int y, String[] movements) {
		char type = Characters.ALLOWED_SPACE;
		int i = 0;
		Coordinate current = new Coordinate(x, y, type);
		do {
			if (!this.knownCoordinates.contains(current)) {
				// current not in List -> add
				this.knownCoordinates.add(current);
			} else if (current.getType() == Characters.FINISH || current.getType() == Characters.START) {
				// just in case there are duplicates in the strands:
				// if our current is start or finish, let's replace the old one
				// with the start or finish. Ofcourse, we might replace start with
				// finish here, but for my purposes this should be enough.
				int index = this.knownCoordinates.indexOf(current);
				this.knownCoordinates.set(index, current);
			}
			// if we get unknowns in the map, we have a problem in the code!
			char nextMovement = Characters.UNKNOWN;
			if (i < movements.length - 1) {
				nextMovement = movements[i + 1].charAt(0);
			}
			current = this.calculateNext(current, movements[i].charAt(0), nextMovement);
			// update grid size to fit all coordinates
			this.checkMaxXY(current.getX(), current.getY());
		} while (i < movements.length);
	}

	private Coordinate calculateNext(Coordinate current, char movement, char nextMovement) {
		int x = current.getX();
		int y = current.getY();
		// new coordinate type is taken from the next character in the strand
		char type = nextMovement;
		// if next character is a movement character, let's use 'ALLOWED_SPACE' as
		// coordinate type
		for (char c : Characters.ALLOWED_MOVEMENTS) {
			if (nextMovement == c) {
				type = Characters.ALLOWED_SPACE;
			}
		}
		switch (movement) {
		case Characters.UP:
			y--;
			break;
		case Characters.DOWN:
			y++;
			break;
		case Characters.LEFT:
			x--;
			break;
		case Characters.RIGHT:
			x++;
			break;
		}
		return new Coordinate(x, y, type);
	}

	private void checkMaxXY(int x, int y) {
		this.maxX = Math.max(x, this.maxX);
		this.maxY = Math.max(y, this.maxY);
	}
}
