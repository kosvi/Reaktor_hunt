package pathfinder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import grid.Node;

public class Path {

	private List<Step> steps;
	private Node[][] grid;
	private Step finish;
	private List<Character> route;

	public Path(Node[][] matrix) {
		this.steps = new ArrayList<>();
		this.route = new ArrayList<>();
		this.grid = matrix;
		this.findStart();
		this.calculateSteps();
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

	public void findRoute() {
		// now we know where we found the finish and we can start there and go down the
		// steps until we hit the first step we added when we found the start from the
		// matrix
		Step current = this.finish;
		for (int i = this.steps.indexOf(this.finish); i >= 0; i--) {
			// ok, let's start from finish
			Step nextCurrent = this.findSmallestAdjacent(current);
			char direction = this.calculateDirection(current, nextCurrent);
			this.route.add(direction);
			if (nextCurrent.getDistance() == 0) {
				// ok, we are in the start
				break;
			}
			current = nextCurrent;
		}
		// now reverse the route (from finisth to start -> from start to finish)
		Collections.reverse(this.route);
	}

	private char calculateDirection(Step from, Step to) {
		// since we are walking backwards, we return U when going down and R when goind
		// left
		return ' ';
	}

	private Step findSmallestAdjacent(Step s) {
		Step smallest = null;
		// think of how to find the smallest number next to s and return it
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
