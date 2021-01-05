package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import config.Characters;
import logic.Grid;
import logic.Pathfinder;
import logic.Step;

public class Cli {
	private Scanner reader;
	private Grid grid;
	private Pathfinder pathfinder;
	private boolean routeFound;
	private String filename;

	public Cli(Scanner reader, Grid grid, Pathfinder pathfinder) {
		this.reader = reader;
		this.grid = grid;
		this.pathfinder = pathfinder;
		this.routeFound = false;
		this.filename = "";
	}

	public void start() {
		while (true) {
			String input = this.printMenu();
			if (input.equalsIgnoreCase("q")) {
				break;
			} else {
				int selection = 0;
				try {
					selection = Integer.valueOf(input);
				} catch (NumberFormatException e) {
					System.err.println("Incorrect in input!");
				}
				if (selection == 1) {
					this.getFilename();
				} else if (selection == 2 && this.filename.length() > 0) {
					this.generateGrid();
				} else if (selection == 3 && this.grid.getGridGenerated()) {
					this.printGrid();
				} else if (selection == 4 && this.grid.getGridGenerated()) {
					this.findRoute();
				} else if (selection == 5 && this.routeFound) {
					this.drawRoute();
				} else if (selection == 6 && this.routeFound) {
					this.printRoute();
				}
			}
		}
		System.out.println("Goodbye!");
	}

	private String printMenu() {
		System.out.println("1) insert data");
		if (this.filename.length() > 0) {
			System.out.println("2) generate grid from data");
		}
		if (this.grid.getGridGenerated()) {
			System.out.println("3) show grid");
			System.out.println("4) find route from start to finish");
		}
		if (this.routeFound) {
			System.out.println("5) draw route");
			System.out.println("6) print route as commands");
		}
		System.out.println("Q) quit");
		System.out.print("> ");
		return reader.nextLine();
	}

	private boolean getFilename() {
		boolean fileNotFound = true;
		String filename = "";
		while (fileNotFound && !filename.equalsIgnoreCase("exit")) {
			System.out.println("Give a path to data (exit to cancel: ");
			System.out.print("> ");
			filename = reader.nextLine();
			File file = new File(filename);
			if (file.exists() && !file.isDirectory()) {
				// file exists and isn't directory,
				// we are ok with that
				this.filename = filename;
				fileNotFound = false;
			}
		}
		// true if we got the file, false if we didn't
		return !fileNotFound;
	}

	private void generateGrid() {
		try (Scanner fileReader = new Scanner(Paths.get(this.filename))) {
			int i = 0;
			while (fileReader.hasNextLine()) {
				System.out.println("Reading strand " + ++i + "... ");
				if (!this.grid.insertStrand(fileReader.nextLine())) {
					System.err.println("  Error reading strand " + i);
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		this.grid.generateGrid();
	}

	private void printGrid() {
		this.printMap(this.grid.getGrid());
	}

	private void printMap(char[][] map) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				System.out.print(map[y][x]);
			}
			System.out.print("\n");
		}
	}

	private void findRoute() {
		this.routeFound = this.pathfinder.findRoute(grid.getGrid());
	}

	private void drawRoute() {
		char[][] map = this.grid.getGrid();
		String route = this.pathfinder.getRoute();
		Step current = this.pathfinder.getStart();

		int x = current.getX();
		int y = current.getY();
		// -1 because we don't want to overwrite the finish
		for (int i = 0; i < route.length() - 1; i++) {
			char direction = route.charAt(i);
			switch (direction) {
			case Characters.UP:
				y--;
				break;
			case Characters.DOWN:
				y++;
				break;
			case Characters.RIGHT:
				x++;
				break;
			case Characters.LEFT:
				x--;
				break;
			}
			map[y][x] = Characters.ROUTE;
		}
		this.printMap(map);
	}

	private void printRoute() {
		System.out.println("Route is: ");
		System.out.println(this.pathfinder.getRoute());
	}
}
