
import ui.Cli;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Reaktor Puzzle 3");
		Scanner input = new Scanner(System.in);
		Cli cli = new Cli(input);
		cli.start();
	}
}
