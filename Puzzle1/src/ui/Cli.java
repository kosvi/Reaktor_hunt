package ui;

import decoder.Decoder;

import java.io.File;
import java.util.Scanner;

public class Cli {

	private Scanner input;
	private Decoder decoder;
	private String filename;

	public Cli(Scanner input, Decoder decoder) {
		this.input = input;
		this.decoder = decoder;
	}

	public void start() {
		System.out.println("");
		if (!this.getFilename()) {
			return;
		}
		while (true) {
			int selection = 0;
			this.printMenu();
			try {
				selection = Integer.valueOf(input.nextLine());
			} catch (Exception e) {
				System.out.println("");
			}
			if (selection == 4) {
				break;
			} else if (selection == 3) {
				if (!this.getFilename()) {
					break;
				}
			} else if (selection == 2) {
				System.out.println("\n" + this.decoder.getDataAsCharacters() + "\n");
			} else if (selection == 1) {
				System.out.println("\n" + this.decoder.getDataAsNumbers() + "\n");
			}
		}
	}

	private void printMenu() {
		System.out.println("");
		System.out.println("current file: " + this.filename);
		System.out.println("1) Get data as numbers ");
		System.out.println("2) Get data as characters ");
		System.out.println("3) Change file");
		System.out.println("4) Exit");
		System.out.print("> ");
	}

	private boolean getFilename() {
		while (true) {
			System.out.println("Enter filename for data (exit to exit): ");
			System.out.print("> ");
			String filename = input.nextLine();
			if (filename.equalsIgnoreCase("exit")) {
				return false;
			}
			File file = new File(filename);
			if (file.exists() && !file.isDirectory()) {
				// we didn't check the file format, but this is not for production
				// so, I guess we are fine with this amount of checks
				this.filename = filename;
				this.decoder.readChannelDataFromFile(filename);
				return true;
			}
		}
	}
}
