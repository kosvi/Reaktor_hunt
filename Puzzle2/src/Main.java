
import java.util.Scanner;
import ui.Cli;

public class Main {
	public static void main(String[] args) {
		System.out.println("Reaktor puzzle 2");
		Scanner input = new Scanner(System.in);
		Cli cli = new Cli(input);
		cli.start();
	}
}
