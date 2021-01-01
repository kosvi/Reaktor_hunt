package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import tools.SignalMapper;

public class Cli {

	private Scanner input;
	private SignalMapper signalMapper;
	private String filename;

	public Cli(Scanner input) {
		this.input = input;
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
			if (selection == 3) {
				break;
			} else if (selection == 2) {
				System.out.println(this.signalMapper.getSignalData());
			} else if (selection == 1) {
				this.signalMapper.addCharacterToBase();
			}
		}
		System.out.println("Goodbye!");
	}

	private void printMenu() {
		System.out.println("\nUsing file: " + this.filename);
		System.out.println("Current base value: " + this.signalMapper);
		System.out.println("1) generate next char to base value");
		System.out.println("2) show current signaldata");
		System.out.println("3) exit");
	}

	private boolean getFilename() {
		while (true) {
			System.out.println("Give path to signaldata (exit to Exit): ");
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
					if (fileReader.hasNextLine()) {
						this.signalMapper = new SignalMapper(fileReader.nextLine());
					}
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}

				return true;
			}
		}
	}
}
