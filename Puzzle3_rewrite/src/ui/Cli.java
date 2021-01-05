package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import logic.Grid;
import logic.StepList;
import logic.Step;

public class Cli {
	private Scanner reader;
	private Grid grid;
	private StepList steps;
	private String filename;

	public Cli(Scanner reader, Grid grid, StepList steplist) {
		this.reader = reader;
		this.grid = grid;
		this.steps = steplist;
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
			System.out.println("4) generate route from start to finish");
		}
		System.out.println("Q) quit");
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
		char[][] map = this.grid.getGrid();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				System.out.print(map[y][x]);
			}
			System.out.print("\n");
		}
	}
}
