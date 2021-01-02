package ui;

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
			} else if (selection == 4) {
				break;
			}
		}
		System.out.println("Goodbye!");
	}

	private void printMenu() {
		System.out.println("\nUsing file: " + this.filename);
		System.out.println("1) generate matrix");
		System.out.println("2) draw matrix");
		System.out.println("3) find route");
		System.out.println("4) exit");
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
