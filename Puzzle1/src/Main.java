
import java.util.Scanner;
import decoder.Decoder;
import ui.Cli;

public class Main {

	public static void main(String[] args) {
		System.out.println("Reaktor puzzle 1");

		Scanner input = new Scanner(System.in);
		Decoder decoder = new Decoder();
		Cli cli = new Cli(input, decoder);
		cli.start();
		
		System.out.println("Goodbye!");
	}

}
