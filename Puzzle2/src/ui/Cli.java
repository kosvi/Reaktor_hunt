package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import tools.SignalMapper;

public class Cli {

	private Scanner input;
	private String filename;

	public Cli(Scanner input) {
		this.input = input;
	}

	public void start() {
		while (true) {
			if (!this.getFilename()) {
				break;
			}
			try (Scanner fileReader = new Scanner(Paths.get(this.filename))) {
				if (fileReader.hasNextLine()) {
					SignalMapper sm = new SignalMapper(fileReader.nextLine());
					System.out.println("Signal base value: " + sm.toString());
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("Goodbye!");
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
				return true;
			}
		}
	}
}
