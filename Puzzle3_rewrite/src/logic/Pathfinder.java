package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import config.Characters;

public class Pathfinder {

	private StepList steps;
	private Step start;
	private Step finish;
	private boolean found;
	private String route;

	public Pathfinder() {
		this.steps = new StepList();
		this.found = false;
		this.route = "";
	}

	public String getRoute() {
		return route;
	}

	public boolean getFound() {
		return found;
	}

	public Step getStart() {
		return start;
	}

	public Step getFinish() {
		return finish;
	}

	public boolean findRoute(char[][] map) {
		// initialize start
		this.start = this.findStart(map);
		if (this.start == null) {
			// we didn't find start!
			return false;
		}
		this.steps.add(this.start);
		int index = 0;
		// and start rolling
		while (!this.found) {
			Step current = this.steps.get(index);
			List<Step> neighbours = this.findNeighbours(current.getX(), current.getY(), current.getDistance() + 1,
					map[0].length, map.length);
			for (Step s : neighbours) {
				// StepList doesn't allow duplicates and in case given a duplicate, it keeps the
				// one with smaller distance
				// but we have to make sure, we don't store walls or unknown space to our steps
				if (map[s.getY()][s.getX()] == Characters.FINISH) {
					// first check if we found finish
					this.found = true;
					this.finish = s;
					this.steps.add(this.finish);
					break;
				}
				if (map[s.getY()][s.getX()] == Characters.ALLOWED_SPACE) {
					// didn't find finish, but a valid move anyways
					this.steps.add(s);
				}
			}
			index++;
		}
		this.makeRoute();
		return this.found;
	}

	// now we have calculated distance for every step towards finish-line, but we
	// still have to choose the steps that actually lead us to finish with the
	// shortest road
	private void makeRoute() {
		StringBuilder route = new StringBuilder();
		// let's start from the finish
		Step current = this.finish;
		while (!current.equals(this.start)) {
			Step nextCurrent = this.findSmallestNeighbour(current);
			char direction = this.getDirection(current, nextCurrent);
			route.append(direction);
			current = nextCurrent;
		}
		// since we started from the finish, we have to reverse the road
		route.reverse();
		this.route = route.toString();
	}

	private Step findSmallestNeighbour(Step current) {
		int x = current.getX();
		int y = current.getY();
		int[][] possibleNeighbours = new int[][] { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
		Step next = null;
		for (int i = 0; i < possibleNeighbours.length; i++) {
			int newX = possibleNeighbours[i][0];
			int newY = possibleNeighbours[i][1];
			if (this.steps.contains(new Step(newX, newY))) {
				// ok, this coordinate is in our steps list
				int index = this.steps.indexOf(new Step(newX, newY));
				if (next == null) {
					// if next is still null -> put this to next
					next = this.steps.get(index);
				} else {
					// else choose smaller from next & current
					next = Collections.min(Arrays.asList(next, this.steps.get(index)));
				}
			}
		}
		return next;
	}

	private char getDirection(Step from, Step to) {
		int fromX = from.getX();
		int fromY = from.getY();
		int toX = to.getX();
		int toY = to.getY();
		char direction;
		// since we are going backwards our UP is DOWN and LEFT is RIGHT etc.
		if (fromX < toX) {
			direction = Characters.LEFT;
		} else if (toX < fromX) {
			direction = Characters.RIGHT;
		} else if (fromY < toY) {
			direction = Characters.UP;
		} else {
			direction = Characters.DOWN;
		}
		return direction;
	}

	private Step findStart(char[][] map) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				if (map[y][x] == Characters.START) {
					return new Step(x, y, 0);
				}
			}
		}
		return null;
	}

	private List<Step> findNeighbours(int x, int y, int distance, int mapWidth, int mapHeight) {
		List<Step> neighbours = new ArrayList<>();
		// let's find possible moves and store them in loopable array
		int[][] neighbourCoords = new int[][] { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
		for (int i = 0; i < neighbourCoords.length; i++) {
			int currentX = neighbourCoords[i][0];
			int currentY = neighbourCoords[i][1];
			// first check that new coords are within the map
			if (currentY >= mapHeight || currentX >= mapWidth) {
				// and skip if we went "off the grid"
				continue;
			} else {
				// if we stayed on the map, add to possible moves for now
				neighbours.add(new Step(currentX, currentY, distance));
			}
		}
		return neighbours;
	}
}
