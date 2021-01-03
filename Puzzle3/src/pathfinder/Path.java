package pathfinder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import grid.Node;

public class Path {

	private List<Step> steps;
	private Node[][] grid;
	private Step start;
	private Step finish;
	private List<Character> route;

	public Path(Node[][] matrix) {
		this.steps = new ArrayList<>();
		this.route = new ArrayList<>();
		this.grid = matrix;
		this.findStart();
		this.calculateSteps();
		this.findRoute();
	}

	public String getRoute() {
		StringBuilder sb = new StringBuilder();
		for (char c : this.route) {
			sb.append(Character.toString(c));
		}
		return sb.toString();
	}
	
	public Step getStart() {
		return this.start;
	}

	private void findStart() {
		boolean found = false;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] == null) {
					continue;
				}
				if (this.grid[i][j].getType() == 'S') {
					this.steps.add(new Step(this.grid[i][j].getX(), this.grid[i][j].getY(), 0));
					this.start = this.steps.get(0);
					found = true;
				}
				if (found) {
					break;
				}
			}
			if (found) {
				break;
			}
		}
	}

	private void findRoute() {
		// now we know where we found the finish and we can start there and go down the
		// steps until we hit the first step we added when we found the start from the
		// matrix
		Step current = this.finish;
		while (current.getDistance() > 0) {
			// ok, let's start from finish
			Step nextCurrent = this.findSmallestAdjacent(current);
			char direction = this.calculateDirection(current, nextCurrent);
			this.route.add(direction);
			current = nextCurrent;
		}
		// now reverse the route (from finisth to start -> from start to finish)
		Collections.reverse(this.route);
	}

	private char calculateDirection(Step from, Step to) {
		// since we are walking backwards, we return U when going down and R when goind
		// left
		char direction = ' ';
		int fromX = from.getX();
		int fromY = from.getY();
		int toX = to.getX();
		int toY = to.getY();
		if (fromX < toX) {
			// we moved right
			direction = 'L';
		} else if (toX < fromX) {
			// we moved left
			direction = 'R';
		} else if (fromY < toY) {
			// we moved down
			direction = 'U';
		} else if (toY < fromY) {
			direction = 'D';
		}
		return direction;
	}

	private Step findSmallestAdjacent(Step s) {
		Step smallest = null;
		int x = s.getX();
		int y = s.getY();
		// list all possible adjacent cells
		List<Step> adjacentCells = new ArrayList<>(
				Arrays.asList(new Step(x + 1, y), new Step(x - 1, y), new Step(x, y + 1), new Step(x, y - 1)));
		for (Step n : adjacentCells) {
			// steps contains cell if it's safe to move into
			if (this.steps.contains(n)) {
				int index = this.steps.indexOf(n);
				// and if it contains it, it has a distance (and hopefully smaller then our
				// current)
				if (smallest == null) {
					smallest = this.steps.get(index);
				} else if (smallest.getDistance() > this.steps.get(index).getDistance()) {
					smallest = this.steps.get(index);
				}
			}
		}
		return smallest;
	}

	private void calculateSteps() {
		int index = 0;
		Step current = this.steps.get(index);
		boolean stillLooking = true;
		while (stillLooking) {
			List<Step> newVisits = this.adjacentCells(current);
			// ok, we visited those cells, now:
			// - check if any of them is the finish-cell
			// - check if they already exist in the this.steps -list
			// - add them to the list
			for (Step s : newVisits) {
				if (this.grid[s.getY()][s.getX()].getType() == 'F') {
					this.finish = s;
					stillLooking = false;
				}
				if (this.steps.contains(s)) {
					int duplicate = this.steps.indexOf(s);
					// https://stackoverflow.com/questions/51265954/min-max-function-with-two-comparable
					this.steps.set(duplicate, Collections.min(Arrays.asList(this.steps.get(duplicate), s)));
				} else {
					this.steps.add(s);
				}
			}
			current = this.steps.get(++index);
		}
	}

	private List<Step> adjacentCells(Step s) {
		// we want to check all 4 cells next to this cell and
		// see if they are available to visit and then if visit them

		// add visited cells here so they can be visited in the next iteration
		List<Step> nowVisited = new ArrayList<>();
		int newDistance = s.getDistance() + 1;
		int x = s.getX();
		int y = s.getY();
		if (this.setDistance(x + 1, y, newDistance)) {
			nowVisited.add(new Step(x + 1, y, newDistance));
		}
		if (this.setDistance(x - 1, y, newDistance)) {
			nowVisited.add(new Step(x - 1, y, newDistance));
		}
		if (this.setDistance(x, y + 1, newDistance)) {
			nowVisited.add(new Step(x, y + 1, newDistance));
		}
		if (this.setDistance(x, y - 1, newDistance)) {
			nowVisited.add(new Step(x, y - 1, newDistance));
		}
		return nowVisited;
	}

	private boolean setDistance(int x, int y, int newDistance) {
		boolean status = false;
		if (this.grid[y][x] == null) {
			return status;
		}
		if (this.grid[y][x].safeForMoving()) {
			// ok, this tile can be moved into
			// set visited to the step number when this tile is visited
			this.grid[y][x].setVisited(Math.min(newDistance, this.grid[y][x].getVisited()));
			status = true;
		}
		return status;
	}
}
