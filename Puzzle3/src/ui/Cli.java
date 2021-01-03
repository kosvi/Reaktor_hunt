package ui;

import pathfinder.Step;
import pathfinder.Path;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import grid.TheGrid;
import grid.Node;

public class Cli {

	private Scanner input;
	private String filename;
	private TheGrid grid;
	private Path route;

	public Cli(Scanner input) {
		this.input = input;
		this.grid = new TheGrid();
	}

	public void start() {
		if (!this.getFilename()) {
			return;
		}
		while (true) {
			this.printMenu();
			int selection = 0;
			try {
				selection = Integer.valueOf(input.nextLine());
			} catch (NumberFormatException e) {
				System.err.println(e.getMessage());
			}
			if (selection == 1) {
				this.grid.generateMatrix();
			} else if (selection == 2) {
				this.drawMatrix();
			} else if (selection == 3) {
				this.drawRoute();
			} else if (selection == 4) {
				this.printRoute();
			} else if (selection == 5) {
				break;
			}
		}
		System.out.println("Goodbye!");
	}

	private void findPath() {
		if (this.route == null) {
			System.out.println("Calculating route...");
			this.route = new Path(this.grid.getMatrix());
		}
	}

	private void printRoute() {
		this.findPath();
		System.out.print("Correct route is: \n" + this.route.getRoute());
	}

	// this method is crap and useless,
	// I just wanted to see the route I calculated
	private void drawRoute() {
		this.findPath();
		Node[][] matrix = this.grid.getMatrix();
		char[][] map = new char[matrix.length][matrix[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (matrix[i][j] != null) {
					map[i][j] = matrix[i][j].getType();
				} else {
					map[i][j] = ' ';
				}
			}
		}
		String route = this.route.getRoute();
		Step start = this.route.getStart();
		int x = start.getX();
		int y = start.getY();
		for (int i = 0; i < route.length(); i++) {
			if (route.charAt(i) == 'U') {
				y--;
			} else if (route.charAt(i) == 'D') {
				y++;
			} else if (route.charAt(i) == 'R') {
				x++;
			} else if (route.charAt(i) == 'L') {
				x--;
			}
			map[y][x] = 'r';
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.print("\n");
		}
	}

	private void drawMatrix() {
		Node[][] matrix = this.grid.getMatrix();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != null) {
					System.out.print(matrix[i][j].getType());
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
	}

	private void printMenu() {
		System.out.println("\nUsing file: " + this.filename);
		System.out.println("1) generate matrix");
		System.out.println("2) draw matrix");
		System.out.println("3) draw route");
		System.out.println("4) print route");
		System.out.println("5) exit");
	}

	private boolean getFilename() {
		while (true) {
			System.out.println("Give path to neuraldata (exit to Exit): ");
			System.out.print("> ");
			String filename = input.nextLine();
			if (filename.equalsIgnoreCase("exit")) {
				return false;
			}
			File file = new File(filename);
			// check if file exists, don't check the contents validity
			if (file.exists() && !file.isDirectory()) {
				this.filename = filename;
				try (Scanner fileReader = new Scanner(Paths.get(this.filename))) {
					int i = 0;
					while (fileReader.hasNextLine()) {
						System.out.print("\nReading strand " + ++i + "... ");
						this.grid.insertStrand(fileReader.nextLine());
					}
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
				return true;
			}
		}
	}
}
