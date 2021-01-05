import java.util.Scanner;
import ui.Cli;
import logic.StepList;
import logic.Grid;

public class Main {
	public static void main(String[] args) {
		System.out.println("Reaktor Puzzle 3 - rewrite");
		Scanner reader = new Scanner(System.in);
		StepList list = new StepList();
		Grid grid = new Grid();
		Cli cli = new Cli(reader, grid, list);
		cli.start();
	}
}
